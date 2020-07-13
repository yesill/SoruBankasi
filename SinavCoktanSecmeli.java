import java.util.ArrayList;

import javax.xml.transform.Source;

//@mehmet uysal, last update: 03/05/2020

public class SinavCoktanSecmeli extends Sinav{
	
	private int alinanPuan = 0, toplamPuan = 0, dogruSayaci = 1;
	private ArrayList<SoruCoktanSecmeli> sorular = new ArrayList<SoruCoktanSecmeli>();

	//methodlar
	
	public SinavCoktanSecmeli(ArrayList<SoruCoktanSecmeli> arr) {
		setSoruNumaralari(arr.size());
		for(int i=0; i<soruSayisi; i++)
			sorular.add(arr.get(soruNumaralari[i]));
		sinavYap();
		sonucYazdir();
	}
	
	//phase: hazirlanma
	private void setSoruNumaralari(int soruListesiSize) {
		ArrayList<Integer> tempRandom = tekrarsizRandom.olustur(soruSayisi, soruListesiSize);
		super.setSoruNumaralari(tempRandom);
	}
	
	//phase: uygulama
	private void sinavYap() {
		System.out.println("\n�oktan Se�meli s�nav"
				+ "\nBu s�navda soru bankas�ndan rastgele se�ilen"
				+ "\n5 adet �oktan se�meli soru bulunmaktad�r.");
		for (SoruCoktanSecmeli soru : sorular) {
			System.out.println("\n" + mevcutSoruNumarasi + ") " + soru.getSoruMetni());
			secenekleriYazdir(soru);
			System.out.print("cevab�n�z(a,b,c): ");
			super.cevapAlma();
			cevapDogrumu(soru);	//cevap dogruysa alinan puani artiriyor.
			mevcutSoruNumarasi++;
		}
	}
	
	private void secenekleriYazdir(SoruCoktanSecmeli soru) {
		String[] secenekler = {"","",""};	//new secenekeler[3] diye tanimlarsam += isleminde basinda null oluyor
		int sayac = 0;
		for (int i=0; i<soru.getSecenekler().length(); i++) {
			if (soru.getSecenekler().charAt(i) == '$')	sayac++;
			else	secenekler[sayac] += soru.getSecenekler().charAt(i);
		}
		System.out.println("A) " + secenekler[0] + "\nB) " + secenekler[1] + "\nC) " + secenekler[2]);
	}
	
	private void cevapDogrumu(SoruCoktanSecmeli soru) {
		if (cevaplar[mevcutSoruNumarasi-1].equalsIgnoreCase(soru.getCevap())) {
			alinanPuan += soru.getPuan();	dogruSayaci++;
		}
		toplamPuan += soru.getPuan();
	}
	
	//phase: sonlandirma
	//sonuc yazdirma
	private void sonucYazdir() {
		alinanPuan = (int)(((float)alinanPuan / (float)toplamPuan) * 100);
		System.out.println("\nS�nav Bitti..!\n" + soruSayisi + " sorudan " + dogruSayaci + " tanesine"
						+ " do�ru cevap verdiniz.\nPuan: " + alinanPuan + "/" + toplamPuan);
	}
	
}
