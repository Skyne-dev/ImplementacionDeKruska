/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kruskalanimado;

import com.mycompany.kruskalanimado.controller.MainController;
import com.mycompany.kruskalanimado.view.GraphView;
import com.mycompany.kruskalanimado.view.ControlPanel;
import javax.swing.*;
import java.awt.*;

public class KruskalAnimadoApp extends JFrame {
    
    public KruskalAnimadoApp() {
        initializeFrame();
        
        // Crear controlador (que a su vez crea modelo y vistas)
        MainController controller = new MainController();
        
        // Obtener vistas del controlador
        GraphView graphView = controller.getGraphView();
        ControlPanel controlPanel = controller.getControlPanel();
        
        // Layout principal
        setLayout(new BorderLayout());
        add(graphView, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        
        // Aplicar tema
        applyTheme();
    }
    
    private void initializeFrame() {
        setTitle("Kruskal Animado - Visualizador de Múltiples MSTs");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 750);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1000, 500));
    }
    
    private void applyTheme() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            // Personalizar colores
            UIManager.put("Panel.background", new Color(240, 240, 240));
            UIManager.put("Button.background", new Color(52, 152, 219));
            UIManager.put("Button.foreground", Color.WHITE);
            UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 12));
            UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 12));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new KruskalAnimadoApp().setVisible(true);
        });
    }
}