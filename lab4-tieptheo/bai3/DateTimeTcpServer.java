package bai3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeTcpServer {
    public static void main(String[] args) {
        int port = 9999;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("TCP DateTime Server đang chạy trên cổng " + port + "...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client TCP đã kết nối: " + socket.getRemoteSocketAddress());

                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                     BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {

                    String line;
                    while ((line = reader.readLine()) != null) {
                        String cmd = line.trim().toUpperCase();
                        String response;

                        LocalDateTime now = LocalDateTime.now();
                        switch (cmd) {
                            case "DATE":
                                response = now.format(DateTimeFormatter.ofPattern("dd MM yyyy"));
                                break;
                            case "TIME":
                                response = now.format(DateTimeFormatter.ofPattern("HH mm ss"));
                                break;
                            case "DATETIME":
                                response = now.format(DateTimeFormatter.ofPattern("dd MM yyyy HH mm ss"));
                                break;
                            case "QUIT":
                                response = "Tạm biệt!";
                                writer.write(response + "\n");
                                writer.flush();
                                socket.close();
                                return;
                            default:
                                response = "ERR UNKNOWN_COMMAND";
                                break;
                        }

                        writer.write(response + "\n");
                        writer.flush();
                    }
                } catch (Exception e) {
                    System.err.println("Mất kết nối đột ngột với client TCP: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}