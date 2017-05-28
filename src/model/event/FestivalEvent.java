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
		resources.increaseGoodEventOccurrence();
		return new ArrayList<>(0);
	}

	/**
	 * Return an festival event message.
	 */
	@Override
	public String getMessage(LocalizedTexts texts) {
		return texts.getFestivalEventMessage();
	}
}

