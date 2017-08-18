package com.example.bilal.arksolutions_jomwork;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class Notifications extends AppCompatActivity {
TextView title;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        toolbar=(Toolbar)findViewById(R.id.notif_toolbar);
        title=(TextView)toolbar.findViewById(R.id.TitleToolbar);
        title.setText("Notifications");
    }
}
