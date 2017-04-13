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

public class StadiumTile extends Tile implements Destroyable {
	/**
     * Evolution state
     */
    protected boolean isDestroyed;

	public StadiumTile() {
		CityResources.setBuildHarbor(true);
		this.productsCapacity = capacity;
        this.maxNeededEnergy = StadiumTile.DEFAULT_MAX_NEEDED_ENERGY;
	}
    
    @Override
    public void disassemble(CityResources res) {
        if (!this.isDestroyed) {

        	this.isDestroyed = true;
            CityResources.setBuildHarbor(false);
        }
    }

	@Override
	public boolean isDestroyed() {
		return this.isDestroyed;
	}
	
    @Override
    public int hashCode() {
        int result = 1;
        result = result * 17 + this.maxNeededEnergy;
        result = result * 17 + Boolean.hashCode(this.isDestroyed);
        result = result * 17 + Boolean.hashCode(this.isEnergyMissing);
        return result;
    }

    // Status
    @Override
    public boolean equals(Object o) {
        return o instanceof StadiumTile && this.equals((StadiumTile) o);
    }

    /**
     * @param o
     * @return Is {@value o} equals to this?
     */
    public boolean equals(TransportTile o) {
        return this == o || super.equals(o)
                && o.maxNeededEnergy == this.maxNeededEnergy
                && o.isDestroyed == this.isDestroyed
                && o.isEnergyMissing == this.isEnergyMissing;
    }


	@Override
	public void update(CityResources res) {
		
	}
}