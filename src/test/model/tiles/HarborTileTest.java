package test.model.tiles;

import org.junit.Assert;
import org.junit.Test;

import localization.UKTexts;
import model.CityResources;
import model.GameBoard;
import model.tiles.AirportTile;
import model.tiles.HarborTile;

public class HarborTileTest {
	@Test
    public void testInit() {
        HarborTile ht = new HarborTile();
		Assert.assertEquals(HarborTile.DEFAULT_MAINTENANCE_COST, ht.getMaintenanceCost());
        Assert.assertEquals(HarborTile.DEFAULT_MAX_NEEDED_ENERGY, ht.getMaxNeededEnergy());
        Assert.assertEquals(HarborTile.DEFAULT_MAX_SOLD_PRODUCTS, ht.getMaxSoldProducts());
        Assert.assertEquals(HarborTile.DEFAULT_MAX_NEEDED_INHABITANTS, ht.getMaxNeededInhabitants());
        Assert.assertEquals(HarborTile.DEFAULT_PRODUCTS_PRICE, ht.getProductsPrice());
        Assert.assertEquals(HarborTile.DEFAULT_SATISFACTION_VALUE, ht.getSatisfactionValue());
	}
	
	@Test
	public void testUpdate() {
		HarborTile ht = new HarborTile();
        CityResources resources = new CityResources(100, 40);
        resources.increaseEnergyProduction(100);
        resources.increaseProductsCapacity(40);
        resources.storeProducts(40);
        final int initialEnergy = resources.getUnconsumedEnergy();
		final int initialCurrency = resources.getCurrency();
		final int initialProduhts = resources.getProductsCount();
		ht.update(resources);
		final int neededEnergy = Math.max(10, ht.getMaxNeededEnergy());
		final int meanPrice = ht.getMaxSoldProducts() * ht.getProductsPrice();
		final long meanEarn = initialCurrency + meanPrice * resources.getVat() / 100 - Math.round(ht.getMaintenanceCost() * GameBoard.getDifficulty().getCoeff());
		final int minPrice = (int)(meanEarn * 0.6);
		final int maxPrice = (int)(meanEarn * 1.4);
		Assert.assertEquals(ht.isEnergyMissing(), false);
		Assert.assertEquals(ht.isPopulationMissing(), false);
		Assert.assertEquals(initialEnergy - neededEnergy, resources.getUnconsumedEnergy());
		Assert.assertEquals(true, minPrice <= resources.getCurrency());
		Assert.assertEquals(true, maxPrice >= resources.getCurrency());
		Assert.assertEquals(initialProduhts - ht.getMaxSoldProducts(), resources.getProductsCount());		
	}
	
    @Test
    public void testDisassemble() {
		@SuppressWarnings("unused")
		GameBoard gb = new GameBoard(10, new UKTexts());
		HarborTile at = new HarborTile();
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
		HarborTile ht = new HarborTile();
		CityResources resources = new CityResources(100);
        resources.increaseEnergyProduction(100);
		ht.disassemble(resources);
		Assert.assertEquals(true, ht.isDestroyed());
	}
	
	@Test
	public void testEqual() {
		HarborTile ht1 = new HarborTile();
		CityResources resources = new CityResources(100);
        resources.increaseEnergyProduction(200);
		ht1.update(resources);
		ht1.disassemble(resources);
		HarborTile ht2 = new HarborTile();
		ht2.update(resources);
		ht2.disassemble(resources);
		Assert.assertEquals(true, ht1.equals(ht2));
	}
}