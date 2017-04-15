package model.event;

import java.util.ArrayList;
import java.util.List;

import localization.LocalizedTexts;
import model.CityResources;
import model.tiles.Destroyable;
import model.tiles.GrassTile;
import model.tiles.Tile;
import model.TilePosition;
/**
 * The CriminalEvent make you loose money.
 */
public class EarthquakeEvent extends Event {

    /**
     * Default Constructor.
     */
	public EarthquakeEvent() {
        super();
    }

	protected Tile innerEffect(TilePosition aTarget, CityResources r) {
        ((Destroyable) aTarget).disassemble(r);
        return GrassTile.getDefault();
    }
	
    /**
     * Apply no effects.
     */
	@Override
    public List<Event> applyEffects(CityResources resources) {
        System.out.println("Earthquake occured.");
        innerEffect(startingTile,resources);
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


