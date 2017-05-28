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

public class StadiumTile extends Tile implements Destroyable {
	private static final long serialVersionUID = 1L;

	// Constants
	/**
	 * Default value of {@link #getMaintenanceCost()}
	 */
	public final static int DEFAULT_MAINTENANCE_COST = 20;

	/**
	 * Default value of {@link #getMaxNeededEnergy()}
	 */
	public final static int DEFAULT_NEEDED_ENERGY = 80;

	/**
	 * Default value of {@link #getIncome()}
	 */
	public final static int DEFAULT_INCOME = 100;

	/**
	 * Default value of {@link #getSatisfactionValue()}
	 */
	public final static int DEFAULT_SATISFACTION_VALUE = 5;

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
	 * {@link #getIncome()}
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

	/**
	 * {@link #getMaintenanceCost()}
	 */
	protected int maintenanceCost;

	public StadiumTile() {
		this.neededEnergy = StadiumTile.DEFAULT_NEEDED_ENERGY;
		this.income = StadiumTile.DEFAULT_INCOME;
		this.satisfactionValue = StadiumTile.DEFAULT_SATISFACTION_VALUE;
		this.maintenanceCost = StadiumTile.DEFAULT_MAINTENANCE_COST;
		StadiumTile.alreadyBuild = true;
		this.isEnergyMissing = false;
		this.isDestroyed = false;
	}

	// Access
	/**
	 * @return Energy units to consume by default.
	 */
	public int getMaxNeededEnergy() {
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
	public final int getSatisfactionValue() {
		return this.satisfactionValue;
	}

	/**
	 * @return Maintenance cost.
	 */
	public int getMaintenanceCost() {
		return this.maintenanceCost;
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
		result = result * 17 + this.maintenanceCost;
		result = result * 17 + this.satisfactionValue;
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
	 *            - Object
	 * @return Is o equals to this?
	 */
	public boolean equals(StadiumTile o) {
		return this == o || super.equals(o)
				&& o.neededEnergy == this.neededEnergy
				&& o.income == this.income
				&& o.isDestroyed == this.isDestroyed
				&& o.isEnergyMissing == this.isEnergyMissing
				&& o.satisfactionValue == this.satisfactionValue
				&& o.isDestroyed == this.isDestroyed
				&& o.maintenanceCost == this.maintenanceCost;
	}

	@Override
	public void update(CityResources res) {
		if(!this.isDestroyed) {
			final double fluctuation = (5 + (Math.random() * 10)) / 10;
			int busyPercentage = 100;
			int consumedEnergy = this.neededEnergy;

			if(res.getUnconsumedEnergy() >= consumedEnergy)
				this.isEnergyMissing = false;
			else {
				consumedEnergy = res.getUnconsumedEnergy();
				busyPercentage -= (1 - consumedEnergy / this.neededEnergy) * 100.0;
				this.isEnergyMissing = true;
			}

			System.out.println(consumedEnergy);
			res.consumeEnergy(Math.max(15, consumedEnergy));
			res.creditWithTaxes((int)(fluctuation * busyPercentage * this.income / 100.0));
			res.spend((int)(Math.round(this.maintenanceCost * GameBoard.getDifficulty().getCoeff())));
			this.updateSatisfaction(res, busyPercentage);
		}
	}

	private void updateSatisfaction(CityResources res, int percentage) {
		res.increaseSatisfaction((int)(this.satisfactionValue * percentage / 100.0));
	}
}