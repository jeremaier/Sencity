package model.event;

import java.util.ArrayList;
import java.util.List;

import localization.LocalizedTexts;
import model.CityResources;

/**
 * The DiseaseEvent make you loose money.
 */
public class DiseaseEvent extends Event {

	/**
	 * Number of death.
	 */
	public final static int DEADS = 50;
	
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
        resources.decreasePopulation(DiseaseEvent.DEADS);
        return new ArrayList<>(0);
    }

    /**
     * Return a disease event message.
     */
	@Override
    public String getMessage(LocalizedTexts texts) {
		return texts.getDiseaseEventMessage();
    }
}