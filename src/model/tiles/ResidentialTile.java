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
public class ResidentialTile extends BuildableTile {
	private static final long serialVersionUID = 1L;
	
    // Constants
    /**
     * Default value of {@link ResidentialTile#getEvolutionEnergyConsumption()}
     */
    public final static int DEFAULT_EVOLUTION_ENERGY_CONSUMPTION = 5;

    /**
     * Default value of {@link ResidentialTile#maxJoiningInhabitants}
     */
    private final static int DEFAULT_MAX_JOINING_INHABITANTS = 15;

    /**
     * Default value of {@link ResidentialTile#maxLeavingInhabitants}
     */
    private final static int DEFAULT_MAX_LEAVING_INHABITANTS = 10;

    /**
     * Default value of {@link ResidentialTile#getMaxNeededEnergy()}
     */
    public final static int DEFAULT_MAX_NEEDED_ENERGY = 30;

    /**
     * Default value of {@link ResidentialTile#getInhabitantsCapacity()}
     */
    public final static int DEFAULT_INHABITANTS_CAPACITY = 40;

    // Implementation
    /**
     * {@link #getInhabitantsCapacity()}
     */
    private final int inhabitantsCapacity;

    /**
     * Maximum number of newcomers for each update.
     */
    private final int maxJoiningInhabitants;

    /**
     * Maximum number of leaving inhabitants for each update.
     */
    private final int maxLeavingInhabitants;

    /**
     * {@link #getMaxNeededEnergy()}
     */
    private final int maxNeededEnergy;

    // Creation
    /**
     * @param capacity
     *            - {@link #getInhabitantsCapacity()}
     */
    public ResidentialTile(int capacity) {
        super(ResidentialTile.DEFAULT_EVOLUTION_ENERGY_CONSUMPTION);

        this.inhabitantsCapacity = capacity;
        this.maxNeededEnergy = ResidentialTile.DEFAULT_MAX_NEEDED_ENERGY;
        this.maxJoiningInhabitants = ResidentialTile.DEFAULT_MAX_JOINING_INHABITANTS;
        this.maxLeavingInhabitants = ResidentialTile.DEFAULT_MAX_LEAVING_INHABITANTS;
    }

    /**
     * Create with default settings.
     */
    public ResidentialTile() {
        this(ResidentialTile.DEFAULT_INHABITANTS_CAPACITY);
    }

    // Access
    /**
     * @return Maximum number of inhabitants.
     */
    public final int getInhabitantsCapacity() {
        return this.inhabitantsCapacity;
    }

    /**
     * @return Maximum number of energy units to consume. This maximum is
     *         consumed if the residence is full.
     */
    public final int getMaxNeededEnergy() {
        return this.maxNeededEnergy;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 17 + this.inhabitantsCapacity;
        result = result * 17 + this.maxJoiningInhabitants;
        result = result * 17 + this.maxLeavingInhabitants;
        result = result * 17 + this.maxNeededEnergy;
        return result;
    }

    // Status
    @Override
    public boolean equals(Object o) {
        return o instanceof ResidentialTile && this.equals((ResidentialTile) o);
    }

    /**
     * @param o
     * @return Is {@value o} equals to this?
     */
    public boolean equals(ResidentialTile o) {
        return this == o || super.equals(o) && o.inhabitantsCapacity == this.inhabitantsCapacity && o.maxJoiningInhabitants == this.maxJoiningInhabitants
                && o.maxLeavingInhabitants == this.maxLeavingInhabitants && o.maxNeededEnergy == this.maxNeededEnergy;
    }

    @Override
    public boolean isDestroyed() {
        return this.state == ConstructionState.DESTROYED;
    }

    // Change
    @Override
    public void disassemble(CityResources res) {
        if (this.state == ConstructionState.BUILT) {
            res.decreasePopulationCapacity(this.inhabitantsCapacity);

            super.disassemble(res);
        }
    }

    @Override
    public void evolve(CityResources res) {
        super.evolve(res);

        if (this.state == ConstructionState.BUILT) {
            res.increasePopulationCapacity(this.inhabitantsCapacity);

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
