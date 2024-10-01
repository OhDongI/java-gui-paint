package shapeTools;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

public class GLine extends GShape {
	
	public GLine() {
		super(EDrawingStyle.e2PStyle, new Line2D.Float());//두점을 기준으로 직선 그림
	}
	public GLine clone() {
		return new GLine();
	}
	@Override
	public void drag(Graphics graphics) {
		Graphics2D graphics2D = (Graphics2D) graphics;
		graphics2D.setXORMode(graphics2D.getBackground());
		
		Line2D.Float shape = (Line2D.Float)this.shape;
		shape.setLine(x1, y1, ox2, oy2);
		graphics2D.draw(shape);
		shape.setLine(x1, y1, x2, y2);
		graphics2D.draw(shape);
	}
}
