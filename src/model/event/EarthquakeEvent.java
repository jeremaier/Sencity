package model.event;

import java.util.ArrayList;
import java.util.List;

import localization.LocalizedTexts;
import model.CityResources;
import model.tiles.Destroyable;
import model.tiles.GrassTile;
import model.tiles.Tile;
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
			
	protected Tile innerEffect(Tile aTarget, CityResources resources) {
        ((Destroyable) aTarget).disassemble(resources);
        return GrassTile.getDefault();
    }
	
    /**
     * Apply no effects.
     */
	@Override
    public List<Event> applyEffects(CityResources resources) {
        //System.out.println("Earthquake occured at (" + startingTile.getRow() +"," + startingTile.getColumn() + ").");
		System.out.println("Earthquake occured");
        innerEffect(world.getTile(startingTile.getRow(), startingTile.getColumn()), resources);
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


