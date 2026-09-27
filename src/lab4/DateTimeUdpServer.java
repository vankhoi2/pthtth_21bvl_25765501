package lab4;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUdpServer {
    private static final int PORT = 5003;
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter DATETIME_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            System.out.println("UDP DateTime Server đang lắng nghe trên port " + PORT);
            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String request = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8).trim();
                String cmd = request.toUpperCase();
                LocalDateTime now = LocalDateTime.now();
                String response;

                if ("DATE".equals(cmd)) {
                    response = "OK " + now.format(DATE_FMT);
                } else if ("TIME".equals(cmd)) {
                    response = "OK " + now.format(TIME_FMT);
                } else if ("DATETIME".equals(cmd)) {
                    response = "OK " + now.format(DATETIME_FMT);
                } else {
                    response = "ERR UNKNOWN_COMMAND";
                }

                byte[] sendData = response.getBytes(StandardCharsets.UTF_8);
                DatagramPacket sendPacket = new DatagramPacket(
                    sendData, sendData.length, packet.getAddress(), packet.getPort());
                socket.send(sendPacket);
            }
        } catch (IOException e) {
            System.err.println("Lỗi UDP Server: " + e.getMessage());
        }
    }
}