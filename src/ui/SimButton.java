package ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class SimButton extends JButton {

	private static final long serialVersionUID = 1L;

	public SimButton(String label) {
		super(label);
		this.setForeground(Color.WHITE);
		this.setFont(new Font("Calibri", Font.BOLD, 20));

		this.setOpaque(false);
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
		this.setFocusPainted(false);

		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setHorizontalTextPosition(SwingConstants.CENTER);

		this.setIcon(new ImageIcon("src/resources/icons/buttonIcon.png"));
		this.setRolloverIcon(new ImageIcon("src/resources/icons/buttonOverIcon.png"));
		this.setPressedIcon(new ImageIcon("src/resources/icons/buttonPressedIcon.png"));
	}
}