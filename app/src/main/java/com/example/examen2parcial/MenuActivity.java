package com.example.examen2parcial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    Button btnAlta, btnregistrar, btnbuscar, btnVentas, btnregreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnregreso= findViewById(R.id.btnregreso);
        btnAlta= findViewById(R.id.btnAlta);
        btnregistrar= findViewById(R.id.btnregistrar);
        btnbuscar= findViewById(R.id.btnbuscar);
        btnVentas= findViewById(R.id.btnVentas);



        btnAlta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(MenuActivity.this,DarDeAltaActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this,RegistroActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this,BuscarProductoActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnVentas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(MenuActivity.this,Ventas.class);
                //startActivity(intent);
                //finish();
            }
        });
        btnregreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}