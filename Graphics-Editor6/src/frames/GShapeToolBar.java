package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import global.Constants.EShapeButtons;

public class GShapeToolBar extends JToolBar {
    private static final long serialVersionUID = 1L;
    private GDrawingPanel drawingPanel;

    public GShapeToolBar() {
        ButtonGroup buttonGroup = new ButtonGroup();
        ShapeActionHandler shapeActionHandler = new ShapeActionHandler();

        for (EShapeButtons eShapeButtons : EShapeButtons.values()) {
            JRadioButton button = new JRadioButton(eShapeButtons.getText());
            button.addActionListener(shapeActionHandler);
            button.setActionCommand(eShapeButtons.name());
            add(button);
            buttonGroup.add(button);
        }
    }

    public void initialize() {
        JRadioButton defaultButton = (JRadioButton) (this.getComponent(EShapeButtons.eRectangle.ordinal()));
        defaultButton.doClick();
        associate(drawingPanel); // 추가: drawingPanel과 연결
    }

    public void associate(GDrawingPanel drawingPanel) {
        this.drawingPanel = drawingPanel;
    }

    private void setShapeTool(EShapeButtons eshapeButton) {
        drawingPanel.setShapeTool(eshapeButton.getShapeTool());
    }

    private class ShapeActionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            EShapeButtons eshapeButton = EShapeButtons.valueOf(e.getActionCommand());
            setShapeTool(eshapeButton);
        }
    }
}
