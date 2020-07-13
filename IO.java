import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.nio.channels.NonWritableChannelException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

public class IO {

	private final String fileNameSinavlar = "sinavlar.dat";
	private final String fileNameSorular = "sorular.dat";
	private ArrayList<Soru> sorular = new ArrayList<Soru>();

	/*
		eger "sorular.dat" yok ise olusturuyoruz ve icine default olarak kayitli
		her soru cinsinden 5 er tane olmak uzere 20 soru yaziyor.
		dosya var ve soru sayisi eksik yada fazla ise bir islem yapmýyoruz.
	*/
	
	public IO(ArrayList<SoruAcikUclu> au,
			ArrayList<SoruBoslukDoldurma> bd,
			ArrayList<SoruCoktanSecmeli> cs,
			ArrayList<SoruDogruYanlis> dy)
	{
		sorularDosyaOku(au,bd,cs,dy);
	}

	@SuppressWarnings("unchecked")
	public void sorularDosyaOku(ArrayList<SoruAcikUclu> au,
			ArrayList<SoruBoslukDoldurma> bd,
			ArrayList<SoruCoktanSecmeli> cs,
			ArrayList<SoruDogruYanlis> dy) 
	{
		try {
			FileInputStream fis = new FileInputStream(fileNameSorular);
			ObjectInputStream ois = new ObjectInputStream(fis);
			sorular = (ArrayList<Soru>)ois.readObject();
			sorulariListeyeAl(au, bd, cs, dy);
			ois.close();
			
		} catch (FileNotFoundException e) {
			//dosya bulunamadiginda, dosya olusturup varsayilan sorulari kaydediyor.
			sorularDosyaYazVarsayilan(au, bd, cs, dy);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			//dosya var ama içi boþ ise bu varsayýlan sorularý ekliyor.
			sorularDosyaYazVarsayilan(au, bd, cs, dy);
			sorularDosyaOku(au, bd, cs, dy);
		}
	}

	public void sorularDosyaYaz(ArrayList<Soru> sorular) {
		this.sorular = sorular;
		try {
			FileOutputStream fos = new FileOutputStream(fileNameSorular);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(this.sorular);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	//eger sorular.dat bos ise assadaki sorulari varsayilan olarak ekliyor.
	private void sorularDosyaYazVarsayilan(
			ArrayList<SoruAcikUclu> au,
			ArrayList<SoruBoslukDoldurma> bd,
			ArrayList<SoruCoktanSecmeli> cs,
			ArrayList<SoruDogruYanlis> dy)
	{
		au.add(new SoruAcikUclu("Büyük Ýskender kimdir? Kýsaca anlatýn.", 1));
		au.add(new SoruAcikUclu("Sokrates kimdir? Kýsaca anlatýn.", 1));
		au.add(new SoruAcikUclu("Gaius Julius Caesar kimdir? Kýsaca anlatýn.", 2));
		au.add(new SoruAcikUclu("Satraplýk sistemi nedir kýsaca açýklayýn.", 2));
		au.add(new SoruAcikUclu("Platon'un Maðara Deneyini açýklayýn.", 3));
		
		bd.add(new SoruBoslukDoldurma("Büyük Ýskender babasý II. Filip'ten ......... tahtýný devralmýþtýr.", 1, "Makedonya"));
		bd.add(new SoruBoslukDoldurma(".....'in açýlýmý international network tür.", 1, " internet"));
		bd.add(new SoruBoslukDoldurma("..... Roma Ordularýnýn en geniþ birimidir.", 2, "lejyon"));
		bd.add(new SoruBoslukDoldurma("Sokrates .....'da doðmuþtur.", 2, "Atina"));
		bd.add(new SoruBoslukDoldurma(".......... Budizm'in dini liderine verilen isimdir.", 3, "Dalai Lama"));
		
		cs.add(new SoruCoktanSecmeli("Atatürk'ün doðum tarihi?", 1, "1880$1881$1882", "b"));
		cs.add(new SoruCoktanSecmeli("Roma Devletinin resmi dili hangisidir?", 1, "Latince$Ýtalyanca$Flemenkçe", "a"));
		cs.add(new SoruCoktanSecmeli("Pers Ýmparatorluðunu kim yýkmýþtýr?", 2, "Scipio Africanus$Hannibal$Büyük Ýskender", "c"));
		cs.add(new SoruCoktanSecmeli("Ceaser'la iliþki yaþan, sonrasýnda Mark Anthony ile evlenen Mýsýr Kraliçesi hangisidir?", 2, 
				"VII. Kleopatra$VIII Kleopatra$IX. Kleopatra", "a"));
		cs.add(new SoruCoktanSecmeli("Son Pers Ýmparatoru kimdir?", 3, "Darius I$Darius II$Darius III", "c"));
		
		dy.add(new SoruDogruYanlis("Büyük Ýskender 32 yaþýnda ölmüþtür.", 1, "d"));
		dy.add(new SoruDogruYanlis("Julius Ceaser savaþta ölmüþtür.", 1, "y"));
		dy.add(new SoruDogruYanlis("Kubilay Çin'i fethedip Yuan Hanedanlýðýný kurmuþtur.", 2, "d"));
		dy.add(new SoruDogruYanlis("Cengiz Han'ýn gerçek adý Cengiz'dir.", 2, "y"));
		dy.add(new SoruDogruYanlis("Hinduism'de ki yaratici tanrinin adi Brahma'dir", 3, "d"));	
		
		sorularTekListe(au, bd, cs, dy);
	}
	
	private void sorularTekListe(
			ArrayList<SoruAcikUclu> au,
			ArrayList<SoruBoslukDoldurma> bd,
			ArrayList<SoruCoktanSecmeli> cs,
			ArrayList<SoruDogruYanlis> dy)
	{
		for (SoruAcikUclu soru : au)
			sorular.add((Soru)soru);
		for (SoruBoslukDoldurma soru : bd)
			sorular.add((Soru)soru);
		for (SoruCoktanSecmeli soru : cs)
			sorular.add((Soru)soru);
		for (SoruDogruYanlis soru : dy)
			sorular.add((Soru)soru);
	}

	private void sorulariListeyeAl(ArrayList<SoruAcikUclu> au,
			ArrayList<SoruBoslukDoldurma> bd,
			ArrayList<SoruCoktanSecmeli> cs,
			ArrayList<SoruDogruYanlis> dy)
	{
		for (Soru soru : sorular) {
			switch (soru.getSoruCesidi()) {
				case 'a':	au.add((SoruAcikUclu)soru);	break;
				case 'b':	bd.add((SoruBoslukDoldurma)soru);	break;
				case 'c':	cs.add((SoruCoktanSecmeli)soru);	break;
				case 'd':	dy.add((SoruDogruYanlis)soru);	break;
				default:	break;
			}
		}
	}
	
	//sinavlar
	public void sinavlarDosyaYaz(ArrayList<Sinav> sinavlar) {
		try {
			FileOutputStream fos = new FileOutputStream(fileNameSinavlar);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(sinavlar);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();			
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (StackOverflowError e) {
			e.printStackTrace();
		} catch (NotSerializableException e) {
			e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		
	}
	
}