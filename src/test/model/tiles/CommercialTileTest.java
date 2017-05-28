package test.model.tiles;

import org.junit.Assert;
import org.junit.Test;

import localization.UKTexts;
import model.CityResources;
import model.GameBoard;
import model.tiles.AirportTile;
import model.tiles.CommercialTile;

public class CommercialTileTest {
	@Test
    public void testInit() {
        CommercialTile ct = new CommercialTile();
		Assert.assertEquals(CommercialTile.DEFAULT_MAINTENANCE_COST, ct.getMaintenanceCost());
        Assert.assertEquals(CommercialTile.DEFAULT_EVOLUTION_ENERGY_CONSUMPTION, ct.getEvolutionEnergyConsumption());
        Assert.assertEquals(CommercialTile.DEFAULT_MAX_NEEDED_ENERGY, ct.getMaxNeededEnergy());
        Assert.assertEquals(CommercialTile.DEFAULT_MAX_SOLD_PRODUCTS, ct.getMaxSoldProducts());
        Assert.assertEquals(CommercialTile.DEFAULT_MAX_NEEDED_INHABITANTS, ct.getMaxNeededInhabitants());
        Assert.assertEquals(CommercialTile.DEFAULT_PRODUCTS_PRICE, ct.getProductsPrice());
	}
	
	@Test
	public void testUpdate() {
		CommercialTile ct = new CommercialTile();
        CityResources resources = new CityResources(100, 40);
        resources.increaseEnergyProduction(40);
        resources.increaseProductsCapacity(40);
        resources.storeProducts(40);
        final int initialEnergy = resources.getUnconsumedEnergy();
		final int initialCurrency = resources.getCurrency();
		final int initialProducts = resources.getProductsCount();
		ct.evolve(resources);
		final int neededEnergy = Math.max(2, ct.getMaxNeededEnergy());
		final int meanPrice = ct.getMaxSoldProducts() * ct.getProductsPrice();
		final long meanEarn = initialCurrency + meanPrice * resources.getVat() / 100 * resources.getSatisfaction() / 50  - Math.round(ct.getMaintenanceCost() * GameBoard.getDifficulty().getCoeff());
		Assert.assertEquals(ct.isEnergyMissing(), false);
		Assert.assertEquals(ct.isPopulationMissing(), false);
		Assert.assertEquals(initialEnergy - neededEnergy, resources.getUnconsumedEnergy());
		Assert.assertEquals(resources.getCurrency(), meanEarn, meanEarn * 0.3);
		Assert.assertEquals(initialProducts - ct.getMaxSoldProducts(), resources.getProductsCount());		
	}
	
    @Test
    public void testDisassemble() {
		@SuppressWarnings("unused")
		GameBoard gb = new GameBoard(10, new UKTexts());
		CommercialTile at = new CommercialTile();
        CityResources resources = new CityResources(100, 40);
        resources.increaseEnergyProduction(100);
        resources.increaseProductsCapacity(40);
        resources.storeProducts(40);
        at.update(resources);
        final int initialEnergy = resources.getUnconsumedEnergy();
		final int initialProducts = resources.getProductsCount();
        final int initialPopulation = resources.getUnworkingPopulation();
        at.disassemble(resources);
        Assert.assertEquals(Math.max(0, initialPopulation), resources.getUnworkingPopulation());
        Assert.assertEquals(Math.max(0, initialEnergy), resources.getUnconsumedEnergy());
        Assert.assertEquals(Math.max(0, initialProducts), resources.getProductsCount());
        Assert.assertEquals(AirportTile.alreadyBuild, false);
    }
	
	@Test
	public void testIsDestroyed() {
		@SuppressWarnings("unused")
		GameBoard gb = new GameBoard(10, new UKTexts());
		CommercialTile ct = new CommercialTile();
		CityResources resources = new CityResources(100);
        resources.increaseEnergyProduction(40);
		ct.disassemble(resources);
		Assert.assertEquals(false, ct.isDestroyed());
		ct.evolve(resources);
		ct.disassemble(resources);
		Assert.assertEquals(true, ct.isDestroyed());
	}
	
	@Test
	public void testEqual() {
		CommercialTile ct1 = new CommercialTile();
		CityResources res = new CityResources(100);
		ct1.update(res);
		ct1.disassemble(res);
		CommercialTile ct2 = new CommercialTile();
		ct2.update(res);
		ct2.disassemble(res);
		Assert.assertEquals(true, ct1.equals(ct2));
	}
}
