package test.model.tiles;

import org.junit.Assert;
import org.junit.Test;

import localization.UKTexts;
import model.*;
import model.tiles.*;

public class FireStationTileTest {
	@Test
	public void testInit() {
		FireStationTile fst = new FireStationTile();
		Assert.assertEquals(FireStationTile.DEFAULT_MAINTENANCE_COST, fst.getMaintenanceCost());
		Assert.assertEquals(FireStationTile.DEFAULT_EVOLUTION_ENERGY_CONSUMPTION, fst.getEvolutionEnergyConsumption());
		Assert.assertEquals(FireStationTile.DEFAULT_MAX_NEEDED_ENERGY, fst.getMaxNeededEnergy());
		Assert.assertEquals(FireStationTile.DEFAULT_SATISFACTION_VALUE, fst.getSatisfactionValue());
	}
	
	@Test
	public void testUpdate() {
		FireStationTile fst = new FireStationTile();
        CityResources resources = new CityResources(100, 100);
        resources.increaseEnergyProduction(40);
        final int initialSatisfaction = resources.getSatisfaction();
        final int initialCurrency = resources.getCurrency();
        final int initialPopulation = resources.getUnworkingPopulation();
        final int initialEnergy = resources.getUnconsumedEnergy();
        fst.evolve(resources);
        Assert.assertEquals(initialPopulation - fst.getMaxNeededInhabitants(), resources.getUnworkingPopulation());
        Assert.assertEquals(initialEnergy - fst.getMaxNeededEnergy() - fst.getEvolutionEnergyConsumption(), resources.getUnconsumedEnergy());
        Assert.assertEquals(initialCurrency - (int)(Math.round(fst.getMaintenanceCost() * GameBoard.getDifficulty().getCoeff())), resources.getCurrency()); 
        Assert.assertEquals(Math.max(initialSatisfaction, fst.getSatisfactionValue()), resources.getSatisfaction());
	}
	
	@Test
	public void testDisassemble() {
		FireStationTile fst = new FireStationTile();
		CityResources resources = new CityResources(100, 100);
		fst.evolve(resources);
		final int initialSatisfaction = resources.getSatisfaction();
        final int initialPopulation = resources.getUnworkingPopulation();
        final int initialEnergy = resources.getUnconsumedEnergy();
		fst.disassemble(resources);
		Assert.assertEquals(Math.max(0, initialSatisfaction), resources.getSatisfaction());
        Assert.assertEquals(Math.max(0, initialPopulation), resources.getUnworkingPopulation());
        Assert.assertEquals(Math.max(0, initialEnergy), resources.getUnconsumedEnergy());
	}
	
	@Test
	public void testIsDestroyed() {
		@SuppressWarnings("unused")
		GameBoard gb = new GameBoard(10, new UKTexts());
		FireStationTile fst = new FireStationTile();
		CityResources resources = new CityResources(100);
        resources.increaseEnergyProduction(100);
		fst.disassemble(resources);
		Assert.assertEquals(false, fst.isDestroyed());
		fst.evolve(resources);
		fst.disassemble(resources);
		Assert.assertEquals(true, fst.isDestroyed());
	}
	
	@Test
	public void testEqual() {
		FireStationTile fst1 = new FireStationTile();
		CityResources resources = new CityResources(100);
		fst1.update(resources);
		fst1.disassemble(resources);
		FireStationTile fst2 = new FireStationTile();
		fst2.update(resources);
		fst2.disassemble(resources);
		Assert.assertEquals(true, fst1.equals(fst2));
	}
}
