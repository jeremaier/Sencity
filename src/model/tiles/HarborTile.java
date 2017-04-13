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
    // Constants
    /**
     * Default value of {@link #getProductsPrice()}
     */
    public final static int DEFAULT_PRODUCTS_PRICE = 2;
    
    /**
     * Default value of {@link TransportTile#getMaxNeededEnergy()}
     */
    public final static int DEFAULT_MAX_NEEDED_ENERGY = 30;
    
    /**
     * Default value of {@link TransportTile#getMaxNeededProducts()}
     */
    public final static int DEFAULT_MAX_NEEDED_PRODUCTS = 30;

    /**
     * Default value of {@link TransportTile#getNeededInhabitants()}
     */
    public final static int DEFAULT_MAX_NEEDED_INHABITANTS = 30;
    
    /**
     * Default value of {@link TransportTile#getProductsCapacity()}
     */
    protected final static int DEFAULT_PRODUCTS_CAPACITY = 10;
    
    /**
     * This building is already build?
     */
    public static boolean alreadyBuild = false;

	public HarborTile(int capacity) {
		super(DEFAULT_PRODUCTS_PRICE);
		
		HarborTile.alreadyBuild = true;
		this.productsCapacity = capacity;
        this.maxNeededEnergy = HarborTile.DEFAULT_MAX_NEEDED_ENERGY;
        this.maxNeededProducts = HarborTile.DEFAULT_MAX_NEEDED_PRODUCTS;
        this.maxNeededInhabitants= HarborTile.DEFAULT_MAX_NEEDED_INHABITANTS;
	}
	
    public HarborTile() {
        this(HarborTile.DEFAULT_PRODUCTS_CAPACITY);
    }
    
    @Override
    public boolean equals(Object o) {
        return o instanceof HarborTile && this.equals((HarborTile) o);
    }
    
    @Override
    public void disassemble(CityResources res) {
        if (!this.isDestroyed) {
        	res.decreaseProductsCapacity(this.productsCapacity);
            this.isDestroyed = true;
            HarborTile.alreadyBuild = false;
        }
    }
}