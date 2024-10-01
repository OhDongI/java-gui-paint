package frames;
import javax.swing.JMenuBar;

import menus.GFileMenu;

public class GMenuBar extends JMenuBar {//메뉴바 기본 기능 상속
	private static final long serialVersionUID = 1L;
	
	public GFileMenu fileMenu;//인스턴스 생성
	private GDrawingPanel drawingPanel;
	public GMenuBar() {
		
		this.fileMenu = new GFileMenu("file");
		this.add(this.fileMenu);
	}
	public void associate(GDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
		
		this.fileMenu.associate(drawingPanel);//GDrawingPanel과 메뉴 바를 연결
	}
	public void initialize() {
	}

}