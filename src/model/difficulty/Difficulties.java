package model.difficulty;

import ui.MainFrame;

public enum Difficulties {
	EASY(MainFrame.getTexts().getEasyLabel(), DifficultyLevel.EASY_LEVEL),
	STANDARD(MainFrame.getTexts().getStandardLabel(), DifficultyLevel.STANDARD_LEVEL),
	HARD(MainFrame.getTexts().getHardLabel(), DifficultyLevel.HARD_LEVEL);
	
	private String name;
	private DifficultyLevel level;
	
	Difficulties(String nameLevel, DifficultyLevel level) {
		this.name = nameLevel;
		this.level = level;
	}
	
	public DifficultyLevel getLevel() {
		return this.level;
	}
		   
	public String toString() {
		return this.name;
	}
}
