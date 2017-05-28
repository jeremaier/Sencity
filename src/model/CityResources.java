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

package model;

import java.io.Serializable;

/**
 * Represents the resources and the parameters of the city. An ephemeral
 * resource is reset at each step thanks to {@link CityResources#getVat()}.
 */
public class CityResources implements Serializable {
	private static final long serialVersionUID = 1L;
	
    // Constant
    /**
     * Default value for {@link CityResources#getVat()}.
     */
    public final static int DEFAULT_VAT = 20;
	
	/**
	 * Amount of satisfaction gain for a good event.
	 */
	public final static int EXTRA_EVENT_SATISFACTION = 3;
	
	/**
	 * Amount of satisfaction gain for a bad event.
	 */
	private static final int DECREASE_EVENT_SATISFACTION = 1;

    // Implementation (Currency)
    /**
     * {@link #getCurrency()}
     */
    private int currency;

    /**
     * {@link #getVat()}
     */
    private int vat;

    // Implementation (Energy)
    /**
     * {@link #getUnconsumedEnergy()}
     */
    private int unconsumedEnergy;

    /**
     * {@link #getEnergyProduction()}
     */
    private int energyProduction;

    // Implementation (Population)
    /**
     * {@link #getUnworkingPopulation()}
     */
    private int unworkingPopulation;

    /**
     * {@link #getPopulation()}
     */
    private int population;

    /**
     * {@link #getPopulationCapacity()}
     */
    private int populationCapacity;

    // Implementation (Product)
    /**
     * {@link #getProductsCount()}
     */
    private int productsCount;

    /**
     * {@link #getProductsCapacity()}
     */
    private int productsCapacity;
    
    // Implementation (Satisfaction / Pollution)
    /**
     * {@link #getSatisfaction()}
     */
    private int satisfaction;
    
    /**
     * {@link #getPollution()}
     */
    private int pollution;
    
    /**
     * {@link #getGoodEventOccurrence()}
     */
    private int goodEventOccurence;
    
    /**
     * {@link #getBadEventOccurrence()}
     */
    private int badEventOccurence;
    
    // Creation
    /**
     *
     * @param aCurrency
     *            - {@link #getCurrency()}
     */
    public CityResources(int aCurrency) {
        assert aCurrency >= 0;

        this.currency = aCurrency;
        this.vat = CityResources.DEFAULT_VAT;
    }

    /**
     *
     * @param aCurrency
     *            - {@link #getCurrency()}
     * @param aPopulation
     *            - {@link #getPopulation()} and
     *            {@link #getPopulationCapacity()}
     */
    public CityResources(int aCurrency, int aPopulation) {
        this(aCurrency);
        assert aPopulation >= 0;

        this.population = aPopulation;
        this.populationCapacity = aPopulation;

        this.resetEphemerals();
    }

    // Status
    @Override
    public boolean equals(Object o) {
        return o instanceof CityResources && this.equals((CityResources) o);
    }

    /**
     * @param o
     *            - Object
     * @return Is o equals to this?
     */
    public boolean equals(CityResources o) {
        return this == o || super.equals(o) && o.currency == this.currency && o.vat == this.vat && o.unconsumedEnergy == this.unconsumedEnergy && o.energyProduction == this.energyProduction
                && o.unworkingPopulation == this.unworkingPopulation && o.population == this.population && o.populationCapacity == this.populationCapacity && o.productsCount == this.productsCount
                && o.productsCapacity == this.productsCapacity && o.satisfaction == this.satisfaction && o.pollution == this.pollution;
    }

    // Access
    @Override
    public int hashCode() {
        int result = 1;
        result = result * 17 + this.currency;
        result = result * 17 + this.vat;
        result = result * 17 + this.unconsumedEnergy;
        result = result * 17 + this.energyProduction;
        result = result * 17 + this.unworkingPopulation;
        result = result * 17 + this.population;
        result = result * 17 + this.populationCapacity;
        result = result * 17 + this.productsCount;
        result = result * 17 + this.productsCapacity;
        result = result * 17 + this.satisfaction;
        result = result * 17 + this.pollution;
        return result;
    }

    // Access (Currency)
    /**
     * @return Accumulated currency.
     */
    public int getCurrency() {
        return this.currency;
    }

    /**
     * @return Value-Added-Tax in percentage.
     */
    public int getVat() {
        return this.vat;
    }

    // Access (Energy)
    /**
     * @return Number of consumed energy units.
     */
    public int getConsumedEnergy() {
        return this.energyProduction - this.unconsumedEnergy;
    }

    /**
     * @return Number of available energy units.
     */
    public int getUnconsumedEnergy() {
        return this.unconsumedEnergy;
    }

    /**
     *
     * @return Monthly production of energy units.
     */
    public int getEnergyProduction() {
        return this.energyProduction;
    }

    // Access (Population)
    /**
     * @return Number of job-less citizens.
     */
    public int getUnworkingPopulation() {
        return this.unworkingPopulation;
    }

    /**
     * @return Number of citizens with a job.
     */
    public int getWorkingPopulation() {
        return this.population - this.unworkingPopulation;
    }

    /**
     * @return Total number of citizens.
     */
    public int getPopulation() {
        return this.population;
    }

    /**
     *
     * @return Capacity of the city.
     */
    public int getPopulationCapacity() {
        return this.populationCapacity;
    }

    // Access (Product)
    /**
     * @return Accumulated number of products.
     */
    public int getProductsCount() {
        return this.productsCount;
    }

    /**
     * @return Maximum number of products that can be stored.
     */
    public int getProductsCapacity() {
        return this.productsCapacity;
    }

    // Change (Currency)
    /**
     * Set the Value-Added-Tax in percentage.
     * 
     * @param vat
     *            - VAT value
     */
    public void setVat(int vat) {
        this.vat = vat;
    }
    
    /**
     * Decrease {@link #getCurrency()} by an amount.
     *
     * @param amount
     *            - Amount to credit
     */
    public void credit(int amount) {
        assert amount >= 0;

        this.currency = this.currency + amount;
    }

    /**
     * Get VAT on a currencyAmount and {@link #credit(int)} with the
     * obtained result.
     *
     * @param currencyAmount
     *            - Amount of currency to evaluate
     */
    public void creditWithTaxes(int currencyAmount) {
        assert currencyAmount >= 0;

        this.credit(currencyAmount * this.vat / 100 * this.getSatisfaction() / 50); // Integer division
    }

    /**
     * Increase {@link #getCurrency()} by an amount.
     *
     * @param amount
     *            - Amount to spend
     */
    public void spend(int amount) {
        assert amount >= 0;

        this.currency = this.currency - amount;
    }

    // Change (Energy)
    /***
     * Increase {@link #getConsumedEnergy()} by an amount.
     *
     * @param amount
     *            - Amount to consume
     */
    public void consumeEnergy(int amount) {
        assert 0 <= amount && amount <= this.getUnconsumedEnergy();

        this.unconsumedEnergy = this.unconsumedEnergy - amount;
    }

    /**
     * Decrease {@link #getEnergyProduction()} by an amount.
     *
     * @param amount
     *            - Amount to decrease
     */
    public void decreaseEnergyProduction(int amount) {
        assert amount >= 0;

        this.energyProduction = Math.max(0, this.energyProduction - amount);
        this.unconsumedEnergy = Math.min(this.unconsumedEnergy, this.energyProduction);
    }

    /**
     * Increase {@link #getEnergyProduction()} by an amount.
     *
     * @param amount
     *            - Amount to increase
     */
    public void increaseEnergyProduction(int amount) {
        assert amount >= 0;

        this.energyProduction = this.energyProduction + amount;
        this.unconsumedEnergy = this.unconsumedEnergy + amount;
    }

    // Change (Population)
    /**
     * Increase {@link #getWorkingPopulation()} by an amount.
     *
     * @param amount
     *            - Amount to hire
     */
    public void hireWorkers(int amount) {
        assert 0 <= amount && amount <= this.getUnworkingPopulation();

        this.unworkingPopulation = this.unworkingPopulation - amount;
    }

    /**
     * Increase {@link #getPopulation()} by an amount.
     *
     * @param amount
     *            - Amount to increase
     */
    public void increasePopulation(int amount) {
        assert amount >= 0;

        final int joiningPopulation = Math.min(this.populationCapacity - this.population, amount);
        this.population = this.population + joiningPopulation;
        this.unworkingPopulation = this.unworkingPopulation + joiningPopulation;
    }

    /**
     * Decrease {@link #getPopulation()} by an amount.
     *
     * @param amount
     *            - Amount to decrease
     */
    public void decreasePopulation(int amount) {
        assert amount >= 0;

        this.population = Math.max(0, this.population - amount);
        this.unworkingPopulation = Math.min(this.unworkingPopulation, this.population);
    }

    /**
     * Increase {@link #getPopulationCapacity()} by an amount.
     *
     * @param amount
     *            - Amount to increase
     */
    public void increasePopulationCapacity(int amount) {
        assert amount >= 0;

        this.populationCapacity = this.populationCapacity + amount;
    }

    /**
     * Decrease {@link #getPopulationCapacity()} by an amount.
     *
     * @param amount
     *            - Amount to decrease
     */
    public void decreasePopulationCapacity(int amount) {
        assert 0 <= amount && amount <= this.getPopulationCapacity();

        this.populationCapacity = this.populationCapacity - amount;
        this.population = Math.min(this.population, this.populationCapacity);
    }

    // Change (Product)
    /**
     * Decrease {@link #getProductsCount()} by an amount.
     *
     * @param amount
     *            - Amount to consume
     */
    public void consumeProducts(int amount) {
        assert amount >= 0;

        this.productsCount = Math.max(0, this.productsCount - amount);
    }

    /**
     * Increase {@link #getProductsCount()} by an amount.
     *
     * @param amount
     *            - Amount to store
     */
    public void storeProducts(int amount) {
        assert amount >= 0;
        
        this.productsCount = Math.min(this.productsCapacity, this.productsCount + amount);
    }

    /**
     * Decrease {@link #getProductsCapacity()} by an amount.
     * 
     * @param products
     *            - Amount products to decrease
     * @param amount
     *            - Amount capacity to decrease
     */
    public void decreaseProductsCapacity(int products, int amount) {
        assert 0 <= amount && amount <= this.getProductsCapacity();

        this.productsCapacity -= amount;
        this.productsCount -= products;
    }

    /**
     * Increase {@link #getProductsCapacity()} by an amount.
     *
     * @param amount
     *            - Amount to increase
     */
    public void increaseProductsCapacity(int amount) {
        assert amount >= 0;

        this.productsCapacity = this.productsCapacity + amount;
    }

    // Reset
    /**
     * Reset ephemeral resources.
     */
    public void resetEphemerals() {
        this.unworkingPopulation = this.population;
        this.unconsumedEnergy = this.energyProduction;
        this.satisfaction = 100;
        this.pollution = 0;
    }
	
    /**
     * @return Actual satisfaction of the population.
     */
	public int getSatisfaction() {
		return this.satisfaction;
	}

    /**
     * Increase {@link #getSatisfaction()} by an amount.
     *
     * @param amount
     *            - Amount to increase
     */
	public void increaseSatisfaction(int amount) {
		this.satisfaction = Math.min(100, this.satisfaction + amount);
	}

    /**
     * Decrease {@link #getSatisfaction()} by amount.
     *
     * @param amount
     *            - Amount to decrease
     */
	public void decreaseSatisfaction(int amount) {
		this.satisfaction = Math.max(0, this.satisfaction - amount);
	}
	
    /**
     * @return Actual satisfaction of the population.
     */
	public int getPollution() {
		return this.pollution;
	}

    /**
     * Increase {@link #getPollution()} by an amount.
     *
     * @param amount
     *            - Amount to increase
     */
	public void increasePollution(int amount) {
		this.pollution = Math.min(100, this.pollution + amount);
	}

    /**
     * Decrease {@link #getPollution()} by an amount.
     *
     * @param amount
     *            - Amount to decrease
     */
	public void decreasePollution(int amount) {
		this.pollution = Math.max(0, this.pollution - amount);
	}

    /**
     * Increase {@link #getGoodEventOccurrence()}.
     */
	public void increaseGoodEventOccurrence() {
		this.goodEventOccurence++;
	}
	
    /**
     * @return Total good event occurrence during the game.
     */
	public int getGoodEventOccurrence() {
		return this.goodEventOccurence;
	}

    /**
     * Increase {@link #getBadEventOccurrence()}.
     */
	public void increaseBadEventOccurrence() {
		this.badEventOccurence++;
	}
	
    /**
     * @return Total bad event occurrence during the game.
     */
	public int getBadEventOccurrence() {
		return this.badEventOccurence;
	}
	
	/**
	 * @return The extra satisfaction earn for each good event.
	 */
	public static int getExtraEventSatisfaction() {
		return EXTRA_EVENT_SATISFACTION;
	}

	/**
	 * @return The decrease amount of satisfaction for each bad event.
	 */
	public static int getDecreaseEventSatisfaction() {
		return DECREASE_EVENT_SATISFACTION;
	}
}
