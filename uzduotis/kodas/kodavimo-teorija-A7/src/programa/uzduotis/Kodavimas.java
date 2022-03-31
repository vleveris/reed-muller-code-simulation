package programa.uzduotis;

/**
 * Klasė Kodavimas. Užkoduoja vektorių pagal kodą kodas.
 */
public class Kodavimas {
	private Kodas kodas;

	/**
	 * Konstruktorius.
	 *
	 * @param kodas kodas, pagal kurį koduojami vektoriai
	 */
	public Kodavimas(Kodas kodas) {
		this.kodas = kodas;
	}

	/**
	 * Koduoja vektorių.
	 *
	 * @param vektorius vektorius, kurį reikia užkoduoti
	 * @return kodo žodis
	 */
	public boolean[] užkoduoti(boolean[] vektorius) {
		boolean[][] genMatrica = kodas.getGenMatrica();
		int eilutėsIlgis = genMatrica[0].length;
		int eilučiųSkaičius = vektorius.length;
		boolean[][] žodžiai = new boolean[eilučiųSkaičius][eilutėsIlgis];
		for (int i = 0; i < eilučiųSkaičius; i++) {
			for (int j = 0; j < eilutėsIlgis; j++) {
				žodžiai[i][j] = genMatrica[i][j] && vektorius[i];
			}
		}
		boolean[] rezultatas = new boolean[eilutėsIlgis];
		rezultatas = žodžiai[0];
		for (int i = 1; i < eilučiųSkaičius; i++) {
			rezultatas = Matematika.sudėtis(rezultatas, žodžiai[i]);
		}
		return rezultatas;
	}

	/**
	 * Nustato kodą.
	 *
	 * @param kodas naujas kodas
	 */
	public void setKodas(Kodas kodas) {
		this.kodas = kodas;
	}

	/**
	 * Gražina užkoduoto žodžio ilgį.
	 *
	 * @return užkoduoto žodžio ilgis
	 */
	public int getIlgis() {
		return kodas.getGenMatrica()[0].length;
	}
}
