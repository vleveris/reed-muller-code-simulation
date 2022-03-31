package programa.uzduotis;

/**
 * Klasė Kanalas. Siun�?ia dvejetainius vektorius ir pagal klaidos tikimybę
 * simbolius iškraipo.
 */
public class Kanalas {

	private double klaidosTikimybė;

	private int[] klaidos;

	private int klaidųSkaičius;

	/**
	 * Konstruktorius.
	 *
	 * @param klaidosTikimybė klaidos tikimybė
	 */
	public Kanalas(double klaidosTikimybė) {
		this.klaidosTikimybė = klaidosTikimybė;
	}

	/**
	 * Siun�?ia vektorių.
	 *
	 * @param vektorius siun�?iamas vektorius
	 * @return išsiųstas vektorius
	 */
	public boolean[] siųsti(boolean[] vektorius) {
		int ilgis = vektorius.length;
		boolean[] išKanalo = new boolean[ilgis];
		klaidos = new int[ilgis];
		klaidųSkaičius = 0;
		for (int i = 0; i < ilgis; i++) {
			if (Math.random() < klaidosTikimybė) {
				išKanalo[i] = !vektorius[i];
				klaidos[klaidųSkaičius] = i + 1;
				klaidųSkaičius++;
			} else {
				išKanalo[i] = vektorius[i];
			}
		}
		return išKanalo;
	}

	/**
	 * Gražina klaidų pozicijas.
	 *
	 * @return klaidų pozicijos
	 */
	public int[] getKlaidos() {
		int[] tikKlaidos = new int[klaidųSkaičius];
		System.arraycopy(klaidos, 0, tikKlaidos, 0, klaidųSkaičius);
		return tikKlaidos;
	}

	/**
	 * Nustato klaidos tikimybę.
	 *
	 * @param klaidosTikimybė nauja klaidos tikimybė
	 */
	public void setKlaidosTikimybė(double klaidosTikimybė) {
		this.klaidosTikimybė = klaidosTikimybė;
	}

	/**
	 * Gauna klaidos tikimybę.
	 *
	 * @return klaidos tikimybė
	 */
	public double getKlaidosTikimybė() {
		return klaidosTikimybė;
	}

	/**
	 * Formuoja klaidų masyvą lyginant siųstą ir iškraipytą vektorius.
	 *
	 * @param v           pradinis vektorius
	 * @param vIškreiptas galimai iškreiptas vektorius
	 * @return nesutampan�?ios pozicijos
	 */
	public int[] formuotiKlaidųMasyvą(boolean[] v, boolean[] vIškreiptas) {
		int klaidųKiekis = 0;
		int[] pozicijos = new int[v.length];
		for (int i = 0; i < v.length; i++) {
			if (v[i] != vIškreiptas[i]) {
				pozicijos[klaidųKiekis] = i + 1;
				klaidųKiekis++;
			}
		}
		int[] tikKlaidos = new int[klaidųKiekis];
		System.arraycopy(pozicijos, 0, tikKlaidos, 0, klaidųKiekis);
		return tikKlaidos;
	}
}
