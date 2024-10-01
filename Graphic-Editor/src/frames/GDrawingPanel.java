package frames;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Vector;
import javax.swing.JPanel;
import shapeTools.GShape;
import shapeTools.GShape.EAnchors;
import shapeTools.GShape.EDrawingStyle;

public class GDrawingPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private enum EDrawingState {
        eIdle,
        e2PState,
        eNPState,
        eTransformation
    }
    
    private EDrawingState eDrawingState;
    private Vector<GShape> shapes;
    private GShape shapeTool;
    private GShape currentShape;
    private BufferedImage bufferedImage;
    private Graphics2D bufferedGraphics;

    public GDrawingPanel(int width, int height) {
        this.setBackground(Color.white);
        this.eDrawingState = EDrawingState.eIdle;
        MouseEventhandler mouseEventhandler = new MouseEventhandler();
        this.addMouseListener(mouseEventhandler);
        this.addMouseMotionListener(mouseEventhandler);
        this.shapes = new Vector<GShape>();

        // 너비와 높이를 인수로 받아 BufferedImage를 초기화합니다.
        this.bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        this.bufferedGraphics = this.bufferedImage.createGraphics();
        this.bufferedGraphics.setColor(Color.WHITE);
        this.bufferedGraphics.fillRect(0, 0, width, height);
    }


    public void initialize() {
    }

    public void setShapeTool(GShape shapeTool) {
        this.shapeTool = shapeTool;
    }

    public Vector<GShape> getShapes() {
        return this.shapes;
    }

    public void setShapes(Object object) {
        this.shapes = (Vector<GShape>) object;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bufferedImage, 0, 0, null);
        for (GShape shape : shapes) {
            shape.draw(g);
        }
    }

    private void startDrawing(int x, int y) {
        currentShape = shapeTool.clone();
        currentShape.setOrigin(x, y);
    }

    private void keepDrawing(int x, int y) {
        currentShape.movePoint(x, y);
        bufferedGraphics.clearRect(0, 0, getWidth(), getHeight());
        for (GShape shape : shapes) {
            shape.draw(bufferedGraphics);
        }
        currentShape.drag(bufferedGraphics);
        repaint();
    }

    private void continueDrawing(int x, int y) {
        currentShape.addPoint(x, y);
    }

    private void stopDrawing(int x, int y) {
        shapes.add(currentShape);
        currentShape.setSelected(getGraphics());
        repaint();
    }

    private GShape onShape(int x, int y) {
        for (GShape shape : this.shapes) {
            boolean isShape = shape.onShape(x, y);
            if (isShape) {
                return shape;
            }
        }
        return null;
    }

    private void changeCursor(int x, int y) {
        GShape shape = this.onShape(x, y);
        if (shape == null) {
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } else {
            this.setCursor(shape.getCursor());
        }
    }

    private class MouseEventhandler implements MouseListener, MouseMotionListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 1) {
                mouse1Clicked(e);
            } else if (e.getClickCount() == 2) {
                mouse2Clicked(e);
            }
        }

        private void mouse1Clicked(MouseEvent e) {
            if (eDrawingState == EDrawingState.eIdle) {
                if (shapeTool.getEDrawingStyle() == EDrawingStyle.eNPStyle) {
                    startDrawing(e.getX(), e.getY());
                    eDrawingState = EDrawingState.eNPState;
                }
            } else if (eDrawingState == EDrawingState.eNPState) {
                continueDrawing(e.getX(), e.getY());
                repaint();
            }
        }

        private void mouse2Clicked(MouseEvent e) {
            if (eDrawingState == EDrawingState.eNPState) {
                stopDrawing(e.getX(), e.getY());
                eDrawingState = EDrawingState.eIdle;
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            if (eDrawingState == EDrawingState.eIdle) {
                changeCursor(e.getX(), e.getY());
            } else if (eDrawingState == EDrawingState.eNPState) {
                keepDrawing(e.getX(), e.getY());
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (eDrawingState == EDrawingState.eIdle) {
                currentShape = onShape(e.getX(), e.getY());
                if (currentShape == null) {
                    if (shapeTool.getEDrawingStyle() == EDrawingStyle.e2PStyle) {
                        startDrawing(e.getX(), e.getY());
                        eDrawingState = EDrawingState.e2PState;
                    }
                } else {
                    if (currentShape.getSelectedAnchor() == EAnchors.eMM) {
                        currentShape.startMove(getGraphics(), e.getX(), e.getY());
                    } else if (currentShape.getSelectedAnchor() == EAnchors.eRR) {
                        // Add rotation handling if necessary
                    } else {
                        currentShape.startResize(getGraphics(), e.getX(), e.getY());
                    }
                    eDrawingState = EDrawingState.eTransformation;
                }
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (eDrawingState == EDrawingState.e2PState) {
                keepDrawing(e.getX(), e.getY());
            } else if (eDrawingState == EDrawingState.eTransformation) {
                if (currentShape.getSelectedAnchor() == EAnchors.eMM) {
                    currentShape.keepMove(getGraphics(), e.getX(), e.getY());
                } else if (currentShape.getSelectedAnchor() == EAnchors.eRR) {
                    // Add rotation handling if necessary
                } else {
                    currentShape.keepResize(getGraphics(), e.getX(), e.getY());
                }
                repaint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (eDrawingState == EDrawingState.e2PState) {
                stopDrawing(e.getX(), e.getY());
                eDrawingState = EDrawingState.eIdle;
            } else if (eDrawingState == EDrawingState.eTransformation) {
                if (currentShape.getSelectedAnchor() == EAnchors.eMM) {
                    currentShape.stopMove(getGraphics(), e.getX(), e.getY());
                } else if (currentShape.getSelectedAnchor() == EAnchors.eRR) {
                    // Add rotation handling if necessary
                } else {
                    currentShape.stopResize(getGraphics(), e.getX(), e.getY());
                }
                eDrawingState = EDrawingState.eIdle;
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}
