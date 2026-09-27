package lab4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TcpDigitServer {
    private static final int PORT = 5001;
    private static final String[] DIGIT_NAMES = {
        "Không", "Một", "Hai", "Ba", "Bốn", 
        "Năm", "Sáu", "Bảy", "Tám", "Chín"
    };

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("TCP Digit Server đang lắng nghe trên port " + PORT);

            while (true) {
                try (Socket socket = server.accept()) {
                    serve(socket);
                } catch (IOException e) {
                    System.err.println("Lỗi phiên client: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Không mở được server: " + e.getMessage());
        }
    }

    static void serve(Socket socket) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(
                socket.getInputStream(), StandardCharsets.UTF_8));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(
                socket.getOutputStream(), StandardCharsets.UTF_8), true)) {

            String request;
            while ((request = in.readLine()) != null) {
                String response = process(request);
                out.println(response);
                if (request.trim().equalsIgnoreCase("QUIT")) break;
            }
        }
    }

    static String process(String request) {
        // Kiểm tra lệnh QUIT đóng phiên
        if (request.trim().equalsIgnoreCase("QUIT")) {
            return "OK BYE";
        }

        // Kiểm tra đúng chuẩn 1 ký tự và là chữ số từ '0' đến '9'
        if (request.length() == 1 && Character.isDigit(request.charAt(0))) {
            int digit = request.charAt(0) - '0';
            return "OK " + DIGIT_NAMES[digit];
        }

        // Trường hợp chuỗi rỗng, 10, ký tự 'a', chứa khoảng trắng...
        return "ERR INVALID_DIGIT";
    }
}