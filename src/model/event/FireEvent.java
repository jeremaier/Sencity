package model.event;

import java.util.ArrayList;
import java.util.List;

import localization.LocalizedTexts;
import model.CityResources;


/**
 * The CriminalEvent make you loose money.
 */
public class FireEvent extends Event {

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
        resources.consumeProducts(2);
        return new ArrayList<>(0);
    }

    /**
     * Return an empty message.
     */
	@Override
    public String getMessage(LocalizedTexts texts) {
		if(texts.getLangageName()=="Français"){
			return "Les réserves d'une industrie ont brulés";
		}
		else{
			return "Reserves of an industry burned";
		}
    }

}

