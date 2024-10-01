package shapeTools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class GRuler extends GShape {

    private static final long serialVersionUID = -6363194106450360803L;
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    private int orientation;

    public GRuler(int orientation) {
        super(EDrawingStyle.eNPStyle, null);
        this.orientation = orientation;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(getColor());
        
        if (this.orientation == HORIZONTAL) {
            g2d.draw(new Line2D.Float(0, 0, 100, 0)); // 가로 자 그리기
        } else {
            g2d.draw(new Line2D.Float(0, 0, 0, 100)); // 세로 자 그리기
        }
    }

    private Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
    public void setCoordinates(int x1, int y1, int x2, int y2) {
        // 좌표 설정 로직
    }

    @Override
    public GShape clone() {
        return new GRuler(this.orientation);
    }

    @Override
    public void drag(Graphics graphics) {
        // 드래그 로직 구현
    }
}

