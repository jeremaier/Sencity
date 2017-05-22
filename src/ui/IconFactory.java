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

package ui;

import java.net.URL;

import javax.swing.ImageIcon;

import model.tiles.BuildableTile;
import model.tiles.Destroyable;
import model.tiles.Tile;
import model.tools.Tool;

/**
 * A Tile's or tool"s icon name is based on its class name.
 *
 * @author Martine Gautier
 * @author Victorien Elvinger
 *
 */
public class IconFactory {

	// Constant
	/**
	 * Default value of {@link IconFactory#getpathTemplate()} 1: id
	 */
	public final static String DEFAULT_PATH_TEMPLATE = "resources/icons/%s.png";

	/**
	 * {@link IconFactory#getInstance()}
	 */
	private final static IconFactory INSTANCE = new IconFactory(IconFactory.DEFAULT_PATH_TEMPLATE);

	/**
	 * 1: path
	 */
	private final static String ERROR_MESSAGE_TEMPLATE = "error: icon not found at %s";

	// Constant (id)
	/**
	 * Default 'no-icon' for tool.
	 */
	private final static String NO_ICON_TOOL_ID = "no-icon-tool";

	/**
	 * Default 'no-icon' for tile.
	 */
	private final static String NO_ICON_TILE_ID = "no-icon-tile";

	/**
	 * Icon for all Destroyable tiles where {@link Destroyable#isDestroyed()} is
	 * true.
	 */
	public final static String DEBRIS_TILE_ID = "debris";

	/**
	 * Id if {@link BuildableTile#isEnergyMissing()} is enabled.
	 */
	public final static String MISSING_ENERGY_POSTID = "missing-energy";

	/**
	 * Id if {@link BuildableTile#isPopulationMissing()} is enabled.
	 */
	public final static String MISSING_POPULATION_POSTID = "missing-population";

	// Implementation
	private final String pathTemplate;

	// Factory
	static public IconFactory getInstance() {
		return IconFactory.INSTANCE;
	}

	// Creation
	/**
	 * Prefer use {@link IconFactory#getInstance()} instead.
	 *
	 * @param pathTpl
	 */
	private IconFactory(String pathTpl) {
		this.pathTemplate = pathTpl;
	}

	// Access
	/**
	 * @return Path template to icons.
	 */
	public String getpathTemplate() {
		return this.pathTemplate;
	}

	/**
	 * @param aTool
	 * @return Icon associated to {@value aTool}.
	 */
	public ImageIcon getToolIcon(Tool aTool) {
		final String toolId = this.dashSeparatedWordsFromCamelCase(aTool.getClass().getSimpleName());
		return this.getIcon(toolId, IconFactory.NO_ICON_TOOL_ID);
	}

	/**
	 * @param aTile
	 * @return Icon associated to {@value aTile}.
	 */
	public ImageIcon getTileIcon(Tile aTile) {
		return this.getIcon(this.getTileId(aTile), IconFactory.NO_ICON_TILE_ID);
	}

	// Implementation
	/**
	 * @param aIconId
	 * @param aReplacementIconId
	 *            - May be null.
	 * @return Retrieve icon with {@value aId} as name or {@value aIconId} if
	 *         {@value aId} doesn't exist. If both are not retrievable then
	 *         throw a runtime exception.
	 */
	private ImageIcon getIcon(String aIconId, String aReplacementIconId) {
		final String path = String.format(this.pathTemplate, aIconId);
		final URL url1 = ClassLoader.getSystemResource(path);

		if (url1 != null) {
			return new ImageIcon(url1);
		} else if (aReplacementIconId != null) {
			return this.getIcon(aReplacementIconId, null);
		} else {
			throw new RuntimeException(String.format(IconFactory.ERROR_MESSAGE_TEMPLATE, path));
		}
	}

	/**
	 * e.g. turns "RandomWord" into "random-word".
	 *
	 * @param s
	 *            - CamelCase string
	 * @return lower-case string with dash-separated words.
	 */
	private String dashSeparatedWordsFromCamelCase(String s) {
		final String regexPattern = "([a-z])([A-Z]+)";
		final String replacementPattern = "$1-$2";

		return s.replaceAll(regexPattern, replacementPattern).toLowerCase();
	}

	/**
	 * @param aTile
	 * @return Id that corresponds to {@value aTile}.
	 */
	private String getTileId(Tile aTile) {
		if (aTile instanceof Destroyable && ((Destroyable) aTile).isDestroyed()) {
			return IconFactory.DEBRIS_TILE_ID;
		} else {
			final String id = this.dashSeparatedWordsFromCamelCase(aTile.getClass().getSimpleName());
			// Turn the class's name into dash-separated words in lower-case.

			if (aTile instanceof BuildableTile) {
				final BuildableTile t = (BuildableTile) aTile;

				final String statePostId = '-' + t.getState().name().toLowerCase().replace('_', '-');
				// Turn enumeration value into dash-separated words in
				// lower-case

				final String energyPostId;
				if (t.isEnergyMissing()) {
					energyPostId = '-' + IconFactory.MISSING_ENERGY_POSTID;
				} else {
					energyPostId = "";
				}

				final String populationPostId;
				if (t.isPopulationMissing())
					populationPostId = '-' + IconFactory.MISSING_POPULATION_POSTID;
				else populationPostId = "";

				return id + statePostId + energyPostId + populationPostId;
			} else {
				return id;
			}
		}
	}

}
