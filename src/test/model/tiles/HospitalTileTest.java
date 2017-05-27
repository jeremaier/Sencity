package test.model.tiles;

import org.junit.Assert;
import org.junit.Test;

import localization.UKTexts;
import model.*;
import model.tiles.*;

public class HospitalTileTest {
	@Test
	public void testInit() {
		final int initialHospitalNumber = HospitalTile.getHospitalNumber();
		HospitalTile ht = new HospitalTile();
		Assert.assertEquals(HospitalTile.DEFAULT_MAINTENANCE_COST, ht.getMaintenanceCost());
		Assert.assertEquals(PoliceStationTile.DEFAULT_MAX_NEEDED_INHABITANTS, ht.getMaxNeededInhabitants());
		Assert.assertEquals(HospitalTile.DEFAULT_MAX_NEEDED_ENERGY, ht.getMaxNeededEnergy());
		Assert.assertEquals(HospitalTile.DEFAULT_SATISFACTION_VALUE, ht.getSatisfactionValue());
		Assert.assertEquals(initialHospitalNumber + 1, HospitalTile.getHospitalNumber());
	}
	
	@Test
	public void testUpdate() {
		HospitalTile ht = new HospitalTile();
        CityResources resources = new CityResources(100, 100);
        resources.increaseEnergyProduction(40);
        final int initialSatisfaction = resources.getSatisfaction();
        final int initialCurrency = resources.getCurrency();
        final int initialPopulation = resources.getUnworkingPopulation();
        final int initialEnergy = resources.getUnconsumedEnergy();
        ht.update(resources);
        Assert.assertEquals(initialPopulation - ht.getMaxNeededInhabitants(), resources.getUnworkingPopulation());
        Assert.assertEquals(initialEnergy - ht.getMaxNeededEnergy(), resources.getUnconsumedEnergy());
        Assert.assertEquals(initialCurrency - (int)(Math.round(ht.getMaintenanceCost() * GameBoard.getDifficulty().getCoeff())), resources.getCurrency()); 
        Assert.assertEquals(Math.max(initialSatisfaction, ht.getSatisfactionValue()), resources.getSatisfaction());
	}
	
	@Test
	public void testDisassemble() {
		HospitalTile ht = new HospitalTile();
		CityResources resources = new CityResources(100, 100);
        resources.increaseEnergyProduction(100);
		ht.update(resources);
		final int initialSatisfaction = resources.getSatisfaction();
        final int initialPopulation = resources.getUnworkingPopulation();
        final int initialEnergy = resources.getUnconsumedEnergy();
		ht.disassemble(resources);
		Assert.assertEquals(Math.max(0, initialSatisfaction), resources.getSatisfaction());
        Assert.assertEquals(Math.max(0, initialPopulation), resources.getUnworkingPopulation());
        Assert.assertEquals(Math.max(0, initialEnergy), resources.getUnconsumedEnergy());
	}
	
	@Test
	public void testIsDestroyed() {
		@SuppressWarnings("unused")
		GameBoard gb = new GameBoard(10, new UKTexts());
		HospitalTile ht = new HospitalTile();
		CityResources resources = new CityResources(100);
        resources.increaseEnergyProduction(100);
        ht.disassemble(resources);
		Assert.assertEquals(true, ht.isDestroyed());
	}
	
	@Test
	public void testEqual() {
		HospitalTile ht1 = new HospitalTile();
		CityResources resources = new CityResources(100);
        resources.increaseEnergyProduction(100);
		ht1.update(resources);
		ht1.disassemble(resources);
		HospitalTile ht2 = new HospitalTile();
		ht2.update(resources);
		ht2.disassemble(resources);
		Assert.assertEquals(true, ht1.equals(ht2));
	}
}
