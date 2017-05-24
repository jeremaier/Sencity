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
 * France localized texts.
 */
public class FRTexts extends LocalizedTexts implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getLangageName() {
		return "Français";
	}
	
    // Messages
    @Override
    public String getToolCannotAffectMsg() {
        return "Impossible de modifier cet emplacement";
    }
    
    @Override
    public String getAlreadyBuildMsg() {
        return "Ce batiment ne peut être construit qu'une seule fois";
    }

	@Override
	public String getToolCannotEvolveMsg() {
		return "Impossible de faire evoluer ce batiment (batiment qui ne peut pas evoluer ou qui a atteint son niveau maximum ou doit être totalement construit)";
	}

    @Override
    public String getCurrencyMsg() {
        return "{0} €";
    }

    @Override
    public String getEarthQuakeMsg() {
        return "Un tremblement de terre a fait des degâts aux coordonnées [ {0} ]";
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
        return "Energie non consommée";
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
		return "Ce batiment doit être posé à côté de l'eau";
	}
    
    @Override
	public String getBorderTitle() {
    	return "Quelque chose de spécial?";
    }
    
    @Override
	public String getLanguageLabel() {
    	return "Langue";
    }
    
    @Override
	public String getDifficultyLabel() {
    	return "Difficulté";
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
	public String getSizeLabel() {
    	return "Taille de la carte";
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

	@Override
	public String getSelectLabel() {
		return "Selectionner";
	}

	@Override
	public String getSaveListLabel() {
		return "Liste des sauvegardes";
	}
	
	@Override
	public String getRefreshButtonLabel() {
		return "Rafraichir";
	}
	
	@Override
	public String getSaveButtonLabel() {
		return "Sauvegarder";
	}
	
	@Override
	public String getSaveMessage(int nbr) {
		return "La partie a bien été sauvegardée sous le nom : Sauvegarde " + nbr;
	}
	
	@Override
	public String getVatLabel() {
		return "Taxe sur les produits";
	}
}
