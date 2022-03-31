package programa.uzduotis;

/**
 * Klasė Matematika. Veiksmai su matricomis, vektoriais, reikalingos
 * kombinatorikos funkcijos.
 */
public class Matematika {

	/**
	 * Nepilnas Faktorialas (dauginama nurodytą skai�?ių kartų).
	 *
	 * @param skaicius skai�?ius, kurio nepilnas faktorialas ieškomas
	 * @param sustoti  nurodo, kada baigti dauginti
	 * @return faktorialo rezultatas
	 */
	public static int faktorialasIki(int skaicius, int sustoti) {
		int rezultatas = 1;
		while (skaicius > sustoti) {
			rezultatas *= skaicius;
			skaicius--;
		}
		return rezultatas;
	}

	/**
	 * Deriniai
	 *
	 * @param n iš kiek elementų renkamės
	 * @param k kiek elementų renkamės
	 * @return derinių skai�?ius
	 */
	public static int deriniai(int n, int k) {
		int rezultatas = faktorialasIki(n, n - k) / faktorialasIki(k, 1);
		return rezultatas;
	}

	/**
	 * Dešimtainio skai�?iaus reprezentacija dvejetainiu.
	 *
	 * @param n           dvejetainio skai�?iaus ilgis
	 * @param dešimtainis dešimtainis skai�?ius
	 * @return the dvejetainis skai�?ius (true=1, false=0)
	 */
	public static boolean[] iDvejetaine(int n, int dešimtainis) {
		boolean[] dvejetainis = new boolean[n];
		n--;
		int liekana;
		while (dešimtainis > 0) {
			liekana = dešimtainis % 2;
			dvejetainis[n] = liekana == 1;
			dešimtainis = dešimtainis / 2;
			n--;
		}
		for (int i = 0; i < n; i++) {
			dvejetainis[i] = false;
		}
		return dvejetainis;
	}

	/**
	 * Vektorių daugyba.
	 *
	 * @param eilutė1 vienas dauginamasis
	 * @param eilutė2 antras dauginamasis
	 * @return dviejų vektorių sandauga
	 */
	public static boolean[] daugyba(boolean[] eilutė1, boolean[] eilutė2) {
		int n = eilutė1.length;
		boolean[] sandauga = new boolean[n];
		for (int i = 0; i < n; i++) {
			sandauga[i] = eilutė1[i] && eilutė2[i];
		}
		return sandauga;
	}

	/**
	 * Vektorių Sudėtis moduliu 2.
	 *
	 * @param eilutė1 pirmas dėmuo
	 * @param eilutė2 antras dėmuo
	 * @return sudėties rezultatas
	 */
	public static boolean[] sudėtis(boolean[] eilutė1, boolean[] eilutė2) {
		int n = eilutė1.length;
		boolean[] suma = new boolean[n];
		for (int i = 0; i < n; i++) {
			suma[i] = eilutė1[i] ^ eilutė2[i];
		}
		return suma;
	}

	/**
	 * Derinių kombinacijos.
	 *
	 * @param n iš kiek elementų renkamės
	 * @param k kiek elementų renkamės
	 * @return visi įmanomi k ilgio indeksų 0...(n-1) rinkiniai
	 */
	public static int[][] indeksųDeriniai(int n, int k) {
		if (k == 0) {
			int[][] nulinis = new int[1][1];
			nulinis[0][0] = -1;
			return nulinis;
		}
		int galimybės = deriniai(n, k);
		int[][] kombinacijos = new int[galimybės][k];
		int indeksas = 0;

		int[] skaitliukas = new int[k];

		for (int i = 0; i < k; i++) {
			skaitliukas[i] = i;
		}
		ciklas: while (true) {
			// Pradžia
			for (int i = 1; i < k; i++) {
				if (skaitliukas[i] >= n - (k - 1 - i)) {
					skaitliukas[i] = skaitliukas[i - 1] + 1;
				}
			}

			// Tikrinimas
			for (int i = 0; i < k; i++) {
				if (skaitliukas[i] < n) {
					continue;
				} else {
					break ciklas;
				}
			}

			// Masyvo formavimas
			kombinacijos[indeksas] = skaitliukas.clone();
			indeksas++;

			// Skaitliuko elementu padidinimas
			skaitliukas[k - 1]++;
			for (int i = k - 1; i >= 1; i--) {
				if (skaitliukas[i] >= n - (k - 1 - i)) {
					skaitliukas[i - 1]++;
				}
			}
		}

		return kombinacijos;
	}

	/**
	 * N vektorių daugyba.
	 *
	 * @param vektoriai vektorių rinkinys
	 * @return sandaugos rezultatas
	 */
	public static boolean[] daugybaN(boolean[][] vektoriai) {
		int ilgis = vektoriai[0].length;
		boolean[] sandauga = new boolean[ilgis];
		for (int i = 0; i < ilgis; i++) {
			sandauga[i] = true;
		}
		for (int i = 0; i < vektoriai.length; i++) {
			sandauga = daugyba(sandauga, vektoriai[i]);
		}
		return sandauga;
	}

	/**
	 * Skaliarinė daugyba.
	 *
	 * @param vektorius1 pirmas vektorius
	 * @param vektorius2 antras vektorius
	 * @return rezultatas
	 */
	public static boolean skaliarine(boolean[] vektorius1, boolean[] vektorius2) {
		boolean[] sandauga = daugyba(vektorius1, vektorius2);
		boolean rezultatas = sandauga[0];
		for (int i = 1; i < sandauga.length; i++) {
			rezultatas = rezultatas ^ sandauga[i];
		}
		return rezultatas;
	}

	/**
	 * Laipsnio funkcija (pagrindas ir rodiklis yra sveikieji skai�?iai).
	 *
	 * @param pagrindas laipsnio pagrindas
	 * @param rodiklis  laipsnio rodiklis
	 * @return pagrindas^rodiklis
	 */
	public static int laipsnis(int pagrindas, int rodiklis) {
		if (rodiklis == 0) {
			return 1;
		}
		int rezultatas = pagrindas;
		for (int i = 0; i < rodiklis - 1; i++) {
			rezultatas *= pagrindas;
		}
		return rezultatas;
	}

	/**
	 * Tikrina, ar elementas egzistuoja masyve.
	 *
	 * @param masyvas   skai�?ių rinkinys
	 * @param elementas tikrinamas elementas
	 * @return true, jei egzistuoja
	 */
	public static boolean arYraMasyve(int[] masyvas, int elementas) {
		boolean yra = false;
		for (int i = 0; i < masyvas.length; i++) {
			if (masyvas[i] == elementas) {
				yra = true;
				break;
			}
		}
		return yra;
	}

	/**
	 * Prailginti - naudojamas viename iš generuojan�?ios matricos sudarymo būdų,
	 * prailgina vienetų sekas nuliais iki reikiamo ilgio.
	 *
	 * @param n               ilgis
	 * @param vienetaiIšEilės skai�?ius vienetų, einan�?ių iš eilės
	 * @return reikiamo ilgio vektorius
	 */
	public static boolean[] prailginti(int n, int vienetaiIšEilės) {
		int iteracijos = n / vienetaiIšEilės / 2;
		boolean[] masyvas = new boolean[n];
		int indeksas = 0;
		for (int i = 0; i < iteracijos; i++) {
			for (int j = 0; j < vienetaiIšEilės; j++) {
				masyvas[indeksas] = true;
				indeksas++;
			}
			for (int j = 0; j < vienetaiIšEilės; j++) {
				masyvas[indeksas] = false;
				indeksas++;
			}
		}
		return masyvas;
	}

	/**
	 * Generuoja atsitiktinį vektorių.
	 *
	 * @param ilgis vektoriaus ilgis
	 * @return sugeneruotas vektorius
	 */
	public static boolean[] generuotiVektorių(int ilgis) {
		boolean[] vektorius = new boolean[ilgis];
		for (int i = 0; i < vektorius.length; i++) {
			if (Math.random() <= 0.5) {
				vektorius[i] = true;
			} else {
				vektorius[i] = false;
			}
		}
		return vektorius;
	}

	/**
	 * Iš dvejetainės eilutės konvertuoja į loginį vektorių.
	 *
	 * @param vektorius eilutės tipo vektorius
	 * @return loginis vektorius
	 */
	public static boolean[] išEilutėsĮLoginį(String vektorius) {
		int ilgis = vektorius.length();
		boolean[] vLoginis = new boolean[ilgis];
		for (int i = 0; i < ilgis; i++) {
			vLoginis[i] = vektorius.charAt(i) == '1';
		}
		return vLoginis;
	}

	/**
	 * Iš loginio vektoriaus konvertuoja į eilutės tipo vektorių.
	 *
	 * @param vLoginis loginio tipo vektorius
	 * @return eilutės tipo vektorius
	 */
	public static String išLoginioĮEilutę(boolean[] vLoginis) {
		String vektorius = "";
		int ilgis = vLoginis.length;
		for (int i = 0; i < ilgis; i++) {
			char simbolis = '1';
			if (!vLoginis[i]) {
				simbolis = '0';
			}
			vektorius = vektorius + simbolis;
		}
		return vektorius;

	}
}
