package edu.jsu.mcis;

public class ShapeEvent {
    private boolean[] selected;
    public ShapeEvent() {
        this(new boolean[]{false, false});
    }
    public ShapeEvent(boolean[] selected) {
        this.selected = selected;
    }
    public boolean[] isSelected() { return selected; }
}