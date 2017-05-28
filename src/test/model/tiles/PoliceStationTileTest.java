package test.model.tiles;

import org.junit.Assert;
import org.junit.Test;

import localization.UKTexts;
import model.*;
import model.tiles.*;

public class PoliceStationTileTest {
	@Test
	public void testInit() {
		final int initialPoliceStationNumber = PoliceStationTile.getPoliceStationNumber();
		PoliceStationTile pst = new PoliceStationTile();
		Assert.assertEquals(PoliceStationTile.DEFAULT_MAINTENANCE_COST, pst.getMaintenanceCost());
		Assert.assertEquals(PoliceStationTile.DEFAULT_MAX_NEEDED_INHABITANTS, pst.getMaxNeededInhabitants());
		Assert.assertEquals(PoliceStationTile.DEFAULT_MAX_NEEDED_ENERGY, pst.getMaxNeededEnergy());
		Assert.assertEquals(PoliceStationTile.DEFAULT_SATISFACTION_VALUE, pst.getSatisfactionValue());
		Assert.assertEquals(initialPoliceStationNumber + 1, PoliceStationTile.getPoliceStationNumber());
	}
	
	@Test
	public void testUpdate() {
		PoliceStationTile pst = new PoliceStationTile();
        CityResources resources = new CityResources(100, 100);
        resources.increaseEnergyProduction(40);
        final int initialSatisfaction = resources.getSatisfaction();
        final int initialCurrency = resources.getCurrency();
        final int initialPopulation = resources.getUnworkingPopulation();
        final int initialEnergy = resources.getUnconsumedEnergy();
        pst.update(resources);
        Assert.assertEquals(initialPopulation - pst.getMaxNeededInhabitants(), resources.getUnworkingPopulation());
        Assert.assertEquals(initialEnergy - pst.getMaxNeededEnergy(), resources.getUnconsumedEnergy());
        Assert.assertEquals(initialCurrency - (int)(Math.round(pst.getMaintenanceCost() * GameBoard.getDifficulty().getCoeff())), resources.getCurrency()); 
        Assert.assertEquals(Math.max(initialSatisfaction, pst.getSatisfactionValue()), resources.getSatisfaction());
	}
	
	@Test
	public void testDisassemble() {
		PoliceStationTile pst = new PoliceStationTile();
		CityResources resources = new CityResources(100, 100);
        resources.increaseEnergyProduction(100);
		pst.update(resources);
		final int initialSatisfaction = resources.getSatisfaction();
        final int initialPopulation = resources.getUnworkingPopulation();
        final int initialEnergy = resources.getUnconsumedEnergy();
		pst.disassemble(resources);
		Assert.assertEquals(Math.max(0, initialSatisfaction), resources.getSatisfaction());
        Assert.assertEquals(Math.max(0, initialPopulation), resources.getUnworkingPopulation());
        Assert.assertEquals(Math.max(0, initialEnergy), resources.getUnconsumedEnergy());
	}
	
	@Test
	public void testIsDestroyed() {
		@SuppressWarnings("unused")
		GameBoard gb = new GameBoard(10, new UKTexts());
		PoliceStationTile pst = new PoliceStationTile();
		CityResources resources = new CityResources(100);
        resources.increaseEnergyProduction(100);
		pst.disassemble(resources);
		Assert.assertEquals(true, pst.isDestroyed());
	}
	
	@Test
	public void testEqual() {
		PoliceStationTile pst1 = new PoliceStationTile();
		CityResources resources = new CityResources(100);
        resources.increaseEnergyProduction(100);
		pst1.update(resources);
		pst1.disassemble(resources);
		PoliceStationTile pst2 = new PoliceStationTile();
		pst2.update(resources);
		pst2.disassemble(resources);
		Assert.assertEquals(true, pst1.equals(pst2));
	}
}
