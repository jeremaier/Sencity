package test.model.tiles;

import org.junit.Assert;
import org.junit.Test;

import model.CityResources;
import model.tiles.CommercialTile;

public class CommercialTileTest {
	
	@Test
    public void testInit() {
        CommercialTile ct = new CommercialTile();
        Assert.assertEquals(CommercialTile.DEFAULT_MAX_NEEDED_ENERGY, ct.getMaxNeededEnergy());
        Assert.assertEquals(CommercialTile.DEFAULT_MAX_NEEDED_PRODUCTS, ct.getMaxNeededProducts());
        Assert.assertEquals(CommercialTile.DEFAULT_MAX_NEEDED_INHABITANTS, ct.getMaxNeededInhabitants());
        Assert.assertEquals(CommercialTile.DEFAULT_PRODUCTS_CAPACITY, ct.getProductionCapacity());
        ct = new CommercialTile(10);
        Assert.assertEquals(10,ct.getProductionCapacity());
	}
	
	@Test
	public void testUpdate() {
		CommercialTile ct = new CommercialTile();
		CityResources res = new CityResources(100,100);
		int initialEnergy = res.getUnconsumedEnergy();
		int initialCurrency = res.getCurrency();
		ct.update(res);
		int product = res.getProductsCount()*(ct.getProductionCapacity() * 100 / res.getProductsCapacity())/100;
		int busyPercentage = product * 100 / ct.getProductionCapacity();
		int neededEnergy = Math.max(1,  busyPercentage * ct.getMaxNeededEnergy() / 100);
		int TotalPrice = res.getProductsCount();
		Assert.assertEquals(initialEnergy - neededEnergy, res.getUnconsumedEnergy());
		Assert.assertEquals(initialCurrency + TotalPrice, res.getCurrency());		
	}
	
	@Test
	public void testDisassemble(){
		CommercialTile ct = new CommercialTile();
		CityResources resources = new CityResources(100,100);
		ct.update(resources);
		int product = resources.getProductsCount()*(ct.getProductionCapacity() * 100 / resources.getProductsCapacity())/100;
		int busyPercentage = product * 100 / ct.getProductionCapacity();
		int neededEnergy = Math.max(1,  busyPercentage * ct.getMaxNeededEnergy() / 100);
		int initialEnergy=resources.getUnconsumedEnergy();
		ct.disassemble(resources);
		Assert.assertEquals(Math.max(0,initialEnergy + neededEnergy), resources.getUnconsumedEnergy());
	}
	
	@Test
	public void testIsDestroyed(){
		CommercialTile ct = new CommercialTile();
		CityResources resources =  new CityResources(100,100);
		ct.disassemble(resources);
		Assert.assertEquals(true,ct.isDestroyed());
	}
	
	@Test
	public void testEqual() {
		CommercialTile ct1 = new CommercialTile();
		CityResources res = new CityResources(100,100);
		ct1.update(res);
		ct1.disassemble(res);
		CommercialTile ct2 = new CommercialTile();
		ct2.update(res);
		ct2.disassemble(res);
		Assert.assertEquals(true,ct1.equals(ct2));
	}
}
