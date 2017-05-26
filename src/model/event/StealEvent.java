package model.event;

import java.util.ArrayList;
import java.util.List;

import localization.LocalizedTexts;
import model.CityResources;

/**
 * The StealEvent make you loose money.
 */
public class StealEvent extends Event {
	/**
	 * Money lost during the steal.
	 */
	public final static int MONEY_LOOSE = 150;
	
    /**
     * Default Constructor.
     */
	public StealEvent() {
        super();
    }

    /**
     * Apply no effects.
     */
	@Override
    public List<Event> applyEffects(CityResources resources) {
        resources.spend(StealEvent.MONEY_LOOSE);
        return new ArrayList<>(0);
    }

    /**
     * Return an empty message.
     */
	@Override
    public String getMessage(LocalizedTexts texts) {
		return texts.getStealEventMessage();
    }
}


