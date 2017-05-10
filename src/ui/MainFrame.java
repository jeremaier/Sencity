package ui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import localization.LocalizedTexts;
import localization.UKTexts;
import model.difficulty.Difficulties;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static LocalizedTexts texts = new UKTexts();
	private static Difficulties difficulty = Difficulties.STANDARD;

	public MainFrame(int height, int width) {
		super("Simcity Télécom");

		this.add(new MainMenuView(this, height, width));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public static LocalizedTexts getTexts() {
		return texts;
	}

	public static void setTexts(LocalizedTexts texts) {
		MainFrame.texts = texts;
	}

	public static Difficulties getDifficulty() {
		return difficulty;
	}

	public static void setDifficulty(Difficulties difficulty) {
		MainFrame.difficulty = difficulty;
	}

	public void setNewPanel(JPanel panel) {
		this.getContentPane().removeAll();
		this.getContentPane().add(panel);
		this.revalidate();
	}
}
