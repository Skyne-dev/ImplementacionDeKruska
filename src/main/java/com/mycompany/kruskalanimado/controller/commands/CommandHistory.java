/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kruskalanimado.controller.commands;

import java.util.*;

public class CommandHistory {
    private Deque<Command> undoStack;
    private Deque<Command> redoStack;
    private static final int MAX_HISTORY = 50;
    
    public CommandHistory() {
        this.undoStack = new ArrayDeque<>();
        this.redoStack = new ArrayDeque<>();
    }
    
    public void push(Command command) {
        undoStack.push(command);
        redoStack.clear();
        
        if (undoStack.size() > MAX_HISTORY) {
            undoStack.removeLast();
        }
    }
    
    public boolean canUndo() {
        return !undoStack.isEmpty();
    }
    
    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
    
    public void undo() {
        if (canUndo()) {
            Command cmd = undoStack.pop();
            cmd.undo();
            redoStack.push(cmd);
        }
    }
    
    public void redo() {
        if (canRedo()) {
            Command cmd = redoStack.pop();
            cmd.execute();
            undoStack.push(cmd);
        }
    }
    
    public void clear() {
        undoStack.clear();
        redoStack.clear();
    }
}