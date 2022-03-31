package programa.uzduotis;

/**
 * Klasė Dekodavimas. Dekoduoja vektorių pagal kodą kodas.
 */
public class Dekodavimas {

	private boolean[] koeficientai;

	private int m, r;

	private boolean[][] erdve;

	private boolean[][] genMatrica;

	/**
	 * Konstruktorius.
	 *
	 * @param kodas kodas, pagal kurį dekoduojama
	 */
	public Dekodavimas(Kodas kodas) {
		setKodas(kodas);
	}

	/**
	 * Dekoduoja vektorių.
	 *
	 * @param užkoduotas užkoduotas vektorius
	 * @return pradinė informacija
	 */
	public boolean[] dekoduoti(boolean[] užkoduotas) {
		int eiluciuSkaicius = genMatrica.length;
		return rastiRKoeficientus(r, užkoduotas, eiluciuSkaicius);
	}

	/**
	 * Randa koeficientus.
	 *
	 * @param dabartinisR         einamasis r parametras
	 * @param vektorius           užkoduotas vektorius
	 * @param koeficientuIndeksas koeficientų indeksų ruožas
	 * @return dekoduotas vektorius
	 */
	private boolean[] rastiRKoeficientus(int dabartinisR, boolean[] vektorius, int koeficientuIndeksas) {
		int tKiekis = Matematika.laipsnis(2, m - dabartinisR);
		int tIlgis = m - dabartinisR;
		boolean[][] t = new boolean[tKiekis][tIlgis];
		int n = Matematika.laipsnis(2, m);
		boolean[][] w = new boolean[tKiekis][n];

		// Formuojame t rinkinius
		for (int i = 0; i < tKiekis; i++) {
			t[i] = Matematika.iDvejetaine(tIlgis, i);
		}
		int[][] sandauguIndeksai = Matematika.indeksųDeriniai(m, dabartinisR);
		int[] l = new int[tIlgis];
		boolean[] balsavimas = new boolean[tKiekis];
		boolean[][] sandaugos = new boolean[sandauguIndeksai.length][n];
		boolean[][] vektoriai = new boolean[dabartinisR > 0 ? dabartinisR : 1][n];
		for (int i = 0; i < sandauguIndeksai.length; i++) {
			int[] indeksai = sandauguIndeksai[i];
			int lIndeksas = 0;
			for (int k = 0; k < indeksai.length; k++) {
				vektoriai[k] = genMatrica[sandauguIndeksai[i][k] + 1];
				indeksai[k]++;
			}

			// Formuojame l aibę
			for (int j = 1; j <= m; j++) {
				if (!Matematika.arYraMasyve(indeksai, j)) {
					l[lIndeksas] = j;
					lIndeksas++;
				}
			}

// Formuojame w vektorius
			for (int tIndeksas = 0; tIndeksas < tKiekis; tIndeksas++) {
				for (int j = 0; j < erdve.length; j++) {
					boolean vienetas = true;
					for (int k = 0; k < tIlgis; k++) {
						if (erdve[j][l[k] - 1] != t[tIndeksas][k]) {
							vienetas = false;
							break;
						}
					}
					w[tIndeksas][j] = vienetas;
				}
			}
			for (int j = 0; j < tKiekis; j++) {
				balsavimas[j] = Matematika.skaliarine(vektorius, w[j]);
			}

			// Skaičiuojame, kiek nulių
			int kiekNuliu = 0;
			for (int j = 0; j < tKiekis; j++) {
				if (!balsavimas[j]) {
					kiekNuliu++;
				}
			}
			koeficientai[koeficientuIndeksas - sandauguIndeksai.length + i] = kiekNuliu < (tKiekis - kiekNuliu);
			// Skaičiuojame r-gubas sandaugas
			sandaugos[i] = Matematika.daugybaN(vektoriai);
// Visas sandaugas padauginame iš gauto koeficiento
			for (int j = 0; j < n; j++) {
				sandaugos[i][j] = sandaugos[i][j] && koeficientai[koeficientuIndeksas - sandauguIndeksai.length + i];
			}
		}

		// Sudedame gautus vektorius tarpusavyje ir su koduojamu vektoriumi
		for (int i = 0; i < sandauguIndeksai.length; i++) {
			vektorius = Matematika.sudėtis(vektorius, sandaugos[i]);
		}
		if (dabartinisR > 0) {
			return rastiRKoeficientus(dabartinisR - 1, vektorius, koeficientuIndeksas - sandauguIndeksai.length);
		} else {
			return koeficientai.clone();
		}
	}

	/**
	 * Nustato kodą.
	 *
	 * @param kodas naujas kodas
	 */
	public void setKodas(Kodas kodas) {
		genMatrica = kodas.getGenMatrica();
		m = kodas.getM();
		r = kodas.getR();
		erdve = kodas.getErdvė();
		koeficientai = new boolean[genMatrica.length];
	}
}