package model.difficulty;

public enum Difficulties {
	EASY(DifficultyLevel.EASY_LEVEL),
	STANDARD(DifficultyLevel.STANDARD_LEVEL),
	HARD(DifficultyLevel.HARD_LEVEL);
	
	private DifficultyLevel level;
	
	Difficulties(DifficultyLevel level) {
		this.level = level;
	}
	
	public DifficultyLevel getLevel() {
		return this.level;
	}
}
