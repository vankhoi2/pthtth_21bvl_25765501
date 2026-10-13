package lab3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ConsoleReaderDemo {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in, StandardCharsets.UTF_8))) {

            int count = 0;
            System.out.println("Nhập văn bản; nhập q để kết thúc:");

            while (true) {
                String line = reader.readLine();

                if (line == null || line.equalsIgnoreCase("q")) {
                    break;
                }

                count++;
                System.out.printf("Dòng %d: %s%n", count, line);
            }

            System.out.println("Tổng số dòng đã nhập: " + count);

        } catch (IOException e) {
            System.err.println("Không thể đọc dữ liệu: " + e.getMessage());
        }
    }
}