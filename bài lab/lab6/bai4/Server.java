package bai4;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            // Tạo Registry tại cổng 1099
            Registry registry = LocateRegistry.createRegistry(1099);
            
            // Khởi tạo dịch vụ quản lý danh bạ
            ContactService service = new ContactServiceImpl();
            
            // Đăng ký dịch vụ với tên "ContactService"
            registry.rebind("ContactService", service);
            
            System.out.println("RMI Server (Quản lý danh bạ) đã sẵn sàng...");
        } catch (Exception e) {
            System.err.println("Lỗi Server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}