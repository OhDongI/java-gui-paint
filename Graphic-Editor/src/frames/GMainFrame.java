package frames;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JRadioButton;

import global.Constants;
import global.Constants.EShapeButtons;

public class GMainFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private GMenuBar menuBar;
    private GShapeToolBar shapeToolBar;
    private GDrawingPanel drawingPanel;
    
    public GMainFrame() { //기본적 뼈대
        //set attributes
        
        this.setSize(Constants.GMainFrame.WIDTH,Constants.GMainFrame.HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // create components
        
        LayoutManager layoutManager = new BorderLayout();
        this.setLayout(layoutManager);
        
        this.menuBar = new GMenuBar();
        this.setJMenuBar(this.menuBar);
        
        this.shapeToolBar = new GShapeToolBar();
        this.add(shapeToolBar, BorderLayout.NORTH);
        
        this.drawingPanel = new GDrawingPanel(800,600);
        this.add(drawingPanel, BorderLayout.CENTER); // 그리기 패널을 CENTER 영역에 추가
        
        //associate
        this.menuBar.associate(this.drawingPanel);
        this.shapeToolBar.associate(this.drawingPanel);
        
        // Initialize components
        initialize();
    }
    
    public void initialize() {
        this.menuBar.initialize();
        this.shapeToolBar.initialize();
        this.drawingPanel.initialize();
    }
}
