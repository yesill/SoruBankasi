import java.util.ArrayList;
import java.util.Scanner;

//@mehmet uysal, last update: 03/05/2020


public class Main {

	public static SoruBankasi soruBankasi = new SoruBankasi();
	
	public static void main(String[] args) {

		Scanner girdi = new Scanner(System.in);
		boolean donguDevam = true;
		while(donguDevam) {
			System.out.print("\nSoru Bankasi v2.0"
					+ "\n1- Sorulara göz at,"
					+ "\n2- Sýnav yap,"
					+ "\n3- Soru ekle - çýkar,"
					+ "\n4- Programý sonlandýr."
					+ "\nYapmak istediginiz iþlem: ");
			switch (islemAl("1-4 arasý sayý girin..!\t: ")) {
				case 1: sorularaGozAt();	break;
				case 2: soruBankasi.sinavYap();	break;
				case 3: soruBankasi.soruEkleCikarMenu(donguDevam);	break;
				case 4: 
					System.out.println("Program sonlandýrýldý..!");
					donguDevam = false;
					soruBankasi.dosyayaKaydet();
					break;
				default: 
					System.err.println("\nHatalý giriþ yaptýnýz..!");	
					donguDevam = true;
					break;
			}
		}

	}
	
	private static void sorularaGozAt() {
		System.out.print("\nSorular"
				+ "\n1- Arama yap,"
				+ "\n2- Listeleme yap,"
				+ "\n3- Ana menuye don."
				+ "\nYapmak istediðiniz iþlem: ");
		switch (islemAl("1-3 arasý sayý girin..!\t:")) {
			case 1: soruBankasi.arama();	break;
			case 2: soruBankasi.listeleme();	break;
			default: break;
		}
	}
	
	private static int islemAl(String hataMetni) {
		Scanner girdi = new Scanner(System.in);
		while(!girdi.hasNextInt()) {
			System.err.print(hataMetni);
			girdi.next();
		}
		return girdi.nextInt();
	}
}