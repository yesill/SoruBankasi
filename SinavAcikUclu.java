import java.util.ArrayList;

//@mehmet uysal, last update: 03/05/2020

public class SinavAcikUclu extends Sinav{

	private ArrayList<SoruAcikUclu> sorular = new ArrayList<SoruAcikUclu>();
	
	//phase: hazirlanma
	public SinavAcikUclu(ArrayList<SoruAcikUclu> arr) {	//acik uclu sorulari(arr) soru bankasindan aliyoruz.
		setSoruNumaralari(arr.size());
		for(int i=0; i<soruSayisi; i++)	
			sorular.add(arr.get(soruNumaralari[i]));
		sinavYap();
		System.out.println("\nS�nav bitti..! Sonu�lar�n�z g�sterilmeyecektir..!");
	}
		
	private void setSoruNumaralari(int soruListesiSize) {
		ArrayList<Integer> tempRandom = tekrarsizRandom.olustur(soruSayisi, soruListesiSize);
		super.setSoruNumaralari(tempRandom);
	}
	
	//phase: execution
	private void sinavYap() {
		System.out.println("\nA��k u�lu s�nav"
				+ "\nBu s�navda soru bankas�ndan rastgele se�ilen"
				+ "\n5 adet a��k u�lu soru bulunmaktad�r."
				+ "\nS�nav sonunda s�nav sonucu g�sterilmeyecektir..!");
		for (SoruAcikUclu soru : sorular) {
			System.out.println("\n" + mevcutSoruNumarasi + ") " + soru.getSoruMetni());
			System.out.print("cevab�n�z: ");
			cevapAlma();
			mevcutSoruNumarasi++;
		}
	}
	
}
