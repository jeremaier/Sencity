package test.model.tiles;
import org.junit.Test;
import model.*;
import model.tiles.*;


public class IndustrialTileTest {
	
	@Test
	public void testInit() {
		IndustrialTile it = new IndustrialTile();
		Assert.assertEquals(IndustrialTile.DEFAULT_MAX_NEEDED_ENERGY, it.getMaxNeededEnergy());
		Assert.assertEquals(IndustrialTile.DEFAULT_MAX_NEEDED_INHABITANTS, it.getMaxNeededInhabitants());
		Assert.assertEquals(IndustrialTile.DEFAULT_MAX_PRODUCTION, it.getMaxProduction());
		Assert.assertEquals(IndustrialTile.DEFAULT_MIN_PRODUCTION, it.getMinProduction());
		Assert.assertEquals(IndustrialTile.DEFAULT_PRODUCTS_CAPACITY, it.getProductionCapacity());
		it = new IndustrialTile(10);
		Assert.assertEquals(10, it.getProductionCapacity());
	}
	
	@Test
	public void testUpdate() {
		IndustrialTile it = new IndustrialTile();
		CityResources resources = new CityResources(100,100,100);
		int initialProduction = resources.getProductsProduction();
		it.update(resources);
		Assert.assertEquals(InitialProduction + it.getProductionCapacity(), resources.getProductsProduction());		
	}
	
	@Test
	public void testDisassemble() {
		IndustrialTile it= new IndustrialTile();
		CityResources resources = new CityResources(100,100,100);
		it.update(resources);
		int initialProduction = resources.getProductsProduction();
		it.disassemble(resources);
		Assert.assertEquals(Math.max(0, initialProduction - it.getProductionCapacity()), resources.getProductcProduction());
	}
	
	@Test
	public void testIsDestroyed() {
		IndustrialTile it = new IndustrialTile();
		CityResources resources = new CityResourcse(100,100,100);
		it.disassemble(resources);
		Assert.assertEquals(true, it.isDestroyed());		
	}
	
	@Test
	public void testIsEqual() {
		IndustrialTile it1 = new IndustrialTile();
		CityResources resources = new CityResources(100,100,100);
		it1.update(resources);
		it1.disassemble(resources);
		IndustrialTile it2 = new IndustrialTile();
		it2.update(resources);
		it2.disassemble(resources);
		Assert.assertEquals(true, it1.equals(it2));
	}
	
}
