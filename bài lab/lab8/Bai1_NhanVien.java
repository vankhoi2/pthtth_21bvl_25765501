import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Bai1_NhanVien {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:nhanvien.db";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // B1. Nạp driver và thiết lập kết nối
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
            System.out.println("Kết nối SQLite thành công!");

            // B2. Tạo đối tượng Statement
            stmt = conn.createStatement();

            // B3.1. Tạo bảng NhanVien (Thêm IF NOT EXISTS để chạy lại không bị lỗi)
            String sqlCreateTable = "CREATE TABLE IF NOT EXISTS NhanVien ("
                    + "id INTEGER PRIMARY KEY, "
                    + "ten TEXT NOT NULL, "
                    + "chuc_vu TEXT"
                    + ")";
            stmt.executeUpdate(sqlCreateTable);
            System.out.println("Đã tạo bảng NhanVien.");

            // Sử dụng Transaction cho thao tác thêm dữ liệu
            conn.setAutoCommit(false);

            // Xóa dữ liệu cũ (nếu có) để tránh lỗi trùng khóa chính khi chạy test nhiều lần
            stmt.executeUpdate("DELETE FROM NhanVien");

            // B3.2. Thêm ít nhất 3 nhân viên
            String insert1 = "INSERT INTO NhanVien (id, ten, chuc_vu) VALUES (1, 'Nguyen Van A', 'Truong phong')";
            String insert2 = "INSERT INTO NhanVien (id, ten, chuc_vu) VALUES (2, 'Tran Thi B', 'Ke toan')";
            String insert3 = "INSERT INTO NhanVien (id, ten, chuc_vu) VALUES (3, 'Le Van C', 'Nhan vien IT')";

            stmt.executeUpdate(insert1);
            stmt.executeUpdate(insert2);
            stmt.executeUpdate(insert3);

            // Xác nhận giao dịch thành công
            conn.commit();
            System.out.println("Đã thêm 3 nhân viên thành công (Committed).");

            // Trả lại chế độ auto-commit mặc định
            conn.setAutoCommit(true);

            // B4. Truy vấn và hiển thị dữ liệu
            String sqlSelect = "SELECT id, ten, chuc_vu FROM NhanVien";
            rs = stmt.executeQuery(sqlSelect);

            System.out.println("\n--- DANH SÁCH NHÂN VIÊN ---");
            while (rs.next()) {
                int id = rs.getInt("id");
                String ten = rs.getString("ten");
                String chucVu = rs.getString("chuc_vu");
                System.out.printf("ID: %d | Tên: %-15s | Chức vụ: %s%n", id, ten, chucVu);
            }

        } catch (ClassNotFoundException e) {
            System.err.println("Không tìm thấy driver SQLite JDBC: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Lỗi SQL: " + e.getMessage());
            // Rollback nếu có lỗi xảy ra trong quá trình Transaction
            if (conn != null) {
                try {
                    System.err.println("Đang hoàn tác dữ liệu (Rollback)...");
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            // B5. Đóng kết nối và giải phóng tài nguyên theo thứ tự ngược lại
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
                System.out.println("\nĐã đóng toàn bộ tài nguyên JDBC an toàn.");
            } catch (SQLException e) {
                System.err.println("Lỗi khi đóng tài nguyên: " + e.getMessage());
            }
        }
    }
}