package com.example.bilal.arksolutions_jomwork;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
Toolbar toolbar;
    TextView toolbarTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        toolbarTitle=(TextView)findViewById(R.id.TitleToolbar);


    }
}
