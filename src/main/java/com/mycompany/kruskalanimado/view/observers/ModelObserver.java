/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kruskalanimado.view.observers;

import com.mycompany.kruskalanimado.model.GraphModel;

public interface ModelObserver {
    void onModelChanged(GraphModel.ModelChangeType changeType, GraphModel model);
}