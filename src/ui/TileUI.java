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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.MessageFormat;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import model.GameBoard;
import model.tiles.Evolvable;
import model.tiles.Tile;
import model.tools.Tool;

public class TileUI extends JLabel {

    private static final long serialVersionUID = 1L;

    private int row;

    private int column;

    private GameBoard model;

    public TileUI(GameBoard m, final int row, final int column) {
        super(" ");
        this.model = m;
        this.row = row;
        this.column = column;
        
        this.setVerticalAlignment(SwingConstants.BOTTOM);
        this.setHorizontalAlignment(SwingConstants.LEFT);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TileUI.this.model.effectTile(row, column);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                TileUI.this.model.setSelectedTile(row, column);
            }
        });

        this.update();

    }

    // Rafraichissement du composant
    public void update() {
        final Tile elt = this.model.getTile(this.row, this.column);
        final Tool selectedTool = this.model.getSelectedTool();

        if (selectedTool.canEffect(elt) || selectedTool.canEvolve(elt)) {
            final int cost = selectedTool.getCost(elt);
            	
            this.setToolTipText(MessageFormat.format(this.model.getTexts().getCurrencyMsg(), cost));
        } else this.setToolTipText(this.model.getTexts().getToolCannotAffectMsg());

        ImageIcon ii = IconFactory.getInstance().getTileIcon(elt);
        
        if(elt instanceof Evolvable)
        	this.setText(IconFactory.getInstance().getLevelLabel(elt));
        
        this.setIcon(ii);
    }

}
