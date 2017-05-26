package test.model.tiles;

import org.junit.Assert;
import org.junit.Test;

import localization.UKTexts;
import model.CityResources;
import model.GameBoard;
import model.event.EarthquakeEvent;
import model.tiles.AirportTile;
import model.tiles.GrassTile;
import model.tiles.Tile;

public class EarthquakeEventTest {
	@Test
	public void testUpdate() {
		GameBoard gb = new GameBoard(1, new UKTexts());
        CityResources resources = new CityResources(100, 100);
        EarthquakeEvent event = new EarthquakeEvent(gb);
		gb.tiles[0][0] = new AirportTile();
		final Tile tile = gb.getTile(0, 0);
        final int initialBadEventOccurrence = resources.getBadEventOccurrence();
		event.applyEffects(resources);
		Assert.assertEquals(tile.equals(gb.getTile(0, 0)), false);
		Assert.assertEquals(gb.getTile(0, 0) instanceof GrassTile, true);
		Assert.assertEquals(initialBadEventOccurrence + 1, resources.getBadEventOccurrence());
	}
}
