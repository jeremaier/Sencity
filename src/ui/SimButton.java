package ui;
 
import java.awt.Color;
 
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
 
public class SimButton extends JButton {
 
    private static final long serialVersionUID = 1L;
 
    public SimButton(String txt, String icon, String iconHover) {
        super(txt);
        setForeground(Color.WHITE);
         
        setOpaque(false);
        setContentAreaFilled(false); // On met � false pour emp�cher le composant de peindre l'int�rieur du JButton.
        setBorderPainted(false); // De m�me, on ne veut pas afficher les bordures.
        setFocusPainted(false); // On n'affiche pas l'effet de focus.
         
        setHorizontalAlignment(SwingConstants.CENTER);
        setHorizontalTextPosition(SwingConstants.CENTER);
         
        setIcon(new ImageIcon(icon));
        setRolloverIcon(new ImageIcon(iconHover));
    }
}