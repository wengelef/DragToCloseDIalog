package com.wengelef.dragtoclosedialogsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_normal_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TestDialog().show(getSupportFragmentManager(), "TestDialog");
            }
        });
    }
}
