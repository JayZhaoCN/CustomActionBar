package com.jayzhao.customactionbar.another_world;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.jayzhao.customactionbar.R;
import com.jayzhao.customactionbar.another_world.Widget.WheelMenu;

public class WheelMainActivity extends Activity {

    private WheelMenu wheelMenu;
    private TextView selectedPositionText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wheel_main);

        wheelMenu = (WheelMenu) findViewById(R.id.wheelMenu);

        wheelMenu.setDivCount(12);
        wheelMenu.setWheelImage(R.drawable.wheel);

        selectedPositionText = (TextView) findViewById(R.id.selected_position_text);
        selectedPositionText.setText("selected: " + (wheelMenu.getSelectedPosition() + 1));

        wheelMenu.setWheelChangeListener(new WheelMenu.WheelChangeListener() {
            @Override
            public void onSelectionChange(int selectedPosition) {
                selectedPositionText.setText("selected: " + (selectedPosition + 1));
            }
        });

    }
}
