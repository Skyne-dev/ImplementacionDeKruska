/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kruskalanimado.model.graph;

public enum AristaState {
    PENDING,        // Gris - no evaluada
    EVALUATING_GOOD,// Verde - evaluando y válida
    EVALUATING_BAD, // Naranja - evaluando y ciclo
    FIXED,          // Azul - incluida en MST
    DISCARDED;      // Rojo - descartada
    
    public boolean isEvaluating() {
        return this == EVALUATING_GOOD || this == EVALUATING_BAD;
    }
    
    public boolean isInMST() {
        return this == FIXED;
    }
    
    public boolean isDiscarded() {
        return this == DISCARDED;
    }
    
    public boolean isPending() {
        return this == PENDING;
    }
}