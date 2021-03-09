package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {  //WelcomeActivity page to show the welcome and tends to redirect to homepage from a button (another activity lifecycle)
    Button buttonWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        buttonWelcome = findViewById(R.id.buttonwelcome);
        buttonWelcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(WelcomeActivity.this , MainActivity.class);
                startActivity(intent);


            }
        });
    }
}