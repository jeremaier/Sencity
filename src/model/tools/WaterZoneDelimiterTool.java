package model.tools;

import model.CityResources;
import model.tiles.GrassTile;
import model.tiles.Tile;
import model.tiles.WaterTile;

public class WaterZoneDelimiterTool extends Tool{
	// Constant
	private final static int CURRENCY_COST = 100;

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
		return this == o || o instanceof WaterZoneDelimiterTool;
	}

	/**
     * isAfordable returns true if the user can apply the Airport Tool, false
     * otherwise.
     */
	@Override
	public boolean isAfordable(Tile aTarget, CityResources r) {
		return WaterZoneDelimiterTool.CURRENCY_COST <= r.getCurrency();
	}

	// Access
	@Override
	public int getCost (Tile aTarget) {
		return WaterZoneDelimiterTool.CURRENCY_COST;
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

		r.spend(WaterZoneDelimiterTool.CURRENCY_COST);

		return WaterTile.getDefault();
	}

	// Debugging
	@Override
	public String toString () {
		return getClass().getSimpleName();
	}
}
