package frames;

import javax.swing.JColorChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import menus.GFileMenu;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GMenuBar extends JMenuBar {
    private static final long serialVersionUID = 1L;
    private GFileMenu fileMenu; // GFileMenu 인스턴스 생성
    private GDrawingPanel drawingPanel;

    public GMenuBar() {
        // 파일 메뉴 생성
        this.fileMenu = new GFileMenu("file");
        this.add(this.fileMenu);
        
        // "Reset" 메뉴 아이템 추가
        JMenuItem itemNew = new JMenuItem("Reset");
        this.add(itemNew);
        itemNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetDrawingPanel();
            }
        });

        // "Color" 메뉴 아이템 추가
        JMenuItem colorMenuItem = new JMenuItem("Color");
        this.add(colorMenuItem);
        colorMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectColor();
            }
        });

        // "Help" 메뉴 아이템 추가
        JMenuItem helpMenuItem = new JMenuItem("Help");
        this.add(helpMenuItem);
        helpMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showHelp();
            }
        });
    }

    public void associate(GDrawingPanel drawingPanel) {
        this.drawingPanel = drawingPanel;
        this.fileMenu.associate(drawingPanel); // GDrawingPanel과 메뉴 바 연결
    }

    public void initialize() {
    }

    // 그림 패널 초기화 메서드
    private void resetDrawingPanel() {
        if (drawingPanel != null) {
            drawingPanel.clear();
        }
    }

    // 색상 선택 메서드
    private void selectColor() {
        Color selectedColor = JColorChooser.showDialog(null, "Select Color", Color.BLACK);
        if (selectedColor != null) {
            if (drawingPanel != null) {
                drawingPanel.setSelectedColor(selectedColor);
            }
        }
    }

    // 도움말 보여주는 메서드
    private void showHelp() {
        JOptionPane.showMessageDialog(null, 
                "이 프로그램은 그림판 프로그램입니다.\n" +
                "Rectangle - 사각형\n" +
                "Oval - 타원\n" +
                "Line - 직선\n" +
                "Polygon - 다각형\n" +
                "Pencil - 이용자가 자유롭게 그리는 직선\n" +
                "각각의 버튼을 누르면 그릴 수 있습니다.", 
                "도움말", 
                JOptionPane.INFORMATION_MESSAGE);
    }
}

