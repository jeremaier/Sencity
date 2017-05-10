package ui;

import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import launcher.SimCityUI;

public class MainMenuView extends JPanel {

	private static final long serialVersionUID = 1L;
	private final MainFrame frame;

	public MainMenuView(MainFrame frame, int height, int width) {
		super();

		final JButton newWorld = new JButton(MainFrame.getTexts().getNewGameButtonLabel());
		final JButton options = new JButton("Options");
		final JButton load = new JButton(MainFrame.getTexts().getLoadButtonLabel());
		final JButton[] buttons = {newWorld, load, options};
		final Insets margin = new Insets(20, 150, 20, 150);

		this.setLayout(new GridLayout(0, 1, 10, 10));
		this.setBorder(new EmptyBorder(20, 50, 20, 50));
		this.frame = frame;
		this.actions(buttons, height, width);

		for(JButton button : buttons) {
			button.setMargin(margin);
			this.add(button);
		}
	}


	private void actions(JButton[] buttons, int height, int width) {
		buttons[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> new SimCityUI(height, width, MainFrame.getDifficulty().getLevel(), MainFrame.getTexts()));
				frame.dispose();
			}
		});

		buttons[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});

		buttons[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setNewPanel(new OptionsView(frame, height, width));
			}
		});
	}
}
