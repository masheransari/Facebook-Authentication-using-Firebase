package com.example.asheransari.auth_firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

public class ab extends AppCompatActivity {

    Button out;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ab);
        out = (Button) findViewById(R.id.signout);
        mAuth = FirebaseAuth.getInstance();

        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginManager.getInstance().logOut();
                mAuth.signOut();

                Intent i = new Intent(ab.this, MainActivity.class);
                startActivity(i);

                finish();
            }
        });
    }
}
