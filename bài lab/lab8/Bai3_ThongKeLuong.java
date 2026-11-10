import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Bai3_ThongKeLuong {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:nhanvien_luong.db";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();

            // Tạo bảng có thêm cột luong
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS NhanVien ("
                    + "id INTEGER PRIMARY KEY, "
                    + "ten TEXT NOT NULL, "
                    + "chuc_vu TEXT, "
                    + "luong REAL)");
            stmt.executeUpdate("DELETE FROM NhanVien");

            // Thêm dữ liệu mẫu
            stmt.executeUpdate("INSERT INTO NhanVien VALUES (1, 'Nguyen Van A', 'Dev', 15000000)");
            stmt.executeUpdate("INSERT INTO NhanVien VALUES (2, 'Tran Thi B', 'Dev', 18000000)");
            stmt.executeUpdate("INSERT INTO NhanVien VALUES (3, 'Le Van C', 'Tester', 12000000)");
            stmt.executeUpdate("INSERT INTO NhanVien VALUES (4, 'Pham Thi D', 'Tester', 13000000)");
            stmt.executeUpdate("INSERT INTO NhanVien VALUES (5, 'Hoang Van E', 'PM', 25000000)");

            // 1. Thống kê SUM, AVG, MIN, MAX
            String sqlStats = "SELECT SUM(luong) AS tongLuong, AVG(luong) AS tbLuong, "
                            + "MIN(luong) AS minLuong, MAX(luong) AS maxLuong FROM NhanVien";
            rs = stmt.executeQuery(sqlStats);
            if (rs.next()) {
                System.out.println("=== THỐNG KÊ LƯƠNG TOÀN CÔNG TY ===");
                System.out.printf("Tổng quỹ lương: %,.0f VNĐ%n", rs.getDouble("tongLuong"));
                System.out.printf("Lương trung bình: %,.0f VNĐ%n", rs.getDouble("tbLuong"));
                System.out.printf("Lương thấp nhất: %,.0f VNĐ%n", rs.getDouble("minLuong"));
                System.out.printf("Lương cao nhất: %,.0f VNĐ%n", rs.getDouble("maxLuong"));
            }
            rs.close();

            // 2. Đếm số lượng nhân viên theo chức vụ (COUNT + GROUP BY)
            String sqlGroup = "SELECT chuc_vu, COUNT(id) AS soLuong FROM NhanVien GROUP BY chuc_vu";
            rs = stmt.executeQuery(sqlGroup);
            System.out.println("\n=== SỐ LƯỢNG NHÂN VIÊN THEO CHỨC VỤ ===");
            while (rs.next()) {
                System.out.printf("Chức vụ: %-10s | Số lượng: %d nhân viên%n",
                        rs.getString("chuc_vu"), rs.getInt("soLuong"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}