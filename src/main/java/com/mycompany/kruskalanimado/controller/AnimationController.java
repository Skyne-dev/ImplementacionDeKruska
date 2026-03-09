/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kruskalanimado.controller;

import com.mycompany.kruskalanimado.model.GraphModel;
import com.mycompany.kruskalanimado.model.algorithm.MSTSolution;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.List;

public class AnimationController {
    private GraphModel model;
    private Timer timer;
    private AnimationState state;
    private int speed; // 1-10
    private int currentStep;
    private List<AnimationListener> listeners;
    
    public AnimationController(GraphModel model) {
        this.model = model;
        this.state = AnimationState.STOPPED;
        this.speed = 5;
        this.currentStep = -1;
        this.listeners = new ArrayList<>();
    }
    
    public void start() {
        if (model.getCurrentSolution() == null || state == AnimationState.PLAYING) return;
        
        state = AnimationState.PLAYING;
        int delay = calculateDelay();
        
        if (timer != null) {
            timer.stop();
        }
        
      // Dentro del constructor o método start() donde inicializas el Timer
        timer = new Timer(delay, e -> {
        MSTSolution currentSolution = model.getCurrentSolution();
        if (currentSolution != null && model.getCurrentStep() < currentSolution.getStepCount() - 1) {
            model.nextStep();
            currentStep = model.getCurrentStep(); // Sincronizar localmente
            notifyStepChanged();
        } else {
        // Hemos llegado al final del algoritmo
            pause(); // O stop() dependiendo de si quieres que se quede el dibujo final
            state = AnimationState.STOPPED; 
            notifyStateChanged();
            }
        });
        
        timer.start();
        notifyStateChanged();
    }
    
    public void pause() {
        if (state == AnimationState.PLAYING && timer != null) {
            timer.stop();
            state = AnimationState.PAUSED;
            notifyStateChanged();
        }
    }
    
    public void stop() {
        if (timer != null) {
            timer.stop();
            timer = null;
        }
        state = AnimationState.STOPPED;
        currentStep = -1;
        notifyStateChanged();
        notifyStepChanged();
    }
    
    public void setSpeed(int newSpeed) {
        this.speed = Math.max(1, Math.min(10, newSpeed));
        if (state == AnimationState.PLAYING) {
            // Reiniciar timer con nueva velocidad
            pause();
            start();
        }
    }
    
    private int calculateDelay() {
        // Velocidad 1 = 800ms, velocidad 10 = 100ms
        return 900 - (80 * speed);
    }
    
    public AnimationState getState() {
        return state;
    }
    
    public int getSpeed() {
        return speed;
    }
    
    public int getCurrentStep() {
        return currentStep;
    }
    
    public void addListener(AnimationListener listener) {
        listeners.add(listener);
    }
    
    public void removeListener(AnimationListener listener) {
        listeners.remove(listener);
    }
    
    private void notifyStepChanged() {
        for (AnimationListener l : listeners) {
            l.onStepChanged(currentStep);
        }
    }
    
    private void notifyStateChanged() {
        for (AnimationListener l : listeners) {
            l.onStateChanged(state);
        }
    }
}