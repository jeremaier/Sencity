package ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import localization.FRTexts;
import localization.LocalizedTexts;
import localization.UKTexts;
import model.GameBoard;
import model.difficulty.Difficulties;

public class OptionsView extends JPanel {

	private static final long serialVersionUID = 1L;
	private final MainFrame frame;
	private static String languageLabel = MainFrame.getTexts().getStandardLabel();

	public OptionsView(MainFrame frame, GameBoard game, int height, int width) {
		super();

		final SimButton langage = new SimButton(MainFrame.getTexts().getLanguageLabel() + " : " + MainFrame.getTexts().getLangageName());
		final SimButton difficulty = new SimButton(MainFrame.getTexts().getDifficultyLabel() + " : " + languageLabel);
		final SimButton back = new SimButton(MainFrame.getTexts().getBackLabel());
		final SimButton[] buttons = {langage, difficulty, back};

		this.setBackground(MainMenuView.getBackgroundColor());
		this.setLayout(new GridLayout(0, 1, 10, 10));
		this.setBorder(new EmptyBorder(134, 50, 134, 50));
		this.frame = frame;
		this.actions(buttons, game, height, width);

		for(SimButton button : buttons)
			this.add(button);
	}

	private void actions(SimButton[] buttons, GameBoard game, int height, int width) {		
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
				buttons[2].setText(MainFrame.getTexts().getBackLabel());
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

				OptionsView.languageLabel = languageLabel(difficulty);
				MainFrame.setDifficulty(difficulty);
				buttons[1].setText(MainFrame.getTexts().getDifficultyLabel() + " : " + languageLabel);
			}
		});

		buttons[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setNewPanel(new MainMenuView(frame, game, height, width));
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
