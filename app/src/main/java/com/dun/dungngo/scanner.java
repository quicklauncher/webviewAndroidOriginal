package com.dun.dungngo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class scanner extends AppCompatActivity {
    public static TextView resulttextview;
    Button scanbutton, buttontoast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        resulttextview = findViewById(R.id.barcodetextview);

        scanbutton = findViewById(R.id.buttonscan);
        buttontoast = findViewById(R.id.buttontoast);

        scanbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), startscaner.class));
            }
        });

        buttontoast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(scanner.this, resulttextview.getText(), Toast.LENGTH_SHORT).show();
            }
        });




    }
}