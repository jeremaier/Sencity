package model.event;

import java.util.ArrayList;
import java.util.List;

import localization.LocalizedTexts;
import model.CityResources;

/**
 * The FireEvent make you loose products.
 */
public class FireEvent extends Event {
	/**
	 * Number of products lost during the fire.
	 */
	public final static int PRODUCTS_LOOSE = 20;

    /**
     * Default Constructor.
     */
	public FireEvent() {
        super();
    }

    /**
     * Apply no effects.
     */
	@Override
    public List<Event> applyEffects(CityResources resources) {
        resources.consumeProducts(FireEvent.PRODUCTS_LOOSE);
        return new ArrayList<>(0);
    }

    /**
     * Return an empty message.
     */
	@Override
    public String getMessage(LocalizedTexts texts) {
		return texts.getFireEventMessage();
    }
}