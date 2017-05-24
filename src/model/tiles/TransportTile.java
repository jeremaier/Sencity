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
 * Enable to sell more expensive products
 */
public abstract class TransportTile extends Tile implements Destroyable {
	private static final long serialVersionUID = 1L;

	// Implementation
	/**
	 * {@link #getMaxNeededEnergy()}
	 */
	protected int maxNeededEnergy;

	/**
	 * {@link #getMaxNeededInhabitants()}
	 */
	protected int maxNeededInhabitants;

	/**
	 * Evolution state
	 */
	protected boolean isDestroyed;

	/**
	 * {@link #isEnergyMissing()}
	 */
	protected boolean isEnergyMissing;

	/**
	 * {@link #isPopulationMissing()}
	 */
	protected boolean isPopulationMissing;

	/**
	 * {@link #getProductsPrice()}
	 */
	private final int productsPrice;

	/**
	 * {@link #getSatisfactionValue()}
	 */
	protected int satisfactionValue;

	/**
	 * {@link #getMaxSoldProducts()}
	 */
	protected int maxSoldProducts;

	/**
	 * {@link #getMaintenanceCost()}
	 */
	protected int maintenanceCost;

	// Creation
	/**
	 * @param capacity
	 *            - {@link #getProductsCapacity()}
	 */
	public TransportTile(int productsPrice) {
		this.productsPrice = productsPrice;
		this.isEnergyMissing = false;
		this.isPopulationMissing = false;
		this.isDestroyed = false;
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
	 * @return Maximum number of inhabitants at airport. This maximum is working
	 * 		   if the commerce is full.
	 */
	public final int getMaxNeededInhabitants() {
		return this.maxNeededInhabitants;
	}

	/**
	 * @return Maximum number of products units to consume. This maximum is
	 *         consumed if the commerce has maximum employee and energy.
	 */
	public final int getMaxSoldProducts() {
		return this.maxSoldProducts;
	}

	/**
	 * @return Maintenance cost.
	 */
	public int getMaintenanceCost() {
		return this.maintenanceCost;
	}

	/**
	 * @return Is energy missing in order to evolve or to update?
	 */
	public final boolean isEnergyMissing() {
		return this.isEnergyMissing;
	}

	/**
	 * @return Is population missing in order to evolve or to update?
	 */
	public final boolean isPopulationMissing() {
		return this.isPopulationMissing;
	}

	/**
	 * @return Price of a single product
	 */
	public final int getProductsPrice() {
		return this.productsPrice;
	}

	/**
	 * @return Increase or decrease value of satisfaction
	 */
	public final int getSatisfactionValue() {
		return this.satisfactionValue;
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = result * 17 + this.productsPrice;
		result = result * 17 + this.maxNeededEnergy;
		result = result * 17 + this.maxNeededInhabitants;
		result = result * 17 + this.maxSoldProducts;
		result = result * 17 + this.maintenanceCost;
		result = result * 17 + this.satisfactionValue;
		result = result * 17 + Boolean.hashCode(this.isDestroyed);
		result = result * 17 + Boolean.hashCode(this.isEnergyMissing);
		result = result * 17 + Boolean.hashCode(this.isPopulationMissing);
		return result;
	}

	// Status
	@Override
	public boolean equals(Object o) {
		return o instanceof TransportTile && this.equals((TransportTile) o);
	}

	/**
	 * @param o
	 * @return Is {@value o} equals to this?
	 */
	public boolean equals(TransportTile o) {
		return this == o || o.productsPrice == this.productsPrice
				&& o.maxNeededEnergy == this.maxNeededEnergy
				&& o.maxNeededInhabitants == this.maxNeededInhabitants
				&& o.maxSoldProducts == this.maxSoldProducts
				&& o.isDestroyed == this.isDestroyed
				&& o.isEnergyMissing == this.isEnergyMissing
				&& o.isPopulationMissing == this.isPopulationMissing
				&& o.maintenanceCost == this.maintenanceCost
				&& o.satisfactionValue == this.satisfactionValue
				&& o.isDestroyed() == this.isDestroyed();
	}
	
	@Override
	public boolean isDestroyed() {
		return this.isDestroyed;
	}

	public abstract void disassemble(CityResources res);

	@Override
	public void update(CityResources res) {
		if(!this.isDestroyed) {
			int consumedEnergy = this.maxNeededEnergy;
			int workingPopulation = this.maxNeededInhabitants;
			int soldProducts = this.maxSoldProducts;
			final double fluctuation = (6 + (Math.random() * 8)) / 10;
			final boolean enoughProducts = res.getProductsCount() >= soldProducts;
			final boolean enoughEnergy = res.getUnconsumedEnergy() >= consumedEnergy;
			final boolean enoughPopulation = res.getUnworkingPopulation() > workingPopulation;
			int soldPercentage = 100;

			if(enoughEnergy && enoughPopulation && enoughProducts) {
				this.isPopulationMissing = false;
				this.isEnergyMissing = false;
			} else {
				if(!enoughEnergy) {
					consumedEnergy = res.getUnconsumedEnergy();
					this.isEnergyMissing = true;
				} else this.isEnergyMissing = false;

				if(!enoughPopulation) {
					workingPopulation = res.getUnworkingPopulation();
					this.isPopulationMissing = true;
				} else this.isPopulationMissing = false;
				
				if(!enoughProducts)
					soldProducts = res.getProductsCount();

				final float productsPercentage = (float)soldProducts / this.maxSoldProducts;
				final float energyPercentage = (float)consumedEnergy / this.maxNeededEnergy;
				final float workersPercentage = (float)workingPopulation / this.maxNeededInhabitants;

				soldPercentage -= productsPercentage * energyPercentage * workersPercentage;
			}
			
			res.consumeEnergy(Math.max(10, consumedEnergy));
			res.hireWorkers(workingPopulation);
			res.creditWithTaxes((int)(fluctuation * soldPercentage / 100.0 * maxSoldProducts * productsPrice));
			res.spend((int)(Math.round(this.maintenanceCost * GameBoard.getDifficulty().getCoeff())));
			res.consumeProducts((int)(soldPercentage / 100.0 * maxSoldProducts));
			this.updateSatisfaction(res, soldPercentage);
		}
	}

    private void updateSatisfaction(CityResources res, int percentage) {
		res.increaseSatisfaction(this.satisfactionValue);
	}
}