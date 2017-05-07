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

public class StadiumTile extends Tile implements Destroyable {
    // Constants
    /**
     * Default value of {@link #getProductsPrice()}
     */
    public final static int DEFAULT_NEEDED_ENERGY = 100;
    
    /**
     * Default value of {@link #getProductsPrice()}
     */
    public final static int DEFAULT_INCOME = 1000;
    
    /**
     * Default value of {@link #getSatisfactionValue()}
     */
    public final static int DEFAULT_SATISFACTION_VALUE = 10;
    
    /**
     * {@link #getMaxNeededEnergy()}
     */
    private final int neededEnergy;
    
	/**
     * Evolution state
     */
    private boolean isDestroyed;
    
    /**
     * {@link #isEnergyMissing()}
     */
    private boolean isEnergyMissing;
    
	/**
    * {@link #getProductsPrice()}
    */
    private final int income;
   
    /**
     * This building is already build?
     */
    public static boolean alreadyBuild = false;
    
    /**
     * {@link #getSatisfactionValue()}
     */
    private int satisfactionValue;

	public StadiumTile() {
        this.neededEnergy = StadiumTile.DEFAULT_NEEDED_ENERGY;
        this.income = StadiumTile.DEFAULT_INCOME;
        this.satisfactionValue = StadiumTile.DEFAULT_SATISFACTION_VALUE;
		StadiumTile.alreadyBuild = true;
        this.isEnergyMissing = false;
	}
	
	// Access
    /**
     * @return Energy units to consume by default.
     */
	public int getNeededEnergy() {
		return neededEnergy;
	}
	
    /**
     * @return Income per round.
     */
    public final int getIncome() {
        return this.income;
    }
	
    /**
     * @return Is energy missing in order to evolve or to update?
     */
    public final boolean isEnergyMissing() {
        return this.isEnergyMissing;
    }
    
    /**
     * @return Increase or decrease value of satisfaction
     */
    public final int getSatisfaction() {
    	return this.satisfactionValue;
    }
    
    @Override
    public void disassemble(CityResources res) {
        if (!this.isDestroyed) {
        	this.isDestroyed = true;
        	StadiumTile.alreadyBuild = false;
        }
    }

	@Override
	public boolean isDestroyed() {
		return this.isDestroyed;
	}
	
    @Override
    public int hashCode() {
        int result = 1;
        result = result * 17 + this.neededEnergy;
        result = result * 17 + this.income;
        result = result * 17 + Boolean.hashCode(this.isDestroyed);
        result = result * 17 + Boolean.hashCode(this.isEnergyMissing);
        return result;
    }

    // Status
    @Override
    public boolean equals(Object o) {
        return o instanceof StadiumTile && this.equals((StadiumTile) o);
    }

    /**
     * @param o
     * @return Is {@value o} equals to this?
     */
    public boolean equals(StadiumTile o) {
        return this == o || super.equals(o)
                && o.neededEnergy == this.neededEnergy
                && o.income == this.income
                && o.isDestroyed == this.isDestroyed
                && o.isEnergyMissing == this.isEnergyMissing;
    }

	@Override
	public void update(CityResources res) {
        if (!this.isDestroyed) {
            int vacantPercentage = 100;
            int energyAvailable = this.neededEnergy;
            
            if(res.getUnconsumedEnergy() >= this.neededEnergy) {
                this.isEnergyMissing = false;
            } else {
            	energyAvailable = res.getUnconsumedEnergy();
                vacantPercentage -= energyAvailable / this.neededEnergy * 100;
            	this.isEnergyMissing = true;
            }
            
            res.consumeEnergy(energyAvailable);
            res.creditWithTaxes(vacantPercentage * this.income / 100);
            this.updateSatisfaction(res);
        }
	}
	
    private void updateSatisfaction(CityResources res) {
		res.increaseSatisfaction(this.satisfactionValue);
	}
}