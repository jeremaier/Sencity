package test.model.tiles;

import org.junit.Assert;
import org.junit.Test;

import model.CityResources;
import model.event.StealEvent;

public class StealEventTest {
	@Test
	public void testUpdate() {
        CityResources resources = new CityResources(100, 100);
		StealEvent event = new StealEvent();
        final int initialCurrency = resources.getCurrency();
        final int initialBadEventOccurrence = resources.getBadEventOccurrence();
		event.applyEffects(resources);
		Assert.assertEquals(initialCurrency - StealEvent.MONEY_LOOSE, resources.getCurrency());
		Assert.assertEquals(initialBadEventOccurrence + 1, resources.getBadEventOccurrence());
	}
}
