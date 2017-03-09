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


    // Implementation

    /**
     * {@link #getMaxNeededEnergy()}
     */
    private final int maxNeededEnergy;
    
    /**
     * {@link #getMaxNeededProducts()}
     */
    private final int maxNeededProducts;
    
    /**
     * {@link #getMaxNeededInhabitants()}
     */
    private final int maxNeededInhabitants;

    // Creation
    /**
     * Create with default settings.
     */
    public CommercialTile() {
        super(CommercialTile.DEFAULT_EVOLUTION_ENERGY_CONSUMPTION);

        this.maxNeededEnergy = CommercialTile.DEFAULT_MAX_NEEDED_ENERGY;
        this.maxNeededProducts = CommercialTile.DEFAULT_MAX_NEEDED_PRODUCTS;
        this.maxNeededInhabitants= CommercialTile.DEFAULT_MAX_NEEDED_INHABITANTS;

    }

    // Access

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
     * @return Maximum number of inhabitants at commerce. This maximum is working
     * 		   if the commerce is full.
     */
    public final int getMaxNeededInhabitants() {
        return this.maxNeededInhabitants;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 17 + this.maxNeededEnergy;
        result = result * 17 + this.maxNeededProducts;
        result = result * 17 + this.maxNeededInhabitants;
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
                && o.maxNeededEnergy == this.maxNeededEnergy
                && o.maxNeededProducts == this.maxNeededProducts
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
            super.disassemble(res);
        }
    }

    @Override
    public void evolve(CityResources res) {
        super.evolve(res);
        if (this.state == ConstructionState.BUILT) {
            this.update(res);
        }
    }

    @Override
    public void update(CityResources res) {
        if (this.state == ConstructionState.BUILT) {
            final int inhabitants = this.getInhabitants(res);

            final int busyPercentage = inhabitants * 100 / this.inhabitantsCapacity; // Integer
                                                                                     // division
            final int neededEnergy = Math.max(1, busyPercentage * this.maxNeededEnergy / 100); // Integer
                                                                                               // division

            if (res.getUnconsumedEnergy() >= neededEnergy) {
                res.consumeEnergy(neededEnergy);
                this.isEnergyMissing = false;

                // Less space is available, less newcomers join
                final int vacantPercentage = 100 - busyPercentage;
                final int newcomers = vacantPercentage * this.maxJoiningInhabitants / 100;

                res.increasePopulation(newcomers);
            } else {
                final int consumedEnergy = res.getUnconsumedEnergy();
                res.consumeEnergy(consumedEnergy);
                this.isEnergyMissing = true;

                // More energy units are missing, more inhabitants leave
                final int missingEnergyPercentage = 100 - consumedEnergy * 100 / neededEnergy; // Integer
                                                                                               // division
                final int leavingInhabitants = Math.min(this.maxLeavingInhabitants, missingEnergyPercentage * inhabitants / 100); // Integer
                                                                                                                                  // division

                res.decreasePopulation(leavingInhabitants);
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
    private int getInhabitants(CityResources res) {
        assert res.getPopulationCapacity() != 0;

        final int caapcityPercentage = this.inhabitantsCapacity * 100 / res.getPopulationCapacity(); // Integer
                                                                                                     // division
        return res.getPopulation() * caapcityPercentage / 100; // Integer
                                                               // division
    }

}

