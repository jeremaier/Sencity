package model.event;

import java.util.ArrayList;
import java.util.List;

import localization.LocalizedTexts;
import model.CityResources;
import model.GameBoard;
import model.tiles.GrassTile;
import model.tiles.WaterTile;

/**
 * The EarthquakeEvent make you loose a building.
 */
public class EarthquakeEvent extends Event {

	/**
	 * Constructor.
	 */
	public EarthquakeEvent(GameBoard world) {
		super(world);
	}

	/**
	 * Apply no effects.
	 */
	@Override
	public List<Event> applyEffects(CityResources resources) {
		if( !(world.tiles[startingTile.getRow()][startingTile.getColumn()] instanceof WaterTile) ){
			world.tiles[startingTile.getRow()][startingTile.getColumn()] = GrassTile.getDefault();
		}
		return new ArrayList<>(0);
	}

	/**
	 * Return an empty message.
	 */
	@Override
	public String getMessage(LocalizedTexts texts) {
		return texts.getEarthquakeEventMessage(startingTile.getRow(), startingTile.getColumn());
	}
}