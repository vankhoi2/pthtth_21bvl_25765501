package bai6;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            // Kết nối tới Registry
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            
            // Tìm kiếm dịch vụ từ xa
            BankService service = (BankService) registry.lookup("BankService");
            
            Scanner scanner = new Scanner(System.in);
            int choice = 0;
            
            do {
                System.out.println("\n--- HỆ THỐNG NGÂN HÀNG RMI ---");
                System.out.println("1. Kiểm tra số dư");
                System.out.println("2. Gửi tiền vào tài khoản (Deposit)");
                System.out.println("3. Rút tiền từ tài khoản (Withdraw)");
                System.out.println("4. Thoát");
                System.out.print("Chọn chức năng (1-4): ");
                
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                } else {
                    System.out.println("Vui lòng nhập một số hợp lệ!");
                    scanner.next();
                    continue;
                }
                
                switch (choice) {
                    case 1:
                        double currentBalance = service.getBalance();
                        System.out.println("-> Số dư hiện tại của bạn: " + currentBalance + " VNĐ");
                        break;
                        
                    case 2:
                        System.out.print("Nhập số tiền cần gửi: ");
                        double depAmount = scanner.nextDouble();
                        service.deposit(depAmount);
                        System.out.println("-> Gửi tiền thành công!");
                        break;
                        
                    case 3:
                        System.out.print("Nhập số tiền cần rút: ");
                        double witAmount = scanner.nextDouble();
                        boolean success = service.withdraw(witAmount);
                        if (success) {
                            System.out.println("-> Rút tiền thành công!");
                        } else {
                            System.out.println("-> Rút tiền thất bại! Kiểm tra lại số dư hoặc số tiền nhập.");
                        }
                        break;
                        
                    case 4:
                        System.out.println("Thoát chương trình ứng dụng ngân hàng.");
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