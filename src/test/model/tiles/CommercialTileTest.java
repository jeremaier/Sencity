package test.model.tiles;
import org.junit.Assert;
import org.junit.Test;

import model.CityResources;
import model.tiles.CommercialTile;
import model.tiles.PowerPlantTile;
import model.*;
import model.tiles.*;

public class CommercialTileTest {
	
	@Test
    public void testInit() {
        CommercialTile ct = new CommercialTile();
        Assert.assertEquals(CommercialTile.DEFAULT_MAX_NEEDED_ENERGY, ct.getMaxNeededEnergy());
        Assert.assertEquals(CommercialTile.DEFAULT_MAX_NEEDED_PRODUCTS, ct.getMaxNeededProducts());
        Assert.assertEquals(CommercialTile.DEFAULT_MAX_NEEDED_INHABITANTS, ct.getMaxNeededInhabitants());
	}
	
	@Test
	public void testUpdate() {
		CommercialTile ct = new CommercialTile();
		CityResources resources = new CityResources(100,100,100,10);
		int InitialConsomation = resources.getProductsConsomation();
		int initialRevenues = resources.getRevenues();
		int initialUnworkingPopulation= resources.getUnworkingPopulation();
		int initialWorkingPopulation = resources.getWorkingPopulation();
		int initialEnergy = resources.getUnconsumedEnergy();
		ct.update(resources);
		Assert.assertEquals(InitialConsomation + ct.getNeededProducts(), resources.getProductsConsomation());
		Assert.assertEquals(initialRevenues + ct.getRevenues(), resources.getRevenues());
		Assert.assertEquals(initialUnworkingPopulation - ct.getWorkers(), resources.getUnworkingPopulation());
		Assert.assertEquals(initialWorkingPopulation + ct.getWorkers(), resources.getWorkingPopulation());
		Assert.assertEquals(initialEnergy - ct.getNeededEnergy(), resources.getUnconsumedEnergy());
	}
}
