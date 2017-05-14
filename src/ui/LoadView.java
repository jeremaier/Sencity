package ui;

import java.awt.BorderLayout;
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
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import launcher.SimCityUI;
import localization.FRTexts;
import localization.LocalizedTexts;
import model.CityResources;
import model.GameBoard;
import model.difficulty.DifficultyLevel;
import model.tiles.Tile;

public class LoadView extends JPanel {

	private static final long serialVersionUID = 1L;
	private final MainFrame frame;

	JTextArea output;
	JList<String> list; 
	ListSelectionModel listSelectionModel;
	private int selectedSave = -1;

	public LoadView(MainFrame frame, int height, int width) {
		super(new BorderLayout());

		JPanel buttonPanel = new JPanel();
		final SimButton select = new SimButton(MainFrame.getTexts().getSelectLabel());
		final SimButton back = new SimButton(MainFrame.getTexts().getBackLabel());
		final SimButton[] buttons = {select, back};
		String[] listData = listSavesName();

		this.setOpaque(true);
		this.setBackground(MainMenuView.getBackgroundColor());
		this.frame = frame;
		this.actions(buttons, height, width);

		list = new JList<String>(listData);
		listSelectionModel = list.getSelectionModel();
		listSelectionModel.addListSelectionListener((ListSelectionListener) new SharedListSelectionHandler());
		JScrollPane listPane = new JScrollPane(list);

		output = new JTextArea(1, 10);
		output.setEditable(false);
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		this.add(splitPane, BorderLayout.CENTER);
		splitPane.setOneTouchExpandable(false);
		splitPane.setDividerLocation(220);

		JPanel topHalf = new JPanel();
		topHalf.setLayout(new BoxLayout(topHalf, BoxLayout.LINE_AXIS));

		JPanel listContainer = new JPanel(new GridLayout(1, 1));
		listContainer.setBorder(BorderFactory.createTitledBorder(MainFrame.getTexts().getSaveListLabel()));
		listContainer.add(listPane);
		
		topHalf.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
		topHalf.add(listContainer);
		splitPane.add(topHalf);
		
		if(!listData[0].equals("Vide"))
			buttonPanel.add(select);
			
		buttonPanel.add(back);
		splitPane.add(buttonPanel);
	}
	
	private String[] listSavesName() {
		final File directory = new File(System.getProperty("user.dir") + "\\saves");
		final File[] filesList = directory.listFiles();
		final String[] emptyList = {"Vide"};
		
		if(directory.isDirectory()) {
			if(directory.list().length < 1)
				return emptyList;
		} else return emptyList;
		
		final int filesNbr = filesList.length;
		String[] list = new String[filesNbr];
		
		for(int i = 0; i < filesNbr; i++) {
			String saveName;
			
			if(MainFrame.getTexts() instanceof FRTexts)
				saveName = "Sauvegarde";
			else saveName = "Save";
			
			list[i] = saveName + " " + (i + 1);
		}
			
		return list;
	}

	class SharedListSelectionHandler implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) { 
			ListSelectionModel lsm = (ListSelectionModel)e.getSource();
			int minIndex = lsm.getMinSelectionIndex();
			int maxIndex = lsm.getMaxSelectionIndex();

			for(int i = minIndex; i <= maxIndex; i++) {
				if (lsm.isSelectedIndex(i))
					selectedSave = i;
			}

			output.setCaretPosition(output.getDocument().getLength());
		}
	}

	private void actions(SimButton[] buttons, int height, int width) {		
		buttons[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(selectedSave != -1)
					SwingUtilities.invokeLater(() -> LoadView.loadGame(selectedSave + 1));
				
				frame.dispose();
			}
		});

		buttons[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setNewPanel(new MainMenuView(frame, height, width));
			}
		});
	}
	
	public static SimCityUI loadGame(int saveNbr) {
		File file = new File(System.getProperty("user.dir") + "\\saves\\save" + saveNbr);
		FileInputStream fIn;
		ObjectInputStream oIn;

		try {
			fIn = new FileInputStream(file);
			oIn = new ObjectInputStream(fIn);
			
			CityResources res = (CityResources)oIn.readObject();
			Tile[][] tiles = (Tile[][])oIn.readObject();
			DifficultyLevel difficulty = (DifficultyLevel)oIn.readObject();
			LocalizedTexts texts = (LocalizedTexts)oIn.readObject();
			
			if(oIn != null)
				oIn.close();
			
			if(fIn != null)
				fIn.close();
			
			return new SimCityUI(new GameBoard(tiles, res, difficulty, texts), texts);
		} catch(IOException e) {
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}