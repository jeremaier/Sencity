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

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import model.GameBoard;
import model.tools.Tool;

public class ToolUI extends JButton {

    // Constant
    private static final long serialVersionUID = 1L;

    // Creation
    /**
     *
     * @param world
     * @param target
     */
    public ToolUI(GameBoard world, Tool target) {
        super();
        this.setMargin(new Insets(1, 1, 1, 1));
        this.setContentAreaFilled(false);

        this.setIcon(IconFactory.getInstance().getToolIcon(target));
        this.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                world.setSelectedTool(target);
            }
        });
    }

}
