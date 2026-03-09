/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kruskalanimado.view.observers;

import com.mycompany.kruskalanimado.model.graph.Vertices;
import java.awt.Point;

public interface ViewListener {
    // Eventos de canvas
    void onCanvasClick(Point point);
    void onVertexMoved();
    void onVertexDragged();
    void onVertexDeleted(Vertices vertex);
    
    // Eventos de navegación
    void onPrevMSTButton();
    void onNextMSTButton();
    void onPrevStepButton();
    void onNextStepButton();
    
    // Eventos de animación
    void onPlayButton();
    void onPauseButton();
    void onStopButton();
    void onSpeedChanged(int speed);
    
    // Eventos de control
    void onResetButton();
    void onClearButton();
    void onToggleEdges(boolean show);
}