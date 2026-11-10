package bai3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class DateTimeTcpClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 9999);
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             BufferedWriter writer = new BufferedWriter(
                     new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Đã kết nối TCP Server. Nhập lệnh (DATE, TIME, DATETIME, QUIT):");
            while (true) {
                System.out.print("Client nhập: ");
                String cmd = scanner.nextLine();

                writer.write(cmd + "\n");
                writer.flush();

                String res = reader.readLine();
                if (res == null) {
                    System.out.println("Server đã ngắt kết nối.");
                    break;
                }
                System.out.println("Server trả về: " + res);

                if ("QUIT".equalsIgnoreCase(cmd.trim())) {
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Lỗi kết nối TCP Client (Có thể do Server đã bị dừng đột ngột): " + e.getMessage());
        }
    }
}