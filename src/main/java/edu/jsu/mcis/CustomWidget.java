package edu.jsu.mcis;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class CustomWidget extends JPanel implements MouseListener {
    private java.util.List<ShapeObserver> observers;
    
    
    private final Color[] SELECTED_COLOR = {Color.green, Color.red};
    private final Color DEFAULT_COLOR = Color.white;
    private boolean[] selected;
    private Point[][] vertex;

    public CustomWidget() {
        observers = new ArrayList<>();
        
        selected = new boolean[]{true, false};
        
        vertex = new Point[2][6];
        vertex[1] = new Point[8];
        for(int i = 0; i < vertex.length; i++) {
            for(int j = 0; j < vertex[i].length; j++) {
                vertex[i][j] = new Point();
            }
        }
        Dimension dim = getPreferredSize();
        calculateVertices(dim.width, dim.height);
        setBorder(BorderFactory.createLineBorder(Color.black));
        addMouseListener(this);
    }

    
    public void addShapeObserver(ShapeObserver observer) {
        if(!observers.contains(observer)) observers.add(observer);
    }
    public void removeShapeObserver(ShapeObserver observer) {
        observers.remove(observer);
    }
    private void notifyObservers() {
        ShapeEvent event = new ShapeEvent(selected);
        for(ShapeObserver obs : observers) {
            obs.shapeChanged(event);
        }
    }
    
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }

    private void calculateVertices(int width, int height) {
        // Square size should be half of the smallest dimension (width or height).
        double theta = 0.0;
        for(int i = 0; i < vertex.length; i++) {
            theta = 2 * Math.PI / vertex[i].length;
            for(int j = 0; j < vertex[i].length; j++) {
                vertex[i][j].setLocation((width/3)*(1+i) + Math.cos(theta * j)*100,
                                  height/2 + Math.sin(theta * j)*100);
            }
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        calculateVertices(getWidth(), getHeight());
        Shape[] shapes = getShapes();
        
        for(int i = 0; i < shapes.length; i++) {
            g2d.setColor(Color.black);
            g2d.draw(shapes[i]);
            if(selected[i]) {
                g2d.setColor(SELECTED_COLOR[i]);
                g2d.fill(shapes[i]);
            }
            else {
                g2d.setColor(DEFAULT_COLOR);
                g2d.fill(shapes[i]);            
            }
        }
    }

    public void mouseClicked(MouseEvent event) {
        Shape[] shapes = getShapes();
        if(shapes[0].contains(event.getX(), event.getY())) {
            selected[0] = true;
            selected[1] = false;
            notifyObservers();
        }
        else if(shapes[1].contains(event.getX(), event.getY())) {
            selected[1] = true;
            selected[0] = false;
            notifyObservers();
        }
        repaint(shapes[0].getBounds());
        repaint(shapes[1].getBounds());
    }
    public void mousePressed(MouseEvent event) {}
    public void mouseReleased(MouseEvent event) {}
    public void mouseEntered(MouseEvent event) {}
    public void mouseExited(MouseEvent event) {}
    
    public Shape[] getShapes() {
        Shape[] shapes = new Shape[vertex.length];
        
        for(int i = 0; i < vertex.length; i++) {
            int[] x = new int[vertex[i].length];
            int[] y = new int[vertex[i].length];
            for(int j = 0; j < vertex[i].length; j++) {
                x[j] = vertex[i][j].x;
                y[j] = vertex[i][j].y;
            }
            shapes[i] = new Polygon(x, y, vertex[i].length);
        }
        
        return shapes;
    }
    public boolean[] isSelected() { return selected; }



	public static void main(String[] args) {
		JFrame window = new JFrame("Custom Widget");
        window.add(new CustomWidget());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(300, 300);
        window.setVisible(true);
	}
}
