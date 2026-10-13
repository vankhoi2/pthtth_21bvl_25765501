package lab2;

import java.time.Year;

public class bai3 {

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

    // Lớp con GiangVien kế thừa Nguoi
    public static class GiangVien extends Nguoi {
        private String maGiangVien;
        private String chuyenMon;
        private double luongCoBan;
        private double heSoLuong;

        public GiangVien(String hoTen, int namSinh, String diaChi, String maGiangVien, String chuyenMon, double luongCoBan, double heSoLuong) {
            super(hoTen, namSinh, diaChi);
            this.maGiangVien = maGiangVien;
            this.chuyenMon = chuyenMon;
            this.luongCoBan = luongCoBan;
            this.heSoLuong = heSoLuong;
        }

        public String getMaGiangVien() { return maGiangVien; }
        public String getChuyenMon() { return chuyenMon; }
        public double getLuongCoBan() { return luongCoBan; }
        public double getHeSoLuong() { return heSoLuong; }

        public double tinhLuong() {
            return luongCoBan * heSoLuong;
        }

        @Override
        public void hienThiThongTin() {
            super.hienThiThongTin();
            System.out.println("Mã giảng viên: " + maGiangVien);
            System.out.println("Chuyên môn   : " + chuyenMon);
            System.out.println("Lương cơ bản : " + luongCoBan + " VNĐ");
            System.out.println("Hệ số lương  : " + heSoLuong);
            System.out.println("Tổng lương   : " + tinhLuong() + " VNĐ");
        }
    }

    public static void main(String[] args) {
        GiangVien gv1 = new GiangVien("Lê Văn C", 1985, "Hà Nội", "GV001", "Mạng máy tính", 5000000, 3.5);
        GiangVien gv2 = new GiangVien("Phạm Thị D", 1990, "Cần Thơ", "GV002", "Lập trình Web", 5000000, 2.8);

        System.out.println("================ DANH SÁCH GIẢNG VIÊN ================");
        System.out.println("[Giảng viên 1]");
        gv1.hienThiThongTin();

        System.out.println("\n[Giảng viên 2]");
        gv2.hienThiThongTin();
    }
}