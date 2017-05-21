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

package model.tiles;

import model.CityResources;
import model.GameBoard;

/**
 * State-less tile that represents grass tiles.
 */
public final class WaterTile extends Tile {
	private static final long serialVersionUID = 1L;

    // Constant
    /**
     * Default instance.
     */
    private final static WaterTile INSTANCE = new WaterTile();

    // Factory
    /**
     * @return Default water tile.
     */
    public static WaterTile getDefault() {
        // Provide always the same instance since Water is not changing.
        return WaterTile.INSTANCE;
    }

    // Creation
    /**
     * Prefer use {@link WaterTile#getDefault()} instead.
     */
    private WaterTile() {
    }

    // Access
    @Override
    public int hashCode() {
        return 1;
    }

    /**
     * @param gb
     * @return the class name.
     */
    public String toString(GameBoard gb) {
    	return this.getClass().getSimpleName();
    }

    // Status
    @Override
    public boolean equals(Object o) {
        return this == o || o instanceof WaterTile;
    }

    // Change
    @Override
    public void update(CityResources res) {
        // Do nothings.
    }
}
