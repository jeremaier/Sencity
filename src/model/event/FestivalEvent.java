package model.event;

import java.util.ArrayList;
import java.util.List;

import localization.LocalizedTexts;
import model.CityResources;

/**
 * The FestivalEvent make you earn money.
 */
public class FestivalEvent extends Event {
	/**
	 * Amount of money gain.
	 */
	public final static int MONEY = 50;
	
	/**
	 * Amount of money gain.
	 */
	public final static int EXTRA_SATISFACTION = 5;

    /**
     * Default Constructor.
     */
	public FestivalEvent() {
        super();
    }

    /**
     * Apply no effects.
     */
	@Override
    public List<Event> applyEffects(CityResources resources) {
        resources.credit(FestivalEvent.MONEY);
        resources.increaseSatisfaction(FestivalEvent.EXTRA_SATISFACTION);
        return new ArrayList<>(0);
    }

    /**
     * Return an empty message.
     */
	@Override
    public String getMessage(LocalizedTexts texts) {
		return texts.getFestivalEventMessage();
    }
}

