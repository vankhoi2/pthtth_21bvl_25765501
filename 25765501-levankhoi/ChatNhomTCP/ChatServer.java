
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    private static final int PORT = 5000;

    public static void main(String[] args) {
        ChatRoom.initializeRooms();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("================================");
            System.out.println("       CHAT NHOM TCP SERVER");
            System.out.println("================================");
            System.out.println("Server đang chạy tại cổng " + PORT);
            System.out.println("Đang chờ các client kết nối...");

            while (true) {
                Socket clientSocket = serverSocket.accept();

                System.out.println("Client mới kết nối: "
                        + clientSocket.getRemoteSocketAddress());

                ClientHandler handler =
                        new ClientHandler(clientSocket);

                Thread thread = new Thread(handler);
                thread.start();
            }
        } catch (IOException e) {
            System.err.println("Không thể khởi động server: "
                    + e.getMessage());
        }
    }
}
