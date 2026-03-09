/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kruskalanimado.model.builder;

import com.mycompany.kruskalanimado.model.graph.Vertices;
import com.mycompany.kruskalanimado.model.graph.Arista;
import java.awt.Point;
import java.util.*;

public class GraphBuilder {
    private final List<Vertices> vertices;
    private int nextId;
    private final Deque<GraphMemento> undoStack;
    private final Deque<GraphMemento> redoStack;
    private static final int MAX_HISTORY = 50;
    
    public GraphBuilder() {
        this.vertices = new ArrayList<>();
        this.nextId = 0;
        this.undoStack = new ArrayDeque<>();
        this.redoStack = new ArrayDeque<>();
    }
    
    public Vertices addVertex(Point point) {
        return addVertex(point.x, point.y);
    }
    
    public Vertices addVertex(int x, int y) {
        saveState();
        Vertices v = new Vertices(nextId++, x, y);
        vertices.add(v);
        redoStack.clear();
        return v;
    }
    
    public boolean removeVertex(Vertices vertex) {
        saveState();
        boolean removed = vertices.remove(vertex);
        if (removed) {
            redoStack.clear();
        }
        return removed;
    }
    
    public Vertices getVertexAt(int x, int y, int radius) {
        for (Vertices v : vertices) {
            if (v.contains(x, y, radius)) {
                return v;
            }
        }
        return null;
    }
    
    public List<Arista> buildCompleteGraph() {
        List<Arista> edges = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = i + 1; j < vertices.size(); j++) {
                double distance = vertices.get(i).distanceTo(vertices.get(j));
                edges.add(new Arista(i, j, distance));
            }
        }
        Collections.sort(edges);
        return edges;
    }
    
    public void clear() {
        saveState();
        vertices.clear();
        nextId = 0;
        redoStack.clear();
    }
    
    public boolean canUndo() {
        return !undoStack.isEmpty();
    }
    
    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
    
    public void undo() {
        if (canUndo()) {
            redoStack.push(saveCurrentState());
            GraphMemento memento = undoStack.pop();
            restoreState(memento);
        }
    }
    
    public void redo() {
        if (canRedo()) {
            undoStack.push(saveCurrentState());
            GraphMemento memento = redoStack.pop();
            restoreState(memento);
        }
    }
    
    private void saveState() {
        undoStack.push(saveCurrentState());
        if (undoStack.size() > MAX_HISTORY) {
            undoStack.removeLast();
        }
    }
    
    private GraphMemento saveCurrentState() {
        List<Vertices> verticesCopy = new ArrayList<>();
        for (Vertices v : vertices) {
            verticesCopy.add(new Vertices(v.getId(), v.getX(), v.getY()));
        }
        return new GraphMemento(verticesCopy, nextId);
    }
    
    private void restoreState(GraphMemento memento) {
        this.vertices.clear();
        this.vertices.addAll(memento.getVertices());
        this.nextId = memento.getNextId();
    }
    
    public List<Vertices> getVertices() {
        return Collections.unmodifiableList(vertices);
    }
    
    public int getVertexCount() {
        return vertices.size();
    }
    
    // Memento class
    private static class GraphMemento {
        private final List<Vertices> vertices;
        private final int nextId;
        
        GraphMemento(List<Vertices> vertices, int nextId) {
            this.vertices = vertices;
            this.nextId = nextId;
        }
        
        List<Vertices> getVertices() {
            return vertices;
        }
        
        int getNextId() {
            return nextId;
        }
    }
}