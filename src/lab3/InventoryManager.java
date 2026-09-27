package lab3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class InventoryManager {
    private static final Path CSV_PATH = Path.of("data", "inventory.csv");
    private static final Path REPORT_PATH = Path.of("data", "inventory-report.txt");

    public static void main(String[] args) {
        // Step 1: Nhập danh sách sản phẩm từ bàn phím
        List<InventoryProduct> inputProducts = readProductsFromConsole();

        if (!inputProducts.isEmpty()) {
            // Step 2: Lưu danh sách vào CSV
            writeToCsv(inputProducts, CSV_PATH);
        }

        // Step 3: Đọc lại tệp CSV
        List<InventoryProduct> loadedProducts = readFromCsv(CSV_PATH);

        if (loadedProducts.isEmpty()) {
            System.out.println("Không có dữ liệu hợp lệ để xử lý.");
            return;
        }

        // Step 4 & 5: Hiển thị, tính tổng và tìm sản phẩm có giá trị tồn kho cao nhất
        System.out.println("\n===== DANH SÁCH SẢN PHẨM TỪ TỆP CSV =====");
        double totalInventoryValue = 0;
        InventoryProduct maxProduct = loadedProducts.get(0);

        for (InventoryProduct p : loadedProducts) {
            System.out.println(p);
            totalInventoryValue += p.getInventoryValue();

            if (p.getInventoryValue() > maxProduct.getInventoryValue()) {
                maxProduct = p;
            }
        }

        System.out.printf("%n=> TỔNG GIÁ TRỊ TỒN KHO: %,.0f VND%n", totalInventoryValue);
        System.out.println("=> SẢN PHẨM CÓ GIÁ TRỊ TỒN KHO CAO NHẤT: " + maxProduct.getName() 
                + " (" + String.format("%,.0f", maxProduct.getInventoryValue()) + " VND)");

        // Step 6: Ghi báo cáo tổng hợp
        writeReport(loadedProducts, totalInventoryValue, maxProduct, REPORT_PATH);
    }

    private static List<InventoryProduct> readProductsFromConsole() {
        List<InventoryProduct> list = new ArrayList<>();
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

        System.out.println("=== NHẬP DANH SÁCH SẢN PHẨM (Nhập 'q' tại Mã SP để kết thúc) ===");
        while (true) {
            try {
                System.out.print("\nNhập mã SP: ");
                String code = consoleReader.readLine();
                if (code == null || code.trim().equalsIgnoreCase("q")) break;

                System.out.print("Nhập tên SP: ");
                String name = consoleReader.readLine();

                System.out.print("Nhập đơn giá: ");
                double unitPrice = Double.parseDouble(consoleReader.readLine());

                System.out.print("Nhập số lượng: ");
                int quantity = Integer.parseInt(consoleReader.readLine());

                InventoryProduct p = new InventoryProduct(code, name, unitPrice, quantity);
                list.add(p);
                System.out.println("-> Thêm sản phẩm thành công!");

            } catch (NumberFormatException e) {
                System.err.println("Lỗi nhập liệu: Đơn giá hoặc số lượng phải là số hợp lệ.");
            } catch (IllegalArgumentException e) {
                System.err.println("Dữ liệu không hợp lệ: " + e.getMessage());
            } catch (IOException e) {
                System.err.println("Lỗi đọc dữ liệu từ bàn phím: " + e.getMessage());
            }
        }
        return list;
    }

    private static void writeToCsv(List<InventoryProduct> products, Path path) {
        try {
            if (path.getParent() != null) Files.createDirectories(path.getParent());
            try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
                writer.write("ma,ten,donGia,soLuong");
                writer.newLine();
                for (InventoryProduct p : products) {
                    writer.write(p.toCsvLine());
                    writer.newLine();
                }
            }
            System.out.println("\n-> Đã lưu danh sách vào " + path);
        } catch (IOException e) {
            System.err.println("Lỗi ghi tệp " + path + ": " + e.getMessage());
        }
    }

    private static List<InventoryProduct> readFromCsv(Path path) {
        List<InventoryProduct> products = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            reader.readLine(); // Bỏ qua tiêu đề
            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.isBlank()) continue;

                String[] parts = line.split(",", -1);
                if (parts.length != 4) {
                    System.err.println("Tệp " + path + " - Dòng " + lineNumber + " bị lỗi: Thiếu hoặc thừa cột.");
                    continue;
                }

                try {
                    products.add(new InventoryProduct(
                        parts[0],
                        parts[1],
                        Double.parseDouble(parts[2].trim()),
                        Integer.parseInt(parts[3].trim())
                    ));
                } catch (NumberFormatException e) {
                    System.err.println("Tệp " + path + " - Dòng " + lineNumber + " lỗi: Dữ liệu số không hợp lệ.");
                } catch (IllegalArgumentException e) {
                    System.err.println("Tệp " + path + " - Dòng " + lineNumber + " lỗi: " + e.getMessage());
                }
            }
        } catch (NoSuchFileException e) {
            System.err.println("Lỗi tệp: Không tìm thấy tệp " + path);
        } catch (IOException e) {
            System.err.println("Lỗi đọc tệp " + path + ": " + e.getMessage());
        }
        return products;
    }

    private static void writeReport(List<InventoryProduct> products, double totalVal, InventoryProduct maxProduct, Path path) {
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer.write("=== BÁO CÁO TỔNG HỢP TỒN KHO ===");
            writer.newLine();
            writer.write("Tổng số sản phẩm: " + products.size());
            writer.newLine();
            writer.write("Tổng giá trị tồn kho: %,.0f VND".formatted(totalVal));
            writer.newLine();
            writer.write("Sản phẩm giá trị tồn cao nhất: %s (%s - %,.0f VND)"
                    .formatted(maxProduct.getName(), maxProduct.getCode(), maxProduct.getInventoryValue()));
            writer.newLine();
            System.out.println("-> Đã ghi báo cáo thành công vào " + path);
        } catch (IOException e) {
            System.err.println("Lỗi ghi báo cáo " + path + ": " + e.getMessage());
        }
    }
}