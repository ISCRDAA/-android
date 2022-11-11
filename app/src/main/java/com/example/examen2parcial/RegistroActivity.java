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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {

    Button btnscann, btnatrasact, btnguardar;
    EditText etcodigo_baras, etfecha, etnumfact, etcantidad, etmontofact, etprecio;
    String cant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        btnscann = findViewById(R.id.btnscann);
        btnatrasact = findViewById(R.id.btnatrasact);
        btnguardar = findViewById(R.id.btnguardar);
        etcodigo_baras = findViewById(R.id.etcodigo_baras);
        etfecha = findViewById(R.id.etfecha);
        etnumfact = findViewById(R.id.etnumfact);
        etcantidad = findViewById(R.id.etcantidad);
        etmontofact = findViewById(R.id.etmontofact);
        etprecio = findViewById(R.id.etprecio);

        btnscann.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(RegistroActivity.this);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                intentIntegrator.setCameraId(0);//0 camaratrasera, 1 camarafrontal
                intentIntegrator.setBeepEnabled(true);//para ponerle el sonido a la hora de leer el codigo
                intentIntegrator.setBarcodeImageEnabled(true);//para que aparezaca la rayta rojo y la seccion donde tenemos que centrar nuestro codigo
                intentIntegrator.initiateScan();//para incializarlo

            }
        });
        btnatrasact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistroActivity.this, Menu.class);
                startActivity(intent);
                finish();
            }
        });
        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                regitrar("http://192.168.0.91:8888/Login/regprodfact.php");
                Buscar("http://192.168.0.91:8888/Login/buscarcant.php?codigo_barras="+etcodigo_baras.getText().toString());
                actualizar("http://192.168.0.91:8888/Login/actualizar.php?codigo_barras=" + etcodigo_baras.getText().toString()+"&cantidad="+etcantidad.getText().toString());




            }
        });

    }

    private void regitrar(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "operación realizada", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("codigo_barras", etcodigo_baras.getText().toString());
                parametros.put("fecha", etfecha.getText().toString());
                parametros.put("num_fact", etnumfact.getText().toString());
                parametros.put("cantidad", etcantidad.toString());
                parametros.put("monto_fact", etmontofact.getText().toString());
                parametros.put("precio", etprecio.getText().toString());
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    protected void onActivityResult(int recuestCode, int resultCode, Intent dato) {
        IntentResult result = IntentIntegrator.parseActivityResult(recuestCode, resultCode, dato);

        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Lectura Cancelada", Toast.LENGTH_SHORT).show();//para que salga n mesaje emergente en la palccaciobn
            } else {//imprimir lo que nos del er result
                Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
                etcodigo_baras.setText(result.getContents());

            }
        }
        super.onActivityResult(recuestCode, resultCode, dato);


    }

    private void actualizar(String url){
        StringRequest stringRequest=new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "operción realiizada", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void Buscar(String url){
        JsonArrayRequest stringArray= new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i <= response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        cant = jsonObject.getString("nombre");
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
}