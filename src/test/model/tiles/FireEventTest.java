package test.model.tiles;

import org.junit.Assert;
import org.junit.Test;

import model.CityResources;
import model.event.FireEvent;

public class FireEventTest {
	@Test
	public void testUpdate() {
        CityResources resources = new CityResources(100, 100);
		FireEvent event = new FireEvent();
		resources.increaseProductsCapacity(50);
		resources.storeProducts(50);
        final int initialProducts = resources.getProductsCount();
        final int initialBadEventOccurrence = resources.getBadEventOccurrence();
		event.applyEffects(resources);
		Assert.assertEquals(initialProducts - FireEvent.PRODUCTS_LOOSE, resources.getProductsCount());
		Assert.assertEquals(initialBadEventOccurrence + 1, resources.getBadEventOccurrence());
	}
}
