/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kruskalanimado.model.algorithm;

import com.mycompany.kruskalanimado.model.graph.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class KruskalStrategy implements MSTStrategy {
    private static final long TIME_LIMIT_MS = 5000;
    private long startTime;
    private boolean timeout;
    private Map<String, List<List<Arista>>> combinationCache;
    private int totalSolutions;
    
    // VARIABLES AÑADIDAS PARA EVITAR EL ERROR "CANNOT FIND SYMBOL"
    private List<Arista> allEdges;
    private int vertexCount;
    
    public KruskalStrategy() {
        this.combinationCache = new ConcurrentHashMap<>();
    }
    
    @Override
    public String getName() {
        return "Kruskal";
    }
    
    @Override
    public String getDescription() {
        return "Algoritmo de Kruskal: ordena aristas por peso y las añade si no forman ciclo";
    }
    
    @Override
    public List<MSTSolution> findAllMSTs(List<Vertices> vertices, List<Arista> edges, int maxSolutions) {
        this.startTime = System.currentTimeMillis();
        this.timeout = false;
        this.combinationCache.clear();
        this.totalSolutions = 0;
        
        // GUARDAMOS VARIABLES GLOBALES
        this.vertexCount = vertices.size();
        this.allEdges = new ArrayList<>(edges);
        Collections.sort(this.allEdges);
        
        List<MSTSolution> solutions = new ArrayList<>();
        
        if (this.vertexCount < 2) return solutions;
        
        Map<Double, List<Arista>> edgesByWeight = groupEdgesByWeight(this.allEdges);
        List<Double> sortedWeights = new ArrayList<>(edgesByWeight.keySet());
        
        dfsMSTs(new UnionFind(this.vertexCount), sortedWeights, 0, 
                edgesByWeight, new ArrayList<>(), solutions, maxSolutions);
        
        return solutions;
    }
    
    private Map<Double, List<Arista>> groupEdgesByWeight(List<Arista> edges) {
        Map<Double, List<Arista>> map = new TreeMap<>();
        for (Arista e : edges) {
            map.computeIfAbsent(e.getWeight(), k -> new ArrayList<>()).add(e);
        }
        return map;
    }
    
    private void dfsMSTs(UnionFind uf, List<Double> weights, int weightIndex,
                         Map<Double, List<Arista>> edgesByWeight,
                         List<Arista> currentMST,
                         List<MSTSolution> solutions, int maxSolutions) {
        
        if (System.currentTimeMillis() - startTime > TIME_LIMIT_MS) {
            timeout = true;
            return;
        }
        
        if (solutions.size() >= maxSolutions) return;
        
        // CORRECCIÓN MATEMÁTICA 1: Un MST siempre tiene exactamente (Vértices - 1) aristas
        if (currentMST.size() == this.vertexCount - 1) {
            // AQUÍ CREAMOS LA SOLUCIÓN CON LOS 3 PARÁMETROS CORRECTOS
            solutions.add(new MSTSolution(this.allEdges, new ArrayList<>(currentMST), this.vertexCount));
            totalSolutions++;
            return;
        }
        
        if (weightIndex >= weights.size()) return;
        
        double currentWeight = weights.get(weightIndex);
        List<Arista> sameWeightEdges = edgesByWeight.get(currentWeight);
        
        // CORRECCIÓN MATEMÁTICA 2: Cuántas aristas faltan para llegar a V-1
        int maxPossible = (this.vertexCount - 1) - currentMST.size();
        int maxToTake = Math.min(maxPossible, sameWeightEdges.size());
        
        for (int k = maxToTake; k >= 0; k--) {
            if (solutions.size() >= maxSolutions || timeout) return;
            
            List<List<Arista>> combinations = generateCombinations(sameWeightEdges, k);
            
            for (List<Arista> comb : combinations) {
                if (solutions.size() >= maxSolutions || timeout) return;
                
                UnionFind ufTemp = uf.clone();
                List<Arista> validEdges = new ArrayList<>();
                boolean valid = true;
                
                for (Arista e : comb) {
                    if (!ufTemp.connected(e.getU(), e.getV())) {
                        ufTemp.union(e.getU(), e.getV());
                        validEdges.add(e);
                    } else {
                        valid = false;
                        break;
                    }
                }
                
                if (valid) {
                    List<Arista> newMST = new ArrayList<>(currentMST);
                    newMST.addAll(validEdges);
                    dfsMSTs(ufTemp, weights, weightIndex + 1, edgesByWeight,
                           newMST, solutions, maxSolutions);
                }
            }
        }
    }
    
    private List<List<Arista>> generateCombinations(List<Arista> edges, int k) {
        if (k == 0) {
            List<List<Arista>> empty = new ArrayList<>();
            empty.add(new ArrayList<>());
            return empty;
        }
        if (k > edges.size()) return new ArrayList<>();
        
        String cacheKey = edges.hashCode() + "_" + k;
        if (combinationCache.containsKey(cacheKey)) return combinationCache.get(cacheKey);
        
        List<List<Arista>> result = new ArrayList<>();
        generateCombinationsRec(edges, k, 0, new ArrayList<>(), result);
        
        if (combinationCache.size() > 200) combinationCache.clear();
        combinationCache.put(cacheKey, result);
        return result;
    }
    
    private void generateCombinationsRec(List<Arista> edges, int k, int start,
                                         List<Arista> current, List<List<Arista>> result) {
        if (current.size() == k) {
            result.add(new ArrayList<>(current));
            return;
        }
        for (int i = start; i <= edges.size() - (k - current.size()); i++) {
            current.add(edges.get(i));
            generateCombinationsRec(edges, k, i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }
    
    public boolean isTimeout() { return timeout; }
    public int getTotalSolutions() { return totalSolutions; }
}