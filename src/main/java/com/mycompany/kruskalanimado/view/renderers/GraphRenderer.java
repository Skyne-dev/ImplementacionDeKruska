/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kruskalanimado.view.renderers;

import com.mycompany.kruskalanimado.model.graph.*;
import com.mycompany.kruskalanimado.model.algorithm.MSTSolution;
import com.mycompany.kruskalanimado.model.algorithm.MSTStep;
import java.awt.*;
import java.util.List;

public class GraphRenderer {
    private final ColorScheme colorScheme;
    private Vertices highlightedVertex;
    private Arista highlightedEdge;
    
    public GraphRenderer() {
        this.colorScheme = new ColorScheme();
    }
    
    public void drawGraph(Graphics2D g2, List<Vertices> vertices, List<Arista> edges,
                         MSTSolution currentSolution, int currentStep) {
        
        // Dibujar fondo
        drawBackground(g2);
        
        // Dibujar todas las aristas posibles en gris claro
        drawBaseEdges(g2, edges, vertices);
        
        if (currentSolution != null && currentStep >= 0) {
            // Dibujar aristas descartadas
            drawDiscardedEdges(g2, vertices, currentSolution, currentStep);
            
            // Dibujar aristas del MST
            drawMSTEdges(g2, vertices, currentSolution, currentStep);
        }
        
        // Dibujar arista destacada
        if (highlightedEdge != null && currentSolution != null) {
            drawHighlightedEdge(g2, vertices);
        }
        
        // Dibujar vértices
        drawVertices(g2, vertices);
        
        // Dibujar etiquetas de peso si hay solución
        if (currentSolution != null && currentStep >= 0) {
            drawWeightLabels(g2, vertices, currentSolution, currentStep);
        }
    }
    
    private void drawBackground(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, g2.getClipBounds().width, g2.getClipBounds().height);
        
        // Dibujar grid sutil
        g2.setColor(new Color(240, 240, 240));
        g2.setStroke(new BasicStroke(1));
        
        int step = 50;
        for (int x = 0; x < g2.getClipBounds().width; x += step) {
            g2.drawLine(x, 0, x, g2.getClipBounds().height);
        }
        for (int y = 0; y < g2.getClipBounds().height; y += step) {
            g2.drawLine(0, y, g2.getClipBounds().width, y);
        }
    }
    
    private void drawBaseEdges(Graphics2D g2, List<Arista> edges, List<Vertices> vertices) {
        g2.setColor(colorScheme.getBaseEdgeColor());
        g2.setStroke(new BasicStroke(1));
        
        for (Arista e : edges) {
            Vertices v1 = vertices.get(e.getU());
            Vertices v2 = vertices.get(e.getV());
            g2.drawLine(v1.getX(), v1.getY(), v2.getX(), v2.getY());
        }
    }
    
    private void drawDiscardedEdges(Graphics2D g2, List<Vertices> vertices,
                                   MSTSolution solution, int currentStep) {
        UnionFind uf = new UnionFind(vertices.size());
        
        for (int i = 0; i <= currentStep && i < solution.getStepCount(); i++) {
            MSTStep step = solution.getStep(i);
            Arista e = step.getEdge();
            
            if (!step.wasIncluded()) {
                Vertices v1 = vertices.get(e.getU());
                Vertices v2 = vertices.get(e.getV());
                
                if (uf.connected(e.getU(), e.getV())) {
                    g2.setColor(colorScheme.getColorForState(AristaState.DISCARDED, false));
                    g2.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, 
                                                BasicStroke.JOIN_BEVEL, 0, 
                                                new float[]{9}, 0));
                    g2.drawLine(v1.getX(), v1.getY(), v2.getX(), v2.getY());
                }
            } else {
                uf.union(e.getU(), e.getV());
            }
        }
    }
    
    private void drawMSTEdges(Graphics2D g2, List<Vertices> vertices, 
                         MSTSolution solution, int currentStep) {
    for (int i = 0; i <= currentStep && i < solution.getStepCount(); i++) {
        MSTStep step = solution.getStep(i);
        Arista e = step.getEdge();
        boolean isCurrent = (i == currentStep); // Identificar si es la que se procesa ahora

        if (step.wasIncluded() || isCurrent) {
            Vertices v1 = vertices.get(e.getU());
            Vertices v2 = vertices.get(e.getV());

            // Estilo según si es la actual o ya es fija
            if (isCurrent) {
                if (step.causedCycle()) {
                    // ARISTA NARANJA (Ciclo detectado)
                    g2.setColor(colorScheme.getEvaluatingBad());
                    g2.setStroke(new BasicStroke(4, BasicStroke.CAP_BUTT, 
                                 BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
                } else {
                    // ARISTA VERDE (Evaluando éxito)
                    g2.setColor(colorScheme.getEvaluatingGood());
                    g2.setStroke(new BasicStroke(5));
                }
            } else if (step.wasIncluded()) {
                // ARISTA AZUL (Ya es parte del MST)
                g2.setColor(colorScheme.getFixed());
                g2.setStroke(new BasicStroke(3));
            }
            
            g2.drawLine(v1.getX(), v1.getY(), v2.getX(), v2.getY());
        }
    }
}
    
    private void drawHighlightedEdge(Graphics2D g2, List<Vertices> vertices) {
        if (highlightedEdge == null) return;
        
        Vertices v1 = vertices.get(highlightedEdge.getU());
        Vertices v2 = vertices.get(highlightedEdge.getV());
        
        g2.setColor(new Color(255, 255, 0, 100));
        g2.setStroke(new BasicStroke(8));
        g2.drawLine(v1.getX(), v1.getY(), v2.getX(), v2.getY());
    }
    
    private void drawVertices(Graphics2D g2, List<Vertices> vertices) {
        for (Vertices v : vertices) {
            v.draw(g2);
        }
    }
    
    private void drawWeightLabels(Graphics2D g2, List<Vertices> vertices,
                                 MSTSolution solution, int currentStep) {
        g2.setFont(new Font("Arial", Font.PLAIN, 11));
        g2.setColor(new Color(100, 100, 100));
        
        for (int i = 0; i <= currentStep && i < solution.getStepCount(); i++) {
            MSTStep step = solution.getStep(i);
            Arista e = step.getEdge();
            Vertices v1 = vertices.get(e.getU());
            Vertices v2 = vertices.get(e.getV());
            
            int midX = (v1.getX() + v2.getX()) / 2;
            int midY = (v1.getY() + v2.getY()) / 2;
            
            // Fondo blanco para el texto
            String weight = String.format("%.1f", e.getWeight());
            FontMetrics fm = g2.getFontMetrics();
            int textWidth = fm.stringWidth(weight);
            int textHeight = fm.getHeight();
            
            g2.setColor(new Color(255, 255, 255, 200));
            g2.fillRect(midX - textWidth/2 - 2, midY - textHeight/2 - 2, 
                       textWidth + 4, textHeight + 4);
            
            g2.setColor(new Color(50, 50, 50));
            g2.drawString(weight, midX - textWidth/2, midY + textHeight/4);
        }
    }
    
    public void setHighlightedVertex(Vertices vertex) {
        this.highlightedVertex = vertex;
    }
    
    public void setHighlightedEdge(Arista edge) {
        this.highlightedEdge = edge;
    }
}