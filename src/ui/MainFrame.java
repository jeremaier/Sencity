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
	private static int[] sizeXY = new int[2];

	public MainFrame(int height, int width) {
		super("Simcity Télécom");
		
		MainFrame.setWidthHeight(height, width);
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
		return MainFrame.difficulty;
	}
	
	public static int getGameHeight() {
		return MainFrame.sizeXY[0];
	}
	
	public static int getGameWidth() {
		return MainFrame.sizeXY[1];
	}
	
	public static void setWidthHeight(int height, int width) {
		sizeXY[0] = height;
		sizeXY[1] = width;
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
