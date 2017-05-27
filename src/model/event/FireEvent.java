package model.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import localization.LocalizedTexts;
import model.CityResources;
import model.tiles.FireStationTile;

/**
 * The FireEvent make you loose products.
 */
public class FireEvent extends Event {
	/**
	 * Number of products lost during the fire.
	 */
	public final static int PRODUCTS_LOOSE = 20;
	
	/**
	 * True if the fire is extinguished.
	 */
	private boolean extinct;
	
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
		int random = new Random().nextInt(FireStationTile.getFireStationNumber() + 1);
		
		if(random == 0) {
	        resources.consumeProducts(FireEvent.PRODUCTS_LOOSE);
	        resources.increaseBadEventOccurrence();
	        extinct = false;
		} else {
			resources.consumeProducts(FireEvent.PRODUCTS_LOOSE / 5);
			resources.increaseGoodEventOccurrence();
			extinct = true;
		}

        return new ArrayList<>(0);
    }

    /**
     * Return an fire event message.
     */
	@Override
    public String getMessage(LocalizedTexts texts) {
		if(extinct)
			return texts.getExtinctFireEventMessage();
		
		return texts.getFireEventMessage();
    }
}