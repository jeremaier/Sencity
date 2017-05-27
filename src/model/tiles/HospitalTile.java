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
public class HospitalTile extends ServiceTile {
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
	 * Default value of {@link #getHospitalNumber()}
	 */
	private static int hospitalStationNumber = 0;

	// Creation

	/**
	 * Create with default settings.
	 */
	public HospitalTile() {
		super(HospitalTile.DEFAULT_SATISFACTION_VALUE, HospitalTile.DEFAULT_MAINTENANCE_COST, HospitalTile.DEFAULT_MAX_NEEDED_ENERGY, HospitalTile.DEFAULT_MAX_NEEDED_INHABITANTS);
		HospitalTile.hospitalStationNumber++;
	}

	// Access
	/**
	 * @return Number of hospital builded.
	 */
	public static final int getHospitalNumber() {
		return HospitalTile.hospitalStationNumber;
	}

	// Status
	@Override
	public boolean equals(Object o) {
		return o instanceof HospitalTile && this.equals((HospitalTile) o);
	}
	
    @Override
    public void disassemble(CityResources res) {
        if (!this.isDestroyed) {
            this.isDestroyed = true;
            HospitalTile.hospitalStationNumber--;
        }
    }
}
