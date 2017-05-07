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

import java.text.MessageFormat;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import localization.LocalizedTexts;
import model.GameBoard;

public class PropertiesView extends JPanel implements Observer {

    private static final long serialVersionUID = 1L;

    private JLabel currency;
    private JLabel energy;
    private JLabel unworkingPop;
    private JLabel products;
    private JLabel satisfaction;

    public PropertiesView(GameBoard w, LocalizedTexts texts) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(new JLabel(texts.getCurrencyLabel()));
        this.currency = new JLabel(Integer.toString(w.getCurrency()));
        this.add(this.currency);

        this.add(new JLabel(texts.getUnconsumedEnergyLabel()));
        this.energy = new JLabel(Integer.toString(w.getEnergy()));
        this.add(this.energy);

        this.add(new JLabel(texts.getUnworkingPopulationLabel()));
        this.unworkingPop = new JLabel(Integer.toString(w.getUnworkingPopulation()));
        this.add(this.unworkingPop);

        this.add(new JLabel(texts.getStoredProductsLabel()));
        this.products = new JLabel(Integer.toString(w.getProducts()));
        this.add(this.products);
        
        this.add(new JLabel(texts.getSatisfactionLabel()));
        this.satisfaction = new JLabel(Integer.toString(w.getSatisfaction()));
        this.add(this.satisfaction);
    }

    @Override
    public void update(Observable o, Object arg) {
        assert o instanceof GameBoard;
        GameBoard world = (GameBoard) o;

        this.currency.setText(MessageFormat.format(world.getTexts().getCurrencyMsg(), world.getCurrency()));
        this.energy.setText("" + world.getEnergy());
        this.unworkingPop.setText("" + world.getUnworkingPopulation());
        this.products.setText("" + world.getProducts());
        this.satisfaction.setText("" + world.getSatisfaction());
    }
}
