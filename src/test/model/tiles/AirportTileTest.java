package test.model.tiles;

import org.junit.Assert;
import org.junit.Test;

import localization.UKTexts;
import model.CityResources;
import model.GameBoard;
import model.tiles.AirportTile;

public class AirportTileTest {
	@Test
    public void testInit() {
        Assert.assertEquals(AirportTile.alreadyBuild, false);
		AirportTile at = new AirportTile();
		Assert.assertEquals(AirportTile.DEFAULT_MAINTENANCE_COST, at.getMaintenanceCost());
        Assert.assertEquals(AirportTile.DEFAULT_MAX_NEEDED_ENERGY, at.getMaxNeededEnergy());
        Assert.assertEquals(AirportTile.DEFAULT_MAX_SOLD_PRODUCTS, at.getMaxSoldProducts());
        Assert.assertEquals(AirportTile.DEFAULT_MAX_NEEDED_INHABITANTS, at.getMaxNeededInhabitants());
        Assert.assertEquals(AirportTile.DEFAULT_PRODUCTS_PRICE, at.getProductsPrice());
        Assert.assertEquals(AirportTile.DEFAULT_SATISFACTION_VALUE, at.getSatisfactionValue());
        Assert.assertEquals(AirportTile.alreadyBuild, true);
	}
	
	@Test
	public void testUpdate() {
		@SuppressWarnings("unused")
		GameBoard gb = new GameBoard(10, new UKTexts());
		AirportTile at = new AirportTile();
        CityResources resources = new CityResources(100, 100);
        resources.increaseEnergyProduction(100);
        resources.increaseProductsCapacity(100);
        resources.storeProducts(100);
        final int initialEnergy = resources.getUnconsumedEnergy();
		final int initialCurrency = resources.getCurrency();
		final int initialProducts = resources.getProductsCount();
		final int meanEarn = (int)(at.getMaxSoldProducts() * at.getProductsPrice() * resources.getVat() / 100.0 * resources.getSatisfaction() / 50 - (int)(Math.round(at.getMaintenanceCost() * GameBoard.getDifficulty().getCoeff())));
		at.update(resources);
		Assert.assertEquals(at.isEnergyMissing(), false);
		Assert.assertEquals(at.isPopulationMissing(), false);
		Assert.assertEquals(initialEnergy - at.getMaxNeededEnergy(), resources.getUnconsumedEnergy());
		Assert.assertEquals(initialCurrency + meanEarn, resources.getCurrency(), meanEarn * 0.4);
		Assert.assertEquals(initialProducts - at.getMaxSoldProducts(), resources.getProductsCount());
	}
	
    @Test
    public void testDisassemble() {
		@SuppressWarnings("unused")
		GameBoard gb = new GameBoard(10, new UKTexts());
		AirportTile at = new AirportTile();
        CityResources resources = new CityResources(100, 40);
        resources.increaseEnergyProduction(100);
        resources.increaseProductsCapacity(40);
        resources.storeProducts(40);
        at.update(resources);
        final int initialEnergy = resources.getUnconsumedEnergy();
		final int initialProducts = resources.getProductsCount();
        at.disassemble(resources);
        Assert.assertEquals(Math.max(0, initialEnergy), resources.getUnconsumedEnergy());
        Assert.assertEquals(Math.max(0, initialProducts), resources.getProductsCount());
        Assert.assertEquals(AirportTile.alreadyBuild, false);
    }
	
	@Test
	public void testIsDestroyed() {
		@SuppressWarnings("unused")
		GameBoard gb = new GameBoard(10, new UKTexts());
		AirportTile at = new AirportTile();
		CityResources resources = new CityResources(100);
        resources.increaseEnergyProduction(100);
		at.disassemble(resources);
		Assert.assertEquals(true, at.isDestroyed());
	}
	
	@Test
	public void testEqual() {
		AirportTile at1 = new AirportTile();
		CityResources resources = new CityResources(100);
        resources.increaseEnergyProduction(200);
		at1.update(resources);
		at1.disassemble(resources);
		AirportTile at2 = new AirportTile();
		at2.update(resources);
		at2.disassemble(resources);
		Assert.assertEquals(true, at1.equals(at2));
	}
}