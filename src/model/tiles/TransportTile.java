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
 * Enable to sell more expensive products
 */
public class TransportTile extends Tile implements Destroyable {
    // Implementation

    /**
     * {@link #getProductsCapacity()}
     */
	protected int productsCapacity;
	
    /**
     * {@link #getMaxNeededEnergy()}
     */
	protected int maxNeededEnergy;
    
    /**
     * {@link #getMaxNeededProducts()}
     */
	protected int maxNeededProducts;
    
    /**
     * {@link #getMaxNeededInhabitants()}
     */
	protected int maxNeededInhabitants;
	
    /**
     * Evolution state
     */
    protected boolean isDestroyed;

    /**
     * {@link #isEnergyMissing()}
     */
    protected boolean isEnergyMissing;
    
    /**
     * {@link #isPopulationMissing()}
     */
    protected boolean isPopulationMissing;

    // Creation
	/**
     * @param capacity
     *            - {@link #getProductsCapacity()}
     */
    public TransportTile(int capacity) {
        this.productsCapacity = capacity;
        this.isEnergyMissing = false;
        this.isPopulationMissing = false;
        this.isDestroyed = false;
    }

    // Access
    /**
     * @return Maximum products capacity.
     */
    public int getProductionCapacity() {
        return this.productsCapacity;
    }

    /**
     * @return Maximum number of energy units to consume. This maximum is
     *         consumed if the commerce is full.
     */
    public final int getMaxNeededEnergy() {
        return this.maxNeededEnergy;
    }
    
    /**
     * @return Maximum number of products units to consume. This maximum is
     *         consumed if the commerce is full.
     */
    public final int getMaxNeededProducts() {
        return this.maxNeededProducts;
    }
    
    /**
     * @return Maximum number of inhabitants at airport. This maximum is working
     * 		   if the commerce is full.
     */
    public final int getMaxNeededInhabitants() {
        return this.maxNeededInhabitants;
    }

    /**
     * @return Is energy missing in order to evolve or to update?
     */
    public final boolean isEnergyMissing() {
        return this.isEnergyMissing;
    }
    
    /**
     * @return Is population missing in order to evolve or to update?
     */
    public final boolean isPopulationMissing() {
        return this.isPopulationMissing;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = result * 17 + this.productsCapacity;
        result = result * 17 + this.maxNeededEnergy;
        result = result * 17 + this.maxNeededProducts;
        result = result * 17 + this.maxNeededInhabitants;
        result = result * 17 + Boolean.hashCode(this.isDestroyed);
        return result;
    }

    // Status
    @Override
    public boolean equals(Object o) {
        return o instanceof TransportTile && this.equals((TransportTile) o);
    }

    /**
     * @param o
     * @return Is {@value o} equals to this?
     */
    public boolean equals(TransportTile o) {
        return this == o || super.equals(o) 
                && o.maxNeededEnergy == this.maxNeededEnergy && o.maxNeededProducts == this.maxNeededProducts
                && o.maxNeededInhabitants == this.maxNeededInhabitants && o.productsCapacity == this.productsCapacity
                && o.isDestroyed == this.isDestroyed && o.isEnergyMissing == this.isEnergyMissing
                && o.isPopulationMissing == this.isPopulationMissing;
    }

    @Override
    public boolean isDestroyed() {
        return this.isDestroyed;
    }

    // Change
    @Override
    public void disassemble(CityResources res) {
        if (!this.isDestroyed) {
        	res.decreaseProductsCapacity(this.productsCapacity);
            this.isDestroyed = true;
        }
    }

	@Override
	public void update(CityResources res) {
        final int products = this.getProducts(res);
        final int busyPercentage = products * 100 / this.productsCapacity;
        final int neededEnergy = Math.max(10, busyPercentage * this.maxNeededEnergy / 100);
        final int neededUnworkingPopulation = busyPercentage * this.maxWorkingInhabitants / 100;
        final boolean enoughEnergy = res.getUnconsumedEnergy() >= neededEnergy;
        final boolean enoughPopulation = res.getUnworkingPopulation() >= neededUnworkingPopulation;
        int vacantPercentage = 0;
        
        if(enoughEnergy && enoughPopulation) {
            res.consumeEnergy(neededEnergy);
            res.hireWorkers(neededUnworkingPopulation);
            this.isEnergyMissing = false;
            this.isPopulationMissing = false;

            vacantPercentage = 100 - busyPercentage;
        } else if(!enoughEnergy || !enoughPopulation) {
        	int consumedEnergy = neededEnergy;
        	int workingPopulation = neededUnworkingPopulation;
        		
            if(!enoughEnergy) {
                consumedEnergy = res.getUnconsumedEnergy();
                res.consumeEnergy(consumedEnergy);
            	this.isEnergyMissing = true;
            	res.hireWorkers(neededUnworkingPopulation);
            }
            
            if(!enoughPopulation) {
                workingPopulation = res.getUnworkingPopulation();
                res.hireWorkers(workingPopulation);
            	this.isPopulationMissing = true;
            	res.consumeEnergy(neededEnergy);
            }
            
            final int missingEnergyPercentage = 100 - consumedEnergy * 100 / neededEnergy;
            final int missingPopulationPercentage = 100 - workingPopulation * 100 / neededUnworkingPopulation;
            vacantPercentage = missingEnergyPercentage * missingPopulationPercentage;
        }
        
        res.storeProducts(vacantPercentage * this.maxProduction / 100);
	}
}