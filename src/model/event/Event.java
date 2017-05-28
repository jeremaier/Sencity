/**
 * TNCity
 * Copyright (c) 2017
 *  Jean-Philippe Eisenbarth,
 *  Victorien Elvinger
 *  Martine Gautier,
 *  Quentin Laporte-Chabasse
 *
 *  This file is part of TNCity.
 *
 *  TNCity is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  TNCity is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License
 *  along with TNCity.  If not, see <http://www.gnu.org/licenses/>.
 */

package model.event;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import localization.LocalizedTexts;
import model.CityResources;
import model.GameBoard;
import model.TilePosition;

/**
 * Abstract superclass for all events.
 */
public abstract class Event {
	protected static TilePosition startingTile;
	protected Set<TilePosition> appearanceCoordinates;
	public GameBoard world;

	/**
	 * Default Constructor.
	 */
	public Event() {
	}

	/**
	 * Constructor that randomly chooses a TilePosition to be affected by the event.
	 * @param world
	 *            - World
	 */
	public Event(GameBoard world) {
		int startingRow = ThreadLocalRandom.current().nextInt(0, world.getHeight());
		int startingColumn = ThreadLocalRandom.current().nextInt(0, world.getWidth());
		startingTile = new TilePosition(startingRow, startingColumn);
		this.world=world;
	}

	/**
	 * Applies the effects of this event. Updates the given resources.
	 *
	 * @param resources
	 *            - Resources
	 * @return The events that follow from the effects of this event. Must not
	 *         be null, if no event results from the current one then return an
	 *         empty List.
	 */
	public abstract List<Event> applyEffects(CityResources resources);

	/**
	 * @param texts
	 *            - text localization.
	 * @return Message announcing this event. Must not be null, if no message
	 *         should be displayed then return an empty String.
	 */
	public abstract String getMessage(LocalizedTexts texts);

}
