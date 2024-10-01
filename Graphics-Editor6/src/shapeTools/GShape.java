package shapeTools;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.Serializable;

public abstract class GShape implements Serializable {
    private static final long serialVersionUID = 1L;

    // 도형의 색상 속성 추가
    protected Color color = Color.BLACK; // 기본적으로 검은색으로 설정

    // Setter 및 Getter 추가
    public Color getColor() {
        return color;
    }

    protected Color fillColor; // 도형의 채우기 색
    
    	 

    
    public enum EDrawingStyle {
        e2PStyle, // 두점
        eNPStyle // 다각형
    }

    private EDrawingStyle eDrawingStyle;
    public EDrawingStyle getEDrawingStyle() {
        return this.eDrawingStyle;
    }

    // int x[], int y[]
    protected int x1, y1, x2, y2, ox2, oy2;
    protected Shape shape;

    public enum EAnchors {
        eRR(new Cursor(Cursor.HAND_CURSOR)),
        eNN(new Cursor(Cursor.N_RESIZE_CURSOR)),
        eSS(new Cursor(Cursor.S_RESIZE_CURSOR)),
        eEE(new Cursor(Cursor.E_RESIZE_CURSOR)),
        eWW(new Cursor(Cursor.W_RESIZE_CURSOR)),
        eNE(new Cursor(Cursor.NE_RESIZE_CURSOR)),
        eSE(new Cursor(Cursor.SE_RESIZE_CURSOR)),
        eNW(new Cursor(Cursor.NW_RESIZE_CURSOR)),
        eSW(new Cursor(Cursor.SW_RESIZE_CURSOR)),
        eMM(new Cursor(Cursor.CROSSHAIR_CURSOR));
        private Cursor cursor;

        private EAnchors(Cursor cursor) {
            this.cursor = cursor;
        }

        public Cursor getCursor() {
            return this.cursor;
        }
    }
    public void color(Color c) {
		color = c;
		float py = 0;
		float px = 0;
		System.out.print(Color.getHSBColor(x1, py, px));
		System.out.print("ffff");
	}


    private EAnchors eSelectedAnchor;
    protected Ellipse2D.Float[] anchors;

    private double sx, sy;
    private double dx, dy;

    // setters and getters
    public void setSelected(Graphics graphics) {
        this.drawAnchors(graphics);
    }

    public void clearSelected() {
        this.anchors = null;
    }

    public EAnchors getSelectedAnchor() {
        return this.eSelectedAnchor;
    }

    public Cursor getCursor() {
        return this.eSelectedAnchor.getCursor();
    }

    public GShape(EDrawingStyle eDrawingStyle, Shape shape) {
        this.eDrawingStyle = eDrawingStyle;
        this.shape = shape;

        this.anchors = null;
        this.eSelectedAnchor = null;

        this.x1 = 0;
        this.y1 = 0;
        this.x2 = 0;
        this.y2 = 0;
        this.ox2 = 0;
        this.oy2 = 0;
    }

    public abstract GShape clone();

    public void draw(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.draw(shape);
        drawAnchors(graphics2D); // 앵커를 그리는 메서드를 추가하여 호출
    }

    private void drawAnchors(Graphics graphics) {
        if (this.shape == null) {
            return; // null 체크 후 메서드 종료
        }

        Graphics2D graphics2D = (Graphics2D) graphics;
        
        Rectangle rectangle = this.shape.getBounds();
        int x = rectangle.x;
        int y = rectangle.y;
        int w = rectangle.width;
        int h = rectangle.height;
        int ANCHOR_WIDTH = 10;
        int ANCHOR_HEIGHT = 10;

        this.anchors = new Ellipse2D.Float[EAnchors.values().length - 1];
        this.anchors[EAnchors.eRR.ordinal()] = new Ellipse2D.Float(x + w / 2, y - 30, ANCHOR_WIDTH, ANCHOR_HEIGHT);
        this.anchors[EAnchors.eNN.ordinal()] = new Ellipse2D.Float(x + w / 2, y, ANCHOR_WIDTH, ANCHOR_HEIGHT);
        this.anchors[EAnchors.eSS.ordinal()] = new Ellipse2D.Float(x + w / 2, y + h, ANCHOR_WIDTH, ANCHOR_HEIGHT);
        this.anchors[EAnchors.eEE.ordinal()] = new Ellipse2D.Float(x + w, y + h / 2, ANCHOR_WIDTH, ANCHOR_HEIGHT);
        this.anchors[EAnchors.eWW.ordinal()] = new Ellipse2D.Float(x, y + h / 2, ANCHOR_WIDTH, ANCHOR_HEIGHT);
        this.anchors[EAnchors.eNW.ordinal()] = new Ellipse2D.Float(x, y, ANCHOR_WIDTH, ANCHOR_HEIGHT);
        this.anchors[EAnchors.eNE.ordinal()] = new Ellipse2D.Float(x + w, y, ANCHOR_WIDTH, ANCHOR_HEIGHT);
        this.anchors[EAnchors.eSW.ordinal()] = new Ellipse2D.Float(x, y + h, ANCHOR_WIDTH, ANCHOR_HEIGHT);
        this.anchors[EAnchors.eSE.ordinal()] = new Ellipse2D.Float(x + w, y + h, ANCHOR_WIDTH, ANCHOR_HEIGHT);

        for (Ellipse2D anchor : this.anchors) {
            graphics2D.draw(anchor);
        }
    }

    public void setOrigin(int x1, int y1) {
        this.x1 = x1;
        this.y1 = y1;

        this.ox2 = x1;
        this.oy2 = y1;
        this.x2 = x1;
        this.y2 = y1;
    }

    public void movePoint(int x2, int y2) {
        this.ox2 = this.x2;
        this.oy2 = this.y2;
        this.x2 = x2;
        this.y2 = y2;
    }

    public abstract void drag(Graphics graphics);

    public void addPoint(int x2, int y2) {

        this.x2 = x2;
        this.y2 = y2;
    }

    public boolean onShape(int x, int y) {
        this.eSelectedAnchor = null;
        if (this.anchors != null) {
            for (int i = 0; i < EAnchors.values().length - 1; i++) {
                if (anchors[i].contains(x, y)) {
                    this.eSelectedAnchor = EAnchors.values()[i];
                    return true;
                }
            }
        }

        boolean isOnShape = this.shape.contains(x, y);
        if (isOnShape) {
            this.eSelectedAnchor = EAnchors.eMM;
        }
        return isOnShape;
    }

    public void startMove(Graphics graphics, int x, int y) {
        this.ox2 = x;
        this.oy2 = y;
        this.x2 = x;
        this.y2 = y;
    }

    public void keepMove(Graphics graphics, int x, int y) {

        this.ox2 = this.x2;
        this.oy2 = this.y2;
        this.x2 = x;
        this.y2 = y;

        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setXORMode(graphics2D.getBackground());
        graphics2D.draw(this.shape);

        int dx = x2 - ox2;
        int dy = y2 - oy2;
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.setToTranslation(x2 - ox2, y2 - oy2);
        this.shape = affineTransform.createTransformedShape(this.shape);

        graphics2D.draw(this.shape);

    }

    public void stopMove(Graphics graphics, int x, int y) {

    }

    public void startResize(Graphics graphics, int x, int y) {
        this.ox2 = x;
        this.oy2 = y;
        this.x2 = x;
        this.y2 = y;
    }

    private Point2D getResizeFactor(int x, int y) {
        sx = 1.0;
        sy = 1.0;
        dx = 0.0;
        dy = 0.0;

        double cx = 0;
        double cy = this.anchors[this.eSelectedAnchor.ordinal()].getCenterY();
        double w = this.shape.getBounds().getWidth();
        double h = this.shape.getBounds().getHeight();

        switch (this.eSelectedAnchor) {
            case eEE:
                sx = (w + x2 - ox2) / w;
                cx = this.anchors[EAnchors.eWW.ordinal()].getCenterX();
                dx = cx - cx * sx;
                break;

            case eWW:
                sx = (w + ox2 - x2) / w;
                cx = this.anchors[EAnchors.eEE.ordinal()].getCenterX();
                dx = cx * sx - cx;
                break;
            case eSS:
                sy = (h + y2 - oy2) / h;
                break;
            case eNN:
                sy = (h + oy2 - y2) / h;
                break;
            default:
                break;
        }
        return new Point2D.Double(sx, sy);
    }

    public void keepResize(Graphics graphics, int x, int y) {
        this.ox2 = this.x2;
        this.oy2 = this.y2;
        this.x2 = x;
        this.y2 = y;

        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setXORMode(graphics2D.getBackground());
        graphics2D.draw(this.shape);

        Point2D resizeFactor = getResizeFactor(x, y);
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.setToScale(resizeFactor.getX(), resizeFactor.getY());
        affineTransform.translate(dx, dy);
        this.shape = affineTransform.createTransformedShape(this.shape);

        graphics2D.draw(this.shape);
    }

    public void stopResize(Graphics graphics, int x, int y) {

    }

    public void setCoordinates(int x1, int y1, int x2, int y2) {
        // TODO Auto-generated method stub

    }

	public abstract void setColor(Color selectedColor);

}