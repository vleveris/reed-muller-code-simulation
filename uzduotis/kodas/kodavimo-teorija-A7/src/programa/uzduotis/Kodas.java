package programa.uzduotis;

/**
 * Klasė Kodas. Nustato kodo parametrus, suformuoja generuojan�?ią matricą.
 */
public class Kodas {

	private final int m, r;

	private int eilučiųSkaičius;

	private final boolean[][] genMatrica;

	private final boolean[][] erdvė;

	private boolean būsena = true;

	private String klaidosPranešimas;

	/**
	 * Konstruktorius.
	 *
	 * @param r r reikšmė
	 * @param m m reikšmė
	 */
	public Kodas(int r, int m) {
		if (m < 0 || r < 0) {
			būsena = false;
			klaidosPranešimas = "Kodo parametrai privalo būti neneigiami.";
		}
		if (r > m) {
			būsena = false;
			klaidosPranešimas = "Kodo parametras r negali viršyti m paramettro.";
		}
		this.m = m;
		this.r = r;
		eilučiųSkaičius = 1;
		for (int i = 0; i < r; i++) {
			eilučiųSkaičius += Matematika.deriniai(m, i + 1);
		}
		int n = Matematika.laipsnis(2, m);
		genMatrica = new boolean[eilučiųSkaičius][n];
		erdvė = new boolean[n][m];
		for (int i = 0; i < n; i++) {
			erdvė[i] = Matematika.iDvejetaine(m, i);
			genMatrica[0][i] = true;
		}
		if (r > 0) {

			for (int i = 1; i <= m; i++) {
				for (int j = 0; j < n; j++) {
					genMatrica[i][j] = !erdvė[j][i - 1];
				}
			}

			// Pateikiami dar du alternatyvūs generuojančios matricos sudarymo būdai

			/*
			 * int[] h = new int[n / 2]; for (int i = 1; i <= m; i++) { int hIndeksas = 0;
			 * for (int j = 0; j < n; j++) { if (!erdvė[j][i - 1]) { h[hIndeksas] = j;
			 * hIndeksas++; } } for (int j = 0; j < n; j++) { genMatrica[i][j] =
			 * Matematika.arYraMasyve(h, j); } }
			 */

			/*
			 * int eilutė = 1; for (int i = n / 2; i >= 1; i /= 2) { genMatrica[eilutė] =
			 * Matematika.prailginti(n, i); eilutė++; }
			 */
		}
		int eilutėsIndeksas = m + 1;
		for (int i = 2; i <= r; i++) {
			int[][] sandaugųIndeksai = Matematika.indeksųDeriniai(m, i);
			for (int eilutė = 0; eilutė < sandaugųIndeksai.length; eilutė++) {
				boolean[][] vektoriai = new boolean[i][n];
				for (int indeksas = 0; indeksas < i; indeksas++) {
					vektoriai[indeksas] = genMatrica[sandaugųIndeksai[eilutė][indeksas] + 1];
				}
				genMatrica[eilutėsIndeksas] = Matematika.daugybaN(vektoriai);
				eilutėsIndeksas++;
			}
		}
	}

	/**
	 * Ggrazina klaidos pranešimą.
	 *
	 * @return klaidos pranešimas
	 */
	public String getKlaidosPranešimas() {
		return klaidosPranešimas;
	}

	/**
	 * Grazina būseną.
	 *
	 * @return būsena
	 */
	public boolean getBūsena() {
		return būsena;
	}

	/**
	 * Grazina generuojan�?ią matricą.
	 *
	 * @return generuojanti matrica
	 */
	public boolean[][] getGenMatrica() {
		return genMatrica;
	}

	/**
	 * Grazina m.
	 *
	 * @return m
	 */
	public int getM() {
		return m;
	}

	/**
	 * Grazina r.
	 *
	 * @return r
	 */
	public int getR() {
		return r;
	}

	/**
	 * Grazina erdvę.
	 *
	 * @return erdvė
	 */
	public boolean[][] getErdvė() {
		return erdvė;
	}

	/**
	 * Grazina eilu�?ių skai�?ių.
	 *
	 * @return eilu�?ių skai�?ius
	 */
	public int getEilučiųSkaičius() {
		return eilučiųSkaičius;
	}
}
