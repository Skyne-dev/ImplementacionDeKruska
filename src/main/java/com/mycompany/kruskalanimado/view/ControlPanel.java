package com.mycompany.kruskalanimado.view;

import com.mycompany.kruskalanimado.model.GraphModel;
import com.mycompany.kruskalanimado.view.observers.ModelObserver;
import com.mycompany.kruskalanimado.view.observers.ViewListener;
import com.mycompany.kruskalanimado.controller.AnimationState;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ControlPanel extends JPanel implements ModelObserver {
    
    // --- CONSTANTES DE DISEÑO (Copiadas del Código 1) ---
    private static final Color COLOR_BACKGROUND = new Color(250, 250, 250);
    private static final Color COLOR_PANEL_BG = Color.WHITE;
    private static final Color COLOR_TEXT_PRIMARY = new Color(33, 33, 33);
    private static final Color COLOR_TEXT_SECONDARY = new Color(117, 117, 117);
    private static final Color COLOR_ACCENT = new Color(25, 118, 210);
    private static final Color COLOR_BORDER = new Color(224, 224, 224);
    
    private static final Font FONT_LABEL = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font FONT_VALUE = new Font("Segoe UI", Font.BOLD, 14);
    
    private GraphModel model;
    private ViewListener listener;
    
    // Componentes de información
    private JLabel lblVertexCount;
    private JLabel lblSolutionCount;
    private JLabel lblCurrentSolution;
    private JLabel lblCurrentStep;
    private JLabel lblTotalCost;
    private JProgressBar progressBar;
    
    // Botones
    private JButton btnPrevMST;
    private JButton btnNextMST;
    private JButton btnPlay;
    private JButton btnPause;
    private JButton btnStop;
    private JButton btnReset;
    private JButton btnClear;
    private JSlider speedSlider;
    private JCheckBox chkShowAllEdges;
    
    public ControlPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(COLOR_BACKGROUND);
        setBorder(new EmptyBorder(15, 15, 15, 15));
        
        initializeComponents();
        layoutComponents();
    }
    
    private void initializeComponents() {
        // Labels con estilo minimalista
        lblVertexCount = createValueLabel("0");
        lblSolutionCount = createValueLabel("0");
        lblCurrentSolution = createValueLabel("0/0");
        lblCurrentStep = createValueLabel("0/0");
        lblTotalCost = createValueLabel("0.00");
        
        // Barra de progreso minimalista
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setVisible(false);
        progressBar.setForeground(COLOR_ACCENT);
        progressBar.setBackground(COLOR_BORDER);
        progressBar.setBorder(null);
        progressBar.setPreferredSize(new Dimension(100, 20));
        
        // --- BOTONES CON LÓGICA DEL CÓDIGO 2 Y ESTILO DEL CÓDIGO 1 ---
        btnPrevMST = createMinimalButton("◀", "MST anterior");
        btnPrevMST.addActionListener(e -> { if (listener != null) listener.onPrevMSTButton(); });

        btnNextMST = createMinimalButton("▶", "MST siguiente");
        btnNextMST.addActionListener(e -> { if (listener != null) listener.onNextMSTButton(); });

        btnPlay = createMinimalButton("▶", "Reproducir");
        btnPlay.addActionListener(e -> { if (listener != null) listener.onPlayButton(); });

        btnPause = createMinimalButton("⏸", "Pausa");
        btnPause.addActionListener(e -> { if (listener != null) listener.onPauseButton(); });

        btnStop = createMinimalButton("■", "Detener");
        btnStop.addActionListener(e -> { if (listener != null) listener.onStopButton(); });

        btnReset = createMinimalButton("↺", "Reiniciar animación");
        btnReset.addActionListener(e -> { if (listener != null) listener.onResetButton(); });

        btnClear = createMinimalButton("✕", "Limpiar todo");
        btnClear.addActionListener(e -> { if (listener != null) listener.onClearButton(); });
        
        // Slider
        speedSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 5);
        speedSlider.setBackground(COLOR_PANEL_BG);
        speedSlider.setPreferredSize(new Dimension(120, 30));
        speedSlider.addChangeListener(e -> {
            if (!speedSlider.getValueIsAdjusting() && listener != null) {
                listener.onSpeedChanged(speedSlider.getValue());
            }
        });
        
        // Checkbox
        chkShowAllEdges = new JCheckBox("Mostrar todas las aristas", true);
        chkShowAllEdges.setBackground(COLOR_BACKGROUND);
        chkShowAllEdges.setFont(FONT_LABEL);
        chkShowAllEdges.addActionListener(e -> {
            if (listener != null) listener.onToggleEdges(chkShowAllEdges.isSelected());
        });
        
        setControlsEnabled(false);
    }

    // --- MÉTODOS DE AYUDA VISUAL (Copiados del Código 1) ---
    private JLabel createValueLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_VALUE);
        label.setForeground(COLOR_TEXT_PRIMARY);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        return label;
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_LABEL);
        label.setForeground(COLOR_TEXT_SECONDARY);
        return label;
    }
    
    private JButton createMinimalButton(String text, String tooltip) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (!isEnabled()) g2.setColor(new Color(240, 240, 240));
                else if (getModel().isPressed()) g2.setColor(new Color(220, 220, 220));
                else if (getModel().isRollover()) g2.setColor(new Color(245, 245, 245));
                else g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.setColor(COLOR_BORDER);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
                g2.setColor(isEnabled() ? COLOR_TEXT_PRIMARY : COLOR_TEXT_SECONDARY);
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        button.setToolTipText(tooltip);
        button.setPreferredSize(new Dimension(40, 35));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        return button;
    }

    private void layoutComponents() {
        // Panel de estadísticas (Grid 2x4 como el original pero con estilo nuevo)
        JPanel statsPanel = new JPanel(new GridLayout(2, 4, 15, 8));
        statsPanel.setBackground(COLOR_PANEL_BG);
        statsPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(COLOR_BORDER, 1, true), new EmptyBorder(12, 12, 12, 12)));
        
        statsPanel.add(createLabel("Vértices:")); statsPanel.add(lblVertexCount);
        statsPanel.add(createLabel("MSTs:")); statsPanel.add(lblSolutionCount);
        statsPanel.add(createLabel("Solución:")); statsPanel.add(lblCurrentSolution);
        statsPanel.add(createLabel("Costo:")); statsPanel.add(lblTotalCost);

        // Panel de navegación
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        navPanel.setBackground(COLOR_PANEL_BG);
        navPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(COLOR_BORDER, 1, true), new EmptyBorder(8, 12, 8, 12)));
        navPanel.add(createLabel("MST:")); navPanel.add(btnPrevMST); navPanel.add(btnNextMST);
        navPanel.add(Box.createHorizontalStrut(20));
        navPanel.add(createLabel("Paso:")); navPanel.add(lblCurrentStep);
        navPanel.add(Box.createHorizontalStrut(10)); navPanel.add(progressBar);

        // Panel de animación
        JPanel animPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        animPanel.setBackground(COLOR_PANEL_BG);
        animPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(COLOR_BORDER, 1, true), new EmptyBorder(8, 12, 8, 12)));
        animPanel.add(createLabel("Animación:"));
        animPanel.add(btnPlay); animPanel.add(btnPause); animPanel.add(btnStop); animPanel.add(btnReset);
        animPanel.add(Box.createHorizontalStrut(20));
        animPanel.add(createLabel("Velocidad:")); animPanel.add(speedSlider);

        // Panel inferior
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        bottomPanel.setBackground(COLOR_PANEL_BG);
        bottomPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(COLOR_BORDER, 1, true), new EmptyBorder(8, 12, 8, 12)));
        bottomPanel.add(chkShowAllEdges); bottomPanel.add(btnClear);

        // Contenedor principal
        JPanel centerContainer = new JPanel();
        centerContainer.setLayout(new BoxLayout(centerContainer, BoxLayout.Y_AXIS));
        centerContainer.setBackground(COLOR_BACKGROUND);
        centerContainer.add(statsPanel); centerContainer.add(Box.createVerticalStrut(10));
        centerContainer.add(navPanel); centerContainer.add(Box.createVerticalStrut(10));
        centerContainer.add(animPanel); centerContainer.add(Box.createVerticalStrut(10));
        centerContainer.add(bottomPanel);

        add(centerContainer, BorderLayout.CENTER);
    }

    // --- LÓGICA INTACTA DEL CÓDIGO 2 ---
    private void setControlsEnabled(boolean enabled) {
        btnPrevMST.setEnabled(enabled);
        btnNextMST.setEnabled(enabled);
        btnPlay.setEnabled(enabled);
        btnPause.setEnabled(false);
        btnStop.setEnabled(false);
        btnReset.setEnabled(enabled);
        speedSlider.setEnabled(enabled);
    }

    public void updateStepInfo(int currentStep, int totalSteps) {
        lblCurrentStep.setText((currentStep + 1) + "/" + totalSteps);
        if (totalSteps > 0) {
            progressBar.setValue((int) ((currentStep + 1) * 100.0 / totalSteps));
            progressBar.setString((currentStep + 1) + " / " + totalSteps);
        }
    }

    public void updateAnimationControls(AnimationState state) {
        switch (state) {
            case PLAYING:
                btnPlay.setEnabled(false); btnPause.setEnabled(true);
                btnStop.setEnabled(true); btnReset.setEnabled(false);
                break;
            case PAUSED:
                btnPlay.setEnabled(true); btnPause.setEnabled(false);
                btnStop.setEnabled(true); btnReset.setEnabled(true);
                break;
            case STOPPED:
                btnPlay.setEnabled(true); btnPause.setEnabled(false);
                btnStop.setEnabled(false); btnReset.setEnabled(true);
                break;
        }
    }

    @Override
    public void onModelChanged(GraphModel.ModelChangeType changeType, GraphModel model) {
        this.model = model;
        switch (changeType) {
            case GRAPH_CHANGED:
                lblVertexCount.setText(String.valueOf(model.getVertexCount()));
                setControlsEnabled(model.getVertexCount() >= 2);
                break;
            case SOLUTIONS_CHANGED:
                lblSolutionCount.setText(String.valueOf(model.getSolutionCount()));
                if (model.getSolutionCount() > 0) {
                    lblCurrentSolution.setText("1/" + model.getSolutionCount());
                    lblTotalCost.setText(String.format("%.2f", model.getCurrentSolution().getTotalCost()));
                }
                progressBar.setVisible(model.getSolutionCount() > 0);
                break;
            case SOLUTION_CHANGED:
                if (model.getCurrentSolution() != null) {
                    int idx = model.getAllSolutions().indexOf(model.getCurrentSolution()) + 1;
                    lblCurrentSolution.setText(idx + "/" + model.getSolutionCount());
                    lblTotalCost.setText(String.format("%.2f", model.getCurrentSolution().getTotalCost()));
                }
                break;
            case STEP_CHANGED:
                if (model.getCurrentSolution() != null) {
                    updateStepInfo(model.getCurrentStep(), model.getCurrentSolution().getStepCount());
                }
                break;
            case CALCULATION_STARTED: progressBar.setIndeterminate(true); break;
            case CALCULATION_FINISHED: progressBar.setIndeterminate(false); break;
        }
    }

    public void setListener(ViewListener listener) { this.listener = listener; }

    public void setModel(GraphModel model) {
        this.model = model;
        model.addObserver(this);
    }
}