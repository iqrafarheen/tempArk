package com.example.bilal.arksolutions_jomwork;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Bilal on 6/23/2017.
 */

public class SignUp extends AppCompatActivity {
    TextView toolbarTitle;
    Toolbar toolbar;
    EditText nameEdittext, emailEditText, paswordEditText, cpaswordEditText;
    String name, email, pasword, cpasword;
    Button nextBtn;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        mContext = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar_signup);
        toolbarTitle = (TextView) findViewById(R.id.TitleToolbar);
        nameEdittext = (EditText) findViewById(R.id.name_input_signup);
        emailEditText = (EditText) findViewById(R.id.email_input_signup);
        paswordEditText = (EditText) findViewById(R.id.pasword_input_signup);
        cpaswordEditText = (EditText) findViewById(R.id.re_pasword_input_signup);
        nextBtn = (Button) findViewById(R.id.next_signup);


        toolbarTitle.setText("Sign up");
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameEdittext.getText().toString();
                email = emailEditText.getText().toString();
                pasword = emailEditText.getText().toString();
                pasword = paswordEditText.getText().toString();
                cpasword = cpaswordEditText.getText().toString();
                if (!name.isEmpty() && !email.isEmpty() && !pasword.isEmpty() && !cpasword.isEmpty()) {
                    Intent intent = new Intent(SignUp.this, EditProfileJob.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("name", name);
                    bundle.putString("email", email);
                    bundle.putString("pasword", pasword);
                    bundle.putString("cpasword", cpasword);
                    intent.putExtra("signup_bundle", bundle);
                    startActivity(intent);
                } else {
                    Toast.makeText(mContext, "Please fill  all the fields!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
