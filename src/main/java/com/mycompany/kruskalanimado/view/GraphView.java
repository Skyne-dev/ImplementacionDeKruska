/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kruskalanimado.view;

import com.mycompany.kruskalanimado.model.GraphModel;
import com.mycompany.kruskalanimado.model.graph.*;
import com.mycompany.kruskalanimado.model.algorithm.MSTSolution;
import com.mycompany.kruskalanimado.model.algorithm.MSTStep;
import com.mycompany.kruskalanimado.view.renderers.GraphRenderer;
import com.mycompany.kruskalanimado.view.observers.ModelObserver;
import com.mycompany.kruskalanimado.view.observers.ViewListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class GraphView extends JPanel implements ModelObserver {
    private GraphModel model;
    private GraphRenderer renderer;
    private ViewListener listener;
    private boolean showAllEdges;
    private Point mousePosition;
    private Vertices highlightedVertex;
    private Arista highlightedEdge;
    private boolean dragging;
    private Vertices draggedVertex;
    
    public GraphView() {
        this.renderer = new GraphRenderer();
        this.showAllEdges = true;
        this.mousePosition = new Point();
        
        setBackground(new Color(250, 250, 250));
        setPreferredSize(new Dimension(900, 700));
        setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        
        setupMouseListeners();
        setupKeyboardListeners();
    }
    
    private void setupMouseListeners() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                requestFocusInWindow();
                
                if (model == null) return;
                
                // Verificar si hay un vértice en la posición del clic
                highlightedVertex = findVertexAt(e.getX(), e.getY(), 15);
                
                if (highlightedVertex != null) {
                    // Comenzar arrastre
                    dragging = true;
                    draggedVertex = highlightedVertex;
                    highlightedVertex.setHighlighted(true);
                    repaint();
                } else if (listener != null) {
                    // Agregar nuevo vértice
                    listener.onCanvasClick(e.getPoint());
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                if (dragging && draggedVertex != null) {
                    draggedVertex.setHighlighted(false);
                    dragging = false;
                    draggedVertex = null;
                    
                    if (listener != null) {
                        listener.onVertexMoved();
                    }
                }
            }
            
            @Override
            public void mouseMoved(MouseEvent e) {
                mousePosition = e.getPoint();
                updateHighlight(e.getX(), e.getY());
                repaint();
            }
            
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragging && draggedVertex != null) {
                    // Mover vértice
                    draggedVertex.setPosition(e.getX(), e.getY());
                    
                    if (listener != null) {
                        listener.onVertexDragged();
                    }
                    repaint();
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if (highlightedVertex != null) {
                    highlightedVertex.setHighlighted(false);
                    highlightedVertex = null;
                }
                if (highlightedEdge != null) {
                    highlightedEdge.setHighlighted(false);
                    highlightedEdge = null;
                }
                repaint();
            }
        };
        
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }
    
    private void setupKeyboardListeners() {
        setFocusable(true);
        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE && highlightedVertex != null) {
                    if (listener != null) {
                        listener.onVertexDeleted(highlightedVertex);
                    }
                }
            }
        });
    }
    
    private void updateHighlight(int x, int y) {
        if (dragging) return;
        
        // Actualizar vértice destacado
        Vertices newHighlighted = findVertexAt(x, y, 15);
        if (newHighlighted != highlightedVertex) {
            if (highlightedVertex != null) {
                highlightedVertex.setHighlighted(false);
            }
            highlightedVertex = newHighlighted;
            if (highlightedVertex != null) {
                highlightedVertex.setHighlighted(true);
            }
        }
        
        // Actualizar arista destacada
        if (model != null && model.getCurrentSolution() != null && highlightedVertex == null) {
           
           Arista newHighlightedArista = findEdgeAt(x, y, 5);
            if (newHighlightedArista != highlightedEdge) {
                if (highlightedEdge != null) {
                    highlightedEdge.setHighlighted(false);
                }
                highlightedEdge = newHighlightedArista;
                if (highlightedEdge != null) {
                    highlightedEdge.setHighlighted(true);
                }
            }
        } else if (highlightedEdge != null) {
            highlightedEdge.setHighlighted(false);
            highlightedEdge = null;
        }
    }
    
    private Vertices findVertexAt(int x, int y, int radius) {
        if (model == null) return null;
        
        for (Vertices v : model.getVertices()) {
            if (v.contains(x, y, radius)) {
                return v;
            }
        }
        return null;
    }
    
    private Arista findEdgeAt(int x, int y, int tolerance) {
        if (model == null || model.getCurrentSolution() == null) return null;
        
        List<Vertices> vertices = model.getVertices();
        MSTSolution solution = model.getCurrentSolution();
        
        for (MSTStep step : solution.getSteps()) {
            Arista e = step.getEdge();
            Vertices v1 = vertices.get(e.getU());
            Vertices v2 = vertices.get(e.getV());
            
            if (distanceToLineSegment(x, y, v1.getX(), v1.getY(), v2.getX(), v2.getY()) <= tolerance) {
                return e;
            }
        }
        return null;
    }
    
    private double distanceToLineSegment(int px, int py, int x1, int y1, int x2, int y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        double length = Math.sqrt(dx * dx + dy * dy);
        
        if (length == 0) return Math.hypot(px - x1, py - y1);
        
        double t = ((px - x1) * dx + (py - y1) * dy) / (length * length);
        t = Math.max(0, Math.min(1, t));
        
        double projX = x1 + t * dx;
        double projY = y1 + t * dy;
        
        return Math.hypot(px - projX, py - projY);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (model == null) return;
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                           RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                           RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // Obtener datos del modelo
        List<Vertices> vertices = model.getVertices();
        List<Arista> edges = showAllEdges ? model.getAllEdges() : new ArrayList<>();
        MSTSolution currentSolution = model.getCurrentSolution();
        int currentStep = model.getCurrentStep();
        
        // Configurar renderer
        renderer.setHighlightedVertex(highlightedVertex);
        renderer.setHighlightedEdge(highlightedEdge);
        
        // Renderizar
        renderer.drawGraph(g2, vertices, edges, currentSolution, currentStep);
        
        // Dibujar información en tiempo real
        drawInfo(g2);
    }
    
    private void drawInfo(Graphics2D g2) {
        g2.setFont(new Font("Monospaced", Font.PLAIN, 12));
        g2.setColor(new Color(100, 100, 100));
        
        int y = 30;
        g2.drawString("Vértices: " + (model != null ? model.getVertexCount() : 0), 20, y);
        y += 20;
        
        if (model != null && model.getCurrentSolution() != null) {
            g2.drawString("Solución: " + (model.getCurrentIndex() + 1) + 
                         " de " + model.getSolutionCount(), 20, y);
            y += 20;
            g2.drawString("Paso: " + (model.getCurrentStep() + 1) + 
                         " de " + model.getCurrentSolution().getStepCount(), 20, y);
            y += 20;
            g2.drawString("Costo: " + String.format("%.2f", 
                         model.getCurrentSolution().getTotalCost()), 20, y);
        }
        
        if (highlightedVertex != null) {
            g2.setColor(new Color(52, 152, 219));
            g2.drawString("Vértice seleccionado: " + highlightedVertex.getLabel(), 
                         mousePosition.x + 20, mousePosition.y - 10);
        } else if (highlightedEdge != null) {
            g2.setColor(new Color(46, 204, 113));
            g2.drawString("Arista: " + highlightedEdge.toString(), 
                         mousePosition.x + 20, mousePosition.y - 10);
        }
    }
    
    @Override
    public void onModelChanged(GraphModel.ModelChangeType changeType, GraphModel model) {
        // Actualizar vista según el tipo de cambio
        repaint();
    }
    
    public void setModel(GraphModel model) {
        this.model = model;
        model.addObserver(this);
    }
    
    public void setListener(ViewListener listener) {
        this.listener = listener;
    }
    
    public void setShowAllEdges(boolean show) {
        this.showAllEdges = show;
        repaint();
    }
}