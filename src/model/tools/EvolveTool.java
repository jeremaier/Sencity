package model.tools;

import model.CityResources;
import model.tiles.Tile;

public interface EvolveTool {
	/**
	 * Evolve aTarget and spend needed resources from resources.
	 * 
	 * @param aTarget
	 *            - Target tile
	 * @param resources
	 *            - Resources
	 */
	abstract void evolve(Tile aTarget, CityResources resources);
	
	/**
	 * @param aTarget
	 *            - Target tile
	 * @return Evolve cost function regarding actual state.
	 */
	abstract int getEvolveCost(Tile aTarget);
	
	/**
	 * @param aTarget
	 *            - Target tile
	 * @param r
	 *            - Resources
	 */
	abstract void spend(Tile aTarget, CityResources r);
}
