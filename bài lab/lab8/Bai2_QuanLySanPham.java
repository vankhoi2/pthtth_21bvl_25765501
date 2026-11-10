import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Bai2_QuanLySanPham {
    private static final String URL = "jdbc:sqlite:cuahang.db";

    public static void main(String[] args) {
        khoiTaoBang();
        Scanner scanner = new Scanner(System.in);
        int chon;

        do {
            System.out.println("\n--- QUẢN LÝ SẢN PHẨM ---");
            System.out.println("1. Thêm sản phẩm mới");
            System.out.println("2. Xem danh sách sản phẩm");
            System.out.println("3. Tìm kiếm sản phẩm theo tên");
            System.out.println("0. Thoát");
            System.out.print("Lựa chọn của bạn: ");
            chon = Integer.parseInt(scanner.nextLine());

            switch (chon) {
                case 1:
                    System.out.print("Nhập mã SP: ");
                    String ma = scanner.nextLine();
                    System.out.print("Nhập tên SP: ");
                    String ten = scanner.nextLine();
                    System.out.print("Nhập giá: ");
                    double gia = Double.parseDouble(scanner.nextLine());
                    System.out.print("Nhập số lượng: ");
                    int sl = Integer.parseInt(scanner.nextLine());
                    themSanPham(ma, ten, gia, sl);
                    break;
                case 2:
                    hienThiTatCaSanPham();
                    break;
                case 3:
                    System.out.print("Nhập tên sản phẩm cần tìm: ");
                    String keyword = scanner.nextLine();
                    timKiemSanPhamTheoTen(keyword);
                    break;
                case 0:
                    System.out.println("Thoát chương trình.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (chon != 0);

        scanner.close();
    }

    public static void khoiTaoBang() {
        String sql = "CREATE TABLE IF NOT EXISTS SanPham ("
                + "maSP TEXT PRIMARY KEY, "
                + "tenSP TEXT NOT NULL, "
                + "gia REAL, "
                + "soluong INTEGER"
                + ")";
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void themSanPham(String maSP, String tenSP, double gia, int soluong) {
        String sql = "INSERT INTO SanPham (maSP, tenSP, gia, soluong) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maSP);
            pstmt.setString(2, tenSP);
            pstmt.setDouble(3, gia);
            pstmt.setInt(4, soluong);
            pstmt.executeUpdate();
            System.out.println("Thêm sản phẩm thành công!");
        } catch (SQLException e) {
            System.err.println("Lỗi (có thể trùng mã SP): " + e.getMessage());
        }
    }

    public static void hienThiTatCaSanPham() {
        String sql = "SELECT * FROM SanPham";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            System.out.println("\n--- DANH SÁCH SẢN PHẨM ---");
            while (rs.next()) {
                System.out.printf("Mã: %-5s | Tên: %-20s | Giá: %,.0f | SL: %d%n",
                        rs.getString("maSP"), rs.getString("tenSP"),
                        rs.getDouble("gia"), rs.getInt("soluong"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void timKiemSanPhamTheoTen(String tuKhoa) {
        String sql = "SELECT * FROM SanPham WHERE tenSP LIKE ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + tuKhoa + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("\n--- KẾT QUẢ TÌM KIẾM ---");
                while (rs.next()) {
                    System.out.printf("Mã: %-5s | Tên: %-20s | Giá: %,.0f | SL: %d%n",
                            rs.getString("maSP"), rs.getString("tenSP"),
                            rs.getDouble("gia"), rs.getInt("soluong"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}