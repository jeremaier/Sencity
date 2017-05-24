package test.model.tiles;

import org.junit.Assert;
import org.junit.Test;

import localization.UKTexts;
import model.CityResources;
import model.GameBoard;
import model.tiles.ResidentialTile;

public class ResidentialTileTest {
	@Test
    public void testInit() {
        ResidentialTile ct = new ResidentialTile();
        Assert.assertEquals(ResidentialTile.DEFAULT_EVOLUTION_ENERGY_CONSUMPTION, ct.getEvolutionEnergyConsumption());
        Assert.assertEquals(ResidentialTile.DEFAULT_MAX_JOINING_INHABITANTS, ct.getMaxJoiningInhabitants());
        Assert.assertEquals(ResidentialTile.DEFAULT_MAX_LEAVING_INHABITANTS, ct.getMaxLeavingInhabitants());
        Assert.assertEquals(ResidentialTile.DEFAULT_MAX_NEEDED_ENERGY, ct.getMaxNeededEnergy());
        Assert.assertEquals(ResidentialTile.DEFAULT_INHABITANTS_CAPACITY, ct.getInhabitantsCapacity());
	}
	
	@Test
	public void testUpdate() {
        CityResources resources = new CityResources(100, 40);
        resources.increaseEnergyProduction(100);
		ResidentialTile rt = new ResidentialTile();

        final int initialEnergy = resources.getUnconsumedEnergy();
        final int initialPopulationCapacity = resources.getPopulationCapacity();
        final int initialPopulation = resources.getPopulation();
        
		rt.evolve(resources);
		
        final int busyPercentage = resources.getPopulation() * 100 / rt.getInhabitantsCapacity();
        final int neededEnergy = (int)Math.max(2, Math.ceil(busyPercentage * rt.getMaxNeededEnergy() / 100.0));

		Assert.assertEquals(rt.isEnergyMissing(), false);
		Assert.assertEquals(initialEnergy - neededEnergy, resources.getUnconsumedEnergy());
		Assert.assertEquals(initialPopulation + (100 - busyPercentage) * rt.getMaxJoiningInhabitants() / 100, resources.getPopulation());		
		Assert.assertEquals(initialPopulationCapacity + rt.getInhabitantsCapacity(), resources.getPopulationCapacity());		
	}
	
	@Test
	public void testDisassemble() {
		@SuppressWarnings("unused")
		GameBoard gb = new GameBoard(10, new UKTexts());
        CityResources resources = new CityResources(100);
        resources.increasePopulationCapacity(100);
        resources.increaseEnergyProduction(100);
		ResidentialTile rt = new ResidentialTile();
        rt.evolve(resources);
        final int initialPopulationCapacity = resources.getPopulationCapacity();
        final int initialPopulation = resources.getPopulation();
        rt.disassemble(resources);
        System.out.println(rt.getInhabitants(resources));
        Assert.assertEquals(Math.max(0, initialPopulationCapacity - rt.getInhabitantsCapacity()), resources.getPopulationCapacity());
        Assert.assertEquals(Math.max(0, initialPopulation - rt.getInhabitants(resources)), resources.getPopulation());
	}
	
	@Test
	public void testIsDestroyed() {
		ResidentialTile rt = new ResidentialTile();
		CityResources resources = new CityResources(100);
		rt.disassemble(resources);
		Assert.assertEquals(false, rt.isDestroyed());
		rt.evolve(resources);
		rt.disassemble(resources);
		Assert.assertEquals(true, rt.isDestroyed());
	}
	
	@Test
	public void testEqual() {
		ResidentialTile rt1 = new ResidentialTile();
		CityResources res = new CityResources(100);
		rt1.update(res);
		rt1.disassemble(res);
		ResidentialTile rt2 = new ResidentialTile();
		rt2.update(res);
		rt2.disassemble(res);
		Assert.assertEquals(true, rt1.equals(rt2));
	}
}