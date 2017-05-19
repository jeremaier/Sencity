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
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.GameBoard;

public class RefreshView extends JPanel {

    // Constant
    private static final long serialVersionUID = 1L;

    // Creation
    public RefreshView(GameBoard w, MessagesView mv) {
        super();
        // this.setBorder(BorderFactory.createBevelBorder(1, Color.GRAY,
        // Color.BLUE));
        JButton jb = new JButton(MainFrame.getTexts().getRefreshButtonLabel());
        JButton save = new JButton(MainFrame.getTexts().getSaveButtonLabel());
        
		this.setLayout(new GridLayout(0, 1, 0, 10));
		this.setBorder(new EmptyBorder(100, 0, 100, 10));
		
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                w.nextState();
            }
        });
        
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fileNbr = w.saveGame(w.getResources());
                mv.savePrint(MainFrame.getTexts().getSaveMessage(fileNbr));
            }
        });
        
        this.add(jb);
        this.add(save);
    }

}
