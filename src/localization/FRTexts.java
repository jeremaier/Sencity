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

/**
 * France localized texts.
 */
public class FRTexts extends LocalizedTexts {
	@Override
	public String getLangageName() {
		return "Fran�ais";
	}
	
    // Messages
    @Override
    public String getToolCannotAffectMsg() {
        return "Impossible de modifier cet emplacement";
    }
    
    @Override
    public String getAlreadyBuildMsg() {
        return "Ce batiment ne peut �tre construit qu'une seule fois";
    }

    @Override
    public String getCurrencyMsg() {
        return "£ {0}";
    }

    @Override
    public String getEarthQuakeMsg() {
        return "Un tremblement de terre a fait des deg�ts aux coordonn�es [ {0} ]";
    }

    @Override
    public String getMissingResourcesMsg() {
        return "Ressources manquantes";
    }

    @Override
    public String getRoundMsg() {
        return "Tour #{0} : {1}";
    }

    // Labels
    @Override
    public String getCurrencyLabel() {
        return "Devise";
    }

    @Override
    public String getUnconsumedEnergyLabel() {
        return "Energie non consomm�e";
    }

    @Override
    public String getStoredProductsLabel() {
        return "Produits en stock";
    }

    @Override
    public String getUnworkingPopulationLabel() {
        return "Population sans travail";
    }

	@Override
	public String getSatisfactionLabel() {
		return "Satisfaction actuelle";
	}

    @Override
	public String getNextToMsg(Tile tile) {
		return "Ce batiment doit �tre pos� � c�t� de " + tile.toString();
	}
    
    @Override
	public String getBorderTitle() {
    	return "Quelque chose de sp�cial?";
    }
    
    @Override
	public String getLanguageLabel() {
    	return "Langue";
    }
    
    @Override
	public String getDifficultyLabel() {
    	return "Difficult�";
    }
    
    @Override
	public String getEasyLabel() {
    	return "Facile";
    }
    
    @Override
	public String getStandardLabel() {
    	return "Normal";
    }
    
    @Override
	public String getHardLabel() {
    	return "Difficile";
    }

	@Override
	public String getNewGameButtonLabel() {
		return "Nouvelle partie";
	}

	@Override
	public String getLoadButtonLabel() {
		return "Charger une partie";
	}

	@Override
	public String getBackLabel() {
		return "Retour";
	}
}
