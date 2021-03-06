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
import model.tiles.Tile;

/**
 * Abstract superclass that represents a tool that can affect some types of tiles.
 *
 * @author Victorien Elvinger
 *
 */
public abstract class Tool {

// Status
	/**
	 * @param aTarget
	 *            - Target tile
	 * @return Can current tool effect aTarget?
	 */
	public abstract boolean canEffect (Tile aTarget);

	/**
	 * @param aTarget
	 *            - Target tile
	 * @param r
	 *            - Resources
	 * @return Regarding  r, is the tool usable?
	 */
	public abstract boolean isAfordable (Tile aTarget, CityResources r);
	
	/**
	 * @param aTarget
	 *            - Target tile
	 * @return Can current tool evolve this building?
	 */
	public boolean canEvolve(Tile aTarget) {
		return false;
	}
	
	/**
	 * @return Verify if the tile is already build on the board or not
	 */
	public boolean isAleadyBuild() {
		return false;
	}
	
	/**
	 * @param aTarget
	 *            - Target tile
	 * @return The target is the same building as the tool.
	 */
	public abstract boolean isCorrespondantTile(Tile aTarget);

	// Access
	/**
	 * @param aTarget
	 *            - Target tile
	 * @return Cost of the use of the tool on aTarget.
	 */
	public abstract int getCost (Tile aTarget);

	/**
	 * @param aTarget
	 *            - Target tile
	 * @param r
	 *            - Resources
	 * @return Effect aTarget and spend needed resources from  r
	 * 		if the tool can effect aTarget and
	 * 		if it is affordable regarding r.
	 */
	public Tile effect (Tile aTarget, CityResources r) {
		if (canEffect(aTarget) && isAfordable(aTarget, r)) {
			return innerEffect(aTarget, r);
		}
		else {
			return aTarget;
		}
	}

	// Implementation
	/**
	 * @param aTarget
	 *            - Target tile
	 * @param r
	 *            - Resources
	 * @return Effect {@value aTarget} and spend needed resources from {@value r}.
	 */
	protected abstract Tile innerEffect (Tile aTarget, CityResources r);
}
