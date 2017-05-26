package test.model.tiles;

import org.junit.Assert;
import org.junit.Test;

import model.CityResources;
import model.event.MatchEvent;

public class MatchEventTest {
	@Test
	public void testUpdate() {
        CityResources resources = new CityResources(100, 100);
		MatchEvent event = new MatchEvent();
        final int initialCurrency = resources.getCurrency();
		event.applyEffects(resources);
		Assert.assertEquals(initialCurrency + MatchEvent.MONEY, resources.getCurrency());
	}
}