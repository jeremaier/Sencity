package test.model.tiles;

import org.junit.Assert;
import org.junit.Test;

import localization.UKTexts;
import model.CityResources;
import model.GameBoard;
import model.event.InvestmentEvent;
import model.tiles.IndustrialTile;

public class InvestmentEventTest {
	@Test
	public void testUpdate() {
		GameBoard gb = new GameBoard(1, new UKTexts());
        CityResources resources = new CityResources(100, 100);
		InvestmentEvent event = new InvestmentEvent(gb);
        final int initialProduction = IndustrialTile.getMaxProduction();
		event.applyEffects(resources);
		Assert.assertEquals(initialProduction + InvestmentEvent.PRODUCTION_INCREASMENT, IndustrialTile.getMaxProduction());
	}
}
