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
 * Enable to welcome new inhabitants and consume energy units according to the
 * number of inhabitants.
 */
public class CommercialTile extends BuildableTile {
	private static final long serialVersionUID = 1L;

	// Constants
	/**
	 * Default value of {@link #getMaintenanceCost()}
	 */
	public final static int DEFAULT_MAINTENANCE_COST = 40;

	/**
	 * Default value of {@link #getEvolutionEnergyConsumption()}
	 */
	public final static int DEFAULT_EVOLUTION_ENERGY_CONSUMPTION = 5;

	/**
	 * Default value of {@link #getMaxNeededEnergy()}
	 */
	public final static int DEFAULT_MAX_NEEDED_ENERGY = 10;

	/**
	 * Default value of {@link #getMaxSoldProducts()}
	 */
	public final static int DEFAULT_MAX_SOLD_PRODUCTS = 25;

	/**
	 * Default value of {@link #getMaxNeededInhabitants()}
	 */
	public final static int DEFAULT_MAX_NEEDED_INHABITANTS = 15;

	/**
	 * Default value of {@link #getProductsPrice()}
	 */
	public final static int DEFAULT_PRODUCTS_PRICE = 5;

	// Implementation
	/**
	 * Default value of {@link #getCommerceNumber()}
	 */
	private static int CommerceNumber = 0;
	
	/**
	 * {@link #getMaxWorkingInhabitants()}
	 */
	private final int maxNeededInhabitants;

	/**
	 * {@link #getMaxNeededEnergy()}
	 */
	private final int maxNeededEnergy;

	/**
	 * {@link #getMaxSoldProducts()}
	 */
	private final int maxSoldProducts;

	/**
	 * {@link #getProductsPrice()}
	 */
	private int productsPrice;

	public CommercialTile() {
		super(CommercialTile.DEFAULT_EVOLUTION_ENERGY_CONSUMPTION);
		this.productsPrice = CommercialTile.DEFAULT_PRODUCTS_PRICE;
		this.maxNeededEnergy = CommercialTile.DEFAULT_MAX_NEEDED_ENERGY;
		this.maxSoldProducts = CommercialTile.DEFAULT_MAX_SOLD_PRODUCTS;
		this.maxNeededInhabitants = CommercialTile.DEFAULT_MAX_NEEDED_INHABITANTS;
		this.maintenanceCost = CommercialTile.DEFAULT_MAINTENANCE_COST;
		CommercialTile.CommerceNumber++;
	}

	// Access
	
	/**
	 * @return Number of commerce builded.
	 */
	public static final int getCommerceNumber() {
		return CommercialTile.CommerceNumber;
	}

	/**
	 * @return Maximum number of energy units to consume. This maximum is
	 *         consumed if the commerce is full.
	 */
	public final int getMaxNeededEnergy() {
		return this.maxNeededEnergy;
	}

	/**
	 * @return Maximum number of products units to consume. This maximum is
	 *         consumed if the commerce has maximum employee and energy.
	 */
	public final int getMaxSoldProducts() {
		return this.maxSoldProducts;
	}

	/**
	 * @return Maximum number of inhabitants at work. This maximum is working
	 * 		   if the commerce is full.
	 */
	public final int getMaxNeededInhabitants() {
		return this.maxNeededInhabitants;
	}

	/**
	 * @return Price of a single product
	 */
	public final int getProductsPrice() {
		return this.productsPrice;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = result * 17 + this.productsPrice;
		result = result * 17 + this.maxNeededEnergy;
		result = result * 17 + this.maxSoldProducts;
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
				&& o.productsPrice == this.productsPrice
				&& o.maxNeededEnergy == this.maxNeededEnergy
				&& o.maxSoldProducts == this.maxSoldProducts
				&& o.maxNeededInhabitants == this.maxNeededInhabitants;
	}

	@Override
	public boolean isDestroyed() {
		return this.state == ConstructionState.DESTROYED;
	}

	// Change
	@Override
	public void disassemble(CityResources res) {
		if(this.state == ConstructionState.BUILT || this.state == ConstructionState.BUILTLVL2 || this.state == ConstructionState.BUILTLVL3)
			CommerceNumber--;
			super.disassemble(res);
	}

	@Override
	public void evolve(CityResources res) {
		switch(state) {
		case BUILT:
			this.state = ConstructionState.BUILTLVL2;
			this.productsPrice = (int)(CommercialTile.DEFAULT_PRODUCTS_PRICE * 1.2);
			break;

		case BUILTLVL2:
			this.state = ConstructionState.BUILTLVL3;
			this.productsPrice = (int)(CommercialTile.DEFAULT_PRODUCTS_PRICE * 1.4);
			break;

		case UNDER_CONSTRUCTION:
			this.state = ConstructionState.BUILT;
			break;

		default:
			break;
		}

		super.evolve(res);

		this.update(res);
	}

	@Override
	public void update(CityResources res) {
		if (this.state != ConstructionState.UNDER_CONSTRUCTION && this.state != ConstructionState.DESTROYED) {
			int consumedEnergy = this.maxNeededEnergy;
			int workingPopulation = this.maxNeededInhabitants;
			int soldProducts = this.maxSoldProducts;
			final double fluctuation = (7 + (Math.random() * 6)) / 10;
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

				soldPercentage *= productsPercentage * energyPercentage * workersPercentage;
			}
			
			res.consumeEnergy(Math.max(2, consumedEnergy));
			res.hireWorkers(workingPopulation);
			res.creditWithTaxes((int)(fluctuation * soldPercentage / 100.0 * maxSoldProducts * productsPrice));
			res.spend((int)(Math.round(this.maintenanceCost * GameBoard.getDifficulty().getCoeff())));
			res.consumeProducts((int)(soldPercentage / 100.0 * maxSoldProducts));
		}
	}
}