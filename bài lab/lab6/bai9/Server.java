package bai9;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            // Tạo Registry tại cổng 1099
            Registry registry = LocateRegistry.createRegistry(1099);
            
            // Khởi tạo dịch vụ Đấu giá trực tuyến
            AuctionService service = new AuctionServiceImpl();
            
            // Đăng ký dịch vụ với tên "AuctionService"
            registry.rebind("AuctionService", service);
            
            System.out.println("RMI Server (Hệ thống đấu giá trực tuyến) đã sẵn sàng...");
        } catch (Exception e) {
            System.err.println("Lỗi Server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}