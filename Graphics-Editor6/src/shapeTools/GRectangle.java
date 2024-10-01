package shapeTools;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.RectangularShape;

public class GRectangle extends GShape {
	
	
	public GRectangle() {
		super(EDrawingStyle.e2PStyle, new Rectangle());
		
	}
	public GRectangle clone() {
		return new GRectangle();
	}
	
	@Override
	public void drag(Graphics graphics) {
		Graphics2D graphics2D = (Graphics2D) graphics;
//		graphics2D.setXORMode(graphics2D.getBackground());
//		
		RectangularShape shape = (RectangularShape)this.shape;
//		shape.setFrame(x1, y1, ox2-x1, oy2-y1);
//		graphics2D.draw(shape);
		shape.setFrame(x1, y1, x2-x1, y2-y1);
		graphics2D.draw(shape); // 패턴 = 반복되는 구조
		
	}
	@Override
	public void setColor(Color selectedColor) {
		
		// TODO Auto-generated method stub
		
	}
	
	
}
	

