package lab2;

public class bai1 {

    // Lớp SanPham thể hiện Tính đóng gói (Encapsulation)
    public static class SanPham {
        private String maSanPham;
        private String tenSanPham;
        private double donGia;
        private int soLuongTon;

        // Constructor đầy đủ tham số
        public SanPham(String maSanPham, String tenSanPham, double donGia, int soLuongTon) {
            this.maSanPham = maSanPham;
            this.tenSanPham = tenSanPham;
            this.donGia = donGia;
            this.soLuongTon = Math.max(0, soLuongTon); // Đảm bảo số lượng tồn không âm
        }

        // Getter & Setter
        public String getMaSanPham() { return maSanPham; }
        public void setMaSanPham(String maSanPham) { this.maSanPham = maSanPham; }

        public String getTenSanPham() { return tenSanPham; }
        public void setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; }

        public double getDonGia() { return donGia; }
        public void setDonGia(double donGia) { this.donGia = donGia; }

        public int getSoLuongTon() { return soLuongTon; }

        // 1. Tính thành tiền
        public double tinhThanhTien() {
            return donGia * soLuongTon;
        }

        // 2. Nhập hàng (Kiểm soát dữ liệu)
        public void nhapHang(int soLuongNhap) {
            if (soLuongNhap > 0) {
                this.soLuongTon += soLuongNhap;
                System.out.println(">> Nhập hàng thành công: +" + soLuongNhap + " " + tenSanPham);
            } else {
                System.out.println(">> Lỗi: Số lượng nhập phải lớn hơn 0!");
            }
        }

        // 3. Bán hàng (Kiểm soát dữ liệu)
        public boolean banHang(int soLuongBan) {
            if (soLuongBan <= 0) {
                System.out.println(">> Lỗi: Số lượng bán phải lớn hơn 0!");
                return false;
            }
            if (soLuongBan > this.soLuongTon) {
                System.out.println(">> Lỗi: Không đủ hàng trong kho để bán! (Tồn: " + this.soLuongTon + ", Yêu cầu: " + soLuongBan + ")");
                return false;
            }
            this.soLuongTon -= soLuongBan;
            System.out.println(">> Bán hàng thành công: -" + soLuongBan + " " + tenSanPham);
            return true;
        }

        // 4. Hiển thị thông tin
        public void hienThiThongTin() {
            System.out.println("----------------------------------------");
            System.out.println("Mã SP       : " + maSanPham);
            System.out.println("Tên SP      : " + tenSanPham);
            System.out.println("Đơn giá     : " + donGia + " VNĐ");
            System.out.println("Số lượng tồn: " + soLuongTon);
            System.out.println("Thành tiền  : " + tinhThanhTien() + " VNĐ");
            System.out.println("----------------------------------------");
        }
    }

    // Chương trình chính
    public static void main(String[] args) {
        // Tạo ít nhất hai sản phẩm
        SanPham sp1 = new SanPham("SP01", "Laptop Dell XPS", 25000000, 10);
        SanPham sp2 = new SanPham("SP02", "Chuột Logitech", 500000, 5);

        System.out.println("=== THÔNG TIN BAN ĐẦU ===");
        sp1.hienThiThongTin();
        sp2.hienThiThongTin();

        // Nhập thêm hàng cho sản phẩm 1
        System.out.println("\n=== THỰC HIỆN NHẬP HÀNG ===");
        sp1.nhapHang(5);
        sp1.hienThiThongTin();

        // Thử bán hàng thành công sản phẩm 2
        System.out.println("\n=== THỰC HIỆN BÁN HÀNG THÀNH CÔNG ===");
        sp2.banHang(3);
        sp2.hienThiThongTin();

        // Thử bán số lượng lớn hơn tồn kho sản phẩm 2
        System.out.println("\n=== THỰC HIỆN BÁN HÀNG VƯỢT QUÁ TỒN KHO ===");
        sp2.banHang(10);
        sp2.hienThiThongTin();
    }
}