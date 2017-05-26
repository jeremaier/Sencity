package test.model.tiles;

import org.junit.Assert;
import org.junit.Test;

import model.CityResources;
import model.event.InvestmentEvent;
import model.tiles.IndustrialTile;

public class InvestmentEventTest {
	@Test
	public void testUpdate() {
        CityResources resources = new CityResources(100, 100);
		InvestmentEvent event = new InvestmentEvent();
        final int initialProduction = IndustrialTile.getMaxProduction();
		event.applyEffects(resources);
		Assert.assertEquals(initialProduction + InvestmentEvent.PRODUCTION_INCREASMENT, IndustrialTile.getMaxProduction());
	}
}
