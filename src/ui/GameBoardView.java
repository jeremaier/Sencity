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
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import model.GameBoard;

public class GameBoardView extends JPanel implements Observer {

    private static final long serialVersionUID = 1L;

    private TileUI[][] tiles;

    /**
     * Constructor.
     * 
     * @param w
     *            - World
     */
    public GameBoardView(GameBoard w) {
        super();
        GridLayout gl = new GridLayout(w.getHeight(), w.getWidth());
        gl.setHgap(-8);
        gl.setVgap(0);
        this.setLayout(gl);
        this.tiles = new TileUI[w.getHeight()][w.getWidth()];
        for (int i = 0; i < w.getHeight(); i++) {
            for (int j = 0; j < w.getWidth(); j++) {
                TileUI cc = new TileUI(w, i, j);
                this.tiles[i][j] = cc;
                this.add(cc);
            }
        }
    }

    /**
     * To update the GameBoardView, we update all tiles.
     */
    @Override
    public void update(Observable o, Object arg) {
        assert o instanceof GameBoard;
        
        GameBoard world = (GameBoard) o;
        
        for (int i = 0; i < world.getHeight(); i++)
            for (int j = 0; j < world.getWidth(); j++)
                this.tiles[i][j].update();
    }
}
