package bai5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 6666);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            Scanner scanner = new Scanner(System.in);

            // Luồng riêng để lắng nghe tin nhắn từ Server đẩy về (Server Push)
            new Thread(() -> {
                try {
                    String serverMsg;
                    while ((serverMsg = reader.readLine()) != null) {
                        if (serverMsg.equals("SUBMIT_NICKNAME")) {
                            System.out.print("Nhập nickname của bạn: ");
                            String nick = scanner.nextLine();
                            writer.write(nick + "\n");
                            writer.flush();
                        } else if (serverMsg.startsWith("SUBMIT_SUCCESS")) {
                            System.out.println(">>> Đăng nhập thành công! Hãy gõ MSG <nội dung>, USERS hoặc QUIT.");
                        } else if (serverMsg.startsWith("ERR")) {
                            System.out.println("Lỗi từ Server: " + serverMsg);
                            System.out.print("Nhập lại nickname: ");
                            String nick = scanner.nextLine();
                            writer.write(nick + "\n");
                            writer.flush();
                        } else {
                            System.out.println(serverMsg);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Mất kết nối tới Server.");
                }
            }).start();

            // Vòng lặp chính gửi tin nhắn từ bàn phím lên Server
            while (true) {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("QUIT")) {
                    writer.write("QUIT\n");
                    writer.flush();
                    break;
                } else if (input.equalsIgnoreCase("USERS")) {
                    writer.write("USERS\n");
                    writer.flush();
                } else {
                    writer.write("MSG " + input + "\n");
                    writer.flush();
                }
            }

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}