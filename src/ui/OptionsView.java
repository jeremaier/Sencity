package ui;

import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import localization.FRTexts;
import localization.LocalizedTexts;
import localization.UKTexts;
import model.difficulty.Difficulties;

public class OptionsView extends JPanel {

	private static final long serialVersionUID = 1L;
	private final MainFrame frame;

	public OptionsView(MainFrame frame, int height, int width) {
		super();

		final JButton langage = new JButton(MainFrame.getTexts().getLanguageLabel() + " : " + MainFrame.getTexts().getLangageName());
		final JButton difficulty = new JButton(MainFrame.getTexts().getDifficultyLabel() + " : "+ MainFrame.getDifficulty().name());
		final JButton back = new JButton(MainFrame.getTexts().getBackLabel());
		final JButton[] buttons = {langage, difficulty, back};
		final Insets margin = new Insets(20, 50, 20, 50);

		this.setLayout(new GridLayout(0, 1, 10, 10));
		this.setBorder(new EmptyBorder(20, 30, 20, 30));
		this.frame = frame;
		actions(buttons, height, width);

		for(JButton button : buttons) {
			button.setMargin(margin);
			this.add(button);
		}
	}

	private void actions(JButton[] buttons, int height, int width) {		
		buttons[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizedTexts texts = MainFrame.getTexts();

				if(texts instanceof UKTexts)
					texts = new FRTexts();
				else texts = new UKTexts();

				MainFrame.setTexts(texts);
				buttons[0].setText(MainFrame.getTexts().getLanguageLabel() + " : " + texts.getLangageName());
				buttons[1].setText(MainFrame.getTexts().getDifficultyLabel() + " : "+ MainFrame.getDifficulty().name());
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

				MainFrame.setDifficulty(difficulty);
				buttons[1].setText(MainFrame.getTexts().getDifficultyLabel() + " : "+ difficulty.name());
			}
		});

		buttons[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setNewPanel(new MainMenuView(frame, height, width));
			}
		});
	}
}
