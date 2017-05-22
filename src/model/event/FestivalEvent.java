package model.event;

import java.util.ArrayList;
import java.util.List;

import localization.LocalizedTexts;
import model.CityResources;


/**
 * The CriminalEvent make you loose money.
 */
public class FestivalEvent extends Event {

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
        resources.credit(5);
        resources.increaseSatisfaction(5);
        return new ArrayList<>(0);
    }

    /**
     * Return an empty message.
     */
	@Override
    public String getMessage(LocalizedTexts texts) {
		if(texts.getLangageName()=="Français"){
			return "Un festival a été organisé";
		}
		else{
			return "A festival was organized";
		}
    }

}

