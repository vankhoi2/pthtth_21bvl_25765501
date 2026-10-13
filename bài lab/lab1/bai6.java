package lab1;

import java.io.File;
import java.io.IOException;

public class bai6 {

    // Xóa 1 File đơn
    public void deleteFile(String source) {
        File file = new File(source);
        if (file.exists()) {
            System.out.println("file ton tai");
            file.delete();
            System.out.println("xoa file thanh cong");
        } else {
            System.out.println("file khong ton tai");
        }
    }

    // TH1: Xóa folder rỗng
    public boolean deleteEmptyFolder(String source) {
        File folder = new File(source);
        if (folder.exists()) {
            folder.delete();
            System.out.println("folder ton tai\n xoa folder thanh cong");
            return true;
        } else {
            System.out.println("folder khong ton tai");
            return false;
        }
    }

    // TH2: Xóa folder chỉ chứa các file
    public boolean deleteListFileInfolder(String source) {
        File folder = new File(source);
        if (folder.exists()) {
            File[] listFile = folder.listFiles();
            if (listFile != null && listFile.length != 0) {
                for (File f : listFile) {
                    if (f.isFile()) {
                        f.delete();
                    }
                }
            }
            folder.delete();
            System.out.println("Delete folder thành công!");
            return true;
        } else {
            System.out.println("folder không tồn tại");
            return false;
        }
    }

    // TH3: Xóa folder đệ quy (chứa cả subfolder và file)
    public boolean deleteListFileInfolderRecursive(String source) throws IOException {
        File folder = new File(source);
        if (folder.exists()) {
            File[] listFile = folder.listFiles();
            if (listFile != null && listFile.length != 0) {
                for (File f : listFile) {
                    if (f.isFile()) {
                        f.delete();
                    }
                    if (f.isDirectory()) {
                        deleteListFileInfolderRecursive(f.getAbsolutePath());
                    }
                }
            }
            folder.delete();
            System.out.println("Delete folder thành công!");
            return true;
        } else {
            System.out.println("folder không tồn tại");
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        bai6 demo = new bai6();
        demo.deleteFile("D:/HocJava/demo.txt");
        // demo.deleteListFileInfolderRecursive("D:\\HocJava\\TestDeleteDir");
    }
}