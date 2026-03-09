/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kruskalanimado.model.algorithm;

import com.mycompany.kruskalanimado.model.graph.*;
import java.util.*;

public class MSTSolution {
    private final List<MSTStep> steps;
    private final double totalCost;
    private final Set<String> edgesInMST;
    private final int vertexCount;
    private final String id;
    
    // CONSTRUCTOR CORREGIDO DE 3 PARÁMETROS
    public MSTSolution(List<Arista> allEdgesSorted, List<Arista> mstEdges, int totalVertices) {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.steps = new ArrayList<>();
        this.edgesInMST = new HashSet<>();
        this.vertexCount = totalVertices;
        
        // 1. Identificar aristas que ganaron y sumar el costo
        double cost = 0;
        for (Arista e : mstEdges) {
            String key = Math.min(e.getU(), e.getV()) + "-" + Math.max(e.getU(), e.getV());
            edgesInMST.add(key);
            cost += e.getWeight();
        }
        this.totalCost = cost;
        
        // 2. Construir los pasos usando TODAS las aristas para que la animación no se corte
        buildSteps(allEdgesSorted);
    }
    
    private void buildSteps(List<Arista> allEdgesSorted) {
        UnionFind uf = new UnionFind(vertexCount);
        
        for (Arista e : allEdgesSorted) {
            MSTStep step = new MSTStep(e);
            String key = Math.min(e.getU(), e.getV()) + "-" + Math.max(e.getU(), e.getV());
            
            if (uf.connected(e.getU(), e.getV())) {
                // AQUÍ DETECTA EL CICLO Y SE PINTARÁ DE NARANJA
                step.setWasIncluded(false);
                step.setCausedCycle(true);
            } else {
                if (edgesInMST.contains(key)) {
                    // SE PINTARÁ DE VERDE/AZUL
                    uf.union(e.getU(), e.getV());
                    step.setWasIncluded(true);
                    step.setCausedCycle(false);
                } else {
                    step.setWasIncluded(false);
                    step.setCausedCycle(false);
                }
            }
            steps.add(step);
        }
    }
    
    public MSTStep getStep(int index) {
        if (index >= 0 && index < steps.size()) return steps.get(index);
        return null;
    }
    
    public int getStepCount() { return steps.size(); }
    public double getTotalCost() { return totalCost; }
    public List<MSTStep> getSteps() { return Collections.unmodifiableList(steps); }
    public String getId() { return id; }
    public int getEdgeCount() { return edgesInMST.size(); }
    
    @Override
    public String toString() {
        return String.format("MST %s: %.2f (%d aristas)", id, totalCost, getEdgeCount());
    }
}