package frames;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import shapeTools.GPencil;

public class ThicknessControlPanel extends JPanel {
    private JSlider thicknessSlider;

    public ThicknessControlPanel() {
        this.thicknessSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 1); // 최소값, 최대값, 초기값, 슬라이더 표시값
        this.thicknessSlider.setMajorTickSpacing(1); // 주요 눈금 간격 설정
        this.thicknessSlider.setPaintTicks(true); // 눈금 표시 설정
        this.thicknessSlider.setPaintLabels(true); // 라벨 표시 설정
        this.add(thicknessSlider);

        // 슬라이더의 값이 변경될 때 이벤트 처리
        this.thicknessSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int thickness = thicknessSlider.getValue();
                // GPencil 클래스에 두께 값을 전달
                GPencil.setThickness(thickness);
            }
        });
    }
}

