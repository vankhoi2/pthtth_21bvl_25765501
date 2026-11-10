
package bai4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class CalcServer {
    public static void main(String[] args) {
        int port = 7777;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Calc Server đang chạy trên cổng " + port + "...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client đã kết nối: " + socket.getRemoteSocketAddress());

                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                     BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {

                    String line;
                    while ((line = reader.readLine()) != null) {
                        String response = processCalculation(line.trim());
                        writer.write(response + "\n");
                        writer.flush();
                    }
                } catch (Exception e) {
                    System.err.println("Lỗi xử lý client: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String processCalculation(String request) {
        if (request == null || request.isEmpty()) {
            return "ERR INVALID_FORMAT";
        }

        String[] parts = request.split("\\s+");
        
        // Kiểm tra lệnh có bắt đầu bằng CALC và đủ 4 thành phần hay không
        if (parts.length != 4 || !"CALC".equalsIgnoreCase(parts[0])) {
            return "ERR INVALID_FORMAT";
        }

        String operator = parts[1];
        double num1, num2;

        // Kiểm tra định dạng số hợp lệ
        try {
            num1 = Double.parseDouble(parts[2]);
            num2 = Double.parseDouble(parts[3]);
        } catch (NumberFormatException e) {
            return "ERR INVALID_NUMBER"; //
        }

        double result;
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
            case "x":
                result = num1 * num2;
                break;
            case "/":
                if (num2 == 0) {
                    return "ERR DIVIDE_BY_ZERO"; //
                }
                result = num1 / num2;
                break;
            default:
                return "ERR UNSUPPORTED_OPERATOR"; //
        }

        // Nếu kết quả là số nguyên thì in dạng số nguyên cho đẹp (ví dụ 300 thay vì 300.0)
        if (result == (long) result) {
            return "OK " + (long) result;
        } else {
            return "OK " + result;
        }
    }
}