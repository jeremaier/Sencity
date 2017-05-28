package test.model.tiles;

import org.junit.Assert;
import org.junit.Test;

import localization.UKTexts;
import model.CityResources;
import model.GameBoard;
import model.tiles.HarborTile;

public class HarborTileTest {
	@Test
    public void testInit() {
        Assert.assertEquals(HarborTile.alreadyBuild, false);
        HarborTile ht = new HarborTile();
		Assert.assertEquals(HarborTile.DEFAULT_MAINTENANCE_COST, ht.getMaintenanceCost());
        Assert.assertEquals(HarborTile.DEFAULT_MAX_NEEDED_ENERGY, ht.getMaxNeededEnergy());
        Assert.assertEquals(HarborTile.DEFAULT_MAX_SOLD_PRODUCTS, ht.getMaxSoldProducts());
        Assert.assertEquals(HarborTile.DEFAULT_MAX_NEEDED_INHABITANTS, ht.getMaxNeededInhabitants());
        Assert.assertEquals(HarborTile.DEFAULT_PRODUCTS_PRICE, ht.getProductsPrice());
        Assert.assertEquals(HarborTile.DEFAULT_SATISFACTION_VALUE, ht.getSatisfactionValue());
        Assert.assertEquals(HarborTile.alreadyBuild, true);
	}
	
	@Test
	public void testUpdate() {
		@SuppressWarnings("unused")
		GameBoard gb = new GameBoard(10, new UKTexts());
		HarborTile ht = new HarborTile();
        CityResources resources = new CityResources(100, 100);
        resources.increaseEnergyProduction(100);
        resources.increaseProductsCapacity(100);
        resources.storeProducts(100);
        final int initialEnergy = resources.getUnconsumedEnergy();
		final int initialCurrency = resources.getCurrency();
		final int initialProducts = resources.getProductsCount();
		final int meanEarn = (int)(ht.getMaxSoldProducts() * ht.getProductsPrice() * resources.getVat() / 100.0 * resources.getSatisfaction() / 50 - (int)(Math.round(ht.getMaintenanceCost() * GameBoard.getDifficulty().getCoeff())));
		ht.update(resources);
		Assert.assertEquals(ht.isEnergyMissing(), false);
		Assert.assertEquals(ht.isPopulationMissing(), false);
		Assert.assertEquals(initialEnergy - ht.getMaxNeededEnergy(), resources.getUnconsumedEnergy());
		Assert.assertEquals(initialCurrency + meanEarn, resources.getCurrency(), meanEarn * 0.4);
		Assert.assertEquals(initialProducts - ht.getMaxSoldProducts(), resources.getProductsCount());
	}
	
    @Test
    public void testDisassemble() {
		@SuppressWarnings("unused")
		GameBoard gb = new GameBoard(10, new UKTexts());
		HarborTile ht = new HarborTile();
        CityResources resources = new CityResources(100, 40);
        resources.increaseEnergyProduction(100);
        resources.increaseProductsCapacity(40);
        resources.storeProducts(40);
        ht.update(resources);
        final int initialEnergy = resources.getUnconsumedEnergy();
		final int initialProducts = resources.getProductsCount();
        ht.disassemble(resources);
        Assert.assertEquals(Math.max(0, initialEnergy), resources.getUnconsumedEnergy());
        Assert.assertEquals(Math.max(0, initialProducts), resources.getProductsCount());
        Assert.assertEquals(HarborTile.alreadyBuild, false);
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
        Assert.assertEquals(HarborTile.alreadyBuild, false);
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