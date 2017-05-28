/**
 * TNCity
 * Copyright (c) 2017
 *  Jean-Philippe Eisenbarth,
 *  Victorien Elvinger
 *  Martine Gautier,
 *  Quentin Laporte-Chabasse
 *
 *  This file is part of TNCity.
 *
 *  TNCity is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  TNCity is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License
 *  along with TNCity.  If not, see <http://www.gnu.org/licenses/>.
 */

package launcher;

import java.awt.BorderLayout;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import localization.LocalizedTexts;
import model.GameBoard;
import ui.GameBoardView;
import ui.MainFrame;
import ui.MessagesView;
import ui.PropertiesView;
import ui.RefreshView;
import ui.ToolsView;

public final class SimCityUI extends JFrame {

    // Constants
    private final static long serialVersionUID = 1L;

    private final static int DEFAULT_HEIGHT = 12;

    private final static int DEFAULT_WIDTH = 20;

    // Entry point
    /**
     * Run without arguments or with two arguments:
     *
     * First argument: height Second argument: width
     *
     * @param args
     *            - Arguments
     */
    public static void main(String[] args) {
        final int height;
        final int width;

        if (args.length == 2) {
            final Scanner hSc = new Scanner(args[0]);
            final Scanner wSc = new Scanner(args[1]);

            if (hSc.hasNextInt()) { // if it is an integer
                height = hSc.nextInt();
            } else {
                height = SimCityUI.DEFAULT_HEIGHT;
                System.err.println("pasing: First argument must be an integer");
            }

            if (wSc.hasNextInt()) { // if it is an integer
                width = wSc.nextInt();
            } else {
                width = SimCityUI.DEFAULT_WIDTH;
                System.err.println("pasing: Second argument must be an integer");
            }

            hSc.close();
            wSc.close();
        } else {
            height = SimCityUI.DEFAULT_HEIGHT;
            width = SimCityUI.DEFAULT_WIDTH;

            if (args.length != 0) {
                System.err.println("pasing: Wrong number of arguments");
            }
        }

        SwingUtilities.invokeLater(() -> new MainFrame(height, width));
    }

    // Creation
    public SimCityUI(GameBoard monde, LocalizedTexts texts) {
        super("SimCityT�l�com");

        // Cr�ation de la vue du monde, plac�e au centre de la fen�tre
        GameBoardView vm = new GameBoardView(monde);
        monde.addObserver(vm);
        this.add(vm, BorderLayout.CENTER);

        // Cr�ation de la palette des �l�ments de jeu, plac�e � gauche
        ToolsView ve = new ToolsView(monde);
        monde.addObserver(ve);
        this.add(ve, BorderLayout.WEST);

        // Cr�ation du panneau d'informations
        PropertiesView vi = new PropertiesView(monde, texts);
        monde.addObserver(vi);

        // Cr�ation du panneau de message
        MessagesView mv = new MessagesView(monde);
        monde.addObserver(mv);
        
        // Cr�ation du panneau de rafraichissement
        RefreshView rv = new RefreshView(this, monde, mv);
        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.add(vi);
        right.add(Box.createVerticalGlue());
        right.add(rv);
        this.add(right, BorderLayout.EAST);

        this.add(mv, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    public void setNewPanel(JPanel panel) {
		this.getContentPane().removeAll();
		this.getContentPane().add(panel);
		this.revalidate();
	}
}
