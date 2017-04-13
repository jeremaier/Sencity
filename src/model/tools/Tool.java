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
	 *
	 * @param aTarget
	 * @return Can current tool effect {@value aTarget}?
	 */
	public abstract boolean canEffect (Tile aTarget);

	/**
	 * 
	 * @param r
	 * @return Regarding {@value r}, is the tool usable?
	 */
	public abstract boolean isAfordable (Tile aTarget, CityResources r);

// Access
	/**
	 * @return Cost of the use of the tool on {@value aTarget}.
	 */
	public abstract int getCost (Tile aTarget);

	/**
	 * 
	 * @param aTarget
	 * @param r
	 * @return Effect {@value aTarget} and spend needed resources from {@value r}
	 * 		if the tool can effect {@value aTarget} and
	 * 		if it is affordable regarding {@value r}.
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
	 * @param r
	 * @return Effect {@value aTarget} and spend needed resources from {@value r}.
	 */
	protected abstract Tile innerEffect (Tile aTarget, CityResources r);

	public boolean isAleadyBuild() {
		return false;
	}
}
