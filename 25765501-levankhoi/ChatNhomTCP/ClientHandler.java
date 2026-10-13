
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private String clientName = "Khách";
    private volatile ChatRoom currentRoom;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public String getClientName() {
        return clientName;
    }

    public synchronized void sendMessage(String message) {
        if (output != null) {
            output.println(message);
        }
    }

    private void showRooms() {
        sendMessage("\n===== DANH SÁCH PHÒNG CHAT =====");

        for (String room : ChatRoom.getRoomList()) {
            sendMessage(room);
        }

        sendMessage("Nhập /join số_phòng để tham gia.");
        sendMessage("Nhập /exit để thoát chương trình.");
    }

    private void joinRoom(int number) {
        ChatRoom selectedRoom = ChatRoom.getRoom(number);

        if (selectedRoom == null) {
            sendMessage("Phòng không tồn tại. Nhập /rooms để xem lại.");
            return;
        }

        if (currentRoom != null) {
            currentRoom.removeMember(this);
        }

        currentRoom = selectedRoom;
        currentRoom.addMember(this);

        sendMessage("Bạn đã vào " + currentRoom.getName() + ".");
        sendMessage("Nhập /quit để rời phòng.");
    }

    private void leaveRoom() {
        if (currentRoom != null) {
            ChatRoom oldRoom = currentRoom;
            currentRoom = null;
            oldRoom.removeMember(this);
            sendMessage("Bạn đã rời phòng chat.");
        } else {
            sendMessage("Bạn hiện không ở trong phòng nào.");
        }

        showRooms();
    }

    private void processMessage(String message) {
        if (message.equalsIgnoreCase("/rooms")) {
            showRooms();
            return;
        }

        if (message.equalsIgnoreCase("/quit")) {
            leaveRoom();
            return;
        }

        if (message.equalsIgnoreCase("/exit")) {
            sendMessage("Đang ngắt kết nối. Tạm biệt!");
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Lỗi đóng kết nối: " + e.getMessage());
            }
            return;
        }

        if (message.startsWith("/join ")) {
            try {
                int number = Integer.parseInt(
                        message.substring(6).trim());
                joinRoom(number);
            } catch (NumberFormatException e) {
                sendMessage("Cú pháp đúng: /join 4");
            }
            return;
        }

        if (message.startsWith("/")) {
            sendMessage("Lệnh không hợp lệ. Dùng /rooms, /join, /quit hoặc /exit.");
            return;
        }

        if (currentRoom == null) {
            sendMessage("Bạn chưa vào phòng. Nhập /rooms rồi chọn /join số_phòng.");
            return;
        }

        currentRoom.broadcast(
                "[" + currentRoom.getName() + "] "
                        + clientName + ": " + message,
                this);

        sendMessage("[Bạn đã gửi] " + message);
    }

    @Override
    public void run() {
        try {
            input = new BufferedReader(new InputStreamReader(
                    socket.getInputStream(), StandardCharsets.UTF_8));

            output = new PrintWriter(
                    socket.getOutputStream(), true,
                    StandardCharsets.UTF_8);

            sendMessage("===== CHAT NHÓM TCP =====");
            sendMessage("Vui lòng nhập tên của bạn:");

            String name = input.readLine();

            if (name == null || name.trim().isEmpty()) {
                return;
            }

            clientName = name.trim();
            sendMessage("Xin chào " + clientName + "!");
            showRooms();

            String message;

            while ((message = input.readLine()) != null) {
                processMessage(message);

                if (socket.isClosed()) {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Client ngắt kết nối: " + clientName);
        } finally {
            if (currentRoom != null) {
                ChatRoom oldRoom = currentRoom;
                currentRoom = null;
                oldRoom.removeMember(this);
            }

            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Lỗi đóng socket: " + e.getMessage());
            }

            System.out.println("Đã đóng kết nối: " + clientName);
        }
    }
}
