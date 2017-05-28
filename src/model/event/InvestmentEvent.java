package model.event;

import java.util.ArrayList;
import java.util.List;

import localization.LocalizedTexts;
import model.CityResources;
import model.GameBoard;
import model.tiles.IndustrialTile;

/**
 * The InvestmentEvent make you earn more money.
 */
public class InvestmentEvent extends Event {
	public static final int PRODUCTION_INCREASMENT = 2;
	/**
	 * True if the event happened
	 */

	private boolean happened = false;

    /**
     * Default Constructor.
     */
	public InvestmentEvent(GameBoard world) {
        super(world);
    }

    /**
     * Apply no effects.
     */
	@Override
    public List<Event> applyEffects(CityResources resources) {
		
		if(IndustrialTile.getIndustriesNumber() > 0){
			IndustrialTile.maxProduction += InvestmentEvent.PRODUCTION_INCREASMENT;
			happened = true;
		}
        return new ArrayList<>(0);
    }

	/**
     * Return an investment event message.
     */
	@Override
    public String getMessage(LocalizedTexts texts) {
		if(happened){
			return texts.getInvestmentEventMessage();
		}else{
			return"";
		}
    }
}