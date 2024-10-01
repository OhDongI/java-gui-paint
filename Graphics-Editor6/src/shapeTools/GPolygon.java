package shapeTools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class GPolygon extends GShape {

    public GPolygon() {
        super(EDrawingStyle.eNPStyle, new Polygon());
    }

    public GPolygon clone() {
        return new GPolygon();
    }

    @Override
    public void drag(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setXORMode(graphics2D.getBackground());
        Polygon polygon = (Polygon) this.shape;

        // 기존의 다각형을 임시로 그립니다.
        for (int i = 0; i < polygon.npoints - 1; i++) {
            graphics.drawLine(polygon.xpoints[i], polygon.ypoints[i], polygon.xpoints[i + 1], polygon.ypoints[i + 1]);
        }

        // 새로운 점을 추가하여 다각형을 그립니다.
        graphics.drawLine(polygon.xpoints[polygon.npoints - 1], polygon.ypoints[polygon.npoints - 1], x2, y2);
    }


    @Override
    public void setOrigin(int x, int y) {
        this.shape = new Polygon();
        Polygon polygon = (Polygon) this.shape;
        polygon.addPoint(x, y);
        polygon.addPoint(x, y);
    }

    @Override
    public void addPoint(int x, int y) {
        Polygon polygon = (Polygon) this.shape;
        polygon.addPoint(x, y);
    }

	@Override
	public void setColor(Color selectedColor) {
		// TODO Auto-generated method stub
		
	}
}



  