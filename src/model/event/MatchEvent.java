
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
        resources.credit(5);
        return new ArrayList<>(0);
    }

	/**
     * Return an empty message.
     */
	@Override
    public String getMessage(LocalizedTexts texts) {
		if(texts.getLangageName()=="Français"){
			return "Un grand match s'est déroulé dans votre stade";
		}
		else{
			return "A great match took place in your stadium";
		}
    }

}

