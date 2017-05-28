package test.model.tiles;

import org.junit.Assert;
import org.junit.Test;

import localization.UKTexts;
import model.*;
import model.tiles.*;

public class IndustrialTileTest {
	@Test
	public void testInit() {
		IndustrialTile it = new IndustrialTile();
		Assert.assertEquals(IndustrialTile.DEFAULT_MAINTENANCE_COST, it.getMaintenanceCost());
		Assert.assertEquals(IndustrialTile.DEFAULT_EVOLUTION_ENERGY_CONSUMPTION, it.getEvolutionEnergyConsumption());
		Assert.assertEquals(IndustrialTile.DEFAULT_MAX_NEEDED_ENERGY, it.getMaxNeededEnergy());
		Assert.assertEquals(IndustrialTile.DEFAULT_MAX_NEEDED_INHABITANTS, it.getMaxWorkingInhabitants());
		Assert.assertEquals(IndustrialTile.DEFAULT_MAX_PRODUCTION, IndustrialTile.getMaxProduction());
		Assert.assertEquals(IndustrialTile.DEFAULT_PRODUCTS_CAPACITY, it.getProductionCapacity());
		it = new IndustrialTile(10);
		Assert.assertEquals(10, it.getProductionCapacity());
	}
	
	@Test
	public void testUpdate() {
		IndustrialTile it = new IndustrialTile();
        CityResources resources = new CityResources(100);
        final int initialValue = resources.getProductsCount();
		it.evolve(resources);
        resources.increaseEnergyProduction(40);
        resources.increasePopulationCapacity(40);
        resources.increasePopulation(40);
        it.update(resources);
        Assert.assertEquals(it.isEnergyMissing(), false);
        Assert.assertEquals(it.isPopulationMissing(), false);
        Assert.assertEquals(initialValue + IndustrialTile.getMaxProduction(), resources.getProductsCount(), 1.3 * IndustrialTile.getMaxProduction());
	}
	
	@Test
	public void testDisassemble() {
		IndustrialTile it = new IndustrialTile(10);
		CityResources resources = new CityResources(100);
		resources.increaseProductsCapacity(10);
		final int product = resources.getProductsCount() * it.getProductionCapacity() / resources.getProductsCapacity();
		final int busyPercentage = product * 100 / it.getProductionCapacity();
		final int vacantPercentage = 100 - busyPercentage;
		it.update(resources);
		final int initialProducts = resources.getProductsCount();
		it.disassemble(resources);
		Assert.assertEquals(Math.max(0, initialProducts - (vacantPercentage * IndustrialTile.getMaxProduction() / 100)), resources.getProductsCount());
	}
	
	@Test
	public void testIsDestroyed() {
		@SuppressWarnings("unused")
		GameBoard gb = new GameBoard(10, new UKTexts());
		IndustrialTile it = new IndustrialTile();
		CityResources resources = new CityResources(100);
		it.disassemble(resources);
		Assert.assertEquals(false, it.isDestroyed());
		it.evolve(resources);
		it.disassemble(resources);
		Assert.assertEquals(true, it.isDestroyed());		
	}
	
	@Test
	public void testEqual() {
		IndustrialTile it1 = new IndustrialTile();
		CityResources resources = new CityResources(100);
		it1.update(resources);
		it1.disassemble(resources);
		IndustrialTile it2 = new IndustrialTile();
		it2.update(resources);
		it2.disassemble(resources);
		Assert.assertEquals(true, it1.equals(it2));
	}
}
