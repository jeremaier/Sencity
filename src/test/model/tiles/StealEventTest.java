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
		event.applyEffects(resources);
		Assert.assertEquals(initialCurrency - StealEvent.MONEY_LOOSE, resources.getCurrency());
	}
}
