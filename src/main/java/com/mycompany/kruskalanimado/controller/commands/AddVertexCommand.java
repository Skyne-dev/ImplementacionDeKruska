/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kruskalanimado.controller.commands;

import com.mycompany.kruskalanimado.model.GraphModel;

public class AddVertexCommand implements Command {
    private GraphModel model;
    private int x;
    private int y;
    private int vertexId; // Para poder deshacer
    
    public AddVertexCommand(GraphModel model, int x, int y) {
        this.model = model;
        this.x = x;
        this.y = y;
    }
    
    @Override
    public void execute() {
        model.addVertices(x, y);
        // En una implementación real, guardaríamos el ID del vértice creado
    }
    
    @Override
    public void undo() {
        // Implementar deshacer
    }
    
    @Override
    public String getName() {
        return "Agregar vértice";
    }
}