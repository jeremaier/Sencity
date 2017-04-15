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
 * PoliceStation increase satisfaction and reduce probability of PoliceEvent
 *
 */
public class PoliceStationTile extends BuildableTile {

    // Constants
    /**
     * Default value of {@link PoliceStationTile#getEvolutionEnergyConsumption()}
     */
    public final static int DEFAULT_EVOLUTION_ENERGY_CONSUMPTION = 5;

    /**
     * Default value of {@link PoliceStationTile#getMaxNeededEnergy()}
     */
    public final static int DEFAULT_MAX_NEEDED_ENERGY = 30;

    /**
     * Default value of {@link PoliceStationTile#getMaintenanceCost()}
     */
    public final static int DEFAULT_MAINTENANCE_COST = 5;
    
    /**
     * Default value of {@link PoliceStationTile#getSatisfactionValue()}
     */
    public final static int DEFAULT_SATISFACTION_VALUE = 5;
    
    // Implementation
    
    /**
     * {@link #getMaxNeededEnergy()}
     */
    private final int maxNeededEnergy;
    
    /**
     * {@link #getMaintenanceCost()}
     */
    private final int maintenanceCost;

    /**
     * {@link #getSatisfactionValue()}
     */
    private final int satisfactionValue;
    
    // Creation
    
    /**
     * Create with default settings.
     */
    public PoliceStationTile() {
        super(PoliceStationTile.DEFAULT_EVOLUTION_ENERGY_CONSUMPTION);
        this.satisfactionValue = PoliceStationTile.DEFAULT_SATISFACTION_VALUE;
        this.maintenanceCost = PoliceStationTile.DEFAULT_MAINTENANCE_COST;
        this.maxNeededEnergy = PoliceStationTile.DEFAULT_MAX_NEEDED_ENERGY;
    }

    // Access
    
    /**
     * @return Maximum number of energy units to consume. This maximum is
     *         consumed if the Police station is full.
     */
    public final int getMaxNeededEnergy() {
        return this.maxNeededEnergy;
    }

    public final int getMaintenanceCost() {
        return this.maintenanceCost;
    }
    public final int getSatisfactionValue() {
        return this.satisfactionValue;
    }
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 17 + this.maxNeededEnergy;
        return result;
    }

    // Status
    @Override
    public boolean equals(Object o) {
        return o instanceof PoliceStationTile && this.equals((PoliceStationTile) o);
    }

    /**
     * @param o
     * @return Is {@value o} equals to this?
     */
    public boolean equals(PoliceStationTile o) {
        return this == o || super.equals(o) && o.maxNeededEnergy == this.maxNeededEnergy 
        									&& o.maintenanceCost == this.maintenanceCost
        									&& o.satisfactionValue == this.satisfactionValue;
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
        	int vacantPercentage = 100;
            int neededEnergy = Math.max(1,this.maxNeededEnergy / 100);

            if (res.getUnconsumedEnergy() >= neededEnergy) {
                res.consumeEnergy(neededEnergy);
                this.isEnergyMissing = false;
                
            } else {
                final int consumedEnergy = res.getUnconsumedEnergy();
                vacantPercentage -= consumedEnergy / neededEnergy * 100;
                res.consumeEnergy(consumedEnergy);
                this.isEnergyMissing = true;
                }
            res.spend(this.maintenanceCost);
            res.increaseSatisfaction(satisfactionValue*vacantPercentage);
        }
    }
}
