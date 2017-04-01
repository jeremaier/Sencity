package model.event;

import java.util.ArrayList;
import java.util.List;

import localization.LocalizedTexts;
import model.CityResources;


/**
 * The CriminalEvent make you loose money.
 */
public class Criminal_IndustryEvent extends Event {

    /**
     * Default Constructor.
     */
	public Criminal_IndustryEvent() {
        super();
    }

    /**
     * Apply no effects.
     */
	@Override
    public List<Event> applyEffects(CityResources resources) {
        System.out.println("Criminal disaster occured.");
        resources.consumeProducts(2);
        return new ArrayList<>(0);
    }

    /**
     * Return an empty message.
     */
	@Override
    public String getMessage(LocalizedTexts texts) {
        return "";
    }

}

