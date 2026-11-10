package bai5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
    private static final int PORT = 6666;
    // Lưu trữ danh sách client đang hoạt động: <Nickname, BufferedWriter để gửi tin nhắn>
    private static final ConcurrentHashMap<String, BufferedWriter> clients = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Chat Server đang chạy trên cổng " + PORT + "...");

            while (true) {
                Socket socket = serverSocket.accept();
                pool.execute(new ClientHandler(socket));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket socket;
        private String nickname;
        private BufferedWriter writer;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));

                // 1. Yêu cầu client nhập nickname duy nhất
                while (true) {
                    writer.write("SUBMIT_NICKNAME\n");
                    writer.flush();
                    String name = reader.readLine();
                    if (name == null) return;
                    name = name.trim();

                    if (name.isEmpty()) {
                        writer.write("ERR NICKNAME_EMPTY\n");
                        writer.flush();
                        continue;
                    }

                    // Đồng bộ kiểm tra trùng lặp nickname
                    synchronized (clients) {
                        if (clients.containsKey(name)) {
                            writer.write("ERR NICKNAME_TAKEN\n");
                            writer.flush();
                        } else {
                            nickname = name;
                            clients.put(nickname, writer);
                            writer.write("SUBMIT_SUCCESS " + nickname + "\n");
                            writer.flush();
                            break;
                        }
                    }
                }

                System.out.println("[+] " + nickname + " đã tham gia phòng chat.");
                broadcast("[SERVER] " + nickname + " đã vào phòng chat.", "SERVER");

                // 2. Vòng lặp nhận tin nhắn từ client
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if ("QUIT".equalsIgnoreCase(line)) {
                        break;
                    } else if ("USERS".equalsIgnoreCase(line)) {
                        // Gửi danh sách người dùng đang online
                        writer.write("USERS_LIST " + String.join(", ", clients.keySet()) + "\n");
                        writer.flush();
                    } else if (line.startsWith("MSG ")) {
                        String msgContent = line.substring(4);
                        broadcast(nickname + ": " + msgContent, nickname);
                    } else {
                        writer.write("ERR UNKNOWN_COMMAND\n");
                        writer.flush();
                    }
                }

            } catch (Exception e) {
                System.out.println("[-] Client " + (nickname != null ? nickname : "") + " ngắt kết nối bất thường.");
            } finally {
                // 3. Dọn dẹp khi client thoát hoặc mất kết nối bất thường
                if (nickname != null) {
                    clients.remove(nickname);
                    System.out.println("[-] " + nickname + " đã rời phòng.");
                    broadcast("[SERVER] " + nickname + " đã rời phòng chat.", "SERVER");
                }
                try {
                    socket.close();
                } catch (Exception ignored) {}
            }
        }

        // Hàm broadcast tin nhắn tới tất cả client khác
        private void broadcast(String message, String sender) {
            for (Map.Entry<String, BufferedWriter> entry : clients.entrySet()) {
                try {
                    BufferedWriter w = entry.getValue();
                    w.write(message + "\n");
                    w.flush();
                } catch (Exception e) {
                    // Xử lý lỗi nếu gửi thất bại tới một client nào đó
                }
            }
        }
    }
}