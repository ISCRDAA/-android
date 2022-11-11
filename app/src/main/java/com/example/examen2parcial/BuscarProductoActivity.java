package com.example.examen2parcial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BuscarProductoActivity extends AppCompatActivity {
    Button btntrascons, btnbuscarprod,btnscannbus;
    EditText etcodbus, etnombus, etprebus, etcantbus,etdesbus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_producto);
        btnbuscarprod = findViewById(R.id.btnbuscarprod);
        btnscannbus = findViewById(R.id.btnscannbus);
        btntrascons = findViewById(R.id.btntrascons);
        etcantbus = findViewById(R.id.etcantbus);
        etcodbus = findViewById(R.id.etcodbus);
        etnombus = findViewById(R.id.etnombus);
        etprebus = findViewById(R.id.etprebus);
        etdesbus = findViewById(R.id.etdesbus);

        btntrascons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BuscarProductoActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });




        btnscannbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(BuscarProductoActivity.this);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                intentIntegrator.setCameraId(0);//0 camaratrasera, 1 camarafrontal
                intentIntegrator.setBeepEnabled(true);//para ponerle el sonido a la hora de leer el codigo
                intentIntegrator.setBarcodeImageEnabled(true);//para que aparezaca la rayta rojo y la seccion donde tenemos que centrar nuestro codigo
                intentIntegrator.initiateScan();//para incializarlo


            }
        });

        btnbuscarprod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Buscar("http://192.168.8.117/serviceexamen/buscarprod.php?codigo_barras="+etcodbus.getText().toString());

            }
        });

    }
    protected void onActivityResult(int recuestCode, int resultCode, Intent dato) {
        IntentResult result = IntentIntegrator.parseActivityResult(recuestCode, resultCode, dato);

        if (result!=null){
            if(result.getContents() == null){
                Toast.makeText(this, "Lectura Cancelada", Toast.LENGTH_SHORT).show();//para que salga n mesaje emergente en la palccaciobn
            }else{//imprimir lo que nos del er result
                Toast.makeText(this,result.getContents(), Toast.LENGTH_SHORT).show();
                etcodbus.setText(result.getContents());

            }
        }
        super.onActivityResult(recuestCode, resultCode, dato);


    }

    private void Buscar(String url){
        JsonArrayRequest stringArray= new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i <= response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        etnombus.setText(jsonObject.getString("nombre"));
                        etprebus.setText(jsonObject.getString("precio"));
                        etcantbus.setText(jsonObject.getString("cantidad"));
                        etdesbus.setText(jsonObject.getString("descripcion"));
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error al realizar la onsulta", Toast.LENGTH_LONG).show();
            }

        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringArray);
    }
    }