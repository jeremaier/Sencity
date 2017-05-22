package model.tools;

import model.CityResources;
import model.tiles.Tile;

public interface EvolveTool {
	/**
	 * @param aTarget
	 * @param r
	 * @return Evolve {@value aTarget} and spend needed resources from {@value r}.
	 */
	abstract void evolve(Tile currentTile, CityResources resources);
	
	/**
	 * @param aTarget
	 * @return Evolve cost function regarding actual state.
	 */
	abstract int getEvolveCost(Tile aTarget);
	
	/**
	 * @param aTarget
	 * @param r
	 */
	abstract void spend(Tile aTarget, CityResources r);
}
