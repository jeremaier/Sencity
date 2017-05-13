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
public abstract class TransportTile extends Tile implements Destroyable {
	private static final long serialVersionUID = 1L;
	
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
	
	/**
     * {@link #getProductsPrice()}
     */
    private final int productsPrice;
    
    /**
     * {@link #getSatisfactionValue()}
     */
    protected int satisfactionValue;

    // Creation
	/**
     * @param capacity
     *            - {@link #getProductsCapacity()}
     */
    public TransportTile(int productsPrice) {
    	this.productsPrice = productsPrice;
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
    
    /**
     * @return Price of a single product
     */
    public final int getProductsPrice() {
        return this.productsPrice;
    }
    
    /**
     * @return Increase or decrease value of satisfaction
     */
    public final int getSatisfactionValue() {
    	return this.satisfactionValue;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = result * 17 + this.productsPrice;
        result = result * 17 + this.productsCapacity;
        result = result * 17 + this.maxNeededEnergy;
        result = result * 17 + this.maxNeededProducts;
        result = result * 17 + this.maxNeededInhabitants;
        result = result * 17 + Boolean.hashCode(this.isDestroyed);
        result = result * 17 + Boolean.hashCode(this.isEnergyMissing);
        result = result * 17 + Boolean.hashCode(this.isPopulationMissing);
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
        		&& o.productsPrice == this.productsPrice
                && o.maxNeededEnergy == this.maxNeededEnergy
                && o.maxNeededProducts == this.maxNeededProducts
                && o.maxNeededInhabitants == this.maxNeededInhabitants
                && o.productsCapacity == this.productsCapacity
                && o.isDestroyed == this.isDestroyed
                && o.isEnergyMissing == this.isEnergyMissing
                && o.isPopulationMissing == this.isPopulationMissing;
    }

    @Override
    public boolean isDestroyed() {
        return this.isDestroyed;
    }

    public abstract void disassemble(CityResources res);
    
    @Override
    public void update(CityResources res) {
        if (!this.isDestroyed) {
            final int busyPercentage = this.getProducts(res) * 100 / this.productsCapacity;
            final int neededEnergy = Math.max(10, busyPercentage * this.maxNeededEnergy / 100);
            final int neededUnworkingPopulation = busyPercentage * this.maxNeededInhabitants / 100;
            final boolean enoughEnergy = res.getUnconsumedEnergy() >= neededEnergy;
            final boolean enoughPopulation = res.getUnworkingPopulation() >= neededUnworkingPopulation;
            int vacantPercentage = 100;
            int totalPrice = res.getProductsCount() * productsPrice;
            
            if(enoughEnergy && enoughPopulation) {
                this.isPopulationMissing = false;
                this.isEnergyMissing = false;
                vacantPercentage -= busyPercentage;
            } else {
            	int consumedEnergy = neededEnergy;
            	int workingPopulation = neededUnworkingPopulation;
            		
	            if(!enoughEnergy) {
	                consumedEnergy = res.getUnconsumedEnergy();
	            	this.isEnergyMissing = true;
	            } else this.isEnergyMissing = false;
	            
	            if(!enoughPopulation) {
	                workingPopulation = res.getUnworkingPopulation();
	            	this.isPopulationMissing = true;
	            } else this.isPopulationMissing = false;
	            
	            final int missingEnergyPercentage = 100 - consumedEnergy * 100 / neededEnergy;
                final int missingPopulationPercentage = 100 - workingPopulation * 100 / neededUnworkingPopulation;

                vacantPercentage = missingEnergyPercentage * missingPopulationPercentage;
            }
            
            res.consumeEnergy(neededEnergy);
            res.hireWorkers(neededUnworkingPopulation);
            res.creditWithTaxes(vacantPercentage * totalPrice / 100);
            res.consumeProducts(vacantPercentage * productsCapacity / 100);
            this.updateSatisfaction(res);
        }
    }
    
    private void updateSatisfaction(CityResources res) {
		res.increaseSatisfaction(this.satisfactionValue);
	}

	private int getProducts(CityResources res) {
        assert res.getProductsCapacity() != 0;

        return res.getProductsCount() * this.productsCapacity / res.getProductsCapacity();
    }
}