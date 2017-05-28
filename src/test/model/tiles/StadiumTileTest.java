package test.model.tiles;

import org.junit.Assert;
import org.junit.Test;

import localization.UKTexts;
import model.CityResources;
import model.GameBoard;
import model.tiles.HarborTile;
import model.tiles.StadiumTile;

public class StadiumTileTest {
	@Test
    public void testInit() {
        Assert.assertEquals(StadiumTile.alreadyBuild, false);
		StadiumTile ht = new StadiumTile();
		Assert.assertEquals(StadiumTile.DEFAULT_MAINTENANCE_COST, ht.getMaintenanceCost());
        Assert.assertEquals(StadiumTile.DEFAULT_NEEDED_ENERGY, ht.getNeededEnergy());
        Assert.assertEquals(StadiumTile.DEFAULT_INCOME, ht.getIncome());
        Assert.assertEquals(StadiumTile.DEFAULT_SATISFACTION_VALUE, ht.getSatisfactionValue());
        Assert.assertEquals(StadiumTile.alreadyBuild, true);
	}
	
	@Test
	public void testUpdate() {
		StadiumTile ht = new StadiumTile();
        CityResources resources = new CityResources(100);
        resources.increaseEnergyProduction(100);
        final int initialEnergy = resources.getUnconsumedEnergy();
		final int initialCurrency = resources.getCurrency();
		ht.update(resources);
		final int neededEnergy = Math.max(10, ht.getNeededEnergy());
		final long meanEarn = initialCurrency + ht.getIncome() * resources.getVat() / 100 * resources.getSatisfaction() / 50  - Math.round(ht.getMaintenanceCost() * GameBoard.getDifficulty().getCoeff());
		Assert.assertEquals(ht.isEnergyMissing(), false);
		Assert.assertEquals(initialEnergy - neededEnergy, resources.getUnconsumedEnergy());
		Assert.assertEquals(resources.getCurrency(), meanEarn, meanEarn * 0.5);
	}
	
	@Test
	public void testIsDestroyed() {
		@SuppressWarnings("unused")
		GameBoard gb = new GameBoard(10, new UKTexts());
		HarborTile ht = new HarborTile();
		CityResources resources = new CityResources(100);
        resources.increaseEnergyProduction(100);
		ht.disassemble(resources);
		Assert.assertEquals(true, ht.isDestroyed());
        Assert.assertEquals(StadiumTile.alreadyBuild, false);
	}
	
	@Test
	public void testEqual() {
		HarborTile ht1 = new HarborTile();
		CityResources resources = new CityResources(100);
        resources.increaseEnergyProduction(200);
		ht1.update(resources);
		ht1.disassemble(resources);
		HarborTile ht2 = new HarborTile();
		ht2.update(resources);
		ht2.disassemble(resources);
		Assert.assertEquals(true, ht1.equals(ht2));
	}
}