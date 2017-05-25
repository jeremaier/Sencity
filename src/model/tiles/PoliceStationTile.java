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
 * PoliceStation increase satisfaction and reduce probability of PoliceEvent
 *
 */
public class PoliceStationTile extends BuildableTile {
	private static final long serialVersionUID = 1L;

	// Constants
	/**
	 * Default value of {@link #getMaintenanceCost()}
	 */
	public final static int DEFAULT_MAINTENANCE_COST = 4;

	/**
	 * Default value of {@link #getEvolutionEnergyConsumption()}
	 */
	public final static int DEFAULT_EVOLUTION_ENERGY_CONSUMPTION = 5;

	/**
	 * Default value of {@link #getMaxNeededInhabitants()}
	 */
	public final static int DEFAULT_MAX_NEEDED_INHABITANTS = 15;

	/**
	 * Default value of {@link #getMaxNeededEnergy()}
	 */
	public final static int DEFAULT_MAX_NEEDED_ENERGY = 20;

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
	 * {@link #getMaxNeededEnergy()}
	 */
	private final int maxNeededEnergy;

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
		this.maxNeededInhabitants = PoliceStationTile.DEFAULT_MAX_NEEDED_INHABITANTS;
	}

	// Access
	/**
	 * @return Maximum number of energy units to consume. This maximum is
	 *         consumed if the Police station is full.
	 */
	public final int getMaxNeededEnergy() {
		return this.maxNeededEnergy;
	}
	
	/**
	 * @return Increase or decrease value of satisfaction
	 */
	public final int getSatisfactionValue() {
		return this.satisfactionValue;
	}

	/**
	 * @return Maximum number of inhabitants at work. This maximum is working
	 * 		   if the commerce is full.
	 */
	public final int getMaxNeededInhabitants() {
		return this.maxNeededInhabitants;
	}
	
	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = result * 17 + this.maxNeededEnergy;
		result = result * 17 + this.maintenanceCost;
		result = result * 17 + this.satisfactionValue;
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
		return this == o || super.equals(o)
				&& o.maxNeededEnergy == this.maxNeededEnergy 
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
		if (this.state == ConstructionState.BUILT)
			super.disassemble(res);
	}

	@Override
	public void evolve(CityResources res) {
		super.evolve(res);

		this.update(res);
	}

	@Override
	public void update(CityResources res) {
		if (this.state == ConstructionState.BUILT) {
			int busyPercentage = 100;
			int consumedEnergy = this.maxNeededEnergy;
			int workingPopulation = this.maxNeededInhabitants;
			final boolean enoughEnergy = res.getUnconsumedEnergy() >= consumedEnergy;
			final boolean enoughPopulation = res.getUnworkingPopulation() > workingPopulation;

			if(enoughEnergy && enoughPopulation)
				this.isEnergyMissing = false;
			else {
				if(!enoughEnergy) {
					consumedEnergy = res.getUnconsumedEnergy();
					this.isEnergyMissing = true;
				} else this.isEnergyMissing = false;

				if(!enoughPopulation) {
					workingPopulation = res.getUnworkingPopulation();
					this.isPopulationMissing = true;
				} else this.isPopulationMissing = false;
				
				final float energyPercentage = (float)consumedEnergy / this.maxNeededEnergy;
				final float workersPercentage = (float)workingPopulation / this.maxNeededInhabitants;
				
				busyPercentage -= energyPercentage * workersPercentage;
				this.isEnergyMissing = true;
			}

			res.consumeEnergy(Math.max(3, consumedEnergy));
			res.hireWorkers(workingPopulation);
			res.spend((int)(Math.round(this.maintenanceCost * GameBoard.getDifficulty().getCoeff())));
			this.updateSatisfaction(res, busyPercentage);
		}
	}

	private void updateSatisfaction(CityResources res, int percentage) {
		res.increaseSatisfaction((int)(this.satisfactionValue * percentage / 100.0));
	}
}
