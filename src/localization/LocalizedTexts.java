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

package localization;

import model.tiles.Tile;

/***
 * Texts used by the game.
 */
public abstract class LocalizedTexts {

    // Message
    /**
     * @return Message that indicates that the tool cannot affect a given tile.
     */
    public abstract String getToolCannotAffectMsg();
    
    /**
     * @return Message that indicates that the tool can be build only one time.
     */
	public abstract String getAlreadyBuildMsg();

    /**
     * {0}: currency
     *
     * @return Currency message (including currency symbol).
     */
    public abstract String getCurrencyMsg();

    /**
     * {0}: locations
     *
     * @return EarthQuake event message.
     */
    public abstract String getEarthQuakeMsg();

    /**
     * @return Message that indicates that some resources are missing to
     *         complete a task.
     */
    public abstract String getMissingResourcesMsg();

    /**
     * {0}: round number {1}: action/event message
     *
     * @return Message that indicates an action/event and the attached round.
     */
    public abstract String getRoundMsg();

    // Labels
    /**
     * @return Currency label.
     */
    public abstract String getCurrencyLabel();

    /**
     * @return No-consumed energy label.
     */
    public abstract String getUnconsumedEnergyLabel();

    /**
     * @return Stored products label.
     */
    public abstract String getStoredProductsLabel();

    /**
     * @return No-working population label.
     */
    public abstract String getUnworkingPopulationLabel();

    /**
     * @return satisfaction label.
     */
    public abstract String getSatisfactionLabel();
    
    /**
     * @return Message that indicates that the tool need to be next to a certain tile.
     */
	public abstract String getNextToMsg(Tile tile);

	/**
	 * @return Title of the game border.
	 */
	public abstract String getBorderTitle();
}
