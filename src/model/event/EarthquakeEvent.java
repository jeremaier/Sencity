package model.event;

import java.util.ArrayList;
import java.util.List;

import localization.LocalizedTexts;
import model.CityResources;
import model.GameBoard;
import model.tiles.GrassTile;
import model.tools.BulldozerTool;
import model.tools.Tool;
/**
 * The CriminalEvent make you loose money.
 */
public class EarthquakeEvent extends Event {

    /**
     * Default Constructor.
     */
	public EarthquakeEvent(GameBoard world) {
        super(world);
    }
	
    /**
     * Apply no effects.
     */
	@Override
    public List<Event> applyEffects(CityResources resources) {
		if( !(world.tiles[startingTile.getRow()][startingTile.getColumn()] instanceof GrassTile) ){
			Tool tool = new BulldozerTool();
			world.tiles[startingTile.getRow()][startingTile.getColumn()] = tool.effect(world.tiles[startingTile.getRow()][startingTile.getColumn()], resources);
			System.out.println("Earthquake occured at ("+ startingTile.getRow() +" , " + startingTile.getColumn() +" ).");
		}else{
			System.out.println("Earthquake occured at no man's land");
		}
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


