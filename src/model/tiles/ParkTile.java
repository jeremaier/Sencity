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
import model.GameBoard;

/**
 * Enable to reduce pollution in a certain area.
 */
public class ParkTile extends Tile implements Destroyable {
	private static final long serialVersionUID = 1L;

	// Constants
	/**
	 * Default value of {@link #getMaintenanceCost()}
	 */
	public final static int DEFAULT_MAINTENANCE_COST = 3;

	/**
	 * Default value of {@link #getNeededInhabitants()}
	 */
	public final static int DEFAULT_MAX_NEEDED_INHABITANTS = 5;

	/**
	 * Default value of {@link #getSatisfactionValue()}
	 */
	public final static int DEFAULT_SATISFACTION_VALUE = 5;
	
	// Implementation
	/**
	 * {@link #getMaxWorkingInhabitants()}
	 */
	private final int maxNeededInhabitants;

    /**
     * {@link #isPopulationMissing()}
     */
    protected boolean isPopulationMissing;
    
	/**
	 * {@link #getMaintenanceCost()}
	 */
	private int maintenanceCost;
    
	/**
     * Evolution state
     */
    private boolean isDestroyed;
    
    /**
     * {@link #getSatisfactionValue()}
     */
    private int satisfactionValue;
    
    // Creation
    
    /**
     * Create with default settings.
     */
	public ParkTile() {
		this.maxNeededInhabitants = ParkTile.DEFAULT_MAX_NEEDED_INHABITANTS;
		this.maintenanceCost = ParkTile.DEFAULT_MAINTENANCE_COST;
		this.satisfactionValue = ParkTile.DEFAULT_SATISFACTION_VALUE;
		this.isPopulationMissing = false;
		this.isDestroyed = false;
	}

	// Access
	/**
	 * @return Maximum number of inhabitants at work. This maximum is working
	 * 		   if the commerce is full.
	 */
	public final int getMaxNeededInhabitants() {
		return this.maxNeededInhabitants;
	}
    
    /**
     * @return Increase or decrease value of satisfaction
     */
    public final int getSatisfactionValue() {
    	return this.satisfactionValue;
    }
    
    /**
     * @return Maintenance cost.
     */
    public int getMaintenanceCost() {
    	return this.maintenanceCost;
    }
	
    /**
     * @return Is population missing in order to evolve or to update?
     */
    public final boolean isPopulationMissing() {
        return this.isPopulationMissing;
    }

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = result * 17 + this.maxNeededInhabitants;
		result = result * 17 + this.maintenanceCost;
		return result;
	}

	// Status
	@Override
	public boolean equals(Object o) {
		return o instanceof ParkTile && this.equals((ParkTile) o);
	}

	/**
	 * @param o
	 * @return Is {@value o} equals to this?
	 */
	public boolean equals(ParkTile o) {
		return this == o || o.maxNeededInhabitants == this.maxNeededInhabitants
				&& o.maintenanceCost == this.maintenanceCost
				&& o.satisfactionValue == this.satisfactionValue;
	}

	@Override
	public boolean isDestroyed() {
		return this.isDestroyed;
	}

	// Change
	@Override
	public void disassemble(CityResources res) {
        if (!this.isDestroyed)
        	this.isDestroyed = true;
	}

	@Override
	public void update(CityResources res) {
        if(!this.isDestroyed) {
            int efficacityPercentage = 100;
            int InhabitantsAvailable = this.maxNeededInhabitants;
            
            if(res.getUnworkingPopulation() > InhabitantsAvailable)
                this.isPopulationMissing = false;
            else {
            	InhabitantsAvailable = res.getUnworkingPopulation();
            	efficacityPercentage *= InhabitantsAvailable / this.maxNeededInhabitants;
            	this.isPopulationMissing = true;
            }
            
            res.hireWorkers(InhabitantsAvailable);
            res.spend((int)(Math.round(this.maintenanceCost * GameBoard.getDifficulty().getCoeff())));
            this.updateSatisfaction(res, efficacityPercentage);
        }
	}
	
    private void updateSatisfaction(CityResources res, int percentage) {
		res.increaseSatisfaction((int)(this.satisfactionValue * percentage / 100.0));
	}
}