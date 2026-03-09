/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kruskalanimado.model;

import com.mycompany.kruskalanimado.model.graph.Vertices;
import com.mycompany.kruskalanimado.model.graph.Arista;
import com.mycompany.kruskalanimado.model.algorithm.MSTSolution;
import com.mycompany.kruskalanimado.model.algorithm.MSTStrategy;
import com.mycompany.kruskalanimado.model.algorithm.KruskalStrategy;
import com.mycompany.kruskalanimado.model.builder.GraphBuilder;
import com.mycompany.kruskalanimado.view.observers.ModelObserver;
import com.mycompany.kruskalanimado.controller.AnimationController;
import java.awt.Point;
import java.util.*;
import javax.swing.SwingUtilities;

public class GraphModel {
    private final GraphBuilder graphBuilder;
    private final MSTStrategy mstStrategy;
    private List<MSTSolution> solutions;
    private MSTSolution currentSolution;
    private int currentStep;
    private final List<ModelObserver> observers;
    private boolean isCalculating;
    
    public GraphModel() {
        this.graphBuilder = new GraphBuilder();
        this.mstStrategy = new KruskalStrategy();
        this.solutions = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.currentStep = -1;
        this.isCalculating = false;
    }
    
    // Operaciones del grafo
    public void addVertices(int x, int y) {
        graphBuilder.addVertex(new Point(x, y));
        notifyObservers(ModelChangeType.GRAPH_CHANGED);
    }
    
    public boolean removeVertices(Vertices vertex) {
        boolean removed = graphBuilder.removeVertex(vertex);
        if (removed) {
            notifyObservers(ModelChangeType.GRAPH_CHANGED);
        }
        return removed;
    }
    
    public void clearGraph() {
        graphBuilder.clear();
        solutions.clear();
        currentSolution = null;
        currentStep = -1;
        notifyObservers(ModelChangeType.ALL_CHANGED);
    }
    
    // Cálculo de MSTs
    public void calculateMSTs(int maxSolutions) {
        if (graphBuilder.getVertexCount() < 2 || isCalculating) return;
        
        isCalculating = true;
        notifyObservers(ModelChangeType.CALCULATION_STARTED);
        
        // Simular cálculo en segundo plano (en producción usar SwingWorker)
        new Thread(() -> {
            List<Arista> edges = graphBuilder.buildCompleteGraph();
            List<MSTSolution> newSolutions = mstStrategy.findAllMSTs(
                graphBuilder.getVertices(), 
                edges, 
                maxSolutions
            );
            
            SwingUtilities.invokeLater(() -> {
                solutions = newSolutions;
                if (!solutions.isEmpty()) {
                    currentSolution = solutions.get(0);
                }
                currentStep = -1;
                isCalculating = false;
                notifyObservers(ModelChangeType.SOLUTIONS_CHANGED);
            });
        }).start();
    }
    
    // Navegación entre soluciones
    public void nextSolution() {
        if (solutions.isEmpty() || currentSolution == null) return;
        
        int currentIndex = solutions.indexOf(currentSolution);
        if (currentIndex < solutions.size() - 1) {
            currentSolution = solutions.get(currentIndex + 1);
            currentStep = -1;
            notifyObservers(ModelChangeType.SOLUTION_CHANGED);
        }
    }
    
    public void previousSolution() {
        if (solutions.isEmpty() || currentSolution == null) return;
        
        int currentIndex = solutions.indexOf(currentSolution);
        if (currentIndex > 0) {
            currentSolution = solutions.get(currentIndex - 1);
            currentStep = -1;
            notifyObservers(ModelChangeType.SOLUTION_CHANGED);
        }
    }
    
    public void goToSolution(int index) {
        if (index >= 0 && index < solutions.size()) {
            currentSolution = solutions.get(index);
            currentStep = -1;
            notifyObservers(ModelChangeType.SOLUTION_CHANGED);
        }
    }
    
    // Control de pasos
   public void nextStep() {
    if (currentSolution != null && currentStep < currentSolution.getStepCount() - 1) {
        currentStep++;
        // Importante: Notificar que el modelo cambió para que la vista haga repaint()
        notifyObservers(ModelChangeType.STEP_CHANGED);
    }
}
    
    public void previousStep() {
        if (currentStep > 0) {
            currentStep--;
            notifyObservers(ModelChangeType.STEP_CHANGED);
        }
    }
    
    public void setStep(int step) {
        if (currentSolution != null && step >= -1 && step < currentSolution.getStepCount()) {
            this.currentStep = step;
            notifyObservers(ModelChangeType.STEP_CHANGED);
        }
    }
    
    public void resetSteps() {
        currentStep = -1;
        notifyObservers(ModelChangeType.STEP_CHANGED);
    }
    
    // Getters
    public List<Vertices> getVertices() {
        return graphBuilder.getVertices();
    }
    
    public List<Arista> getAllEdges() {
        if (graphBuilder.getVertexCount() < 2) return new ArrayList<>();
        return graphBuilder.buildCompleteGraph();
    }
    
    public MSTSolution getCurrentSolution() {
        return currentSolution;
    }
    
    public int getCurrentStep() {
        return currentStep;
    }
    
     public int getCurrentIndex(){
        if (currentSolution == null || solutions.isEmpty()) {
            return  -1;
        }else{
        return 0;
        }
     }
    
    public List<MSTSolution> getAllSolutions() {
        return Collections.unmodifiableList(solutions);
    }
     
    
    public int getSolutionCount() {
        return solutions.size();
    }
    
    public int getVertexCount() {
        return graphBuilder.getVertexCount();
    }
    
    public boolean isCalculating() {
        return isCalculating;
    }
    
    // Observer pattern
    public void addObserver(ModelObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }
    
    public void removeObserver(ModelObserver observer) {
        observers.remove(observer);
    }
    
    private void notifyObservers(ModelChangeType changeType) {
        for (ModelObserver obs : observers) {
            obs.onModelChanged(changeType, this);
        }
    }
    public void testAnimation() {
    // Crear un grafo simple de prueba
    clearGraph();
    addVertices(100, 100);
    addVertices(300, 100);
    addVertices(200, 300);
    
    // Forzar cálculo
    calculateMSTs(10);
    
    // Iniciar animación después de un breve delay
    
}
   
    
    // Tipos de cambios
    public enum ModelChangeType {
        GRAPH_CHANGED,
        SOLUTIONS_CHANGED,
        SOLUTION_CHANGED,
        STEP_CHANGED,
        CALCULATION_STARTED,
        CALCULATION_FINISHED,
        ALL_CHANGED
    }
    
    
}