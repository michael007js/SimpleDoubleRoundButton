package com.sss.simpleDoubleRoundButton;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((SimpleDoubleRoundButton) findViewById(R.id.buttonTextSize)).setOnSimpleDoubleRoundButtonCallBack(new SimpleDoubleRoundButton.OnSimpleDoubleRoundButtonCallBack() {
            @Override
            public void onLeftButtonClick(SimpleDoubleRoundButton view) {
                view.setLeftTextSize(20f);
                view.setRightTextSize(15f);
            }

            @Override
            public void onRightButtonClick(SimpleDoubleRoundButton view) {
                view.setLeftTextSize(15f);
                view.setRightTextSize(20f);
            }
        });
        ((SimpleDoubleRoundButton) findViewById(R.id.buttonSize)).setOnSimpleDoubleRoundButtonCallBack(new SimpleDoubleRoundButton.OnSimpleDoubleRoundButtonCallBack() {
            @Override
            public void onLeftButtonClick(SimpleDoubleRoundButton view) {
                view.setButtonRectPercent(0.7f);
            }

            @Override
            public void onRightButtonClick(SimpleDoubleRoundButton view) {
                view.setButtonRectPercent(0.3f);
            }
        });
        ((SimpleDoubleRoundButton) findViewById(R.id.strokeColor)).setOnSimpleDoubleRoundButtonCallBack(new SimpleDoubleRoundButton.OnSimpleDoubleRoundButtonCallBack() {
            @Override
            public void onLeftButtonClick(SimpleDoubleRoundButton view) {
                view.setStrokeRectColor(Color.parseColor("#00f0f0"));
            }

            @Override
            public void onRightButtonClick(SimpleDoubleRoundButton view) {
                view.setStrokeRectColor(Color.BLACK);
            }
        });
        ((SimpleDoubleRoundButton) findViewById(R.id.strokeWidth)).setOnSimpleDoubleRoundButtonCallBack(new SimpleDoubleRoundButton.OnSimpleDoubleRoundButtonCallBack() {
            @Override
            public void onLeftButtonClick(SimpleDoubleRoundButton view) {
                view.setStrokeRectColor(Color.BLACK);
                view.setStrokeWidth(5);
            }

            @Override
            public void onRightButtonClick(SimpleDoubleRoundButton view) {
                view.setStrokeRectColor(Color.BLACK);
                view.setStrokeWidth(1);
            }
        });
        ((SimpleDoubleRoundButton) findViewById(R.id.radius)).setOnSimpleDoubleRoundButtonCallBack(new SimpleDoubleRoundButton.OnSimpleDoubleRoundButtonCallBack() {
            @Override
            public void onLeftButtonClick(SimpleDoubleRoundButton view) {
                view.setRadius(25);
            }

            @Override
            public void onRightButtonClick(SimpleDoubleRoundButton view) {
                view.setRadius(5);
            }
        });
    }
}
