package shapeTools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

public class GPencil extends GShape {
    private static final long serialVersionUID = 1L;
    private Path2D path;
    private static int thickness = 1; // 두께 변수 추가 및 초기화

    public GPencil() {
        super(EDrawingStyle.eNPStyle, null);
        this.path = new Path2D.Double();
    }

    @Override
    public GShape clone() {
        return new GPencil();
    }

    @Override
    public void draw(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setStroke(new BasicStroke(thickness)); // 두께 설정
        graphics2D.draw(this.path);
    }

    @Override
    public void drag(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setStroke(new BasicStroke(thickness)); // 두께 설정
        graphics2D.draw(this.path);
    }

    @Override
    public void movePoint(int x2, int y2) {
        this.path.lineTo(x2, y2);
    }

    public void addPoint(int x2, int y2) {
        this.path.moveTo(x2, y2);
    }

    @Override
    public void setOrigin(int x, int y) {
        super.setOrigin(x, y);
        this.path.moveTo(x, y);
    }

    @Override
    public void setColor(Color selectedColor) {
        // TODO Auto-generated method stub
    }
    
    // 두께 설정 메서드 추가
    public static void setThickness(int value) {
        thickness = value;
    }
}




