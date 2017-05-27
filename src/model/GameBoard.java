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

package model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import localization.LocalizedTexts;
import model.difficulty.Difficulties;
import model.difficulty.DifficultyLevel;
import model.event.Event;
import model.event.EventFactory;
import model.tiles.Evolvable;
import model.tiles.GrassTile;
import model.tiles.Tile;
import model.tiles.WaterTile;
import model.tools.AirportZoneDelimiterTool;
import model.tools.BulldozerTool;
import model.tools.CommercialZoneDelimiterTool;
import model.tools.EvolveTool;
import model.tools.FireStationZoneDelimiterTool;
import model.tools.HarborZoneDelimiterTool;
import model.tools.HospitalZoneDelimiterTool;
import model.tools.IndustrialZoneDelimiterTool;
import model.tools.PoliceStationZoneDelimiterTool;
import model.tools.PowerPlantConstructionTool;
import model.tools.ResidentialZoneDelimiterTool;
import model.tools.StadiumZoneDelimiterTool;
import model.tools.Tool;
import model.tools.WaterZoneDelimiterTool;

public class GameBoard extends Observable implements Serializable {
	// Constant
	private static final long serialVersionUID = 1L;

	public final static DifficultyLevel DEFAULT_DIFFICULTY = DifficultyLevel.STANDARD_LEVEL;

	public final static TilePosition DEFAULT_SELECTED_LOCATION = new TilePosition(0, 0);

	public final static int DEFAULT_SELECTED_TOOL = 0;

	public final static int MAX_HANDLED_EVOLUTIONS = 5;

	public final static String NOTHING_MESSAGE = "";

	public final static AtomicInteger ROUNDCOUNTER = new AtomicInteger(0);

	// Implementation
	/**
	 * Map of the world.
	 */
	public final Tile[][] tiles;

	/**
	 * Available tools.
	 */
	private final List<Tool> tools;

	/**
	 * {@link #getSelectedTool()}
	 */
	public Tool selectedTool;

	/**
	 * {@link #getSelectedTile()}
	 */
	private Tile selectedTile;

	/**
	 * Pending evolutions.
	 */
	private final Queue<Evolvable> pendingEvolutions;

	/**
	 * Events to be applied to the world at the next refresh.
	 */
	private final List<Event> pendingEventsList;

	/**
	 * Available money.
	 */
	private CityResources resources;

	/**
	 * Status message.
	 */
	private String message;

	/**
	 * {@link #getTexts()}
	 */
	private final LocalizedTexts texts;

	/**
	 * {@link #getDifficulty()}
	 */
	private static DifficultyLevel difficulty;

	// Creation
	/**
	 * Create a rectangle world with {@value height * width} tiles.
	 *
	 * @param height
	 *            - {@link #getHeight()}
	 * @param width
	 *            - {@link #getWidth()}
	 * @param tiles
	 * 			  - Game tiles.
	 * @param res
	 * 			  - Game resources.
	 * @param difficulty
	 *            - Game difficulty level.
	 * @param texts
	 *            - {@link #getTexts()}
	 */
	public GameBoard(Tile[][] tiles, CityResources res, DifficultyLevel difficulty, LocalizedTexts texts) {
		this.tiles = tiles;
		this.selectedTile = this.getTile(GameBoard.DEFAULT_SELECTED_LOCATION.getRow(), GameBoard.DEFAULT_SELECTED_LOCATION.getColumn());

		this.tools = new ArrayList<>();
		this.tools.add(new BulldozerTool());
		this.tools.add(new PowerPlantConstructionTool());
		this.tools.add(new ResidentialZoneDelimiterTool());
		this.tools.add(new IndustrialZoneDelimiterTool());
		this.tools.add(new CommercialZoneDelimiterTool());
		this.tools.add(new AirportZoneDelimiterTool());
		this.tools.add(new HarborZoneDelimiterTool());
		this.tools.add(new StadiumZoneDelimiterTool());
		this.tools.add(new WaterZoneDelimiterTool());
		this.tools.add(new PoliceStationZoneDelimiterTool());
		this.tools.add(new FireStationZoneDelimiterTool());
		this.tools.add(new HospitalZoneDelimiterTool());

		this.selectedTool = this.tools.get(GameBoard.DEFAULT_SELECTED_TOOL);

		this.pendingEvolutions = new LinkedList<>();
		this.pendingEventsList = new LinkedList<>();
		this.resources = res;
		GameBoard.difficulty = difficulty;

		this.message = GameBoard.NOTHING_MESSAGE;
		this.texts = texts;
	}

	/**
	 * Create a rectangle world with {@value height * width} tiles.
	 *
	 * @param height
	 *            - {@link #getHeight()}
	 * @param width
	 *            - {@link #getWidth()}
	 * @param difficulty
	 *            - Game difficulty level.
	 * @param texts
	 *            - {@link #getTexts()}
	 */
	public GameBoard(int height, int width, DifficultyLevel difficulty, LocalizedTexts texts) {
		this(setTiles(height, width), new CityResources(difficulty.getInitialCurrency()), difficulty, texts);
	}

	/**
	 * Create a rectangle world with {@value height * width} tiles.
	 *
	 * @param height
	 *            - {@link #getHeight()}
	 * @param width
	 *            - {@link #getWidth()}
	 * @param texts
	 *            - {@link #getTexts()}
	 */
	public GameBoard(int height, int width, LocalizedTexts texts) {
		this(height, width, GameBoard.DEFAULT_DIFFICULTY, texts);
	}

	/**
	 * Create a square world with {@value length * length} tiles.
	 *
	 * @param length
	 *            - {@link #getWidth()} and {@link #getHeight()}
	 * @param texts
	 *            - {@link #getTexts()}
	 */
	public GameBoard(int length, LocalizedTexts texts) {
		this(length, length, texts);
	}

	// Access
	public LocalizedTexts getTexts() {
		return this.texts;
	}

	// Access (World)
	/**
	 * @return Difficulty of the world.
	 */
	public static DifficultyLevel getDifficulty() {
		return GameBoard.difficulty;
	}

	/**
	 * @return Height of the world in row count.
	 */
	public int getHeight() {
		return this.tiles.length;
	}

	/**
	 * @return Width of the world in column count.
	 */
	public int getWidth() {
		return this.tiles[0].length;
	}

	/**
	 * @param row
	 *            - Row number
	 * @param column
	 *            - Column number
	 * @return Cell with at location ({@value row}, {@value column}).
	 * @exception AssertionError
	 *                if {@value row} or {@value column} is invalid.
	 */
	public Tile getTile(int row, int column) {
		assert row >= 0 && row < this.getHeight() && column >= 0 && column < this.getWidth() : "Ligne/Colonne incorrecte";
		return this.tiles[row][column];
	}

	// Access (Tool)
	/**
	 * @return Number of available tools.
	 */
	public int getToolCount() {
		return this.tools.size();
	}

	/**
	 * @return Tools' iterator of available tools.
	 */
	public Iterator<Tool> toolIterator() {
		return this.tools.iterator();
	}

	// Access (Selection)
	/**
	 * @return Selected tile.
	 */
	public Tile getSelectedTile() {
		return this.selectedTile;
	}

	public Tool getSelectedTool() {
		return this.selectedTool;
	}

	// Access (City Resources)

	public CityResources getResources() {
		return resources;
	}

	public int getCurrency() {
		return this.resources.getCurrency();
	}

	public int getUnworkingPopulation() {
		return this.resources.getUnworkingPopulation();
	}

	public int getPopulation() {
		return this.resources.getPopulation();
	}

	public int getEnergy() {
		return this.resources.getUnconsumedEnergy();
	}

	public int getEnergyProduction() {
		return this.resources.getEnergyProduction();
	}

	public int getProducts() {
		return this.resources.getProductsCount();
	}

	public int getProductsCapacity() {
		return this.resources.getProductsCapacity();
	}

	public int getSatisfaction() {
		return this.resources.getSatisfaction();
	}

	// Access (Status)
	/**
	 * @return Status message.
	 */
	public String getMessage() {
		return this.message;
	}

	// Change (Selection)
	/**
	 * Change the difficulty of the current gameBoard
	 * 
	 * @param difficulty
	 */
	public void setDifficulty(Difficulties difficulty) {
		GameBoard.difficulty = difficulty.getLevel();
	}
	
	/**
	 *
	 * @param tool
	 *            - {@link #getSelectedTool()}.
	 */
	public void setSelectedTool(Tool tool) {
		this.selectedTool = tool;
		this.notifyViews();
	}

	/**
	 * Setup whole tiles ({@value height}, {@value width}).
	 *
	 * @param height
	 * @param width
	 */
	public static Tile[][] setTiles(int height, int width) {
		assert width > 0 && height > 0 : "Dimensions incorrectes";

		Tile[][] tiles = new Tile[height][width];

		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++)
				tiles[i][j] = GrassTile.getDefault();

		return tiles;
	}

	/**
	 * Select the tile at location ({@value row}, {@value column}).
	 *
	 * @param row
	 * @param column
	 */
	public void setSelectedTile(int row, int column) {
		this.selectedTile = this.getTile(row, column);
		this.notifyViews();
	}

	/**
	 * Return a set of TilePosition defining a square created from the given
	 * <code>startingTile</code> and the <code>areaSize</code>.
	 *
	 * @param startingTile
	 * @param areaSize
	 * @return Set of TilePosition
	 */
	public Set<TilePosition> getTilesArea(TilePosition startingTile, int areaSize) {
		Set<TilePosition> tilesArea = new HashSet<>();
		int beginningRow = startingTile.getRow() != 0 ? -1 : 0;
		int beginningColumn = startingTile.getColumn() != 0 ? -1 : 0;
		
		for (int i = beginningRow; i < areaSize; i++) {
			for (int j = beginningColumn; j < areaSize; j++) {
				int newRow = startingTile.getRow() + i < this.getHeight() ? startingTile.getRow() + i : this.getHeight() - 1;
				int newColumn = startingTile.getColumn() + j < this.getWidth() ? startingTile.getColumn() + j : this.getWidth() - 1;
				TilePosition newTile = new TilePosition(newRow, newColumn);
				tilesArea.add(newTile);
			}
		}
		
		return tilesArea;
	}
	
	/**
	 * @param row
	 * @param column
	 * @param areaSize
	 * @param tile
	 * @param tilesNbr
	 * @return if a certain tile is in certain area of the map.
	 */
	public boolean isInTileArea(int row, int column, int areaSize, Tile tile, int tilesNeeded) {
		int number = 0;
		Set<TilePosition> tilesArea = this.getTilesArea(new TilePosition(row, column), areaSize);
		
		for(TilePosition tilePos : tilesArea)
			if(tiles[tilePos.getRow()][tilePos.getColumn()].equals(tile)) {
				number++;
				
				if(number >= tilesNeeded)
					return true;
			}
		
		return false;
	}

	// Change (World)
	/**
	 * Effect the tile at location ({@value row}, {@value column}) with
	 * {@link #getSelectedTool()} if possible.
	 *
	 * @param row
	 * @param column
	 */
	public void effectTile(int row, int column) {
		assert row >= 0 && row < this.getHeight() && column >= 0 && column < this.getWidth() : "Ligne/Colonne incorrecte";

		final Tile currentTile = this.tiles[row][column];

		if(this.selectedTool.isAfordable(currentTile, this.resources)) {
			if(this.selectedTool.canEffect(currentTile)) {
				if(!this.selectedTool.isAleadyBuild()) {
					if(this.selectedTool instanceof HarborZoneDelimiterTool && !this.isInTileArea(row, column, 2, WaterTile.getDefault(), 3))
						this.message = this.texts.getNextToMsg(WaterTile.getDefault());
					else {
						final Tile newTile = this.selectedTool.effect(currentTile, this.resources);
						this.tiles[row][column] = newTile;
						this.pendingEvolutions.remove(currentTile);

						if (newTile instanceof Evolvable)
							this.pendingEvolutions.add((Evolvable) newTile);
					}
				} else this.message = this.texts.getAlreadyBuildMsg();
			} else if(currentTile instanceof Evolvable && this.selectedTool.isCorrespondantTile(currentTile)) {
				if(this.selectedTool.canEvolve(currentTile))
					((EvolveTool) this.selectedTool).evolve(currentTile, this.resources);
				else this.message = this.texts.getToolCannotEvolveMsg();
			} else this.message = this.texts.getToolCannotAffectMsg();
		} else this.message = this.texts.getMissingResourcesMsg();

		this.notifyViews();
	}

	/**
	 * Compute the next world state.
	 */
	public void nextState() {
		GameBoard.ROUNDCOUNTER.incrementAndGet();
		this.resources.resetEphemerals();
		this.applyPendingEvents();
		this.applyNewEvent();
		this.applyEvolutions();
		this.updateTiles();
		this.notifyViews();
	}

	/**
	 * Applies the effects of all the pending events (resulting from the
	 * previous one).
	 */
	private void applyPendingEvents() {
		List<Event> entry;
		for (Event event : this.pendingEventsList) {
			entry = event.applyEffects(this.resources);
			this.pendingEventsList.addAll(entry);
		}
	}

	/**
	 * Generates a new event and applies its effects.
	 */
	private void applyNewEvent() {
		Event event = (new EventFactory(this)).generateEvent(this);
		List<Event> resultingEvents = event.applyEffects(this.resources);
		assert resultingEvents != null;
		String eventMessage = event.getMessage(this.texts);
		assert eventMessage != null : "The event message must not be null.";
		this.message = eventMessage.isEmpty() ? GameBoard.NOTHING_MESSAGE : eventMessage;
		this.pendingEventsList.addAll(resultingEvents);
	}

	// Implementation (Notification)
	/**
	 * Notify view of a state change.
	 */
	private void notifyViews() {
		this.setChanged();
		this.notifyObservers();
		this.message = GameBoard.NOTHING_MESSAGE;
	}

	/**
	 * Apply evolutions in the order where it was registered.
	 */
	private void applyEvolutions() {
		final int count = Math.min(GameBoard.MAX_HANDLED_EVOLUTIONS, this.pendingEvolutions.size());

		for (int i = 0; i < count; i++) {
			final Evolvable e = this.pendingEvolutions.poll(); // Not null

			e.evolve(this.resources);

			if (e.canEvolve()) {
				this.pendingEvolutions.add(e);
			}
		}
	}

	/**
	 * Update all tiles via {@link Tile#update(CityResources)}.
	 */
	private void updateTiles() {
		for (final Tile[] rows : this.tiles) {
			for (final Tile t : rows) {
				t.update(this.resources);
			}
		}
	}

	/**
	 * Save a game ({@value row}, {@value column}).
	 * 
	 * @param res
	 * @param path
	 */
	public int saveGame(CityResources res) {
		String directoryPath = System.getProperty("user.dir") + "\\saves";
		File folder = new File(directoryPath);

		if(!folder.exists())
			folder.mkdirs();

		File[] fileList = folder.listFiles();
		FileOutputStream fOut;
		ObjectOutputStream oOut;
		int fileNbr = fileList.length + 1;

		try {
			fOut = new FileOutputStream(directoryPath + "\\save" + fileNbr);
			oOut = new ObjectOutputStream(fOut);

			oOut.writeObject(res);
			oOut.writeObject(this.tiles);
			oOut.writeObject(GameBoard.difficulty);
			oOut.writeObject(this.texts);

			if(oOut != null)
				oOut.close();

			if(fOut != null)
				fOut.close();
		} catch(IOException e) {
			e.printStackTrace();
		}

		return fileNbr;
	}
}
