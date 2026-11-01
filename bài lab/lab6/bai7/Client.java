package bai7;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            // Kết nối tới Registry
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            
            // Tìm kiếm dịch vụ từ xa
            ShapeService service = (ShapeService) registry.lookup("ShapeService");
            
            Scanner scanner = new Scanner(System.in);
            int choice = 0;
            
            do {
                System.out.println("\n--- TÍNH DIỆN TÍCH HÌNH HỌC RMI ---");
                System.out.println("1. Tính diện tích Hình chữ nhật");
                System.out.println("2. Tính diện tích Hình tròn");
                System.out.println("3. Tính diện tích Hình tam giác");
                System.out.println("4. Thoát");
                System.out.print("Chọn hình cần tính (1-4): ");
                
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                } else {
                    System.out.println("Vui lòng nhập một số hợp lệ!");
                    scanner.next();
                    continue;
                }
                
                switch (choice) {
                    case 1:
                        System.out.print("Nhập chiều rộng: ");
                        double w = scanner.nextDouble();
                        System.out.print("Nhập chiều cao: ");
                        double h = scanner.nextDouble();
                        double recArea = service.rectangleArea(w, h);
                        System.out.println("-> Diện tích hình chữ nhật là: " + recArea);
                        break;
                        
                    case 2:
                        System.out.print("Nhập bán kính hình tròn: ");
                        double r = scanner.nextDouble();
                        double cirArea = service.circleArea(r);
                        System.out.println("-> Diện tích hình tròn là: " + cirArea);
                        break;
                        
                    case 3:
                        System.out.print("Nhập cạnh đáy tam giác: ");
                        double base = scanner.nextDouble();
                        System.out.print("Nhập chiều cao tam giác: ");
                        double height = scanner.nextDouble();
                        double triArea = service.triangleArea(base, height);
                        System.out.println("-> Diện tích hình tam giác là: " + triArea);
                        break;
                        
                    case 4:
                        System.out.println("Thoát chương trình.");
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