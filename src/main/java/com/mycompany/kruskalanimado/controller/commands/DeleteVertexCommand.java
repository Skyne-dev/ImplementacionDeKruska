/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kruskalanimado.controller.commands;

import com.mycompany.kruskalanimado.model.GraphModel;
import com.mycompany.kruskalanimado.model.graph.Vertices;

public class DeleteVertexCommand implements Command {
    private GraphModel model;
    private Vertices vertex;
    
    public DeleteVertexCommand(GraphModel model, Vertices vertex) {
        this.model = model;
        this.vertex = vertex;
    }
    
    @Override
    public void execute() {
        model.removeVertices(vertex);
    }
    
    @Override
    public void undo() {
        // Implementar deshacer
    }
    
    @Override
    public String getName() {
        return "Eliminar vértice " + vertex.getLabel();
    }
}