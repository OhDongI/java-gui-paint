package frames;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;
import global.Constants;

public class GMainFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private GMenuBar menuBar;
    private GShapeToolBar shapeToolBar;
    private GDrawingPanel drawingPanel;
    private ThicknessControlPanel thicknessControlPanel; // 두께 조절기 패널 추가
    
    public GMainFrame() {
        // set attributes
        this.setSize(Constants.GMainFrame.WIDTH, Constants.GMainFrame.HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // set icon image
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image img = toolkit.getImage("image/symbol.png"); // 이미지 가져오기
        this.setIconImage(img); // 이미지 아이콘 세팅
        
        setTitle("그림판"); // 제목
        
        // set layout
        this.setLayout(new BorderLayout());
        
        // create components
        this.menuBar = new GMenuBar();
        this.setJMenuBar(this.menuBar);
        
        this.shapeToolBar = new GShapeToolBar();
        this.add(shapeToolBar, BorderLayout.NORTH);
        
        this.drawingPanel = new GDrawingPanel(this.getWidth(), this.getHeight());
        this.add(drawingPanel, BorderLayout.CENTER); // 그리기 패널을 CENTER 영역에 추가
        
        // 추가: 두께 조절기 패널 생성 및 추가
        this.thicknessControlPanel = new ThicknessControlPanel();
        this.add(thicknessControlPanel, BorderLayout.SOUTH);
        
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
