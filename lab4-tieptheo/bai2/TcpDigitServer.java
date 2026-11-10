package bai2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TcpDigitServer {
    public static void main(String[] args) {
        int port = 8888; // Cổng lắng nghe của Server
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("TCP Digit Server đang chạy trên cổng " + port + "...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client đã kết nối từ: " + socket.getRemoteSocketAddress());

                // Xử lý từng client bằng luồng đọc/ghi UTF-8 theo dòng
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                     BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {

                    String inputLine;
                    while ((inputLine = reader.readLine()) != null) {
                        // Cho phép client gửi QUIT để thoát phiên
                        if ("QUIT".equalsIgnoreCase(inputLine.trim())) {
                            writer.write("Tạm biệt!\n");
                            writer.flush();
                            break;
                        }

                        // Xử lý và chuyển đổi chữ số sang tiếng Việt
                        String response = convertDigitToWord(inputLine);
                        writer.write(response + "\n");
                        writer.flush();
                    }
                } catch (Exception e) {
                    System.err.println("Lỗi khi xử lý client: " + e.getMessage());
                } finally {
                    socket.close();
                    System.out.println("Đã ngắt kết nối với client.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String convertDigitToWord(String input) {
        // Kiểm tra dữ liệu rỗng hoặc không phải đúng một ký tự chữ số từ '0' đến '9'
        if (input == null || input.length() != 1) {
            return "ERR INVALID_DIGIT";
        }

        char c = input.charAt(0);
        switch (c) {
            case '0': return "Không";
            case '1': return "Một";
            case '2': return "Hai";
            case '3': return "Ba";
            case '4': return "Bốn";
            case '5': return "Năm";
            case '6': return "Sáu";
            case '7': return "Bảy";
            case '8': return "Tám";
            case '9': return "Chín";
            default: return "ERR INVALID_DIGIT";
        }
    }
}