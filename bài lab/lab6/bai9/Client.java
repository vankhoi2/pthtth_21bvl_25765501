package bai9;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Map;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            // Kết nối tới Registry
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            
            // Tìm kiếm dịch vụ từ xa
            AuctionService service = (AuctionService) registry.lookup("AuctionService");
            
            Scanner scanner = new Scanner(System.in);
            int choice = 0;
            
            do {
                System.out.println("\n--- HỆ THỐNG ĐẤU GIÁ TRỰC TUYẾN RMI ---");
                System.out.println("1. Xem danh sách sản phẩm & giá hiện tại");
                System.out.println("2. Thêm sản phẩm đấu giá mới (Seller)");
                System.out.println("3. Đặt giá thầu cho sản phẩm (Bidder)");
                System.out.println("4. Thoát");
                System.out.print("Chọn chức năng (1-4): ");
                
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine(); // Đọc bỏ dòng thừa
                } else {
                    System.out.println("Vui lòng nhập số hợp lệ!");
                    scanner.next();
                    continue;
                }
                
                switch (choice) {
                    case 1:
                        Map<String, Product> list = service.getProducts();
                        System.out.println("\n--- DANH SÁCH SẢN PHẨM ĐẤU GIÁ ---");
                        if (list.isEmpty()) {
                            System.out.println("Hiện không có sản phẩm nào.");
                        } else {
                            for (Product p : list.values()) {
                                System.out.println("- Tên: " + p.getName() 
                                        + " | Giá hiện tại: " + p.getCurrentPrice() 
                                        + " VNĐ | Người giữ giá cao nhất: " + p.getHighestBidder());
                            }
                        }
                        break;
                        
                    case 2:
                        System.out.print("Nhập tên sản phẩm mới: ");
                        String pName = scanner.nextLine();
                        System.out.print("Nhập giá khởi điểm: ");
                        double startPrice = scanner.nextDouble();
                        scanner.nextLine();
                        
                        service.addProduct(pName, startPrice);
                        System.out.println("-> Thêm sản phẩm đấu giá thành công!");
                        break;
                        
                    case 3:
                        System.out.print("Nhập tên của bạn (Bidder): ");
                        String bidderName = scanner.nextLine();
                        System.out.print("Nhập tên sản phẩm muốn đấu giá: ");
                        String targetProduct = scanner.nextLine();
                        System.out.print("Nhập mức giá thầu muốn đưa ra: ");
                        double bidAmount = scanner.nextDouble();
                        scanner.nextLine();
                        
                        boolean success = service.placeBid(targetProduct, bidAmount, bidderName);
                        if (success) {
                            System.out.println("-> Đặt giá thầu thành công! Bạn đang giữ mức giá cao nhất.");
                        } else {
                            System.out.println("-> Đặt giá thầu thất bại! Giá đưa ra thấp hơn giá hiện tại hoặc sai tên sản phẩm.");
                        }
                        break;
                        
                    case 4:
                        System.out.println("Thoát chương trình đấu giá.");
                        break;
                        
                    default:
                        System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn từ 1 đến 4.");
                }
            } while (choice != 4);
            
        } catch (Exception e) {
            System.err.println("Lỗi Client: " + e.getMessage());
            e.printStackTrace();
        }
    }
}