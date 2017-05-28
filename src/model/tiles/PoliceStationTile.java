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
 * PoliceStation increase satisfaction and reduce probability of PoliceEvent
 *
 */
public class PoliceStationTile extends ServiceTile {
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
	public final static int DEFAULT_MAX_NEEDED_ENERGY = 20;

	/**
	 * Default value of {@link ServiceTile#getSatisfactionValue()}
	 */
	public final static int DEFAULT_SATISFACTION_VALUE = 3;

	// Implementation
	/**
	 * Default value of {@link #getPoliceStationNumber()}
	 */
	public static int policeStationNumber = 0;

	// Creation
	/**
	 * Create with default settings.
	 */
	public PoliceStationTile() {
		super(PoliceStationTile.DEFAULT_SATISFACTION_VALUE, PoliceStationTile.DEFAULT_MAINTENANCE_COST, PoliceStationTile.DEFAULT_MAX_NEEDED_ENERGY, PoliceStationTile.DEFAULT_MAX_NEEDED_INHABITANTS);
		PoliceStationTile.policeStationNumber++;
	}

	// Access
	/**
	 * @return Number of police station builded.
	 */
	public static final int getPoliceStationNumber() {
		return PoliceStationTile.policeStationNumber;
	}
	
	// Status
	@Override
	public boolean equals(Object o) {
		return o instanceof PoliceStationTile && this.equals((PoliceStationTile) o);
	}
	
    @Override
    public void disassemble(CityResources res) {
        if (!this.isDestroyed) {
            this.isDestroyed = true;
            PoliceStationTile.policeStationNumber--;
        }
    }
}
