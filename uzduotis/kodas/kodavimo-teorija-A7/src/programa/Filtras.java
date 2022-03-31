package programa;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

class Filtras extends DocumentFilter {
	private final String neleisti;
	private boolean filtruoti = true;
	private int limitas = 0;

	public Filtras(String neleisti, int limitas) {
		this.neleisti = neleisti;
		this.limitas = limitas;
	}

	public boolean isFiltruoti() {
		return filtruoti;
	}

	public void setFiltruoti(boolean filtruoti) {
		this.filtruoti = filtruoti;
	}

	@Override
	public void insertString(FilterBypass fb, int pozicija, String tekstas, AttributeSet atributai)
			throws BadLocationException {
		if (filtruoti) {
			tekstas = tekstas.replaceAll(neleisti, "");
		}
		int tekstoIlgis = fb.getDocument().getLength();
		int virsutinis = tekstoIlgis + tekstas.length() - limitas;
		if (virsutinis > 0) {
			tekstas = tekstas.substring(0, tekstas.length() - virsutinis);
		}
		super.insertString(fb, pozicija, tekstas, atributai);
	}

	@Override
	public void replace(FilterBypass fb, int pozicija, int ilgis, String tekstas, AttributeSet atributai)
			throws BadLocationException {
		if (filtruoti) {
			tekstas = tekstas.replaceAll(neleisti, "");
		}
		int tekstoIlgis = fb.getDocument().getLength();
		int virsutinis = tekstoIlgis + tekstas.length() - limitas - ilgis;
		if (virsutinis > 0) {
			tekstas = tekstas.substring(0, tekstas.length() - virsutinis);
		}
		super.replace(fb, pozicija, ilgis, tekstas, atributai);
	}

	public void setLimitas(int limitas) {
		this.limitas = limitas;
	}

	public int getLimitas() {
		return limitas;
	}
}