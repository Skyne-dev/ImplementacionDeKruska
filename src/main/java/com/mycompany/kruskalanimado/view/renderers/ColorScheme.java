/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kruskalanimado.view.renderers;

import com.mycompany.kruskalanimado.model.graph.AristaState;
import java.awt.Color;

public class ColorScheme {
    private final Color baseEdgeColor;
    private final Color evaluatingGood;
    private final Color evaluatingBad;
    private final Color fixed;
    private final Color discarded;
    
   public ColorScheme() {
    this.baseEdgeColor = new Color(200, 200, 200, 50); // Más transparente el fondo
    this.evaluatingGood = new Color(46, 204, 113, 255); // Verde sólido
    this.evaluatingBad = new Color(255, 140, 0, 255);  // Naranja intenso (DarkOrange)
    this.fixed = new Color(0, 120, 255, 255);          // Azul MST
    this.discarded = new Color(231, 76, 60, 80);       // Rojo suave para descartadas
}
    
    public Color getColorForState(AristaState state, boolean isCurrent) {
        if (!isCurrent && (state == AristaState.EVALUATING_GOOD || 
                           state == AristaState.EVALUATING_BAD)) {
            return fixed;
        }
        
        switch (state) {
            case EVALUATING_GOOD:
                return evaluatingGood;
            case EVALUATING_BAD:
                return evaluatingBad;
            case FIXED:
                return fixed;
            case DISCARDED:
                return discarded;
            default:
                return baseEdgeColor;
        }
    }
    
    public Color getBaseEdgeColor() {
        return baseEdgeColor;
    }
    
    public Color getEvaluatingGood() {
        return evaluatingGood;
    }
    
    public Color getEvaluatingBad() {
        return evaluatingBad;
    }
    
    public Color getFixed() {
        return fixed;
    }
    
    public Color getDiscarded() {
        return discarded;
    }
}