package com.example.examen2parcial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.HashMap;
import java.util.Map;

public class DarDeAltaActivity extends AppCompatActivity {
    Button btnagregarprod, btnscann, btnatrasreg;
    EditText etcod, etnom, etpre,  etdes;
    Integer etcant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dar_de_alta);
        btnagregarprod = findViewById(R.id.btnagregarprod);
        btnscann = findViewById(R.id.btnscann);
        btnatrasreg = findViewById(R.id.btnatrasreg);
        etcod = findViewById(R.id.etcod);
        etnom = findViewById(R.id.etnom);
        etcant = 0;
        etpre = findViewById(R.id.etpre);
        etdes = findViewById(R.id.etdes);

        btnagregarprod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regitrar("http://192.168.1.71/Login/insertarprod.php");

            }
        });


        btnscann.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(DarDeAltaActivity.this);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                intentIntegrator.setCameraId(0);//0 camaratrasera, 1 camarafrontal
                intentIntegrator.setBeepEnabled(true);//para ponerle el sonido a la hora de leer el codigo
                intentIntegrator.setBarcodeImageEnabled(true);//para que aparezaca la rayta rojo y la seccion donde tenemos que centrar nuestro codigo
                intentIntegrator.initiateScan();//para incializarlo
            }
        });

        btnatrasreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DarDeAltaActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void  regitrar(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "operación realizada", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("codigo_barras", etcod.getText().toString());
                parametros.put("nombre", etnom.getText().toString());
                parametros.put("precio",etpre.getText().toString());
                parametros.put("cantidad",etcant.toString());
                parametros.put("descripcion",etdes.getText().toString());
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    protected void onActivityResult(int recuestCode, int resultCode, Intent dato) {
        IntentResult result = IntentIntegrator.parseActivityResult(recuestCode, resultCode, dato);

        if (result!=null){
            if(result.getContents() == null){
                Toast.makeText(this, "Lectura Cancelada", Toast.LENGTH_SHORT).show();//para que salga n mesaje emergente en la palccaciobn
            }else{//imprimir lo que nos del er result
                Toast.makeText(this,result.getContents(), Toast.LENGTH_SHORT).show();
                etcod.setText(result.getContents());

            }
        }
        super.onActivityResult(recuestCode, resultCode, dato);
    }
    }
