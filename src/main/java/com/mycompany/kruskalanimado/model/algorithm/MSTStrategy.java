/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kruskalanimado.model.algorithm;

import com.mycompany.kruskalanimado.model.graph.Vertices;
import com.mycompany.kruskalanimado.model.graph.Arista;
import java.util.List;

public interface MSTStrategy {
    /**
     * Encuentra todos los árboles de mínima expansión posibles
     * @param vertices Lista de vértices
     * @param edges Lista de aristas
     * @param maxSolutions Límite máximo de soluciones
     * @return Lista de soluciones MST
     */
    List<MSTSolution> findAllMSTs(List<Vertices> vertices, List<Arista> edges, int maxSolutions);
    
    /**
     * @return Nombre descriptivo de la estrategia
     */
    String getName();
    
    /**
     * @return Descripción del algoritmo
     */
    String getDescription();
}