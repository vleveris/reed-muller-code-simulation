package programa.uzduotis;

import java.awt.image.BufferedImage;

/**
 * Klasė Pagalbinis. Skaido eilutę/paveiksliuką į vektorius ir iš vektorių
 * formuoja eilutę/paveiksliuką.
 */
public class Pagalbinis {

	private static final int simbolioDydis = 16; // Vieno simbolio bitų skaičius

	private static final int pikselioDydis = 24; // Vieno pikselio bitų skaičius (RGB po baitą, Alpha nenaudosime).

	/**
	 * Skaido teksta nurodyto ilgio vektoriais.
	 *
	 * @param tekstas skaidomas tekstas
	 * @param n       vektoriaus ilgis
	 * @return n ilgio vektorių rinkinys
	 */
	public static boolean[][] skaidytiTekstą(String tekstas, int n) {
		int[] ascii = new int[tekstas.length()];
		for (int i = 0; i < tekstas.length(); i++) {
			ascii[i] = tekstas.charAt(i);
		}
		return skaidytiSkaičius(ascii, n, simbolioDydis);
	}

	/**
	 * Skaido sveikuosius skaičius vektoriais.
	 *
	 * @param skaičiai     sveikųjų skaičių rinkinys
	 * @param n            vektoriaus ilgis
	 * @param bituSkaičius vieno skaičiaus bitų kiekis
	 * @return n vektorių rinkinys
	 */
	private static boolean[][] skaidytiSkaičius(int[] skaičiai, int n, int bitųSkaičius) {
		int bitai = skaičiai.length * bitųSkaičius;
		int kiek = bitai / n;
		if (bitai % n > 0) {
			kiek++;
		}
		boolean[][] vektoriai = new boolean[kiek][n];
		int ilgis = skaičiai.length;
		int eilutė = 0, stulpelis = 0;
		for (int i = 0; i < ilgis; i++) {
			int simbolis = skaičiai[i];
			for (int j = 0; j < bitųSkaičius; j++) {
				int liekana = Integer.remainderUnsigned(simbolis, 2);
				simbolis = Integer.divideUnsigned(simbolis, 2);
				if (stulpelis == n) {
					eilutė++;
					stulpelis = 0;
				}
				vektoriai[eilutė][stulpelis] = liekana == 1;
				stulpelis++;
			}
		}
		for (int i = stulpelis; i < n; i++) {
			vektoriai[eilutė][stulpelis] = false;
		}
		return vektoriai;
	}

	/**
	 * Iš vektorių formuoja tekstą.
	 *
	 * @param vektoriai vektorių rinkinys
	 * @return tekstas
	 */
	public static String įTekstą(boolean[][] vektoriai) {
		int[] ascii = įSkaičius(vektoriai, simbolioDydis);
		StringBuilder tekstas = new StringBuilder();
		for (int i = 0; i < ascii.length; i++) {
			tekstas.append((char) ascii[i]);
		}
		return tekstas.toString();
	}

	/**
	 * Iš vektorių formuoja skaičių rinkinį.
	 *
	 * @param vektoriai    vektorių rinkinys
	 * @param bituSkaičius vieno skaičiaus bitų kiekis
	 * @return skaičių rinkinys
	 */
	private static int[] įSkaičius(boolean[][] vektoriai, int bitųSkaičius) {
		int eilutė = 0, stulpelis = 0;
		int n = vektoriai[0].length;
		int iki = vektoriai.length * vektoriai[0].length / bitųSkaičius;
		int[] skaičiai = new int[iki];
		for (int i = 0; i < iki; i++) {
			int simbolis = 0;
			for (int j = 0; j < bitųSkaičius; j++) {
				if (stulpelis == n) {
					eilutė++;
					stulpelis = 0;
				}
				int skaitmuo = vektoriai[eilutė][stulpelis] ? 1 : 0;
				simbolis += skaitmuo * Matematika.laipsnis(2, j);
				stulpelis++;
			}
			skaičiai[i] = simbolis;
		}
		return skaičiai;
	}

	/**
	 * Skaido BMP pikselius į n ilgio vektorius.
	 *
	 * @param pav paveiksliukas
	 * @param n   vektoriaus ilgis
	 * @return vektorių rinkinys
	 */
	public static boolean[][] skaidytiBMP(BufferedImage pav, int n) {
		int aukštis = pav.getHeight();
		int plotis = pav.getWidth();
		int[] pikseliai = new int[aukštis * plotis];
		for (int y = 0; y < aukštis; y++) {
			for (int x = 0; x < plotis; x++) {
				pikseliai[y * aukštis + x] = pav.getRGB(x, y);
			}
		}
		return skaidytiSkaičius(pikseliai, n, pikselioDydis);
	}

	/**
	 * Iš vektorių formuoja BMP.
	 *
	 * @param pradinis  pradinis paveiksliukas
	 * @param vektoriai vektorių rinkinys
	 * @return paveiksliukas su pikselių rinkiniu iš vektoriaus
	 */
	public static BufferedImage įBMP(BufferedImage pradinis, boolean[][] vektoriai) {
		int[] pikseliai = įSkaičius(vektoriai, pikselioDydis);
		int plotis = pradinis.getWidth();
		int aukštis = pradinis.getHeight();
		// Alpha nustatome į 0xff000000 (pilnai permatomas)
		int alpha = 255 * 256 * 256 * 256;
		for (int y = 0; y < aukštis; y++) {
			for (int x = 0; x < plotis; x++) {
				pradinis.setRGB(x, y, alpha + pikseliai[y * aukštis + x]);
			}
		}
		return pradinis;
	}
}