package bai3;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            // Tạo Registry tại cổng 1099
            Registry registry = LocateRegistry.createRegistry(1099);
            
            // Khởi tạo dịch vụ
            PrimeService service = new PrimeServiceImpl();
            
            // Đăng ký dịch vụ với tên "PrimeService"
            registry.rebind("PrimeService", service);
            
            System.out.println("RMI Server (Kiểm tra số nguyên tố) đã sẵn sàng...");
        } catch (Exception e) {
            System.err.println("Lỗi Server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}