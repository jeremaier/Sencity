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

/**
 * FireStation increase satisfaction and reduce probability of FireEvent
 *
 */
public class FireStationTile extends ServiceTile {
	private static final long serialVersionUID = 1L;

	// Constants
	/**
	 * Default value of {@link ServiceTile#getMaintenanceCost()}
	 */
	public final static int DEFAULT_MAINTENANCE_COST = 4;

	/**
	 * Default value of {@link ServiceTile#getMaxNeededInhabitants()}
	 */
	public final static int DEFAULT_MAX_NEEDED_INHABITANTS = 15;
	
	/**
	 * Default value of {@link ServiceTile#getMaxNeededEnergy()}
	 */
	public final static int DEFAULT_MAX_NEEDED_ENERGY = 30;

	/**
	 * Default value of {@link ServiceTile#getSatisfactionValue()}
	 */
	public final static int DEFAULT_SATISFACTION_VALUE = 3;

	// Implementation
	/**
	 * Default value of {@link ServiceTile#getFireStationNumber()}
	 */
	private static int fireStationNumber = 0;

	// Creation

	/**
	 * Create with default settings.
	 */
	public FireStationTile() {
		super(FireStationTile.DEFAULT_SATISFACTION_VALUE, FireStationTile.DEFAULT_MAINTENANCE_COST, FireStationTile.DEFAULT_MAX_NEEDED_ENERGY, FireStationTile.DEFAULT_MAX_NEEDED_INHABITANTS);
		FireStationTile.fireStationNumber++;
	}

	// Access
	/**
	 * @return Number of fire station builded.
	 */
	public static final int getFireStationNumber() {
		return FireStationTile.fireStationNumber;
	}

	// Status
	@Override
	public boolean equals(Object o) {
		return o instanceof FireStationTile && this.equals((FireStationTile) o);
	}
	
    @Override
    public void disassemble(CityResources res) {
        if (!this.isDestroyed) {
            this.isDestroyed = true;
            FireStationTile.fireStationNumber--;
        }
    }
}
