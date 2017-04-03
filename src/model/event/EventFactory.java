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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import model.GameBoard;

/**
 * The EventFactory generates Events according to their probabilities.
 */
public class EventFactory {

    /**
     * Default Constructor.
     */
    private static enum eventType {
        NOTHING,
        CRIMINAL_INDUSTRY,
        CRIMINAL_COMMERCE,
        DISEASE,
        FESTIVAL;
        }

    /**
     * Probabilities bound to a specific event. The sum of all probabilities
     * must be equal to 100
     */
    private static final Map<eventType, Integer> event_probabilities = Collections.unmodifiableMap(new HashMap<eventType, Integer>() {
        /**
         *
         */
        private static final long serialVersionUID = -6805412774816642699L;

        {
            this.put(eventType.NOTHING, 20);
            this.put(eventType.CRIMINAL_INDUSTRY, 30);
            this.put(eventType.CRIMINAL_INDUSTRY, 30);
            this.put(eventType.DISEASE, 10);
            this.put(eventType.FESTIVAL, 10);
        }
    });

    private static final List<eventType> probalisticEventsList = new ArrayList<>(100);
    static {
        int probaSum = EventFactory.event_probabilities.values().stream().mapToInt(Number::intValue).sum();
        assert probaSum == 100 : MessageFormat.format("The sum of events probabilities must be equal to 100 (currently {0})", probaSum);
        for (Map.Entry<eventType, Integer> event_entry : EventFactory.event_probabilities.entrySet()) {
            for (int i = 0; i < event_entry.getValue(); i++) {
                EventFactory.probalisticEventsList.add(event_entry.getKey());
            }
        }
    }

    /**
     * Generates a random Event according to the probability set in
     * {@link #event_probabilities}
     *
     * @return
     */
    public static Event generateEvent(GameBoard world) {
        int index = ThreadLocalRandom.current().nextInt(0, EventFactory.probalisticEventsList.size());
        eventType type = EventFactory.probalisticEventsList.get(index);
        Event result;
        switch (type) {
        	case FESTIVAL:
        		result = new FestivalEvent();
        		break;
        	case DISEASE:
        		result = new DiseaseEvent();
        		break;
        	case CRIMINAL_INDUSTRY:
        		result = new Criminal_IndustryEvent();
        		break;
        	case CRIMINAL_COMMERCE:
        		result = new Criminal_CommerceEvent();
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
}
