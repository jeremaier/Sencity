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
public class IndustrialTile extends BuildableTile {

    // Constants
    /**
     * Default value of {@link IndustrialTile#getEvolutionEnergyConsumption()}
     */
    public final static int DEFAULT_EVOLUTION_ENERGY_CONSUMPTION = 5;

    /**
     * Default value of {@link IndustrialTile#getProduction}
     */
    private final static int DEFAULT_PRODUCTS_CAPACITY = 10;

    /**
     * Default value of {@link IndustrialTile#maxJoiningInhabitants}
     */
    private final static int DEFAULT_MAX_PRODUCTION = 15;

    /**
     * Default value of {@link IndustrialTile#getNeededEnergy()}
     */
    public final static int DEFAULT_MAX_NEEDED_ENERGY = 30;  
    
    /**
     * Default value of {@link IndustrialTile#getNeededInhabitants()}
     */
    public final static int DEFAULT_MAX_WORKING_POPULATION = 30;
    
    /**
     * {@link #getProductsCapacity()}
     */
	private final int productsCapacity;

    /**
     * Maximum number of products creation for each update.
     */
    private final int maxProduction;

    /**
     * {@link #getMaxNeededEnergy()}
     */
    private final int maxNeededEnergy;

    /**
     * {@link #getMaxNeededInhabitants()}
     */
    private final int maxWorkingInhabitants;
    
    // Creation
    /**
     * @param capacity
     *            - {@link #getProduction()}
     */
    public IndustrialTile(int capacity) {
        super(IndustrialTile.DEFAULT_EVOLUTION_ENERGY_CONSUMPTION);
        
        this.productsCapacity = capacity;
        this.maxProduction = IndustrialTile.DEFAULT_MAX_PRODUCTION;
        this.maxNeededEnergy = IndustrialTile.DEFAULT_MAX_NEEDED_ENERGY;
        this.maxWorkingInhabitants = IndustrialTile.DEFAULT_MAX_WORKING_POPULATION;
    }

    /**
     * Create with default settings.
     */
    public IndustrialTile() {
        this(IndustrialTile.DEFAULT_PRODUCTS_CAPACITY);
    }

    // Access
    /**
     * @return Maximum products capacity.
     */
    public final int getProductionCapacity() {
        return this.productsCapacity;
    }

    /**
     * @return Maximum number of energy units to consume. This maximum is
     *         consumed if the industry is full.
     */
    public final int getMaxNeededEnergy() {
        return this.maxNeededEnergy;
    }

    /**
     * @return Maximum number of inhabitants at work. This maximum is working
     * 		   if the industry is full.
     */
    public final int getMaxWorkingInhabitants() {
        return this.maxWorkingInhabitants;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 17 + this.productsCapacity;
        result = result * 17 + this.maxProduction;
        result = result * 17 + this.maxNeededEnergy;
        result = result * 17 + this.maxWorkingInhabitants;
        return result;
    }

    // Status
    @Override
    public boolean equals(Object o) {
        return o instanceof IndustrialTile && this.equals((IndustrialTile) o);
    }

    /**
     * @param o
     * @return Is {@value o} equals to this?
     */
    public boolean equals(IndustrialTile o) {
        return this == o || super.equals(o) && o.productsCapacity == this.productsCapacity && o.maxProduction == this.maxProduction
        		&& o.maxNeededEnergy == this.maxNeededEnergy && o.maxWorkingInhabitants == this.maxWorkingInhabitants
        		&& o.isDestroyed() == this.isDestroyed();
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
            res.increaseProductsCapacity(this.productsCapacity / 2);

            this.update(res);
        }
    }

    @Override
    public void update(CityResources res) {
        if (this.state == ConstructionState.BUILT) {
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

    // Implementation
    /**
     * @param res
     * @return The number of products in the current industry.
     *
     *         e.g. if the industry capacity is X = 50, the city capacity is Y
     *         = 100 (including X) and the population is Z = 20, then the
     *         residence has (X / Y) * Z = 10 products.
     */
    private int getProducts(CityResources res) {
        assert res.getProductsCapacity() != 0;

        return res.getProductsCount() * this.productsCapacity / res.getProductsCapacity();
    }
}
