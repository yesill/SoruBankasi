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
		dosya var ve soru sayisi eksik yada fazla ise bir islem yapm�yoruz.
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
			//dosya var ama i�i bo� ise bu varsay�lan sorular� ekliyor.
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
		au.add(new SoruAcikUclu("B�y�k �skender kimdir? K�saca anlat�n.", 1));
		au.add(new SoruAcikUclu("Sokrates kimdir? K�saca anlat�n.", 1));
		au.add(new SoruAcikUclu("Gaius Julius Caesar kimdir? K�saca anlat�n.", 2));
		au.add(new SoruAcikUclu("Satrapl�k sistemi nedir k�saca a��klay�n.", 2));
		au.add(new SoruAcikUclu("Platon'un Ma�ara Deneyini a��klay�n.", 3));
		
		bd.add(new SoruBoslukDoldurma("B�y�k �skender babas� II. Filip'ten ......... taht�n� devralm��t�r.", 1, "Makedonya"));
		bd.add(new SoruBoslukDoldurma(".....'in a��l�m� international network t�r.", 1, " internet"));
		bd.add(new SoruBoslukDoldurma("..... Roma Ordular�n�n en geni� birimidir.", 2, "lejyon"));
		bd.add(new SoruBoslukDoldurma("Sokrates .....'da do�mu�tur.", 2, "Atina"));
		bd.add(new SoruBoslukDoldurma(".......... Budizm'in dini liderine verilen isimdir.", 3, "Dalai Lama"));
		
		cs.add(new SoruCoktanSecmeli("Atat�rk'�n do�um tarihi?", 1, "1880$1881$1882", "b"));
		cs.add(new SoruCoktanSecmeli("Roma Devletinin resmi dili hangisidir?", 1, "Latince$�talyanca$Flemenk�e", "a"));
		cs.add(new SoruCoktanSecmeli("Pers �mparatorlu�unu kim y�km��t�r?", 2, "Scipio Africanus$Hannibal$B�y�k �skender", "c"));
		cs.add(new SoruCoktanSecmeli("Ceaser'la ili�ki ya�an, sonras�nda Mark Anthony ile evlenen M�s�r Krali�esi hangisidir?", 2, 
				"VII. Kleopatra$VIII Kleopatra$IX. Kleopatra", "a"));
		cs.add(new SoruCoktanSecmeli("Son Pers �mparatoru kimdir?", 3, "Darius I$Darius II$Darius III", "c"));
		
		dy.add(new SoruDogruYanlis("B�y�k �skender 32 ya��nda �lm��t�r.", 1, "d"));
		dy.add(new SoruDogruYanlis("Julius Ceaser sava�ta �lm��t�r.", 1, "y"));
		dy.add(new SoruDogruYanlis("Kubilay �in'i fethedip Yuan Hanedanl���n� kurmu�tur.", 2, "d"));
		dy.add(new SoruDogruYanlis("Cengiz Han'�n ger�ek ad� Cengiz'dir.", 2, "y"));
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