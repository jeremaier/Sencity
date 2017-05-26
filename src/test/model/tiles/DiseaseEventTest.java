package test.model.tiles;

import org.junit.Assert;
import org.junit.Test;

import model.CityResources;
import model.event.DiseaseEvent;

public class DiseaseEventTest {
	@Test
	public void testUpdate() {
        CityResources resources = new CityResources(100, 100);
		DiseaseEvent event = new DiseaseEvent();
        final int initialPopulation = resources.getPopulation();
		event.applyEffects(resources);
		Assert.assertEquals(initialPopulation - DiseaseEvent.DEADS, resources.getPopulation());
	}
}
