package lab2;

import java.time.Year;

public class bai2 {

    // Lớp cha Nguoi
    public static class Nguoi {
        private String hoTen;
        private int namSinh;
        private String diaChi;

        public Nguoi(String hoTen, int namSinh, String diaChi) {
            this.hoTen = hoTen;
            this.namSinh = namSinh;
            this.diaChi = diaChi;
        }

        public String getHoTen() { return hoTen; }
        public void setHoTen(String hoTen) { this.hoTen = hoTen; }

        public int getNamSinh() { return namSinh; }
        public void setNamSinh(int namSinh) { this.namSinh = namSinh; }

        public String getDiaChi() { return diaChi; }
        public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

        public int tinhTuoi() {
            return Year.now().getValue() - namSinh;
        }

        public void hienThiThongTin() {
            System.out.println("Họ và tên   : " + hoTen);
            System.out.println("Năm sinh    : " + namSinh + " (Tuổi: " + tinhTuoi() + ")");
            System.out.println("Địa chỉ     : " + diaChi);
        }
    }

    // Lớp con SinhVien kế thừa Nguoi
    public static class SinhVien extends Nguoi {
        private String maSinhVien;
        private String nganhHoc;
        private double diemTrungBinh;

        public SinhVien(String hoTen, int namSinh, String diaChi, String maSinhVien, String nganhHoc, double diemTrungBinh) {
            super(hoTen, namSinh, diaChi);
            this.maSinhVien = maSinhVien;
            this.nganhHoc = nganhHoc;
            this.diemTrungBinh = diemTrungBinh;
        }

        public String getMaSinhVien() { return maSinhVien; }
        public String getNganhHoc() { return nganhHoc; }
        public double getDiemTrungBinh() { return diemTrungBinh; }

        public String xepLoai() {
            if (diemTrungBinh >= 8.5) {
                return "Giỏi";
            } else if (diemTrungBinh >= 7.0) {
                return "Khá";
            } else if (diemTrungBinh >= 5.0) {
                return "Trung bình";
            } else {
                return "Yếu";
            }
        }

        @Override
        public void hienThiThongTin() {
            super.hienThiThongTin();
            System.out.println("Mã sinh viên: " + maSinhVien);
            System.out.println("Ngành học   : " + nganhHoc);
            System.out.println("ĐTB         : " + diemTrungBinh);
            System.out.println("Xếp loại    : " + xepLoai());
        }
    }

    public static void main(String[] args) {
        SinhVien sv1 = new SinhVien("Nguyễn Văn A", 2004, "TP. Hồ Chí Minh", "SV001", "Công nghệ thông tin", 8.8);
        SinhVien sv2 = new SinhVien("Trần Thị B", 2005, "Đồng Nai", "SV002", "Thiết kế đồ họa", 6.5);

        System.out.println("================ DANH SÁCH SINH VIÊN ================");
        System.out.println("[Sinh viên 1]");
        sv1.hienThiThongTin();
        
        System.out.println("\n[Sinh viên 2]");
        sv2.hienThiThongTin();
    }
}