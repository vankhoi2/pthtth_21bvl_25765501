
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ChatClient {
    private static final String SERVER_HOST = "127.0.0.1";
    private static final int SERVER_PORT = 5000;

    public static void main(String[] args) {
        try (
            Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream(),
                            StandardCharsets.UTF_8));
            PrintWriter output = new PrintWriter(
                    socket.getOutputStream(), true,
                    StandardCharsets.UTF_8);
            Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Đã kết nối tới server.");

            Thread receiver = new Thread(new ClientReceiver(input));
            receiver.setDaemon(true);
            receiver.start();

            System.out.print("Nhập tên của bạn: ");
            String name = scanner.nextLine().trim();

            while (name.isEmpty()) {
                System.out.print("Tên không được để trống. Nhập lại: ");
                name = scanner.nextLine().trim();
            }

            output.println(name);

            System.out.println("Nhập /rooms để xem phòng.");
            System.out.println("Nhập /join 4 để vào Phòng Dân Cư.");
            System.out.println("Nhập /quit để rời phòng.");
            System.out.println("Nhập /exit để thoát.");

            while (scanner.hasNextLine()) {
                String message = scanner.nextLine().trim();

                if (message.isEmpty()) {
                    continue;
                }

                output.println(message);

                if (message.equalsIgnoreCase("/exit")) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println(
                    "Không thể kết nối tới server. "
                    + "Hãy kiểm tra server đã chạy chưa. Chi tiết: "
                    + e.getMessage());
        }
    }
}
