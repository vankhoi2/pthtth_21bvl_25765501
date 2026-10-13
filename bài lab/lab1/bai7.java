package lab1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class bai7 {

    // Tìm kiếm File theo từ khóa
    public void finFile(String source, String key) {
        File file = new File(source);
        if (file.exists()) {
            if (file.isFile()) {
                if (file.getName().endsWith(key)) {
                    System.out.println(file.getAbsolutePath());
                }
            }
            File[] listFile = file.listFiles();
            if (listFile != null) {
                for (File f : listFile) {
                    finFile(f.getAbsolutePath(), key);
                }
            }
        } else {
            System.out.println("source không tồn tại");
        }
    }

    // Copy File
    public boolean copyFile(String source, String dest) throws FileNotFoundException, IOException {
        File sourceFile = new File(source);
        File destFile = new File(dest);
        if (sourceFile.exists()) {
            FileInputStream fis = new FileInputStream(sourceFile);
            FileOutputStream fos = new FileOutputStream(destFile);
            byte[] arr = new byte[1024];
            while ((fis.read(arr)) != -1) {
                fos.write(arr);
                fos.flush();
            }
            fis.close();
            fos.close();
            System.out.println("copy thành công");
            return true;
        } else {
            System.out.println("file nguồn không tồn tại");
            return false;
        }
    }

    public static void main(String[] args) {
        bai7 demo = new bai7();
        demo.finFile("D:/HocJava", ".txt");
    }
}