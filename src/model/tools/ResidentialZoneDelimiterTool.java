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

package model.tools;

import model.CityResources;
import model.tiles.BuildableTile;
import model.tiles.ConstructionState;
import model.tiles.GrassTile;
import model.tiles.ResidentialTile;
import model.tiles.Tile;

/**
 *
 * @author Victorien Elvinger
 *
 */
public final class ResidentialZoneDelimiterTool extends Tool implements EvolveTool {

	// Constant
	private final static int CURRENCY_COST = 20;
	private final static int EVOLVE_COST = 60;
	private final static int EVOLVE2_COST = 120;

	// Status
	/**
     * canEffect returns true if the given Tile is buildable, false otherwise.
     */
	@Override
	public boolean canEffect (Tile aTarget) {
		return aTarget instanceof GrassTile;
	}
	
	/**
	 * canEvolve returns true if the given Tile is evolvable and has not the max level already, false otherwise.
	 */
	@Override
	public boolean canEvolve (Tile aTarget) {
		if(!(aTarget instanceof ResidentialTile) || ((BuildableTile) aTarget).getState() == ConstructionState.BUILTLVL3)
			return false;
		
		return true;
	}

	@Override
	public boolean equals (Object o) {
		return this == o || o instanceof ResidentialZoneDelimiterTool;
		
	}

	/**
     * isAfordable returns true if the user can apply the Residential Tool, false
     * otherwise.
     */
	@Override
	public boolean isAfordable (Tile aTarget, CityResources r) {
		if(aTarget instanceof ResidentialTile)
			return this.getEvolveCost(aTarget) <= r.getCurrency();
			
		return ResidentialZoneDelimiterTool.CURRENCY_COST <= r.getCurrency();
	}

// Access
	@Override
	public int getCost (Tile aTarget) {
        if(aTarget instanceof ResidentialTile)
        	return this.getEvolveCost(aTarget);
        
		return ResidentialZoneDelimiterTool.CURRENCY_COST;
	}
	
	@Override
	public int getEvolveCost(Tile aTarget) {
		return ((ResidentialTile) aTarget).getState() == ConstructionState.BUILT ? ResidentialZoneDelimiterTool.EVOLVE_COST : ResidentialZoneDelimiterTool.EVOLVE2_COST;
	}

	@Override
	public int hashCode () {
		return getClass().hashCode();
	}

	/**
     * innerEffect apply the Residential tool to the given tile and update the
     * given CityResources.
     */
	@Override
	protected Tile innerEffect (Tile aTarget, CityResources r) {
		assert canEffect(aTarget);
		assert isAfordable(aTarget, r);
		
		r.spend(ResidentialZoneDelimiterTool.CURRENCY_COST);

		return new ResidentialTile();
	}

	/**
     * innerEffect apply the Commercial tool to the given tile and update the
     * given CityResources.
     */
	@Override
	public void evolve(Tile aTarget, CityResources r) {
		assert canEvolve(aTarget);
		assert isAfordable(aTarget, r);
		
		((ResidentialTile)aTarget).evolve(r);
		r.spend(this.getEvolveCost(aTarget));
	}

	// Debugging
	@Override
	public String toString () {
		return getClass().getSimpleName();
	}
}
