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

public class OptionsView extends JPanel {

	private static final long serialVersionUID = 1L;

	public OptionsView() {
		super();

		JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
		JButton langage = new JButton("Langue : " + StartMenuView.getTexts().getLangageName());
		JButton difficulty = new JButton("Difficulté");
		JButton[] buttons = {langage, difficulty};
		Insets margin = new Insets(20, 150, 20, 150);

		panel.setBorder(new EmptyBorder(20, 30, 20, 30));
		actions(buttons);

		for(JButton button : buttons) {
			button.setMargin(margin);
			panel.add(button);
		}
	}

	private void actions(JButton[] buttons) {
		buttons[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final LocalizedTexts texts = StartMenuView.getTexts();

				if(texts instanceof FRTexts)
					StartMenuView.setTexts(new UKTexts());
				else StartMenuView.setTexts(new FRTexts());

				buttons[0].setText("Langue : " + texts.getLangageName());
			}
		});

		buttons[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//setNewPanel(new OptionsView());
			}
		});
	}
}
