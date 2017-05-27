package model.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import localization.LocalizedTexts;
import model.CityResources;
import model.tiles.HospitalTile;

/**
 * The DiseaseEvent make you loose money.
 */
public class DiseaseEvent extends Event {

	/**
	 * Number of death.
	 */
	public final static int DEADS = 50;
	
	/**
	 * True if the disease has been eradicated.
	 */
	private boolean eradication;
	
    /**
     * Default Constructor.
     */
	public DiseaseEvent() {
        super();
    }

    /**
     * Apply no effects.
     */
	@Override
    public List<Event> applyEffects(CityResources resources) {
		int random = new Random().nextInt(HospitalTile.getHospitalNumber());
		System.out.println(random);
		
		if(random == 0) {
	        resources.decreasePopulation(DiseaseEvent.DEADS);
	        resources.increaseBadEventOccurrence();
	        eradication = false;
		} else {
	        resources.decreasePopulation(DiseaseEvent.DEADS / 5);
			resources.increaseGoodEventOccurrence();
			eradication = true;
		}
		
        return new ArrayList<>(0);
    }

    /**
     * Return a disease event message.
     */
	@Override
    public String getMessage(LocalizedTexts texts) {
		if(eradication)
			return texts.getStoppedDiseaseEventMessage();
		
		return texts.getDiseaseEventMessage();
    }
}