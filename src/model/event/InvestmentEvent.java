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
	 * Default Constructor.
	 * 
	 * @param world
	 *            - World
	 */
	public InvestmentEvent(GameBoard world) {
		super(world);
	}

	/**
	 * Apply no effects.
	 */
	@Override
	public List<Event> applyEffects(CityResources resources) {		
		IndustrialTile.maxProduction += InvestmentEvent.PRODUCTION_INCREASMENT;		
		return new ArrayList<>(0);
	}

	/**
	 * Return an investment event message.
	 */
	@Override
	public String getMessage(LocalizedTexts texts) {
		return texts.getInvestmentEventMessage();
	}
}