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
import model.GameBoard;
import model.tiles.AirportTile;
import model.tiles.GrassTile;
import model.tiles.Tile;

public final class AirportZoneDelimiterTool extends Tool {

	// Constant
	private final static int CURRENCY_COST = 2500;

	// Status
	/**
     * canEffect returns true if the given Tile is buildable, false otherwise.
     */
	@Override
	public boolean canEffect (Tile aTarget) {
		return aTarget instanceof GrassTile;
	}

	@Override
	public boolean equals (Object o) {
		return this == o || o instanceof AirportZoneDelimiterTool;
	}

	/**
     * isAfordable returns true if the user can apply the Airport Tool, false
     * otherwise.
     */
	@Override
	public boolean isAfordable(Tile aTarget, CityResources r) {
		return AirportZoneDelimiterTool.CURRENCY_COST <= r.getCurrency();
	}
	
	@Override
	public boolean isAleadyBuild() {
		return AirportTile.alreadyBuild;
	}

	@Override
	public boolean isCorrespondantTile(Tile aTarget) {
		return aTarget instanceof AirportTile;
	}

	// Access
	@Override
	public int getCost (Tile aTarget) {
		return AirportZoneDelimiterTool.CURRENCY_COST;
	}

	@Override
	public int hashCode () {
		return getClass().hashCode();
	}

	/**
     * innerEffect apply the Airport tool to the given tile and update the
     * given CityResources.
     */
	@Override
	protected Tile innerEffect (Tile aTarget, CityResources r) {
		assert canEffect(aTarget);
		assert isAfordable(aTarget, r);

		r.spend((int)(Math.round(this.getCost(null) * GameBoard.getDifficulty().getCoeff())));

		return new AirportTile();
	}

	// Debugging
	@Override
	public String toString () {
		return getClass().getSimpleName();
	}
}