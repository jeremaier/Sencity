package model.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import localization.LocalizedTexts;
import model.CityResources;
import model.tiles.CommercialTile;
import model.tiles.PoliceStationTile;

/**
 * The StealEvent make you loose money.
 */
public class StealEvent extends Event {
	/**
	 * Money lost during the steal.
	 */
	public static final int MONEY_LOOSE = 150;
	
	/**
	 * True if the steal is foiled.
	 */
	private boolean foil;
	
    /**
     * Default Constructor.
     */
	public StealEvent() {
        super();
    }

    /**
     * Apply effects.
     */
	@Override
    public List<Event> applyEffects(CityResources resources) {
		
			int random = new Random().nextInt(PoliceStationTile.getPoliceStationNumber() +1 );
		
			if(random == 0) {
				resources.spend(StealEvent.MONEY_LOOSE);
				resources.increaseBadEventOccurrence();
				foil = false;
			} else {
				resources.spend(StealEvent.MONEY_LOOSE / 5);
				resources.increaseGoodEventOccurrence();
				foil = true;
			}
		
        return new ArrayList<>(0);
    }

    /**
     * Return an steal event message.
     */
	@Override
    public String getMessage(LocalizedTexts texts) {
			if(foil){
				return texts.getFoilStealEventMessage();
			}else{
			return texts.getStealEventMessage();
			}
	}
	
}