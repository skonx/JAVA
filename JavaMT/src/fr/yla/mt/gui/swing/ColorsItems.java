package fr.yla.mt.gui.swing;

import java.awt.Color;

public enum ColorsItems{
	UNCOLORED (null),
	GREEN (Color.GREEN),
	CYAN (Color.CYAN),
	GRAY (Color.GRAY),
	RED (Color.RED),
	BLUE (Color.BLUE),
	BLACK (Color.BLACK),
	YELLOW (Color.YELLOW),
	ORANGE (Color.ORANGE),
	MAGENTA (Color.MAGENTA),
	WHITE (Color.WHITE),
	PINK (Color.PINK);

	private final Color color;

	private ColorsItems(Color color) {
		this.color = color;
	}
	
	public Color getColor(){
		return color;
	}
}