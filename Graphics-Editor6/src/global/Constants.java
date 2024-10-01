package global;

import shapeTools.GLine;
import shapeTools.GOval;
import shapeTools.GPencil;
import shapeTools.GPolygon;
import shapeTools.GRectangle;
import shapeTools.GShape;

public class Constants {
    public enum EShapeButtons {
        eRectangle("rectangle", new GRectangle()),
        eOval("oval", new GOval()),
        eLine("line", new GLine()),
        ePolygon("polygon", new GPolygon()),
        ePencil("pencil", new GPencil());
 



        private String text;
        private GShape shapeTool;

        private EShapeButtons(String text, GShape shapeTool) {
            this.text = text;
            this.shapeTool = shapeTool;
        }

        public String getText() {
            return this.text;
        }

        public GShape getShapeTool() {
            return this.shapeTool;
        }
    }

    public final static int NUM_POINTS = 20;

    public static class GMainFrame {
        public final static int WIDTH = 400;
        public final static int HEIGHT = 600;
    }

    public static class GMenuBar {
        public enum EMenu {
            eFile("파일"),
            eEdit("편집");

            private String text;

            private EMenu(String text) {
                this.text = text;
            }

            public String getText() {
                return this.text;
            }
        }
    }
}
