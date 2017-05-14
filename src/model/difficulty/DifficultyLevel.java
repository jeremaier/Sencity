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

package model.difficulty;

import java.io.Serializable;

/**
 * Represents the difficulty parameters of the game.
 */
public class DifficultyLevel implements Serializable {

	private static final long serialVersionUID = 1L;

	// Constant (Standard level)
    /**
     * Initial currency of the standard difficulty.
     */
    private final static int STANDARD_CURRENCY = 500;
    
    /**
     * Initial currency of easy.
     */
    private final static int EASY_CURRENCY = 1000;
    
    /**
     * Initial currency of difficult.
     */
    private final static int DIFFICULT_CURRENCY = 200;

    /**
     * Standard difficulty.
     */
    public final static DifficultyLevel STANDARD_LEVEL = new DifficultyLevel(DifficultyLevel.STANDARD_CURRENCY);
    
    /**
     * Standard difficulty.
     */
    public final static DifficultyLevel EASY_LEVEL = new DifficultyLevel(DifficultyLevel.EASY_CURRENCY);
    
    /**
     * Standard difficulty.
     */
    public final static DifficultyLevel HARD_LEVEL = new DifficultyLevel(DifficultyLevel.DIFFICULT_CURRENCY);

    // Constant
    /**
     * 1: {@link DifficultyLevel#getInitialCurrency()}
     */
    public final static String TO_STRING_TEMPLATE = "Difficulty : {%s ¤}";

    // Implementation
    /**
     * {@link #getInitialCurrency()}
     */
    private final int initialCurrency;

    // Creation
    /**
     *
     * @param aCurrency
     *            - {@link #getInitialCurrency()}
     * @param aBuildozerToolCost
     *            - {@link #getBuildozerCost()}
     * @param aZoneDelimiterCost
     *            - {@link #getZoneDelimiterCost()}
     */
    public DifficultyLevel(int aCurrency) {
        this.initialCurrency = aCurrency;
    }

    // Access
    /**
     * @return Currency at startup time.
     */
    public int getInitialCurrency() {
        return this.initialCurrency;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = result * 17 + this.initialCurrency;
        return result;
    }

    // Status
    @Override
    public boolean equals(Object o) {
        return o instanceof DifficultyLevel && this.equals((DifficultyLevel) o);
    }

    /**
     * @param o
     * @return Is {@value o} equals to this?
     */
    public boolean equals(DifficultyLevel o) {
        return this == o || o.initialCurrency == this.initialCurrency;
    }

    // Debugging
    @Override
    public String toString() {
        return String.format(DifficultyLevel.TO_STRING_TEMPLATE, this.initialCurrency);
    }
}
