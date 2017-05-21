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

public class PowerPlantTile extends BuildableTile {
	private static final long serialVersionUID = 1L;
	
    // Constant
    /**
     * Extra energy produces for each new update. In the limit of the capacity
     * {@link #getProductionCapacity()}.
     */
    public int EXTRA_ENERGY_PRODUCTION = 15;

    /**
     * Default value of {@link PowerPlantTile2#getProductionCapacity()}
     */
    public final static int DEFAULT_PRODUCTION_CAPACITY = 70;

    // Implementation
    /**
     * {@link #getProduction()}
     */
    protected int production;

    /**
     * {@link #getProductionCapacity()}
     */
    protected int productionCapacity;

    // Creation
    /**
     * @param productionCapacity
     *            - {@link #getProductionCapacity()}
     */
    public PowerPlantTile(int productionCapacity) {
        super(0);
        this.productionCapacity = productionCapacity;
        this.production = 0;
        this.state = ConstructionState.BUILT;
    }

    /**
     * Create with default settings.
     */
    public PowerPlantTile() {
        this(PowerPlantTile.DEFAULT_PRODUCTION_CAPACITY);
    }

    // Access
    /**
     * @return Current production.
     */
    public int getProduction() {
        return this.production;
    }

    /**
     * @return Maximum production.
     */
    public int getProductionCapacity() {
        return this.productionCapacity;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 17 + this.production;
        result = result * 17 + this.productionCapacity;
        return result;
    }

    // Status
    @Override
    public boolean equals(Object o) {
        return o instanceof PowerPlantTile && this.equals((PowerPlantTile) o);
    }

    /**
     * @param o
     * @return Is {@value o} equals to this?
     */
    public boolean equals(PowerPlantTile o) {
        return this == o || o.production == this.production
        		&& o.productionCapacity == this.productionCapacity;
    }

    @Override
    public boolean isDestroyed() {
        return this.state == ConstructionState.DESTROYED;
    }

    // Change
    @Override
    public void disassemble(CityResources res) {
        if (this.state == ConstructionState.BUILT) {
            res.decreaseEnergyProduction(this.productionCapacity);
            super.disassemble(res);
        }
    }

    @Override
    public void evolve(CityResources res) {
        super.evolve(res);
        
        this.update(res);
    }
    
    /**
     * @param res
     */
    public void evolveLevel(CityResources res) {
		switch(state) {
		case BUILT:
			this.EXTRA_ENERGY_PRODUCTION += 5;
			this.productionCapacity *= 1.5;
			this.state = ConstructionState.BUILTLVL2;
			break;
			
		case BUILTLVL2:
			this.EXTRA_ENERGY_PRODUCTION += 10;
			this.productionCapacity *= 1.5;
			this.state = ConstructionState.BUILTLVL3;
			break;
			
		default:
			break;
		}
		
        this.update(res);
    }
    
    @Override
    public void update(CityResources res) {
		if (this.state != ConstructionState.UNDER_CONSTRUCTION || this.state != ConstructionState.DESTROYED) {
            // Double production
            final int extraProduction = Math.min(this.EXTRA_ENERGY_PRODUCTION, this.productionCapacity - this.production);

            this.production = this.production + extraProduction;
            res.increaseEnergyProduction(extraProduction);
        }
    }
}