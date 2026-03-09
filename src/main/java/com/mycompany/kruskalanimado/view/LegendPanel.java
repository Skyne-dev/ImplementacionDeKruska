/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kruskalanimado.view;

import com.mycompany.kruskalanimado.model.graph.AristaState;
import javax.swing.*;
import java.awt.*;

public class LegendPanel extends JPanel {
    
    public LegendPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(250, 250, 250));
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Leyenda"
        ));
        
        addItem("Arista fija en MST", new Color(0, 120, 255), new BasicStroke(3));
        addItem("Evaluando (válida)", new Color(46, 204, 113, 180), new BasicStroke(5));
        addItem("Evaluando (ciclo)", new Color(255, 165, 0, 180), 
                new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, 
                               new float[]{9}, 0));
        addItem("Arista descartada", new Color(255, 0, 0, 100), 
                new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, 
                               new float[]{9}, 0));
        addItem("Arista pendiente", new Color(200, 200, 200), new BasicStroke(1));
        addItem("Vértice normal", new Color(52, 152, 219), null);
        addItem("Vértice seleccionado", new Color(241, 196, 15), null);
    }
    
    private void addItem(String text, Color color, Stroke stroke) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 2));
        item.setBackground(new Color(250, 250, 250));
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        
        JPanel colorSample = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                                   RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (stroke != null) {
                    // Es una arista
                    g2.setStroke(stroke);
                    g2.setColor(color);
                    g2.drawLine(5, 12, 45, 12);
                } else {
                    // Es un vértice
                    g2.setColor(color);
                    g2.fillOval(20, 5, 15, 15);
                }
            }
        };
        colorSample.setPreferredSize(new Dimension(50, 25));
        colorSample.setBackground(new Color(250, 250, 250));
        
        item.add(colorSample);
        item.add(new JLabel(text));
        
        add(item);
    }
}