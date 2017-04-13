package test.model.tiles;
import org.junit.Assert;
import org.junit.Test;
import model.*;
import model.tiles.*;


public class IndustrialTileTest {
	
	@Test
	public void testInit() {
		IndustrialTile it = new IndustrialTile();
		Assert.assertEquals(IndustrialTile.DEFAULT_MAX_NEEDED_ENERGY, it.getMaxNeededEnergy());
		Assert.assertEquals(IndustrialTile.DEFAULT_MAX_NEEDED_INHABITANTS, it.getMaxWorkingInhabitants());
		Assert.assertEquals(IndustrialTile.DEFAULT_MAX_PRODUCTION, it.getMaxProduction());
		Assert.assertEquals(IndustrialTile.DEFAULT_PRODUCTS_CAPACITY, it.getProductionCapacity());
		it = new IndustrialTile(10);
		Assert.assertEquals(10, it.getProductionCapacity());
	}
	
	@Test
	public void testUpdate() {
		IndustrialTile it = new IndustrialTile();
		CityResources res = new CityResources(100,100);
		int product = res.getProductsCount()*it.getProductionCapacity() / res.getProductsCapacity();
		int busyPercentage = product * 100 / it.getProductionCapacity();
		int neededEnergy = Math.max(1,  busyPercentage * it.getMaxNeededEnergy() / 100);
		int neededUnworkingPopulation = busyPercentage * it.getMaxWorkingInhabitants()/100;
		int initialEnergy=res.getUnconsumedEnergy();
		int initialUnworkingPopulation = res.getUnworkingPopulation();
		int vacantPercentage = 100 - busyPercentage;
		int initialProducts= res.getProductsCount();
		it.update(res);
		Assert.assertEquals(initialProducts + (vacantPercentage*it.getMaxProduction()/100), res.getProductsCount());		
		Assert.assertEquals(initialEnergy - neededEnergy, res.getUnconsumedEnergy());
		Assert.assertEquals(initialUnworkingPopulation - neededUnworkingPopulation, res.getUnworkingPopulation());
	}
	
	@Test
	public void testDisassemble() {
		IndustrialTile it= new IndustrialTile();
		CityResources resources = new CityResources(100,100);
		int product = resources.getProductsCount()*it.getProductionCapacity() / resources.getProductsCapacity();
		int busyPercentage = product * 100 / it.getProductionCapacity();
		int vacantPercentage = 100 - busyPercentage;
		it.update(resources);
		int initialProducts = resources.getProductsCount();
		it.disassemble(resources);
		Assert.assertEquals(Math.max(0, initialProducts - (vacantPercentage*it.getMaxProduction()/100)), resources.getProductsCount());
	}
	
	@Test
	public void testIsDestroyed() {
		IndustrialTile it = new IndustrialTile();
		CityResources resources = new CityResources(100,100);
		it.disassemble(resources);
		Assert.assertEquals(true, it.isDestroyed());		
	}
	
	@Test
	public void testEqual() {
		IndustrialTile it1 = new IndustrialTile();
		CityResources resources = new CityResources(100,100);
		it1.update(resources);
		it1.disassemble(resources);
		IndustrialTile it2 = new IndustrialTile();
		it2.update(resources);
		it2.disassemble(resources);
		Assert.assertEquals(true, it1.equals(it2));
	}
	
}
