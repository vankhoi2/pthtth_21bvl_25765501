package lab1;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class bai9 {

    // Ghi file ảnh
    public static void saveFile(File path, String tfile, byte[] bfile) {
        try {
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(bfile));
            ImageIO.write(img, tfile, path);
        } catch (IOException ex) {
            Logger.getLogger(bai9.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Đọc file ảnh
    public static byte[] readFile(File path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            byte[] buf = new byte[1024];
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum);
            }
            fis.close();
            return bos.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(bai9.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void main(String[] args) {
        File inputFile = new File("D:/test.png");
        byte[] imageBytes = readFile(inputFile);
        if (imageBytes != null) {
            File outputFile = new File("D:/copy_test.png");
            saveFile(outputFile, "png", imageBytes);
            System.out.println("Xử lý file ảnh thành công!");
        }
    }
}