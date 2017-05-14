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

import java.text.MessageFormat;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import model.GameBoard;

public class MessagesView extends JPanel implements Observer {
    private static final long serialVersionUID = 1L;

    private JTextArea message;

    public MessagesView(GameBoard monde) {
        super();
        this.setBorder(BorderFactory.createTitledBorder(monde.getTexts().getBorderTitle()));
        this.message = new JTextArea(10, 110);
        this.message.setWrapStyleWord(true);
        this.message.setLineWrap(true);
        this.message.setOpaque(false);
        this.message.setEditable(false);
        this.message.setFocusable(false);
        this.add(new JScrollPane(this.message));
    }

    @Override
    public void update(Observable o, Object arg) {
        assert o instanceof GameBoard;
        GameBoard world = (GameBoard) o;
        if (world.getMessage() != null && !world.getMessage().isEmpty()) {
            String msg = MessageFormat.format(world.getTexts().getRoundMsg() + "\n", GameBoard.ROUNDCOUNTER.get(), world.getMessage());
            this.message.append(msg);
        }
    }

    public void savePrint(String msg) {
    	this.message.append(msg + "\n");
    }
}
