package edu.jsu.mcis;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main extends JPanel implements ShapeObserver {
    private CustomWidget widget;
    private JLabel label;

    public Main() {
        widget = new CustomWidget();
        widget.addShapeObserver(this);
        label = new JLabel("NOT SELECTED", JLabel.CENTER);
        label.setName("label");
        setLayout(new BorderLayout());
        add(widget, BorderLayout.CENTER);
        add(label, BorderLayout.NORTH);
    }
    
    public void shapeChanged(ShapeEvent event) {
        boolean[] selected = event.isSelected();
        if(selected[0]) { label.setText("HEXAGON"); }
        else if(selected[1]) { label.setText("OCTAGON"); }
        else { label.setText("NONE SELECTED"); }
    }


	public static void main(String[] args) {
		JFrame window = new JFrame();
        window.setTitle("Main");
        window.add(new Main());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(500, 500);
        window.setVisible(true);
	}
}