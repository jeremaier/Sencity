package test.model.tiles;

import org.junit.Assert;
import org.junit.Test;

import localization.UKTexts;
import model.*;
import model.tiles.*;

public class ParkTileTest {
	@Test
	public void testInit() {
		ParkTile pt = new ParkTile();
		Assert.assertEquals(ParkTile.DEFAULT_MAINTENANCE_COST, pt.getMaintenanceCost());
		Assert.assertEquals(ParkTile.DEFAULT_MAX_NEEDED_INHABITANTS, pt.getSatisfactionValue());
		Assert.assertEquals(ParkTile.DEFAULT_SATISFACTION_VALUE, pt.getSatisfactionValue());
	}
	
	@Test
	public void testUpdate() {
		ParkTile pt = new ParkTile();
        CityResources resources = new CityResources(100, 100);
        resources.increaseEnergyProduction(40);
        final int initialSatisfaction = resources.getSatisfaction();
        final int initialPopulation = resources.getUnworkingPopulation();
        final int initialCurrency = resources.getCurrency();
        pt.update(resources);
        Assert.assertEquals(initialPopulation - pt.getMaxNeededInhabitants(), resources.getUnworkingPopulation());
        Assert.assertEquals(initialCurrency - (int)(Math.round(pt.getMaintenanceCost() * GameBoard.getDifficulty().getCoeff())), resources.getCurrency()); 
        Assert.assertEquals(Math.max(initialSatisfaction, pt.getSatisfactionValue()), resources.getSatisfaction());
	}
	
	@Test
	public void testDisassemble() {
		ParkTile pt = new ParkTile();
		CityResources resources = new CityResources(100, 100);
		pt.update(resources);
		final int initialSatisfaction = resources.getSatisfaction();
        final int initialPopulation = resources.getUnworkingPopulation();
		pt.disassemble(resources);
		Assert.assertEquals(Math.max(0, initialSatisfaction), resources.getSatisfaction());
        Assert.assertEquals(Math.max(0, initialPopulation), resources.getUnworkingPopulation());
	}
	
	@Test
	public void testIsDestroyed() {
		@SuppressWarnings("unused")
		GameBoard gb = new GameBoard(10, new UKTexts());
		ParkTile pt = new ParkTile();
		CityResources resources = new CityResources(100);
        resources.increaseEnergyProduction(100);
		pt.disassemble(resources);
		Assert.assertEquals(true, pt.isDestroyed());
	}
	
	@Test
	public void testEqual() {
		ParkTile pt1 = new ParkTile();
		CityResources resources = new CityResources(100);
		pt1.update(resources);
		pt1.disassemble(resources);
		ParkTile pt2 = new ParkTile();
		pt2.update(resources);
		pt2.disassemble(resources);
		Assert.assertEquals(true, pt1.equals(pt2));
	}
}
