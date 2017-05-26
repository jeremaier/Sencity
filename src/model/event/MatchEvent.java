package model.event;

import java.util.ArrayList;
import java.util.List;
import localization.LocalizedTexts;
import model.CityResources;
import model.tiles.StadiumTile;

/**
 * The MatchEvent give you some money.
 */
public class MatchEvent extends Event {
	/**
	 * Money earn for a match.
	 */
	public final static int MONEY = 300;

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
		if(StadiumTile.alreadyBuild) {
	        resources.credit(MatchEvent.MONEY);
	        resources.increaseGoodEventOccurrence();
		}
		
        return new ArrayList<>(0);
    }

	/**
     * Return an match event message.
     */
	@Override
    public String getMessage(LocalizedTexts texts) {
		if(StadiumTile.alreadyBuild)
			return texts.getMatchEventMessage();
		
		return texts.getCancelledMatchEventMessage();
    }
}