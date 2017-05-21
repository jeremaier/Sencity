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
package ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import launcher.SimCityUI;
import model.GameBoard;

public class RefreshView extends JPanel {

    // Constant
    private static final long serialVersionUID = 1L;

    // Creation
    public RefreshView(JFrame frame, GameBoard game, MessagesView mv) {
        super();
        
        JButton refresh = new JButton(MainFrame.getTexts().getRefreshButtonLabel());
        JButton save = new JButton(MainFrame.getTexts().getSaveButtonLabel());
        JButton newGame = new JButton(MainFrame.getTexts().getNewGameButtonLabel());
        JButton load = new JButton(MainFrame.getTexts().getLoadButtonLabel());
        JButton options = new JButton("Options");
        JButton[] buttons = {refresh, save, newGame, load, options};

		this.setLayout(new GridLayout(0, 1, 0, 10));
		this.actions(buttons, (SimCityUI)frame, game, mv, game.getHeight(), game.getWidth());
        
        for(JButton button : buttons)
        	this.add(button);
    }

	private void actions(JButton[] buttons, SimCityUI frame, GameBoard game, MessagesView mv, int height, int width) {		
		buttons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.nextState();
            }
        });
        
        buttons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fileNbr = game.saveGame(game.getResources());
                mv.savePrint(MainFrame.getTexts().getSaveMessage(fileNbr));
            }
        });
		
		buttons[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> new SimCityUI(new GameBoard(MainFrame.getGameHeight(), MainFrame.getGameWidth(), MainFrame.getDifficulty().getLevel(), MainFrame.getTexts()), MainFrame.getTexts()));
				frame.dispose();
			}
		});
        
		buttons[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	frame.setNewPanel(new LoadView(frame, game, height, width));
            }
        });
        
		buttons[4].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	frame.setNewPanel(new OptionsView(frame, game, height, width));
            }
        });
	}
}
