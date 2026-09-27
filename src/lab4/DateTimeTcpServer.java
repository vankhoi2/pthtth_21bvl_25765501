package lab4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeTcpServer {
    private static final int PORT = 5002;
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter DATETIME_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("TCP DateTime Server đang lắng nghe trên port " + PORT);

            while (true) {
                try (Socket socket = server.accept()) {
                    serve(socket);
                } catch (IOException e) {
                    System.err.println("Lỗi phiên TCP client: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Không mở được TCP Server: " + e.getMessage());
        }
    }

    private static void serve(Socket socket) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(
                socket.getInputStream(), StandardCharsets.UTF_8));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(
                socket.getOutputStream(), StandardCharsets.UTF_8), true)) {

            String request;
            while ((request = in.readLine()) != null) {
                String cmd = request.trim().toUpperCase();
                LocalDateTime now = LocalDateTime.now();

                if ("DATE".equals(cmd)) {
                    out.println("OK " + now.format(DATE_FMT));
                } else if ("TIME".equals(cmd)) {
                    out.println("OK " + now.format(TIME_FMT));
                } else if ("DATETIME".equals(cmd)) {
                    out.println("OK " + now.format(DATETIME_FMT));
                } else if ("QUIT".equals(cmd)) {
                    out.println("OK BYE");
                    break;
                } else {
                    out.println("ERR UNKNOWN_COMMAND");
                }
            }
        }
    }
}