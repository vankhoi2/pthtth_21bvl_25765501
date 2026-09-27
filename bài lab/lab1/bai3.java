package lab1;
import java.util.Scanner;

public class bai3 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("vui lòng nhập số hạngu thứ nhất: ");
		int soA = scanner.nextInt();
		System.out.print("vui long nhap so hang thu hai: ");
		int soB = scanner.nextInt();
		int kq = soA + soB;
		System.out.print("tinh tong ["+ soA + "+" + soB + " = " + kq + "]");
		scanner.close();
	}

}
