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
     * Default value of {@link IndustrialTile#maxLeavingInhabitants}
     */
    private final static int DEFAULT_MIN_PRODUCTION = 10;

    /**
     * Default value of {@link IndustrialTile#getNeededEnergy()}
     */
    public final static int DEFAULT_MAX_NEEDED_ENERGY = 30;
    
    /**
     * Default value of {@link IndustrialTile#getNeededInhabitants()}
     */
    public final static int DEFAULT_MAX_NEEDED_INHABITANTS = 30;
    
    /**
     * {@link #getProductsCapacity()}
     */
	private final int productsCapacity;

    /**
     * Maximum number of products creation for each update.
     */
    private final int maxProduction;
    
    /**
     * Minimum number of products creation for each update.
     */
    private final int minProduction;

    /**
     * {@link #getMaxNeededEnergy()}
     */
    private final int maxNeededEnergy;

    /**
     * {@link #getMaxNeededInhabitants()}
     */
    private final int maxNeededInhabitants;
    
    // Creation
    /**
     * @param capacity
     *            - {@link #getProduction()}
     */
    public IndustrialTile(int capacity) {
        super(IndustrialTile.DEFAULT_EVOLUTION_ENERGY_CONSUMPTION);
        
        this.productsCapacity = capacity;
        this.maxNeededEnergy = IndustrialTile.DEFAULT_MAX_NEEDED_ENERGY;
        this.maxNeededInhabitants = IndustrialTile.DEFAULT_MAX_NEEDED_INHABITANTS;
        this.maxProduction = IndustrialTile.DEFAULT_MAX_PRODUCTION;
        this.minProduction = IndustrialTile.DEFAULT_MIN_PRODUCTION;
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
    public final int getMaxNeededInhabitants() {
        return this.maxNeededInhabitants;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 17 + this.productsCapacity;
        result = result * 17 + this.maxProduction;
        result = result * 17 + this.minProduction;
        result = result * 17 + this.maxNeededEnergy;
        result = result * 17 + this.maxNeededInhabitants;
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
        		&& o.minProduction == this.minProduction && o.maxNeededEnergy == this.maxNeededEnergy
        		&& o.maxNeededInhabitants == this.maxNeededInhabitants;
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
            res.increaseProductsCapacity(this.productsCapacity);

            this.update(res);
        }
    }

    @Override
    public void update(CityResources res) {
        if (this.state == ConstructionState.BUILT) {
            final int products = this.getProducts(res);
            final int busyPercentage = products * 100 / this.productsCapacity; // Integer
                                                                               // division
            final int neededEnergy = Math.max(1, busyPercentage * this.maxNeededEnergy / 100); // Integer
                                                                                               // division

            if (res.getUnconsumedEnergy() >= neededEnergy) {
                res.consumeEnergy(neededEnergy);
                this.isEnergyMissing = false;

                // Less space is available, less newcomers join
                final int vacantPercentage = 100 - busyPercentage;
                final int newProducts = vacantPercentage * this.maxProduction / 100;

                res.storeProducts(newProducts);
            } else {
                final int consumedEnergy = res.getUnconsumedEnergy();
                res.consumeEnergy(consumedEnergy);
                this.isEnergyMissing = true;

                // More energy units are missing, more inhabitants leave
                final int missingEnergyPercentage = 100 - consumedEnergy * 100 / neededEnergy; // Integer
                                                                                               // division
                final int leavingInhabitants = Math.min(this.maxProduction, missingEnergyPercentage * products / 100); // Integer
                                                                                                                       // division

                res.consumeProducts(leavingInhabitants);
            }
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

        final int capacityPercentage = this.productsCapacity * 100 / res.getProductsCapacity(); // Integer
                                                                                                // division
        return res.getProductsCount() * capacityPercentage / 100; // Integer
        														  // division
    }
}
