package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import launcher.SimCityUI;
import model.CityResources;
import model.GameBoard;
import model.tiles.Tile;

public class LoadView extends JPanel {

	private static final long serialVersionUID = 1L;
	private final MainFrame frame;

	JTextArea output;
	JList<String> list; 
	ListSelectionModel listSelectionModel;
	private int selectedSave = -1;

	public LoadView(MainFrame frame, GameBoard game, int height, int width) {
		super(new BorderLayout());

		JPanel buttonPanel = new JPanel();
		final SimButton select = new SimButton(MainFrame.getTexts().getSelectLabel());
		final SimButton back = new SimButton(MainFrame.getTexts().getBackLabel());
		final SimButton[] buttons = {select, back};
		String[] listData = {"one", "two", "three"};

		this.setOpaque(true);
		this.setBackground(MainMenuView.getBackgroundColor());
		this.frame = frame;
		this.actions(buttons, game, height, width);

		for(SimButton button : buttons)
			buttonPanel.add(button);

		list = new JList<String>(listData);

		listSelectionModel = list.getSelectionModel();
		listSelectionModel.addListSelectionListener((ListSelectionListener) new SharedListSelectionHandler());
		JScrollPane listPane = new JScrollPane(list);

		output = new JTextArea(1, 10);
		output.setEditable(false);
		JScrollPane outputPane = new JScrollPane(output, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		this.add(splitPane, BorderLayout.CENTER);

		JPanel topHalf = new JPanel();
		topHalf.setLayout(new BoxLayout(topHalf, BoxLayout.LINE_AXIS));

		JPanel listContainer = new JPanel(new GridLayout(1, 1));
		listContainer.setBorder(BorderFactory.createTitledBorder(MainFrame.getTexts().getSaveListLabel()));
		listContainer.add(listPane);

		topHalf.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
		topHalf.add(listContainer);

		topHalf.setMinimumSize(new Dimension(100, 50));
		topHalf.setPreferredSize(new Dimension(100, 110));
		splitPane.add(topHalf);

		JPanel bottomHalf = new JPanel(new BorderLayout());
		bottomHalf.add(outputPane, BorderLayout.CENTER);
		bottomHalf.setPreferredSize(new Dimension(450, 135));

		splitPane.add(buttonPanel);
		//splitPane.add(bottomHalf);
	}

	class SharedListSelectionHandler implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) { 
			ListSelectionModel lsm = (ListSelectionModel)e.getSource();

			int minIndex = lsm.getMinSelectionIndex();
			int maxIndex = lsm.getMaxSelectionIndex();

			for(int i = minIndex; i <= maxIndex; i++) {
				if (lsm.isSelectedIndex(i)) {
					selectedSave = i;
					output.append(selectedSave + " \n");
				}
			}

			output.append(" \n");
			output.setCaretPosition(output.getDocument().getLength());
		}
	}

	private void actions(SimButton[] buttons, GameBoard game, int height, int width) {		
		buttons[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LoadView.loadGame(game, height, width, selectedSave);
			}
		});

		buttons[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setNewPanel(new MainMenuView(frame, game, height, width));
			}
		});
	}
	
	public static SimCityUI loadGame(GameBoard game, int height, int width, int save) {
		File file = null;
		//File file = fileChooser.getSelectedFile();
		FileInputStream fIn;
		ObjectInputStream oIn;

		try {
			fIn = new FileInputStream(file);
			oIn = new ObjectInputStream(fIn);
			
			CityResources res = (CityResources) oIn.readObject();
			Tile[][] tiles = (Tile[][]) oIn.readObject();
			
			if(oIn != null)
				oIn.close();
			
			if(fIn != null)
				fIn.close();
			
			return new SimCityUI(new GameBoard(height, width, tiles, res, MainFrame.getDifficulty().getLevel(), MainFrame.getTexts()), MainFrame.getTexts());
		} catch(IOException e) {
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}