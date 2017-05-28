package test.model.tiles;

import org.junit.Assert;
import org.junit.Test;

import model.CityResources;
import model.event.MatchEvent;
import model.tiles.StadiumTile;

public class MatchEventTest {
	@Test
	public void testUpdate() {
        CityResources resources = new CityResources(100, 100);
		MatchEvent event = new MatchEvent();
		@SuppressWarnings("unused")
		StadiumTile stadium = new StadiumTile();
        final int initialCurrency = resources.getCurrency();
        final int initialGoodEventOccurrence = resources.getGoodEventOccurrence();
		event.applyEffects(resources);
		Assert.assertEquals(initialCurrency + MatchEvent.MONEY, resources.getCurrency());
		Assert.assertEquals(initialGoodEventOccurrence + 1, resources.getGoodEventOccurrence());
	}
}