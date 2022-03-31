package programa;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.PlainDocument;

import programa.uzduotis.Dekodavimas;
import programa.uzduotis.Kanalas;
import programa.uzduotis.Kodas;
import programa.uzduotis.Kodavimas;
import programa.uzduotis.Matematika;
import programa.uzduotis.Pagalbinis;

/**
 * Klasė Langas.
 */
public class Langas extends JFrame {

	private static final long serialVersionUID = -2531239511135624943L;

	private final int numatytasR = 1;

	private final int numatytasM = 3;

	private Kodas k = new Kodas(numatytasR, numatytasM);

	private final double numatytaTikimybė = 0;

	private final Kanalas kan = new Kanalas(numatytaTikimybė);

	private final Kodavimas kod = new Kodavimas(k);

	private final Dekodavimas dek = new Dekodavimas(k);

	private boolean[] užkoduotas;

	private boolean[] iškraipytas;

	private BufferedImage paveiksliukas, pavNek, pavKod;
	private final JLabel pasirinktasFailas = new JLabel();

	/**
	 * Konstruktorius.
	 */
	public Langas() {
		rodyti();
	}

	/**
	 * Komponentų sukūrimas ir išdėliojimas lange, mygtukų funkcijos.
	 */
	private void rodyti() {
		setTitle("Reed Muller kodas");
		setDefaultCloseOperation(Langas.EXIT_ON_CLOSE);
		JPanel turinys = new JPanel();
		JPanel parametrai = new JPanel(new GridBagLayout());
		JPanel scenarijus1 = new JPanel(new GridBagLayout());
		JPanel scenarijus2 = new JPanel(new GridBagLayout());
		JPanel scenarijus3 = new JPanel(new GridBagLayout());
		JLabel rPavadinimas = new JLabel("r:");
		JTextField rLaukas = new JTextField(10);
		Filtras rFiltras = new Filtras("\\D", 3); // max 3 skaičiai
		Filtras mFiltras = new Filtras("\\D", 3); // max 3 skaičiai
		JLabel mPavadinimas = new JLabel("m:");
		JTextField mLaukas = new JTextField(10);
		((PlainDocument) rLaukas.getDocument()).setDocumentFilter(rFiltras);
		((PlainDocument) mLaukas.getDocument()).setDocumentFilter(mFiltras);
		rLaukas.setText(String.valueOf(k.getR()));
		mLaukas.setText(String.valueOf(k.getM()));
		mPavadinimas.setLabelFor(mLaukas);
		rPavadinimas.setLabelFor(rLaukas);
		JLabel tikimybėsPavadinimas = new JLabel("Klaidos tikimybė:");
		JTextField tikimybėsLaukas = new JTextField(15);
		tikimybėsPavadinimas.setLabelFor(tikimybėsLaukas);
		tikimybėsLaukas.setText(String.valueOf(kan.getKlaidosTikimybė()));
		Filtras vektoriausFiltras = new Filtras("[^0^1]", k.getEilučiųSkaičius());
		JLabel įvestiVektorių = new JLabel("Įveskite Ilgio " + vektoriausFiltras.getLimitas()
				+ " vektorių (lauką palikus tuščią, vektorius bus sugeneruotas):");
		JTextField vektorius = new JTextField(30);
		((PlainDocument) vektorius.getDocument()).setDocumentFilter(vektoriausFiltras);
		įvestiVektorių.setLabelFor(vektorius);
		JButton nustatyti = new JButton("Nustatyti");
		JButton koduoti = new JButton("Koduoti");
		JLabel užkPavadinimas = new JLabel("Užkoduotas vektorius:");
		JTextField užkLaukas = new JTextField(50);
		užkPavadinimas.setLabelFor(užkLaukas);
		užkLaukas.setEditable(false);
		JLabel klaidųPavadinimas = new JLabel("Klaidos:");
		JTextArea klaidųLaukas = new JTextArea(2, 20);
		klaidųLaukas.setEditable(false);
		klaidųPavadinimas.setLabelFor(klaidųLaukas);
		JLabel gautoPavadinimas = new JLabel("Gautas vektorius:");
		JTextField gautoLaukas = new JTextField(50);
		gautoPavadinimas.setLabelFor(gautoLaukas);
		Filtras gautoFiltras = new Filtras("[^0^1]", kod.getIlgis());
		((PlainDocument) gautoLaukas.getDocument()).setDocumentFilter(gautoFiltras);
		JButton siųsti = new JButton("Siųsti");
		JButton keisti = new JButton("Keisti");
		JLabel dekPavadinimas = new JLabel("Dekoduotas vektorius:");
		JTextField dekLaukas = new JTextField(30);
		dekLaukas.setEditable(false);
		dekPavadinimas.setLabelFor(dekLaukas);
		JButton dekoduoti = new JButton("Dekoduoti");
		JLabel tekstoPavadinimas = new JLabel("Įveskite tekstą:");
		JTextArea tekstoLaukas = new JTextArea(5, 40);
		tekstoPavadinimas.setLabelFor(tekstoLaukas);
		JLabel kanTekstoPavadinimas = new JLabel("Iš kanalo gautas tekstas:");
		JTextArea kanTekstoLaukas = new JTextArea(5, 40);
		kanTekstoPavadinimas.setLabelFor(kanTekstoLaukas);
		JLabel kodTekstoPavadinimas = new JLabel("Koduotas tekstas:");
		JTextArea kodTekstoLaukas = new JTextArea(5, 40);
		kodTekstoPavadinimas.setLabelFor(kodTekstoLaukas);
		kanTekstoLaukas.setEditable(false);
		kodTekstoLaukas.setEditable(false);
		tekstoLaukas.setLineWrap(true);
		kanTekstoLaukas.setWrapStyleWord(true);
		kodTekstoLaukas.setLineWrap(true);
		JButton koduotiTekstą = new JButton("Koduoti");
		JButton pav = new JButton("Pasirinkti paveiksliuką...");
		JLabel pasirinktas = new JLabel("Pasirinktas:");
		JLabel nekoduotas = new JLabel("Nekoduotas:");
		JLabel koduotas = new JLabel("Koduotas:");
		JLabel pasirinktasPav = new JLabel();
		JLabel nekoduotasPav = new JLabel();
		JLabel koduotasPav = new JLabel();
		JButton piešti = new JButton("Piešti");
		nustatyti.addActionListener(nustatytiE -> {
			Kodas naujas = new Kodas(Integer.parseInt(rLaukas.getText()), Integer.parseInt(mLaukas.getText()));
			if (!naujas.getBūsena()) {
				JOptionPane.showMessageDialog(this, naujas.getKlaidosPranešimas(), "Klaidingi parametrai",
						JOptionPane.ERROR_MESSAGE);
			} else if (k.getR() != naujas.getR() || k.getM() != naujas.getM()) {
				k = naujas;
				rLaukas.setText(String.valueOf(k.getR()));
				mLaukas.setText(String.valueOf(k.getM()));
				vektoriausFiltras.setLimitas(k.getEilučiųSkaičius());
				įvestiVektorių.setText("Įveskite ilgio " + vektoriausFiltras.getLimitas()
						+ " vektorių (lauką palikus tuščią, vektorius bus sugeneruotas):");
				kod.setKodas(k);
				dek.setKodas(k);
				užkLaukas.setText("");
				vektorius.setText("");
				gautoLaukas.setText("");
				klaidųLaukas.setText("");
				gautoFiltras.setLimitas(kod.getIlgis());
				užkoduotas = null;
				iškraipytas = null;
				dekLaukas.setText("");
			}
			double senaTikimybė = kan.getKlaidosTikimybė();
			double naujaTikimybė;
			try {
				naujaTikimybė = Double.parseDouble(tikimybėsLaukas.getText());
			} catch (NumberFormatException x) {
				naujaTikimybė = senaTikimybė;
			}
			kan.setKlaidosTikimybė(naujaTikimybė);
			tikimybėsLaukas.setText(String.valueOf(kan.getKlaidosTikimybė()));
		});
		koduoti.addActionListener(koduotiE -> {
			String vTekstas = vektorius.getText();
			int vIlgis = k.getEilučiųSkaičius();
			boolean[] įvestas = new boolean[vIlgis];
			if (vTekstas == null || vTekstas.length() == 0) {
				įvestas = Matematika.generuotiVektorių(vIlgis);
				vektorius.setText(Matematika.išLoginioĮEilutę(įvestas));
			} else if (vTekstas.length() < vIlgis) {
				JOptionPane.showMessageDialog(this, "Neteisingas vektoriaus ilgis.", "KLAIDA",
						JOptionPane.ERROR_MESSAGE);
				return;
			} else {
				įvestas = Matematika.išEilutėsĮLoginį(vTekstas);
			}
			užkoduotas = kod.užkoduoti(įvestas);
			užkLaukas.setText(Matematika.išLoginioĮEilutę(užkoduotas));
			gautoLaukas.setText("");
			klaidųLaukas.setText("");
			dekLaukas.setText("");
		});
		siųsti.addActionListener(siųstiE -> {
			if (užkoduotas == null) {
				JOptionPane.showMessageDialog(this, "Nėra užkoduoto vektoriaus", "KLAIDA", JOptionPane.ERROR_MESSAGE);
				return;
			}
			iškraipytas = kan.siųsti(užkoduotas);
			gautoLaukas.setText(Matematika.išLoginioĮEilutę(iškraipytas));
			int[] klaidos = kan.getKlaidos();
			vaizduotiKlaidas(klaidos, klaidųLaukas);
			dekLaukas.setText("");
		});
		keisti.addActionListener(keistiE -> {
			if (užkoduotas == null) {
				JOptionPane.showMessageDialog(this, "Nėra užkoduoto vektoriaus", "KLAIDA", JOptionPane.ERROR_MESSAGE);
				return;
			}
			String gautasTekstas = gautoLaukas.getText();
			if (gautasTekstas.length() < kod.getIlgis()) {
				JOptionPane.showMessageDialog(this, "Neteisingas vektoriaus ilgis", "KLAIDA",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			iškraipytas = Matematika.išEilutėsĮLoginį(gautoLaukas.getText());
			int[] klaidos = kan.formuotiKlaidųMasyvą(užkoduotas, iškraipytas);
			vaizduotiKlaidas(klaidos, klaidųLaukas);
			dekLaukas.setText("");
		});
		dekoduoti.addActionListener(dekoduotiE -> {
			if (iškraipytas == null) {
				JOptionPane.showMessageDialog(this, "Nėra iškraipyto vektoriaus.", "KLAIDA", JOptionPane.ERROR_MESSAGE);
				return;
			}
			dekLaukas.setText(Matematika.išLoginioĮEilutę(dek.dekoduoti(iškraipytas)));
		});
		koduotiTekstą.addActionListener(koduotiTekstaE -> {
			String tekstas = tekstoLaukas.getText();
			if (tekstas.length() == 0) {
				kanTekstoLaukas.setText("");
				kodTekstoLaukas.setText("");
			} else {
				boolean[][] vektoriai = Pagalbinis.skaidytiTekstą(tekstas, k.getEilučiųSkaičius());
				boolean[][] išKanalo = new boolean[vektoriai.length][];
				for (int i = 0; i < vektoriai.length; i++) {
					išKanalo[i] = kan.siųsti(vektoriai[i]);
				}
				kanTekstoLaukas.setText(Pagalbinis.įTekstą(išKanalo));
				for (int i = 0; i < vektoriai.length; i++) {
					vektoriai[i] = kod.užkoduoti(vektoriai[i]);
					išKanalo[i] = dek.dekoduoti(kan.siųsti(vektoriai[i]));
				}
				kodTekstoLaukas.setText(Pagalbinis.įTekstą(išKanalo));
			}
		});
		pav.addActionListener(pavE -> {
			atidarytiPaveiksliuką();
		});
		piešti.addActionListener(pieštiE -> {
			if (paveiksliukas == null) {
				JOptionPane.showMessageDialog(this, "Nepasirinktas paveiksliukas.", "KLAIDA",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			pasirinktasPav.setIcon(new ImageIcon(paveiksliukas));
			boolean[][] vektoriai = Pagalbinis.skaidytiBMP(paveiksliukas, k.getEilučiųSkaičius());
			boolean[][] išKanalo = new boolean[vektoriai.length][];
			for (int i = 0; i < vektoriai.length; i++) {
				išKanalo[i] = kan.siųsti(vektoriai[i]);
			}
			nekoduotasPav.setIcon(new ImageIcon(Pagalbinis.įBMP(pavNek, išKanalo)));
			for (int i = 0; i < vektoriai.length; i++) {
				vektoriai[i] = kod.užkoduoti(vektoriai[i]);
				išKanalo[i] = dek.dekoduoti(kan.siųsti(vektoriai[i]));
			}
			koduotasPav.setIcon(new ImageIcon(Pagalbinis.įBMP(pavKod, išKanalo)));

		});

		// Elementų išdėliojimas lange
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		parametrai.add(rPavadinimas, c);
		c.gridx = 1;
		parametrai.add(rLaukas, c);
		c.gridx = 2;
		parametrai.add(mPavadinimas, c);
		c.gridx = 3;
		parametrai.add(mLaukas, c);
		c.gridx = 4;
		parametrai.add(tikimybėsPavadinimas, c);
		c.gridx = 5;
		parametrai.add(tikimybėsLaukas, c);
		c.gridx = 7;
		parametrai.add(nustatyti, c);
		c.gridx = 0;
		scenarijus1.add(įvestiVektorių, c);
		c.gridx = 1;
		c.gridwidth = 3;
		scenarijus1.add(vektorius, c);
		c.gridx = 4;
		c.gridwidth = 1;
		scenarijus1.add(koduoti, c);
		c.gridx = 0;
		c.gridy = 1;
		scenarijus1.add(užkPavadinimas, c);
		c.gridx = 1;
		c.gridwidth = 3;
		scenarijus1.add(užkLaukas, c);
		c.gridwidth = 1;
		c.gridx = 4;
		scenarijus1.add(siųsti, c);
		c.gridy = 2;
		c.gridx = 0;
		scenarijus1.add(gautoPavadinimas, c);
		c.gridx = 1;
		c.gridwidth = 3;
		scenarijus1.add(gautoLaukas, c);
		c.gridwidth = 1;
		c.gridx = 4;
		scenarijus1.add(keisti, c);
		c.gridy = 3;
		c.gridx = 0;
		scenarijus1.add(klaidųPavadinimas, c);
		c.gridx = 1;
		scenarijus1.add(new JScrollPane(klaidųLaukas), c);
		c.gridy = 4;
		c.gridx = 0;
		scenarijus1.add(dekPavadinimas, c);
		c.gridwidth = 3;
		c.gridx = 1;
		scenarijus1.add(dekLaukas, c);
		c.gridwidth = 1;
		c.gridx = 4;
		scenarijus1.add(dekoduoti, c);
		c.gridx = 0;
		c.gridy = 0;
		scenarijus2.add(tekstoPavadinimas, c);
		c.gridx = 1;
		c.gridwidth = 3;
		c.gridheight = 3;
		scenarijus2.add(new JScrollPane(tekstoLaukas), c);
		c.gridy = 3;
		scenarijus2.add(new JScrollPane(kanTekstoLaukas), c);
		c.gridy = 6;
		scenarijus2.add(new JScrollPane(kodTekstoLaukas), c);
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 0;
		scenarijus2.add(kodTekstoPavadinimas, c);
		c.gridy = 3;
		scenarijus2.add(kanTekstoPavadinimas, c);
		c.gridy = 0;
		c.gridx = 4;
		c.gridheight = 3;
		scenarijus2.add(koduotiTekstą, c);
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 0;
		scenarijus3.add(pav, c);
		c.gridx = 1;
		scenarijus3.add(piešti, c);
		c.gridx = 2;
		scenarijus3.add(pasirinktasFailas, c);
		c.gridy = 2;
		c.gridx = 0;
		c.weightx = 0;
		c.weighty = 0;
		c.fill = GridBagConstraints.NONE;
		scenarijus3.add(pasirinktas, c);
		c.gridx = 1;
		scenarijus3.add(pasirinktasPav, c);
		c.gridy = 4;
		scenarijus3.add(nekoduotasPav, c);
		c.gridy = 7;
		scenarijus3.add(koduotasPav, c);
		c.gridx = 0;
		scenarijus3.add(koduotas, c);
		c.gridy = 4;
		scenarijus3.add(nekoduotas, c);
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		JTabbedPane scenarijai = new JTabbedPane();
		scenarijai.addTab("1 scenarijus", scenarijus1);
		scenarijai.addTab("2 scenarijus", scenarijus2);
		scenarijai.addTab("3 scenarijus", scenarijus3);
		turinys.add(parametrai);
		turinys.add(scenarijai);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setResizable(false);
		setContentPane(turinys);
		setVisible(true);
	}

	/**
	 * Vaizduoja klaidas.
	 *
	 * @param klaidos      klaidų masyvas
	 * @param klaidųLaukas laukas, į kurį surašomos klaidų pozicijos
	 */
	private void vaizduotiKlaidas(int[] klaidos, JTextArea klaidųLaukas) {
		int klaidųKiekis = klaidos.length;
		String klaidųInfo = "Klaidų skaičius: " + klaidųKiekis;
		if (klaidųKiekis > 0) {
			klaidųInfo = klaidųInfo + "\nPozicijos: ";
		}
		for (int i = 0; i < klaidųKiekis; i++) {
			klaidųInfo = klaidųInfo + klaidos[i];
			if (i + 1 == klaidųKiekis) {
				klaidųInfo = klaidųInfo + ".";
			} else {
				klaidųInfo = klaidųInfo + ", ";
			}
		}
		klaidųLaukas.setText(klaidųInfo);
	}

	/**
	 * Paveiksliuka išsaugo atmintyje ir paruošia vaizdavimui.
	 */
	private void atidarytiPaveiksliuką() {
		JFileChooser rinktisFailą = new JFileChooser();
		FileNameExtensionFilter failoFiltras = new FileNameExtensionFilter("BMP paveiksliukai", "bmp");
		rinktisFailą.setFileFilter(failoFiltras);
		rinktisFailą.setDialogTitle("Pasirinkite paveiksliuką");
		int pasirinkimas = rinktisFailą.showOpenDialog(this);
		if (pasirinkimas != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(this, "Failas nepasirinktas.", "KLAIDA", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String fVardas = rinktisFailą.getSelectedFile().toString();
		File failas = new File(fVardas);
		try {
			paveiksliukas = ImageIO.read(failas);
			pavNek = ImageIO.read(failas);
			pavKod = ImageIO.read(failas);
			pasirinktasFailas.setText(fVardas);
		} catch (IOException io) {
			JOptionPane.showMessageDialog(this, "Nepavyko atidaryti failo " + fVardas, "KLAIDA",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (paveiksliukas == null) {
			JOptionPane.showMessageDialog(this, "Failas neatitinka formato.", "KLAIDA", JOptionPane.ERROR_MESSAGE);
			pasirinktasFailas.setText("");
			return;
		}
	}
}
