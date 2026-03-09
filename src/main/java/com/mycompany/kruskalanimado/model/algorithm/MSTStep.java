/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kruskalanimado.model.algorithm;

import com.mycompany.kruskalanimado.model.graph.Arista;

public class MSTStep {
    private final Arista edge;
    private boolean wasIncluded;
    private boolean causedCycle;
    private final long timestamp;
    
    public MSTStep(Arista edge) {
        this.edge = edge;
        this.timestamp = System.nanoTime();
    }
    
    public Arista getEdge() {
        return edge;
    }
    
    public boolean wasIncluded() {
        return wasIncluded;
    }
    
    public void setWasIncluded(boolean wasIncluded) {
        this.wasIncluded = wasIncluded;
    }
    
    public boolean causedCycle() {
        return causedCycle;
    }
    
    public void setCausedCycle(boolean causedCycle) {
        this.causedCycle = causedCycle;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    @Override
    public String toString() {
        return edge.toString() + " - " + (wasIncluded ? "INCLUIDA" : "DESCARTADA") + 
               (causedCycle ? " (ciclo)" : "");
    }
}