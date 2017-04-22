package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import launcher.SimCityUI;
import localization.LocalizedTexts;
import localization.UKTexts;
import model.GameBoard;

public class StartMenuView extends JFrame {
    
	private static final long serialVersionUID = 1L;

	public StartMenuView(int height, int width) {
        /*super("Simcity");
        this.setLocationRelativeTo(null);
        this.setSize(300, 300);
        
        // Choix de la langue
        LocalizedTexts texts = new UKTexts();
        JButton newWorld = new JButton("Nouveau monde");
        newWorld.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	SwingUtilities.invokeLater(() -> new SimCityUI(height, width, texts));
            }
        });

        JButton options = new JButton("Options");
        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	SwingUtilities.invokeLater(() -> new OptionsView());
            }
        });
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(newWorld);
        panel.add(Box.createVerticalGlue());
        panel.add(options);        
        
        this.getContentPane().add(panel);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);//*/
		
		
		
		
		
		
		/*this.setTitle("SimcityTelecom");
	    this.setSize(500, 700);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);

	    JPanel pan = new JPanel();
        FlowLayout bl = new FlowLayout(FlowLayout.CENTER);
        pan.setLayout(bl);

        LocalizedTexts texts = new UKTexts();
        JButton newWorld = new JButton("Nouveau monde");
        newWorld.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	SwingUtilities.invokeLater(() -> new SimCityUI(height, width, texts));
            }
        });

        JButton options = new JButton("Options");
        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	SwingUtilities.invokeLater(() -> new OptionsView());
            }
        });
        newWorld.setHorizontalAlignment(JButton.CENTER);
        newWorld.setVerticalAlignment(JButton.CENTER);
        pan.add(newWorld);
        pan.add(Box.createVerticalGlue());
        pan.add(options);

        setContentPane(pan);  //defini le panel de la JFrame

	    //this.getContentPane().add(bl);
        this.setResizable(false);
	    this.setVisible(true);*/
		
		
		
		
	    JFrame f = new JFrame("Label Demo");
	    f.setLayout(new FlowLayout());
	    f.setSize(500, 600);
	    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
        LocalizedTexts texts = new UKTexts();
        JButton newWorld = new JButton("Nouveau monde");
        newWorld.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	SwingUtilities.invokeLater(() -> new SimCityUI(height, width, texts));
            }
        });

        JButton options = new JButton("Options");
        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	SwingUtilities.invokeLater(() -> new OptionsView());
            }
        });
        newWorld.setPreferredSize(new Dimension(150, 100));
        newWorld.setHorizontalAlignment(JLabel.CENTER);
        newWorld.setVerticalAlignment(JLabel.CENTER);
        options.setPreferredSize(new Dimension(150, 100));
        options.setHorizontalAlignment(JLabel.CENTER);
        options.setVerticalAlignment(JLabel.CENTER);
	    f.add(newWorld);
	    f.add(options);
	    
	    
	    f.setVisible(true);
    }
	
    // Creation
    public StartMenuView(int hauteur, int largeur, LocalizedTexts texts) {
        super("SimCityTélécom");

        // Création du monde
        GameBoard monde = new GameBoard(hauteur, largeur, texts);

        // Création de la vue du monde, placée au centre de la fenêtre
        GameBoardView vm = new GameBoardView(monde);
        monde.addObserver(vm);
        this.add(vm, BorderLayout.CENTER);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.pack();

        this.setResizable(false);
        this.setVisible(true);
    }
}
