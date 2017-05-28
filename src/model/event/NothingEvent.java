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

import java.util.ArrayList;
import java.util.List;

import localization.LocalizedTexts;
import model.CityResources;

/**
 * The NothingEvent does nothing.
 */
public class NothingEvent extends Event {

	/**
	 * Default Constructor.
	 */
	public NothingEvent() {
		super();
	}

	/**
	 * Apply no effects.
	 */
	@Override
	public List<Event> applyEffects(CityResources resources) {
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
