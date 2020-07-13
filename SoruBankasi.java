//@mehmet uysal, last update: 03/05/2020


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

import javax.xml.transform.Source;

public class SoruBankasi {	//sorular, 'sorular.dat' dosyasindan okunuyor/yaziliyor..!

	private IO io;
	private Sinav sinav;
	private Arama arayici;
	private Listeleme listeci = new Listeleme();
	private ArrayList<Sinav> arrSinavlar = new ArrayList<Sinav>();
	private ArrayList<SoruAcikUclu> arrSoruAcikUclu = new ArrayList<SoruAcikUclu>();
	private ArrayList<SoruBoslukDoldurma> arrSoruBoslukDoldurma = new ArrayList<SoruBoslukDoldurma>();
	private ArrayList<SoruCoktanSecmeli> arrSoruCoktanSecmeli = new ArrayList<SoruCoktanSecmeli>();
	private ArrayList<SoruDogruYanlis> arrSoruDogruYanlis = new ArrayList<SoruDogruYanlis>();
	
	//constructor calistiginda 'sorular.dat' dosyasini okuyup sorulari arraylistlere atip kullanilabilir hale getiriyor.
	public SoruBankasi() {
		io = new IO(arrSoruAcikUclu, arrSoruBoslukDoldurma, arrSoruCoktanSecmeli, arrSoruDogruYanlis);
		arayici = new Arama();
	}

	//soru ekleme methodlari
	
	//sorular
	public void soruEkleCikarMenu(boolean donguDevam) {
		Scanner girdi = new Scanner(System.in);
		System.out.print("\n1- Soru ekle,"
				+ "\n2- Soru cikar,"
				+ "\n3- Ana menuye don."
				+ "\nYapmak istediðiniz iþlem: ");
		int islem = islemAlInt("1-3 arasý sayý girin..!\t: ");
		switch (islem) {
			case 1: soruEkle();	break;
			case 2: soruCikar();	break;
			case 3: donguDevam = false;	break;
			default: System.out.println("\nHatali giriþ yaptýnýz..!"); donguDevam = true;	break;
		}
	}
	
	//soru ekleme bolumu
	public void soruEkle() {
		System.out.println("\nSoru Ekleme;");
		switch (eklenecekSoruCinsi()) {
			case 1: acikUcluSoruEkle();	break;
			case 2: boslukDoldurmaliSoruEkle();	break;
			case 3:	coktanSecmeliSoruEkle();	break;
			case 4: dogruYanlisSoruEkle();	break;
			default:	break;
		}
	}
	
	private int eklenecekSoruCinsi() {
		Scanner girdi = new Scanner(System.in);
		System.out.print("\n1- Açýk uçlu soru ekle"
				+ "\n2- Boþluk doldurmalý soru ekle"
				+ "\n3- Çoktan seçmeli soru ekle"
				+ "\n4- Doðru yanlýþ sorusu ekle"
				+ "\nEklemek istediðiniz soru biçimi: ");
		return islemAlInt("1-4 arasý sayý girin..!\t: ");
	}
	
	private void acikUcluSoruEkle() {
		Scanner girdi = new Scanner(System.in);
		System.out.print("\nSoru metni: ");
		String soruMetni = girdi.nextLine();
		int zorluk;
		while(true) {
			System.out.print("Zorluk(1,2,3): ");
			zorluk = islemAlInt("1-3 arasý sayý girin..!\t: ");
			if (0 <= zorluk && zorluk <= 3)	break;
			else	System.out.println("zorluk 1 2 yada 3 olmalý..!");
		}
		this.arrSoruAcikUclu.add(new SoruAcikUclu(soruMetni, zorluk));
	}
	
	private void boslukDoldurmaliSoruEkle() {
		Scanner girdi = new Scanner(System.in);
		System.out.print("\nSoru metni: ");
		String soruMetni = girdi.nextLine();
		System.out.print("Cevap: ");
		String cevap = girdi.nextLine();
		int zorluk;
		while(true) {
			System.out.print("Zorluk(1,2,3): ");
			zorluk = islemAlInt("1-3 arasý sayý girin..!\t: ");
			if (0 <= zorluk && zorluk <= 3)	break;
			else	System.out.println("zorluk 1 2 yada 3 olmalý..!");
		}
		this.arrSoruBoslukDoldurma.add(new SoruBoslukDoldurma(soruMetni, zorluk, cevap));
	}
			
	private void coktanSecmeliSoruEkle() {
		Scanner girdi = new Scanner(System.in);
		System.out.print("\nSoru metni: ");
		String soruMetni = girdi.nextLine();
		
		System.out.print("\nSecenek A) ");
		String secenekA = girdi.nextLine();
		System.out.print("Secenek B) ");
		String secenekB = girdi.nextLine();
		System.out.print("Secenek C) ");
		String secenekC = girdi.nextLine();
		String secenekler = secenekA + "$" + secenekB + "$" + secenekC;	//$ ayracimiz.
		
		System.out.print("\nCevap(a,b,c): ");
		String cevap = girdi.nextLine();
		
		int zorluk;
		while(true) {
			System.out.print("\nZorluk(1,2,3): ");
			zorluk = islemAlInt("1-3 arasý sayý girin..!\t: ");
			if (0 <= zorluk && zorluk <= 3)	break;
			else	System.out.println("zorluk 1 2 yada 3 olmalý..!");
		}
		
		this.arrSoruCoktanSecmeli.add(new SoruCoktanSecmeli(soruMetni, zorluk, secenekler, cevap));
	}
		
	private void dogruYanlisSoruEkle() {
		Scanner girdi = new Scanner(System.in);
		System.out.print("\nSoru metni: ");
		String soruMetni = girdi.nextLine();
		System.out.print("Cevap(D/Y): ");
		String cevap = girdi.nextLine();
		int zorluk;
		while(true) {
			System.out.print("Zorluk(1,2,3): ");
			zorluk = islemAlInt("1-3 arasý sayý girin..!\t: ");
			if (0 <= zorluk && zorluk <= 3)	break;
			else	System.err.println("zorluk 1 2 yada 3 olmalý..!");
		}
		this.arrSoruDogruYanlis.add(new SoruDogruYanlis(soruMetni, zorluk, cevap));
	}
	
	//soru cikarma bolumu
	
	//soru cikarma
	public void soruCikar() {
		Scanner girdi = new Scanner(System.in);
		int size = tekArraydeBirlestirSorular().size();
		System.out.println("\nbütün sorular listelenmiþtir;");
		listeci.listele(tekArraydeBirlestirSorular());
		System.out.print("\nSilmek istediðiniz sorunun numarasý: ");
		int soruNumarasi = islemAlInt("1-" + size + " arasýnda olmalý..!\t: ");
		if (1 <= soruNumarasi && soruNumarasi <= size)	{
			Soru tempSoru = tekArraydeBirlestirSorular().get(soruNumarasi-1);
			switch (tempSoru.getSoruCesidi()) {
				case 'a': 
					for (int i=0; i<arrSoruAcikUclu.size(); i++)
						if (arrSoruAcikUclu.get(i).getSoruMetni().equals(tempSoru.getSoruMetni()))
							arrSoruAcikUclu.remove(i);
					break;
				case 'b': 
					for (int i=0; i<arrSoruBoslukDoldurma.size(); i++)
						if (arrSoruBoslukDoldurma.get(i).getSoruMetni().equals(tempSoru.getSoruMetni()))
							arrSoruBoslukDoldurma.remove(i);
					break;
				case 'c': 
					for (int i=0; i<arrSoruCoktanSecmeli.size(); i++)
						if (arrSoruCoktanSecmeli.get(i).getSoruMetni().equals(tempSoru.getSoruMetni()))
							arrSoruCoktanSecmeli.remove(i);
					break;
				case 'd':
					for (int i=0; i<arrSoruDogruYanlis.size(); i++)
						if (arrSoruDogruYanlis.get(i).getSoruMetni().equals(tempSoru.getSoruMetni()))
							arrSoruDogruYanlis.remove(i);
					break;
				default:	break;
			}
			System.err.println("\n" + soruNumarasi + ". soru, soru bankasýndan çýkarýldý..!");
		}
		else
			System.err.println("\n" + soruNumarasi + ". soru yok..!");
	}

	//sinav yap
	public void sinavYap() {
		Scanner girdi = new Scanner(System.in);
		System.out.print("\n1- Açýk uçlu sýnav,"
				+ "\n2- Çoktan seçmeli sýnav,"
				+ "\n3- Karýþýk sýnav,"
				+ "\n4- Ana menüye dön."
				+ "\nyapmak istediðiniz sinav: ");
		int islem = islemAlInt("1-3 arasý sayý girin..!\n : ");
		switch (islem) {
			case 1: sinav = new SinavAcikUclu(arrSoruAcikUclu);	break;
			case 2:	sinav = new SinavCoktanSecmeli(arrSoruCoktanSecmeli);	break;
			case 3: sinav = new SinavKarisik(tekArraydeBirlestirSorular());	break;
			default: break;
		}
		arrSinavlar.add(sinav);
	}
	
	//listeleme bolumu
	public void listeleme() {
		Scanner girdi = new Scanner(System.in);
		System.out.print("\nListeleme Ýþlemleri;"
				+ "\n1- Bütün sorulari listele,"
				+ "\n2- Açýk uçlu sorularý listele,"
				+ "\n3- Boþluk doldurmalý sorularý listele,"
				+ "\n4- Çoktan seçmeli sorularý listele,"
				+ "\n5- Doðru yanlýþ sorularý listele,"
				+ "\n6- Zorluða göre listele,"
				+ "\n7- Puana göre listele,"
				+ "\n8- Ana menüye dön."
				+ "\nYapmak istediðiniz iþlem: ");
		int islem = islemAlInt("1-7 arasý sayý girin..!\t: ");
		System.out.println();
		switch (islem) {
			case 1: listeci.listele(tekArraydeBirlestirSorular());	break;
			case 2: listeci.listele(soruFromAU(arrSoruAcikUclu));	break;
			case 3: listeci.listele(soruFromBD(arrSoruBoslukDoldurma));	break;
			case 4: listeci.listele(soruFromCS(arrSoruCoktanSecmeli));	break;
			case 5: listeci.listele(soruFromDY(arrSoruDogruYanlis));	break;
			case 6: listeci.listele(arayici.zorlukArama(tekArraydeBirlestirSorular(), listeleZorluk()));	break;
			case 7: listeci.listele(arayici.puanArama(tekArraydeBirlestirSorular(), listelePuan()));	break;
			default:	break;
		}
	}
	
	
	private String listeleZorluk() {
		Scanner girdi = new Scanner(System.in);
		System.out.print("Listelemek istediðiniz zorluk(kolay,normal,zor): ");
		return girdi.next();
	}
	
	
	private int listelePuan() {
		Scanner girdi = new Scanner(System.in);
		System.out.print("Listelemek istediðiniz paun(10,20,40): ");
		return islemAlInt("10,20,40 sayýlarýndan birini girin..!\t: ");
	}
	
	//listeleme - belirtilen soru turunu soru ya ceviriyor.
	private ArrayList<Soru> soruFromAU(ArrayList<SoruAcikUclu> arr) {
		ArrayList<Soru> tempArr = new ArrayList<Soru>();
		for (SoruAcikUclu soru : arr)	tempArr.add((Soru)soru);	return tempArr;
	}	
	
	
	private ArrayList<Soru> soruFromBD(ArrayList<SoruBoslukDoldurma> arr) {
		ArrayList<Soru> tempArr = new ArrayList<Soru>();
		for (SoruBoslukDoldurma soru : arr)	tempArr.add((Soru)soru);	return tempArr;
	}
	
	
	private ArrayList<Soru> soruFromCS(ArrayList<SoruCoktanSecmeli> arr) {
		ArrayList<Soru> tempArr = new ArrayList<Soru>();
		for (SoruCoktanSecmeli soru : arr)	tempArr.add((Soru)soru);	return tempArr;
	}
	
	
	private ArrayList<Soru> soruFromDY(ArrayList<SoruDogruYanlis> arr) {
		ArrayList<Soru> tempArr = new ArrayList<Soru>();
		for (SoruDogruYanlis soru : arr)	tempArr.add((Soru)soru);	return tempArr;
	}
	
	//tek arrayde butun sorularý birlestirme
	public ArrayList<Soru> tekArraydeBirlestirSorular(){
		ArrayList<Soru> tempArrayList = new ArrayList<Soru>();
		for (SoruAcikUclu soru : arrSoruAcikUclu) {
			tempArrayList.add((Soru)soru);
		}
		for (SoruBoslukDoldurma soru : arrSoruBoslukDoldurma) {
			tempArrayList.add((Soru)soru);
		}
		for (SoruCoktanSecmeli soru : arrSoruCoktanSecmeli) {
			tempArrayList.add((Soru)soru);
		}
		for (SoruDogruYanlis soru : arrSoruDogruYanlis) {
			tempArrayList.add((Soru)soru);
		}
		Collections.sort(tempArrayList);
		return tempArrayList;
	}
	

	//arama bolumu
	public void arama() {
		Scanner girdi = new Scanner(System.in);
		System.out.print("\nArama Yap;"
				+ "\n1- Soru Metinleri Ýçerisinde Arama Yap,"
				+ "\n2- Seçenekler Ýçerisinde Arama Yap,"
				+ "\n3- Cevaplar Ýçerisinde Arama Yap,"
				+ "\n4- Ana menüye dön."
				+ "\nYapmak istediðiniz iþlem: ");
		switch (islemAlInt("1-3 arasý sayý girin..!\n : ")) {
			case 1: aramaSoruMetni();	break;
			case 2: aramaSecenek();	break;
			case 3: aramaCevap();	break;
			default:	break;
		}
	}
	
	
	private void aramaSoruMetni() {
		Scanner girdi = new Scanner(System.in);
		System.out.print("\nSoru metni içerisinde arama"
				+ "\nAramak Ýstediðiniz kelime/metin: ");
		String aramaMetni = girdi.nextLine();
		listeci.listele(arayici.soruMetniArama(tekArraydeBirlestirSorular(), aramaMetni));
	}
	
	
	private void aramaSecenek() {
		Scanner girdi = new Scanner(System.in);
		System.out.print("\nSeçenek metni içerisinde arama"
				+ "\nAramak Ýstediðiniz kelime/metin: ");
		String aramaMetni = girdi.nextLine();
		listeci.listele(arayici.secenekArama(tekArraydeBirlestirSorular(), aramaMetni));		
	}
	
	
	private void aramaCevap() {
		Scanner girdi = new Scanner(System.in);
		System.out.print("\nCevap arama(a,b,c)"
				+ "\nAramak Ýstediðiniz kelime/metin: ");
		String aramaMetni = girdi.nextLine();
		listeci.listele(arayici.cevapArama(tekArraydeBirlestirSorular(), aramaMetni));
	}

	//islem alma
	private int islemAlInt(String hataMetni) {
		Scanner girdi = new Scanner(System.in);
		while(!girdi.hasNextInt()) {
			System.err.print(hataMetni);
			girdi.next();
		}
		return girdi.nextInt();
	}
	
	//input - output bolumu
	public void dosyayaKaydet() {
		io.sorularDosyaYaz(tekArraydeBirlestirSorular());
		io.sinavlarDosyaYaz(arrSinavlar);
	}

	//gets
	public ArrayList<SoruAcikUclu> getSorularAcikUclu(){
		return this.arrSoruAcikUclu;
	}
	
	
	public ArrayList<SoruBoslukDoldurma> getSorularBoslukDoldurma(){
		return this.arrSoruBoslukDoldurma;
	}
	
	
	public ArrayList<SoruCoktanSecmeli> getSorularCoktanSecmeli(){
		return this.arrSoruCoktanSecmeli;
	}
	
	
	public ArrayList<SoruDogruYanlis> getSorlarDogruYanlis(){
		return this.arrSoruDogruYanlis;
	}
	
}