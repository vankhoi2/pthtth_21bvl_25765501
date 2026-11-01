package bai6;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            // Tạo Registry tại cổng 1099
            Registry registry = LocateRegistry.createRegistry(1099);
            
            // Khởi tạo dịch vụ Ngân hàng
            BankService service = new BankServiceImpl();
            
            // Đăng ký dịch vụ với tên "BankService"
            registry.rebind("BankService", service);
            
            System.out.println("RMI Server (Quản lý tài khoản ngân hàng) đã sẵn sàng...");
        } catch (Exception e) {
            System.err.println("Lỗi Server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}