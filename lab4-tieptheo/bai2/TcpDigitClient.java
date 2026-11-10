package bai2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TcpDigitClient {
    public static void main(String[] args) {
        String serverHost = "127.0.0.1";
        int serverPort = 8888;

        try (Socket socket = new Socket(serverHost, serverPort);
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             BufferedWriter writer = new BufferedWriter(
                     new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Đã kết nối tới Server. Nhập một chữ số (0-9) hoặc 'QUIT' để thoát:");

            while (true) {
                System.out.print("Client nhập: ");
                String input = scanner.nextLine();

                // Gửi thông điệp lên server theo định dạng từng dòng (UTF-8)
                writer.write(input + "\n");
                writer.flush();

                // Đọc phản hồi từ server
                String response = reader.readLine();
                System.out.println("Server trả về: " + response);

                if ("QUIT".equalsIgnoreCase(input.trim()) || response == null) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}