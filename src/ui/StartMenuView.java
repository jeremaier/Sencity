package ui;

import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import launcher.SimCityUI;
import localization.LocalizedTexts;
import localization.UKTexts;

public class StartMenuView extends JFrame {

	private static final long serialVersionUID = 1L;
	private static LocalizedTexts texts = new UKTexts();

	public StartMenuView(int height, int width) {
        super("Simcity Télécom");
        
        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
		JButton newWorld = new JButton("Nouvelle partie");
		JButton options = new JButton("Options");
		JButton load = new JButton("Charger une partie");
        JButton[] buttons = {newWorld, load, options};
        Insets margin = new Insets(20, 150, 20, 150);

        panel.setBorder(new EmptyBorder(20, 30, 20, 30));
        this.actions(buttons, height, width);
        
        for(JButton button : buttons) {
        	button.setMargin(margin);
            panel.add(button);
        }

        this.add(panel);
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
		StartMenuView.texts = texts;
	}
	
	private void setNewPanel(JPanel panel) {
		this.getContentPane().removeAll();
		this.getContentPane().add(panel);
		this.revalidate();
	}
	
	private void actions(JButton[] buttons, int height, int width) {
		buttons[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> new SimCityUI(height, width, getTexts()));
				dispose();
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
				setNewPanel(new OptionsView());
			}
		});
	}
}
