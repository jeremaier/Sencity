package ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import launcher.SimCityUI;
import localization.FRTexts;
import localization.LocalizedTexts;
import localization.UKTexts;
import model.GameBoard;
import model.difficulty.Difficulties;

public class OptionsView extends JPanel {

	private static final long serialVersionUID = 1L;
	private static String languageLabel = MainFrame.getTexts().getStandardLabel();
	
	public OptionsView(JFrame frame, GameBoard game, int height, int width) {
		super();

		final SimButton langage = new SimButton(MainFrame.getTexts().getLanguageLabel() + " : " + MainFrame.getTexts().getLangageName());
		final SimButton difficulty = new SimButton(MainFrame.getTexts().getDifficultyLabel() + " : " + languageLabel);
		final SimButton size = new SimButton(MainFrame.getTexts().getSizeLabel() + " : " + MainFrame.getGameHeight() + " x " + MainFrame.getGameWidth());
		final SimButton back = new SimButton(MainFrame.getTexts().getBackLabel());
		final SimButton[] BeforeGameButtons = {langage, difficulty, size, back};
		final SimButton[] InGameButtons = {langage, difficulty, back};

		this.setBackground(MainMenuView.getBackgroundColor());
		this.setLayout(new GridLayout(0, 1, 10, 10));
		
		if(frame instanceof MainFrame)
			this.setBorder(new EmptyBorder(100, 50, 100, 50));
		else this.setBorder(new EmptyBorder(242, 495, 242, 496));
		
		this.actions(BeforeGameButtons, frame, game, height, width);

		if(frame instanceof MainFrame) {
			for(SimButton button : BeforeGameButtons)
				this.add(button);
		} else {
			for(SimButton button : InGameButtons)
				this.add(button);
		}
	}

	private void actions(SimButton[] buttons, JFrame frame, GameBoard game, int height, int width) {		
		buttons[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizedTexts texts = MainFrame.getTexts();

				if(texts instanceof UKTexts)
					texts = new FRTexts();
				else texts = new UKTexts();

				MainFrame.setTexts(texts);
				buttons[0].setText(MainFrame.getTexts().getLanguageLabel() + " : " + texts.getLangageName());
				buttons[1].setText(MainFrame.getTexts().getDifficultyLabel() + " : " + languageLabel(MainFrame.getDifficulty()));
				buttons[2].setText(MainFrame.getTexts().getSizeLabel() + " : " + MainFrame.getGameHeight() + " x " + MainFrame.getGameWidth());
				buttons[3].setText(MainFrame.getTexts().getBackLabel());
			}
		});

		buttons[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Difficulties difficulty = MainFrame.getDifficulty();

				switch(difficulty) {
				case EASY:
					difficulty = Difficulties.STANDARD;
					break;

				case STANDARD:
					difficulty = Difficulties.HARD;
					break;

				default:
					difficulty = Difficulties.EASY;
					break;
				}

				MainFrame.setDifficulty(difficulty);
				OptionsView.languageLabel = languageLabel(difficulty);
				buttons[1].setText(MainFrame.getTexts().getDifficultyLabel() + " : " + OptionsView.languageLabel);
			}
		});
		
		buttons[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int height = MainFrame.getGameHeight();

				switch(height) {
				case 10:
					MainFrame.setWidthHeight(15, 30);
					break;

				case 15:
					MainFrame.setWidthHeight(20, 40);
					break;

				default:
					MainFrame.setWidthHeight(10, 20);
					break;
				}

				buttons[2].setText(MainFrame.getTexts().getSizeLabel() + " : " + MainFrame.getGameHeight() + " x " + MainFrame.getGameWidth());
			}
		});

		buttons[3].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(frame instanceof MainFrame)
					((MainFrame)frame).setNewPanel(new MainMenuView(frame, height, width));
				else {
					new SimCityUI(game, MainFrame.getTexts());
					frame.dispose();
				}
			}
		});
	}

	private String languageLabel(Difficulties difficulty) {
		switch(difficulty) {
		case EASY:
			return MainFrame.getTexts().getEasyLabel();
		case STANDARD:
			return MainFrame.getTexts().getStandardLabel();
		default:
			return MainFrame.getTexts().getHardLabel();
		}
	}
}
