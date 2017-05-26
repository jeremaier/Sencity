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

import java.io.Serializable;

import model.tiles.Tile;

/**
 * United Kingdom localized texts.
 */
public class UKTexts extends LocalizedTexts implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getLangageName() {
		return "English";
	}

    // Messages
    @Override
    public String getToolCannotAffectMsg() {
        return "Cannot effect this tile";
    }
    
    @Override
    public String getAlreadyBuildMsg() {
        return "This building can be build only one time";
    }

	@Override
	public String getToolCannotEvolveMsg() {
		return "Impossible to evolve this building anymore (building unevolvable or maximum level reached or must be build before evolve)";
	}

    @Override
    public String getCurrencyMsg() {
        return "£ {0}";
    }

    @Override
    public String getEarthQuakeMsg() {
        return "An earthquake occured at coordinates [ {0} ]";
    }

    @Override
    public String getMissingResourcesMsg() {
        return "Missing resources";
    }

    @Override
    public String getRoundMsg() {
        return "Round #{0} : {1}";
    }

    // Labels
    @Override
    public String getCurrencyLabel() {
        return "Currency";
    }

    @Override
    public String getUnconsumedEnergyLabel() {
        return "Unconsumed energy";
    }

    @Override
    public String getStoredProductsLabel() {
        return "Stored products";
    }

    @Override
    public String getUnworkingPopulationLabel() {
        return "Unworking population";
    }

	@Override
	public String getSatisfactionLabel() {
		return "Actual satisfaction";
	}

	@Override
	public String getPollutionLabel() {
		return "Actual pollution";
	}

    @Override
	public String getNextToMsg(Tile tile) {
		return "This building need to be build next to water";
	}
    
    @Override
	public String getBorderTitle() {
    	return "Something special ?";
    }
    
    @Override
	public String getLanguageLabel() {
    	return "Language";
    }
    
    @Override
	public String getDifficultyLabel() {
    	return "Difficulty";
    }
    
    @Override
	public String getEasyLabel() {
    	return "Easy";
    }
    
    @Override
	public String getStandardLabel() {
    	return "Standard";
    }
    
    @Override
	public String getHardLabel() {
    	return "Hard";
    }
    
    @Override
	public String getSizeLabel() {
    	return "Size map";
    }

	@Override
	public String getNewGameButtonLabel() {
		return "New game";
	}

	@Override
	public String getLoadButtonLabel() {
		return "Load a game";
	}

	@Override
	public String getBackLabel() {
		return "Back";
	}

	@Override
	public String getSelectLabel() {
		return "Select";
	}

	@Override
	public String getSaveListLabel() {
		return "Save list";
	}
	
	@Override
	public String getRefreshButtonLabel() {
		return "Refresh";
	}
	
	@Override
	public String getSaveButtonLabel() {
		return "Save";
	}
	
	@Override
	public String getSaveMessage(int nbr) {
		return "Your game has been saved under the name : Save " + nbr;
	}
	
	@Override
	public String getVatLabel() {
		return "Tax on products";
	}
	
	@Override
	public String getDiseaseEventMessage() {
		return "A disease killed people";
	}
	
	@Override
	public String getStoppedDiseaseEventMessage() {
		return "A disease was arrested in time before killing too many people";
	}
	
	@Override
	public String getEarthquakeEventMessage(int row, int column) {
		return "Earthquake occured at (" + row + ", " + column + ")";
	}
	
	@Override
	public String getFestivalEventMessage() {
		return "A festival was organized";
	}
	
	@Override
	public String getFireEventMessage() {
		return "Reserves of an industry burned";
	}
	
	@Override
	public String getExtinctFireEventMessage() {
		return "A fire has been extinguished";
	}
	
	@Override
	public String getInvestmentEventMessage() {
		return "Investors believed in your industry";
	}
	
	@Override
	public String getMatchEventMessage() {
		return "A great match took place in your stadium";
	}
	
	@Override
	public String getCancelledMatchEventMessage() {
		return "A match could not be organized in your city";
	}
	
	@Override
	public String getStealEventMessage() {
		return "Your shops have been stealed";
	}

	@Override
	public String getFoilStealEventMessage() {
		return "A thief was arrested by the police";
	}
}
