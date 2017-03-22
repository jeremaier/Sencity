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
 * Enable to welcome new inhabitants and consume energy units according to the
 * number of inhabitants.
 */
public class CommercialTile extends BuildableTile {

    // Constants
    /**
     * Default value of {@link CommercialTile#getNeededInhabitants()}
     */
    public final static int DEFAULT_MAX_WORKING_POPULATION = 30;
    
    /**
     * Default value of {@link CommercialTile#getEvolutionEnergyConsumption()}
     */
    public final static int DEFAULT_EVOLUTION_ENERGY_CONSUMPTION = 5;

    /**
     * Default value of {@link CommercialTile#getMaxNeededEnergy()}
     */
    public final static int DEFAULT_MAX_NEEDED_ENERGY = 30;
    
    /**
     * Default value of {@link CommercialTile#getMaxNeededProducts()}
     */
    public final static int DEFAULT_MAX_NEEDED_PRODUCTS = 30;

    /**
     * Default value of {@link CommercialTile#getNeededInhabitants()}
     */
    public final static int DEFAULT_MAX_NEEDED_INHABITANTS = 30;
    
    /**
     * Default value of {@link CommercialTile#getProductsCapacity()}
     */
    public final static int DEFAULT_PRODUCTS_CAPACITY = 10;
    
    /**
     * Default value of {@link CommercialTile#getProductsPrice()}
     */
    public final static int DEFAULT_PRODUCTS_PRICE = 1;


    // Implementation
    /**
     * {@link #getMaxWorkingInhabitants()}
     */
    private final int maxWorkingInhabitants;
    
    /**
     * {@link #getMaxNeededEnergy()}
     */
    private final int maxNeededEnergy;
    
    /**
     * {@link #getMaxNeededProducts()}
     */
    private final int maxNeededProducts;
    
    /**
     * {@link #getProductsCapacity()}
     */
	private final int productsCapacity;
	
	 /**
     * {@link #getProductsPrice()}
     */
    private final int productsPrice;

    // Creation
	/**
     * @param capacity
     *            - {@link #getProductsCapacity()}
     */
    public CommercialTile(int capacity) {
        super(CommercialTile.DEFAULT_EVOLUTION_ENERGY_CONSUMPTION);
        this.productsPrice = CommercialTile.DEFAULT_PRODUCTS_PRICE;
        this.productsCapacity = capacity;
        this.maxNeededEnergy = CommercialTile.DEFAULT_MAX_NEEDED_ENERGY;
        this.maxNeededProducts = CommercialTile.DEFAULT_MAX_NEEDED_PRODUCTS;
        this.maxWorkingInhabitants = CommercialTile.DEFAULT_MAX_WORKING_POPULATION;

    }
    
    /**
     * Create with default settings.
     */
    public CommercialTile() {
        this(CommercialTile.DEFAULT_PRODUCTS_CAPACITY);
    }

    // Access
    
    /**
     * @return Maximum products capacity.
     */
    public final int getProductsCapacity() {
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
     * @return Maximum number of inhabitants at work. This maximum is working
     * 		   if the commerce is full.
     */
    public final int getMaxWorkingInhabitants() {
        return this.maxWorkingInhabitants;
    }
    
    /**
     * @return Price of a single product
     */
    public final int getProductsPrice() {
        return this.productsPrice;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 17 + this.productsPrice;
        result = result * 17 + this.productsCapacity;
        result = result * 17 + this.maxNeededEnergy;
        result = result * 17 + this.maxNeededProducts;
        result = result * 17 + this.maxWorkingInhabitants;
        return result;
    }

    // Status
    @Override
    public boolean equals(Object o) {
        return o instanceof CommercialTile && this.equals((CommercialTile) o);
    }

    /**
     * @param o
     * @return Is {@value o} equals to this?
     */
    public boolean equals(CommercialTile o) {
        return this == o || super.equals(o) 
        		&& o.productsPrice == this.productsPrice
                && o.maxNeededEnergy == this.maxNeededEnergy
                && o.maxNeededProducts == this.maxNeededProducts
                && o.maxWorkingInhabitants == this.maxWorkingInhabitants
                && o.productsCapacity == this.productsCapacity;
    }

    @Override
    public boolean isDestroyed() {
        return this.state == ConstructionState.DESTROYED;
    }

    // Change
    @Override
    public void disassemble(CityResources res) {
        if (this.state == ConstructionState.BUILT) {
        	
        	res.decreaseProductsCapacity(this.productsCapacity);
            super.disassemble(res);
        }
    }

    @Override
    public void evolve(CityResources res) {
        super.evolve(res);
        if (this.state == ConstructionState.BUILT) {
        	
        	res.increaseProductsCapacity(this.productsCapacity/2);
            this.update(res);
        }
    }

    @Override
    public void update(CityResources res) {
        if (this.state == ConstructionState.BUILT) {
        	
        	final int products = this.getProducts(res);
            final int busyPercentage = products * 100 / this.productsCapacity; // Integer
                                                                               // division
            final int neededEnergy = Math.max(10, busyPercentage * this.maxNeededEnergy / 100); // Integer
                                                                                                // division
            final int neededUnworkingPopulation = busyPercentage * this.maxWorkingInhabitants / 100;
            int vacantPercentage = 100;
            
            if (res.getUnconsumedEnergy() >= neededEnergy && res.getUnworkingPopulation() >= neededUnworkingPopulation) {
            	
                res.consumeEnergy(neededEnergy);
                res.hireWorkers(neededUnworkingPopulation);
           
                this.isPopulationMissing = false;
                this.isEnergyMissing = false;
                
                final int TotalPrice = res.getProductsCount()*productsPrice;
                res.creditWithTaxes(TotalPrice);
                vacantPercentage = 100 - busyPercentage;
            } else {
            	int consumedEnergy = neededEnergy;
            	int workingPopulation = neededUnworkingPopulation;
            		
	            if(res.getUnconsumedEnergy() < neededEnergy) {
	                consumedEnergy = res.getUnconsumedEnergy();
	                res.consumeEnergy(consumedEnergy);
	            	this.isEnergyMissing = true;
	            	res.hireWorkers(neededUnworkingPopulation);
	            }
	            
	            if(res.getUnworkingPopulation() < neededUnworkingPopulation) {
	                workingPopulation = res.getUnworkingPopulation();
	                res.hireWorkers(workingPopulation);
	            	this.isPopulationMissing = true;
	            	res.consumeEnergy(neededEnergy);
	            }
	            
	            final int missingEnergyPercentage = 100 - consumedEnergy * 100 / neededEnergy;
                final int missingPopulationPercentage = 100 - workingPopulation * 100 / neededUnworkingPopulation;
                vacantPercentage = missingEnergyPercentage * missingPopulationPercentage;
            }
            res.consumeProducts(vacantPercentage*productsCapacity/100);
        }
    }

    // Implementation
    /**
     * @param res
     * @return Approximation of the number of inhabitants in the current
     *         residence if the population is uniformly distributed.
     *
     *         e.g. if the residence capacity is X = 50, the city capacity is Y
     *         = 100 (including X) and the population is Z = 20, then the
     *         residence has (X / Y) * Z = 10 inhabitants.
     */
    private int getProducts(CityResources res) {
        assert res.getProductsCapacity() != 0;

        return res.getProductsCount() * this.productsCapacity / res.getProductsCapacity(); // Integer
        																				   // division
    }

}

