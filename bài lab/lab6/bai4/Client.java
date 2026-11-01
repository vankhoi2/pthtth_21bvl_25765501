package bai4;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            // Kết nối tới Registry
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            
            // Tìm kiếm dịch vụ từ xa
            ContactService service = (ContactService) registry.lookup("ContactService");
            
            Scanner scanner = new Scanner(System.in);
            int choice = 0;
            
            do {
                System.out.println("\n--- QUẢN LÝ DANH BẠ RMI ---");
                System.out.println("1. Thêm liên hệ mới");
                System.out.println("2. Tìm kiếm liên hệ");
                System.out.println("3. Xóa liên hệ");
                System.out.println("4. Thoát");
                System.out.print("Chọn chức năng (1-4): ");
                
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine(); // Đọc bỏ dòng thừa
                } else {
                    System.out.println("Vui lòng nhập một số hợp lệ!");
                    scanner.nextLine();
                    continue;
                }
                
                switch (choice) {
                    case 1:
                        System.out.print("Nhập tên liên hệ: ");
                        String addName = scanner.nextLine();
                        System.out.print("Nhập số điện thoại: ");
                        String addPhone = scanner.nextLine();
                        service.addContact(addName, addPhone);
                        System.out.println("-> Thêm liên hệ thành công!");
                        break;
                        
                    case 2:
                        System.out.print("Nhập tên cần tìm: ");
                        String findName = scanner.nextLine();
                        String phoneResult = service.findContact(findName);
                        if (phoneResult != null) {
                            System.out.println("-> Kết quả: Tìm thấy! SĐT của " + findName + " là: " + phoneResult);
                        } else {
                            System.out.println("-> Kết quả: Không tìm thấy liên hệ nào tên là '" + findName + "'.");
                        }
                        break;
                        
                    case 3:
                        System.out.print("Nhập tên cần xóa: ");
                        String delName = scanner.nextLine();
                        boolean isDeleted = service.deleteContact(delName);
                        if (isDeleted) {
                            System.out.println("-> Đã xóa thành công liên hệ '" + delName + "'.");
                        } else {
                            System.out.println("-> Xóa thất bại: Không tìm thấy tên '" + delName + "'.");
                        }
                        break;
                        
                    case 4:
                        System.out.println("Thoát chương trình Client.");
                        break;
                        
                    default:
                        System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại từ 1 đến 4.");
                }
            } while (choice != 4);
            
        } catch (Exception e) {
            System.err.println("Lỗi Client: " + e.getMessage());
            e.printStackTrace();
        }
    }
}