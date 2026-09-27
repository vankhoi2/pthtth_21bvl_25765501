package lab1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

// Class Môn học
class MonHoc {
    private String tenMonHoc;
    private int tinChi;
    private double diem;

    public MonHoc(String tenMonHoc, int tinChi, double diem) {
        this.tenMonHoc = tenMonHoc;
        this.tinChi = tinChi;
        this.diem = diem;
    }

    public String getTenMonHoc() { return tenMonHoc; }
    public int getTinChi() { return tinChi; }
    public double getDiem() { return diem; }
}

// Class Sinh viên
class SinhVien {
    private String mssv;
    private String ten;
    private int tuoi;
    private ArrayList<MonHoc> listMH;

    public SinhVien(String mssv, String ten, int tuoi, ArrayList<MonHoc> listMH) {
        this.mssv = mssv;
        this.ten = ten;
        this.tuoi = tuoi;
        this.listMH = listMH;
    }

    public String getMssv() { return mssv; }
    public String getTen() { return ten; }
    public int getTuoi() { return tuoi; }
    public ArrayList<MonHoc> getListMH() { return listMH; }

    @Override
    public String toString() {
        return "MSSV: " + mssv + " | Tên: " + ten + " | Tuổi: " + tuoi + " | Số môn: " + listMH.size();
    }
}

public class bai8 {

    // Ghi file nhị phân
    public static void saveSV(String src, ArrayList<SinhVien> listSV) throws IOException {
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(new File(src)));
        dos.writeInt(listSV.size());
        for (SinhVien sv : listSV) {
            dos.writeUTF(sv.getMssv());
            dos.writeUTF(sv.getTen());
            dos.writeInt(sv.getTuoi());
            dos.writeInt(sv.getListMH().size());
            for (MonHoc mh : sv.getListMH()) {
                dos.writeUTF(mh.getTenMonHoc());
                dos.writeInt(mh.getTinChi());
                dos.writeDouble(mh.getDiem());
            }
        }
        dos.flush();
        dos.close();
    }

    // Đọc file nhị phân
    public static void loadSV(String src) throws IOException {
        DataInputStream dis = new DataInputStream(new FileInputStream(new File(src)));
        int size = dis.readInt();
        ArrayList<SinhVien> listSV = new ArrayList<SinhVien>();
        for (int i = 0; i < size; i++) {
            String mssv = dis.readUTF();
            String name = dis.readUTF();
            int age = dis.readInt();
            int sizemh = dis.readInt();
            ArrayList<MonHoc> listMH = new ArrayList<MonHoc>();
            for (int j = 0; j < sizemh; j++) {
                String tenMonHoc = dis.readUTF();
                int tinChi = dis.readInt();
                double diem = dis.readDouble();
                MonHoc mh1 = new MonHoc(tenMonHoc, tinChi, diem);
                listMH.add(mh1);
            }
            listSV.add(new SinhVien(mssv, name, age, listMH));
        }
        for (SinhVien sv : listSV) {
            System.out.println(sv.toString());
        }
        dis.close();
    }

    public static void main(String[] args) throws IOException {
        MonHoc mh = new MonHoc("ltcb", 3, 6.7);
        MonHoc mh1 = new MonHoc("ltw", 3, 6.7);
        MonHoc mh2 = new MonHoc("tkhdt", 3, 6.7);
        ArrayList<MonHoc> listMH = new ArrayList<>();
        listMH.add(mh2);
        listMH.add(mh1);
        listMH.add(mh);

        ArrayList<SinhVien> listSV = new ArrayList<>();
        SinhVien sv = new SinhVien("11329078", "nguyen van A", 23, listMH);
        SinhVien sv1 = new SinhVien("11329078", "nguyen Van B", 23, listMH);
        listSV.add(sv);
        listSV.add(sv1);

        String path = "E:\\a.txt";
        saveSV(path, listSV);
        System.out.println("Ghi file thành công. Tiến hành đọc:");
        loadSV(path);
    }
}