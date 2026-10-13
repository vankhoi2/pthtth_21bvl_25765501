package lab3;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class BinaryFileCopy {
    private static final int BUFFER_SIZE = 8192; // Bộ đệm 8KB

    public static void main(String[] args) {
        // Kiểm tra xem người dùng đã truyền đủ 2 tham số dòng lệnh chưa
        if (args.length != 2) {
            System.out.println("Cách dùng: java BinaryFileCopy <nguồn> <đích>");
            return;
        }

        Path source = Path.of(args[0]);
        Path target = Path.of(args[1]);
        long totalBytes = 0;

        // Tự động tạo thư mục đích nếu chưa tồn tại
        try {
            if (target.getParent() != null) {
                Files.createDirectories(target.getParent());
            }

            // Sử dụng BufferedInputStream và BufferedOutputStream để sao chép
            try (InputStream input = new BufferedInputStream(Files.newInputStream(source));
                 OutputStream output = new BufferedOutputStream(Files.newOutputStream(target))) {

                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead;

                while ((bytesRead = input.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                    totalBytes += bytesRead;
                }

                System.out.printf("Sao chép thành công! Đã sao chép %d byte.%n", totalBytes);
            }
        } catch (IOException e) {
            System.err.println("Sao chép thất bại: " + e.getMessage());
        }
    }
}