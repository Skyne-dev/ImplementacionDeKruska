/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kruskalanimado.model.graph;

import java.util.Arrays;

public class UnionFind implements Cloneable {
    private final int[] parent;
    private final int[] rank;
    private int components;
    
    public UnionFind(int n) {
        this.parent = new int[n];
        this.rank = new int[n];
        this.components = n;
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }
    
    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]); // Compresión de camino
        }
        return parent[x];
    }
    
    public boolean union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        
        if (rootX == rootY) return false;
        
        // Unión por rango
        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }
        
        components--;
        return true;
    }
    
    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }
    
    public int getComponents() {
        return components;
    }
    
    public void reset() {
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
        components = parent.length;
    }
    
    @Override
    public UnionFind clone() {
        UnionFind cloned = new UnionFind(parent.length);
        System.arraycopy(this.parent, 0, cloned.parent, 0, parent.length);
        System.arraycopy(this.rank, 0, cloned.rank, 0, rank.length);
        cloned.components = this.components;
        return cloned;
    }
    
    @Override
    public String toString() {
        return "UnionFind{components=" + components + ", parent=" + Arrays.toString(parent) + "}";
    }
}