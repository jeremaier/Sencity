package model.event;

import java.util.ArrayList;
import java.util.List;
import localization.LocalizedTexts;
import model.CityResources;

/**
 * The MatchEvent give you some money.
 */
public class MatchEvent extends Event {
	/**
	 * Money earn for a match.
	 */
	public final static int MONEY = 200;

    /**
     * Default Constructor.
     */
	public MatchEvent() {
        super();
    }

    /**
     * Apply no effects.
     */
	@Override
    public List<Event> applyEffects(CityResources resources) {
        resources.credit(MatchEvent.MONEY);
        return new ArrayList<>(0);
    }

	/**
     * Return an empty message.
     */
	@Override
    public String getMessage(LocalizedTexts texts) {
		return texts.getMatchEventMessage();
    }
}