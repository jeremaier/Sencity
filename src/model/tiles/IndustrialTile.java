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
public class IndustrialTile extends BuildableTile {
	private static final long serialVersionUID = 1L;

	// Constants
	/**
	 * Default value of {@link #getMaintenanceCost()}
	 */
	public final static int DEFAULT_MAINTENANCE_COST = 4;
	
	/**
	 * Default value of {@link IndustrialTile#getEvolutionEnergyConsumption()}
	 */
	public final static int DEFAULT_EVOLUTION_ENERGY_CONSUMPTION = 5;

	/**
	 * Default value of {@link IndustrialTile#getProductionCapacity}
	 */
	public final static int DEFAULT_PRODUCTS_CAPACITY = 20;

	/**
	 * Default value of {@link IndustrialTile#getMaxProduction}
	 */
	public final static int DEFAULT_MAX_PRODUCTION = 5;

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
	public static int maxProduction;

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
		maxProduction = DEFAULT_MAX_PRODUCTION;
		this.productsCapacity = capacity;
		this.maxNeededEnergy = IndustrialTile.DEFAULT_MAX_NEEDED_ENERGY;
		this.maxNeededInhabitants = IndustrialTile.DEFAULT_MAX_NEEDED_INHABITANTS;
		this.maintenanceCost = IndustrialTile.DEFAULT_MAINTENANCE_COST;
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
	 * @return Maximum number of production per round.
	 */
	public final int getMaxProduction() {
		return maxProduction;
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
	public final int getMaxWorkingInhabitants() {
		return this.maxNeededInhabitants;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = result * 17 + this.productsCapacity;
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
		return this == o || super.equals(o)
				&& o.productsCapacity == this.productsCapacity
				&& o.maxNeededEnergy == this.maxNeededEnergy
				&& o.maxNeededInhabitants == this.maxNeededInhabitants
				&& o.isDestroyed() == this.isDestroyed();
	}

	@Override
	public boolean isDestroyed() {
		return this.state == ConstructionState.DESTROYED;
	}

	// Change
	@Override
	public void disassemble(CityResources res) {
		if (this.state == ConstructionState.BUILT || this.state == ConstructionState.BUILTLVL2 || this.state == ConstructionState.BUILTLVL3) {
			res.decreaseProductsCapacity(this.getProducts(res), this.productsCapacity);

			super.disassemble(res);
		}
	}

	@Override
	public void evolve(CityResources res) {
		switch(state) {
		case BUILT:
			this.state = ConstructionState.BUILTLVL2;
			res.increaseProductsCapacity(this.productsCapacity * (int)(4.0 / 3));
			break;

		case BUILTLVL2:
			this.state = ConstructionState.BUILTLVL3;
			res.increaseProductsCapacity(this.productsCapacity * (int)(5.0 / 3));
			break;

		case UNDER_CONSTRUCTION:
			this.state = ConstructionState.BUILT;
			res.increaseProductsCapacity(this.productsCapacity);
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
			final int neededEnergy = Math.max(5, this.maxNeededEnergy / 100);
			final int neededUnworkingPopulation = this.maxNeededInhabitants / 100;
			final boolean enoughEnergy = res.getUnconsumedEnergy() >= neededEnergy;
			final boolean enoughPopulation = res.getUnworkingPopulation() > neededUnworkingPopulation;
			final double fluctuation = (7 + (Math.random() * 6)) / 10;
			int consumedEnergy = neededEnergy;
			int workingPopulation = neededUnworkingPopulation;
			int productionPercentage = 100;
			
			if(enoughEnergy && enoughPopulation) {
				this.isEnergyMissing = false;
				this.isPopulationMissing = false;
			} else {
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

				productionPercentage *= energyPercentage * workersPercentage;
			}
						
			res.consumeEnergy(consumedEnergy);
			res.hireWorkers(workingPopulation);
			res.storeProducts((int)(fluctuation * productionPercentage * Math.ceil(maxProduction / 100.0)));
            res.spend((int)(Math.round(this.maintenanceCost * GameBoard.getDifficulty().getCoeff())));
		}
	}

	// Implementation
	/**
	 * @param res
	 * @return The number of products in the current industry.
	 *
	 *         e.g. if the industry capacity is X = 50, the city capacity is Y
	 *         = 100 (including X) and the population is Z = 20, then the
	 *         residence has (X / Y) * Z = 10 products.
	 */
	public int getProducts(CityResources res) {
		assert res.getProductsCapacity() != 0;

		return res.getProductsCount() * this.productsCapacity / res.getProductsCapacity();
	}
}
