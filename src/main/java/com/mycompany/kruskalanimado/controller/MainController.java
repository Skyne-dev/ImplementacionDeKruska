/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kruskalanimado.controller;

import com.mycompany.kruskalanimado.model.GraphModel;
import com.mycompany.kruskalanimado.model.graph.Vertices;
import com.mycompany.kruskalanimado.view.GraphView;
import com.mycompany.kruskalanimado.view.ControlPanel;
import com.mycompany.kruskalanimado.view.observers.ViewListener;
import com.mycompany.kruskalanimado.controller.commands.*;
import com.mycompany.kruskalanimado.config.ApplicationConfig;
import java.awt.Point;

public class MainController implements ViewListener {
    private GraphModel model;
    private GraphView graphView;
    private ControlPanel controlPanel;
    private AnimationController animationController;
    private CommandHistory commandHistory;
    
    public MainController() {
        this.model = new GraphModel();
        this.animationController = new AnimationController(model);
        this.commandHistory = new CommandHistory();
        
        initializeViews();
        setupBindings();
    }
    
    private void initializeViews() {
        // Crear vistas
        this.graphView = new GraphView();
        this.controlPanel = new ControlPanel();
        
        // Conectar modelo a vistas
        graphView.setModel(model);
        graphView.setListener(this);
        
        controlPanel.setModel(model);
        controlPanel.setListener(this);
    }
    
    private void setupBindings() {
        // Sincronizar animación con modelo
        animationController.addListener(new AnimationListener() {
            @Override
            public void onStepChanged(int step) {
                model.setStep(step);
            }
            
            @Override
            public void onStateChanged(AnimationState state) {
                controlPanel.updateAnimationControls(state);
            }
        });
    }
    
    // Implementación de ViewListener
    @Override
    public void onCanvasClick(Point point) {
        Command addVertexCmd = new AddVertexCommand(model, point.x, point.y);
        addVertexCmd.execute();
        commandHistory.push(addVertexCmd);
        
        // Recalcular MSTs automáticamente
        model.calculateMSTs(ApplicationConfig.getInstance().getMaxMSTsToShow());
    }
    
    @Override
    public void onVertexMoved() {
        // Recalcular MSTs cuando se mueve un vértice
        model.calculateMSTs(ApplicationConfig.getInstance().getMaxMSTsToShow());
    }
    
    @Override
    public void onVertexDragged() {
        // No recalcular durante el arrastre, solo al soltar
    }
    
    @Override
    public void onVertexDeleted(Vertices vertex) {
        Command deleteCmd = new DeleteVertexCommand(model, vertex);
        deleteCmd.execute();
        commandHistory.push(deleteCmd);
        
        model.calculateMSTs(ApplicationConfig.getInstance().getMaxMSTsToShow());
    }
    
    @Override
    public void onPrevMSTButton() {
        animationController.stop();
        model.previousSolution();
    }
    
    @Override
    public void onNextMSTButton() {
        animationController.stop();
        model.nextSolution();
    }
    
    @Override
    public void onPrevStepButton() {
        animationController.stop();
        model.previousStep();
    }
    
    @Override
    public void onNextStepButton() {
        animationController.stop();
        model.nextStep();
    }
    
    @Override
    public void onPlayButton() {
        animationController.start();
    }
    
    @Override
    public void onPauseButton() {
        animationController.pause();
    }
    
    @Override
    public void onStopButton() {
        animationController.stop();
        model.resetSteps();
    }
    
    @Override
    public void onSpeedChanged(int speed) {
        animationController.setSpeed(speed);
    }
    
    @Override
    public void onResetButton() {
        animationController.stop();
        model.resetSteps();
    }
    
    @Override
    public void onClearButton() {
        animationController.stop();
        model.clearGraph();
        commandHistory.clear();
    }
    
    @Override
    public void onToggleEdges(boolean show) {
        graphView.setShowAllEdges(show);
    }
    
    // Getters para las vistas
    public GraphView getGraphView() {
        return graphView;
    }
    
    public ControlPanel getControlPanel() {
        return controlPanel;
    }

   
}