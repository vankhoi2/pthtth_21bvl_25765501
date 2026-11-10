package bai4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class CalcClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 7777);
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             BufferedWriter writer = new BufferedWriter(
                     new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Đã kết nối tới Calc Server.");
            System.out.println("Nhập biểu thức theo mẫu (Ví dụ: CALC + 100 200) hoặc QUIT để thoát:");

            while (true) {
                System.out.print("Client nhập: ");
                String input = scanner.nextLine();

                if ("QUIT".equalsIgnoreCase(input.trim())) {
                    break;
                }

                writer.write(input + "\n");
                writer.flush();

                String response = reader.readLine();
                System.out.println("Server trả về: " + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}