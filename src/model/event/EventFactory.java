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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import localization.LocalizedTexts;
import model.CityResources;
import model.GameBoard;
import model.difficulty.DifficultyLevel;
import model.tiles.CommercialTile;
import model.tiles.FireStationTile;
import model.tiles.HospitalTile;
import model.tiles.IndustrialTile;
import model.tiles.PoliceStationTile;

/**
 * The EventFactory generates Events according to their probabilities.
 */
public class EventFactory extends Event {

	/**
	 * Default Constructor.
	 * 
	 * @param world
	 *            - World
	 */
	public EventFactory(GameBoard world){
		super(world);
	}
	
	public static enum eventType {
		NOTHING,
		FIRE,
		STEAL,
		DISEASE,
		FESTIVAL,
		EARTHQUAKE,
		MATCH,
		INVESTMENT;
	}

	/**
	 * Probabilities bound to a specific event. The sum of all probabilities
	 * must be equal to 100
	 * 
	 * {@link #getEventProbabilities}
	 */


	private Map<eventType, Integer> eventProbabilities = new HashMap<eventType, Integer>() {
		private static final long serialVersionUID = -6805412774816642699L;
		{}
	};

	public Map<eventType, Integer> getEventProbabilities() {
		return eventProbabilities;
	}

	public void seteventProbabilities(Map<eventType, Integer> eventProbabilities, GameBoard world) {
		if(GameBoard.getDifficulty() == DifficultyLevel.EASY_LEVEL) {
			int nothingProb = 60;

			eventProbabilities.put(eventType.NOTHING, nothingProb);

			eventProbabilities.put(eventType.MATCH, 8);
			eventProbabilities.put(eventType.INVESTMENT, 8);
			eventProbabilities.put(eventType.FESTIVAL, 8);

			eventProbabilities.put(eventType.DISEASE,4);
			eventProbabilities.put(eventType.STEAL, 4);
			eventProbabilities.put(eventType.FIRE, 4);
			eventProbabilities.put(eventType.EARTHQUAKE, 4);

			if(world.isInTileArea( startingTile.getRow(), startingTile.getColumn(), 8, new PoliceStationTile(), 1)){
				nothingProb +=2;
				eventProbabilities.put(eventType.NOTHING, nothingProb);
				eventProbabilities.put(eventType.STEAL, 2);
			}
			
			if(world.isInTileArea( startingTile.getRow(), startingTile.getColumn(), 8, new FireStationTile(), 1)){
				nothingProb += 2;
				eventProbabilities.put(eventType.NOTHING, nothingProb);
				eventProbabilities.put(eventType.STEAL, 2);
			}
			
			if(world.isInTileArea( startingTile.getRow(), startingTile.getColumn(), 8, new HospitalTile(), 1)){
				nothingProb += 2;
				eventProbabilities.put(eventType.NOTHING, nothingProb);
				eventProbabilities.put(eventType.DISEASE, 2);
			}

		} else if(GameBoard.getDifficulty() == DifficultyLevel.STANDARD_LEVEL) {
			int nothingProb = 60;

			eventProbabilities.put(eventType.NOTHING, nothingProb);

			eventProbabilities.put(eventType.MATCH, 6);
			eventProbabilities.put(eventType.INVESTMENT, 6);
			eventProbabilities.put(eventType.FESTIVAL, 6);

			eventProbabilities.put(eventType.DISEASE, 4);
			eventProbabilities.put(eventType.STEAL, 6);
			eventProbabilities.put(eventType.FIRE, 6);
			eventProbabilities.put(eventType.EARTHQUAKE, 6);

			if(world.isInTileArea(startingTile.getRow(), startingTile.getColumn(), 8, new PoliceStationTile(), 1)) {
				nothingProb += 3;
				eventProbabilities.put(eventType.NOTHING, nothingProb);
				eventProbabilities.put(eventType.STEAL, 3);
			}
			
			if(world.isInTileArea(startingTile.getRow(), startingTile.getColumn(), 8 , new FireStationTile(), 1)) {
				nothingProb += 3;
				eventProbabilities.put(eventType.NOTHING, nothingProb);
				eventProbabilities.put(eventType.FIRE, 3);
			}
			
			if(world.isInTileArea(startingTile.getRow(), startingTile.getColumn(), 8 , new HospitalTile(), 1)) {
				nothingProb += 3;
				eventProbabilities.put(eventType.NOTHING, nothingProb);
				eventProbabilities.put(eventType.DISEASE, 3);
			}

		} else if(GameBoard.getDifficulty() == DifficultyLevel.HARD_LEVEL) {
			int nothingProb = 60;

			eventProbabilities.put(eventType.NOTHING, 60);

			eventProbabilities.put(eventType.MATCH, 4);
			eventProbabilities.put(eventType.INVESTMENT, 4);
			eventProbabilities.put(eventType.FESTIVAL, 4);

			eventProbabilities.put(eventType.DISEASE, 6);
			eventProbabilities.put(eventType.STEAL, 8);
			eventProbabilities.put(eventType.FIRE, 8);
			eventProbabilities.put(eventType.EARTHQUAKE, 6);
			
			if(world.isInTileArea( startingTile.getRow(), startingTile.getColumn(), 8, new PoliceStationTile(), 1)) {
				nothingProb += 4;
				eventProbabilities.put(eventType.NOTHING, nothingProb);
				eventProbabilities.put(eventType.STEAL, 4);
			}
			
			if(world.isInTileArea( startingTile.getRow(), startingTile.getColumn(), 8, new FireStationTile(), 1)) {
				nothingProb += 4;
				eventProbabilities.put(eventType.NOTHING, nothingProb);
				eventProbabilities.put(eventType.STEAL, 4);
			}
			
			if(world.isInTileArea( startingTile.getRow(), startingTile.getColumn(), 8, new HospitalTile(), 1)) {
				nothingProb += 3;
				eventProbabilities.put(eventType.NOTHING, nothingProb);
				eventProbabilities.put(eventType.DISEASE, 3);
			}
		}

		if(IndustrialTile.getIndustriesNumber() == 0) {
			eventProbabilities.put(eventType.INVESTMENT, 0);
			eventProbabilities.put(eventType.FIRE, 0);
		}

		if(CommercialTile.getCommerceNumber() == 0)
			eventProbabilities.put(eventType.STEAL, 0);

		this.eventProbabilities = eventProbabilities;
	}

	/**
	 * Generates a random Event according to the probability set in
	 * {@link #eventProbabilities}
	 * 
	 * @param world
	 *            - World
	 *
	 * @return an event
	 */
	public Event generateEvent(GameBoard world) {
		seteventProbabilities(getEventProbabilities(),world);

		List<eventType> probalisticEventsList = new ArrayList<>(100);{
			int probaSum = this.getEventProbabilities().values().stream().mapToInt(Number::intValue).sum();
			assert probaSum == 100 : MessageFormat.format("The sum of events probabilities must be equal to 100 (currently {0})", probaSum);
			for (Map.Entry<eventType, Integer> event_entry : this.getEventProbabilities().entrySet()) {
				for (int i = 0; i < event_entry.getValue(); i++)
					probalisticEventsList.add(event_entry.getKey());
			}
		}

		int index = ThreadLocalRandom.current().nextInt(0, probalisticEventsList.size());
		eventType type = probalisticEventsList.get(index);
		Event result;
		
		switch (type) {
		case MATCH:
			result = new MatchEvent();
			break;
		case INVESTMENT:
			result = new InvestmentEvent(world);
			break;
		case EARTHQUAKE:
			result = new EarthquakeEvent(world);
			break;
		case FESTIVAL:
			result = new FestivalEvent();
			break;
		case DISEASE:
			result = new DiseaseEvent();
			break;
		case FIRE:
			result = new FireEvent();
			break;
		case STEAL:
			result = new StealEvent();
			break;
		case NOTHING:
			result = new NothingEvent();
			break;
		default:
			result = new NothingEvent();
			break;
		}
		
		return result;
	}

	@Override
	public List<Event> applyEffects(CityResources resources) {
		return null;
	}

	@Override
	public String getMessage(LocalizedTexts texts) {
		return null;
	}
}
