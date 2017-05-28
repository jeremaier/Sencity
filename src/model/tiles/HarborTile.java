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

public class HarborTile extends TransportTile {
	private static final long serialVersionUID = 1L;

    // Constants
	/**
	 * Default value of {@link TransportTile#getMaintenanceCost()}
	 */
	public final static int DEFAULT_MAINTENANCE_COST = 10;
    
    /**
     * Default value of {@link TransportTile#getMaxNeededEnergy()}
     */
    public final static int DEFAULT_MAX_NEEDED_ENERGY = 80;
    
	/**
	 * Default value of {@link TransportTile#getMaxSoldProducts()}
	 */
	public final static int DEFAULT_MAX_SOLD_PRODUCTS = 50;

    /**
     * Default value of {@link TransportTile#getMaxNeededInhabitants()}
     */
    public final static int DEFAULT_MAX_NEEDED_INHABITANTS = 50;
	
    /**
     * Default value of {@link TransportTile#getProductsPrice()}
     */
    public final static int DEFAULT_PRODUCTS_PRICE = 10;
    
    /**
     * Default value of {@link TransportTile#getSatisfactionValue()}
     */
    public final static int DEFAULT_SATISFACTION_VALUE = 4;
    
    /**
     * This building is already build?
     */
    public static boolean alreadyBuild = false;

	public HarborTile() {
		super(DEFAULT_PRODUCTS_PRICE);
		
        this.maxNeededEnergy = HarborTile.DEFAULT_MAX_NEEDED_ENERGY;
        this.maxSoldProducts = HarborTile.DEFAULT_MAX_SOLD_PRODUCTS;
        this.maxNeededInhabitants = HarborTile.DEFAULT_MAX_NEEDED_INHABITANTS;
        this.satisfactionValue = HarborTile.DEFAULT_SATISFACTION_VALUE;
        this.maintenanceCost = HarborTile.DEFAULT_MAINTENANCE_COST;
        HarborTile.alreadyBuild = true;
	}
	
	@Override
	public int hashCode() {
		int result = super.hashCode();
        result = result * 17 + Boolean.hashCode(HarborTile.alreadyBuild);
        return result;
	}
    
    @Override
    public boolean equals(Object o) {
        return o instanceof HarborTile && this.equals((HarborTile) o);
    }
    
    @Override
    public void disassemble(CityResources res) {
        if (!this.isDestroyed) {
            this.isDestroyed = true;
            HarborTile.alreadyBuild = false;
        }
    }
}