package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import launcher.SimCityUI;
import model.GameBoard;

public class MainMenuView extends JPanel {

	private static final long serialVersionUID = 1L;
	private final JFrame frame;
	private static Color backgroundColor = new Color(44, 44, 44);

	public MainMenuView(JFrame frame, int height, int width) {
		super();

        final GameBoard game = new GameBoard(MainFrame.getGameHeight(), MainFrame.getGameWidth(), MainFrame.getDifficulty().getLevel(), MainFrame.getTexts());
		final SimButton newWorld = new SimButton(MainFrame.getTexts().getNewGameButtonLabel());
		final SimButton options = new SimButton("Options");
		final SimButton load = new SimButton(MainFrame.getTexts().getLoadButtonLabel());
		final SimButton[] buttons = {newWorld, load, options};
		final Insets margin = new Insets(-3, -7, -2, -7);

		this.setBackground(backgroundColor);
		this.setLayout(new GridLayout(0, 1, 10, 10));
		this.setBorder(new EmptyBorder(65, 50, 0, 50));
		this.frame = frame;
		this.actions(buttons, game, height, width);

		for(SimButton button : buttons) {
			button.setMargin(margin);
			this.add(button);
			this.add(Box.createRigidArea(new Dimension(30, 0)));
		}
	}

	private void actions(SimButton[] buttons, GameBoard game, int height, int width) {
		buttons[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> new SimCityUI(game, MainFrame.getTexts()));
				frame.dispose();
			}
		});

		buttons[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				((MainFrame)frame).setNewPanel(new LoadView(frame, game, height, width));
			}
		});

		buttons[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				((MainFrame)frame).setNewPanel(new OptionsView(frame, game, height, width));
			}
		});
	}
	
	public static Color getBackgroundColor() {
		return MainMenuView.backgroundColor;
	}
}
