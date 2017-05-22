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
import model.tiles.CommercialTile;
import model.tiles.ConstructionState;
import model.tiles.GrassTile;
import model.tiles.Tile;

public final class CommercialZoneDelimiterTool extends Tool implements EvolveTool {

	// Constant
	private final static int CURRENCY_COST = 30;
	private final static int EVOLVE_COST = 90;
	private final static int EVOLVE2_COST = 180;

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
		if(aTarget instanceof CommercialTile && (((BuildableTile) aTarget).getState() == ConstructionState.BUILT || ((BuildableTile) aTarget).getState() == ConstructionState.BUILTLVL2))
			return true;
		
		return false;
	}

	@Override
	public boolean equals (Object o) {
		return this == o || o instanceof CommercialZoneDelimiterTool;
	}

	/**
     * isAfordable returns true if the user can apply the Commercial Tool, false
     * otherwise.
     */
	@Override
	public boolean isAfordable (Tile aTarget, CityResources r) {
		return this.getCost(aTarget) <= r.getCurrency();
	}

	// Access
	@Override
	public int getCost (Tile aTarget) {
        if(aTarget instanceof CommercialTile)
        	return this.getEvolveCost(aTarget);
        
		return CommercialZoneDelimiterTool.CURRENCY_COST;
	}
	
	@Override
	public int getEvolveCost(Tile aTarget) {
		return ((CommercialTile) aTarget).getState() == ConstructionState.BUILT ? CommercialZoneDelimiterTool.EVOLVE_COST : CommercialZoneDelimiterTool.EVOLVE2_COST;
	}

	@Override
	public int hashCode () {
		return getClass().hashCode();
	}

	/**
	 * spend check assertions and dispend the money needed to build or evolve
	 */
	@Override
	public void spend(Tile aTarget, CityResources r) {
		assert canEvolve(aTarget);
		assert isAfordable(aTarget, r);
		
		r.spend(this.getCost(aTarget));
	}
	
	/**
     * innerEffect apply the Commercial tool to the given tile and update the
     * given CityResources.
     */
	@Override
	protected Tile innerEffect (Tile aTarget, CityResources r) {
		this.spend(aTarget, r);

		return new CommercialTile();
	}
	
	/**
     * innerEffect apply the Commercial tool to the given tile and update the
     * given CityResources.
     */
	@Override
	public void evolve(Tile aTarget, CityResources r) {
		this.spend(aTarget, r);
		
		((CommercialTile)aTarget).evolve(r);
	}

	// Debugging
	@Override
	public String toString () {
		return getClass().getSimpleName();
	}
}
