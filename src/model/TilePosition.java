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

package model;

import java.io.Serializable;

/**
 * Represents a location in the city.
 */
public class TilePosition implements Serializable {

    // Constant
    private final static long serialVersionUID = 1L;

    /**
     * 1: {@link #getRow()} 2: {@link #getColumn()}
     */
    public final static String TO_STRING_TEMPLATE = "(%s, %s)";

    // Implementation
    /**
     * {@link #getRow()}
     */
    private final int row;

    /**
     * {@link #getColumn()}
     */
    private final int column;

    // Creation
    /**
     * @param aRow
     *            - {@link #getRow()}
     * @param aColumn
     *            - {@link #getColumn()}
     */
    public TilePosition(int aRow, int aColumn) {
        assert aRow >= 0 : "require: `aRow' is greater or equal to zero";
        assert aColumn >= 0 : "require: `aColumn' is greater or equal to zero";

        this.row = aRow;
        this.column = aColumn;
    }

    // Access
    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = result * 17 + this.row;
        result = result * 17 + this.column;
        return result;
    }

    // Status
    /**
     * @param o
     *            - Object
     * @return Are current and o adjacent locations? Linear or diagonal
     *         adjacency.
     */
    public boolean isAdjacent(TilePosition o) {
        return Math.abs(this.row - o.row) <= 1 && Math.abs(this.column - o.column) <= 1;
    }

    @Override
    public boolean equals(Object o) {
        return this == o || o instanceof TilePosition && this.equals((TilePosition) o);
    }

    /**
     * @param o
     *            - Object
     * @return Is o equals to the current location?
     */
    public boolean equals(TilePosition o) {
        return this.row == o.row && this.column == o.column;
    }

    // Debugging
    @Override
    public String toString() {
        return String.format(TilePosition.TO_STRING_TEMPLATE, this.row, this.column);
    }

}
