package test.model.tiles;

import org.junit.Assert;
import org.junit.Test;

import model.CityResources;
import model.event.FestivalEvent;

public class FestivalEventTest {
	@Test
	public void testUpdate() {
        CityResources resources = new CityResources(100, 100);
		FestivalEvent event = new FestivalEvent();
		resources.decreaseSatisfation(50);
        final int initialCurrency = resources.getCurrency();
        final int initialSatisfaction = resources.getSatisfaction();
		event.applyEffects(resources);
		Assert.assertEquals(initialCurrency + FestivalEvent.MONEY, resources.getCurrency());
		Assert.assertEquals(Math.min(100, initialSatisfaction + FestivalEvent.EXTRA_SATISFACTION), resources.getSatisfaction());
	}
}
