package com.example.emuzika;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private TextView izvajalci;
    private String url = "https://emuzika-eccjbjc2b2hyg7by.italynorth-01.azurewebsites.net/api/v1/izvajalec";
    private TextView pesmi;
    private String urlp = "https://emuzika-eccjbjc2b2hyg7by.italynorth-01.azurewebsites.net/api/v1/Pesmi";

    private TextView albumi;
    private String urla = "https://emuzika-eccjbjc2b2hyg7by.italynorth-01.azurewebsites.net/api/v1/Album";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        izvajalci = (TextView) findViewById(R.id.izvajalci);
        pesmi = (TextView) findViewById(R.id.pesmi);
        albumi = (TextView) findViewById(R.id.albumi);


    }

    public void prikaziIzvajalce(View view) {
        pesmi.setText("");
        albumi.setText("");
        if(view != null) {
            JsonArrayRequest request = new JsonArrayRequest(url,jsonArrayListener, errorListener);
            requestQueue.add(request);
        }
    }


    private Response.Listener<JSONArray> jsonArrayListener = new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            ArrayList<String> data = new ArrayList<>();

            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject object = response.getJSONObject(i);
                    String ime = object.getString("ime");
                    String opis = object.getString("opis");
                    int poslusalci = object.getInt("poslusalci");

                    data.add(ime + "\n" + opis + "\n" + "Število poslušalcev: " + poslusalci);

                } catch (JSONException e) {
                    e.printStackTrace();
                    return;

                }
            }
            izvajalci.setText("");

            for (String row: data) {
                String currentText = izvajalci.getText().toString();
                izvajalci.setText(currentText + "\n\n" + row);
            }

        }
    };
    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("REST error", error.getMessage());
        }
    };

    public void prikaziPesmi(View view) {
        izvajalci.setText("");
        albumi.setText("");
        if (view != null) {
            JsonArrayRequest requestp = new JsonArrayRequest(urlp, jsonArrayListenerp, errorListenerp);
            requestQueue.add(requestp);
        }
    }
    private Response.Listener<JSONArray> jsonArrayListenerp = new Response.Listener<JSONArray>() {

        @Override
        public void onResponse(JSONArray response) {
            ArrayList<String> data = new ArrayList<>();

            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject object = response.getJSONObject(i);
                    String naslov = object.getString("naslov");
                    int dolzina = object.getInt("dolzina");
                    int ocena = object.getInt("ocena");

                    data.add(naslov + "\n" + "Dolžina:" + dolzina + "\n" + "Ocena: " + ocena);

                } catch (JSONException e) {
                    e.printStackTrace();
                    return;

                }
            }
            pesmi.setText("");

            for (String row: data) {
                String currentText = pesmi.getText().toString();
                pesmi.setText(currentText + "\n\n" + row);
            }

        }
    };
    private Response.ErrorListener errorListenerp = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("REST error", error.getMessage());
        }
    };

    public void prikaziAlbume(View view) {
        izvajalci.setText("");
        pesmi.setText("");
        if (view != null) {
            JsonArrayRequest requesta = new JsonArrayRequest(urla, jsonArrayListenera, errorListenera);
            requestQueue.add(requesta);
        }
    }
    private Response.Listener<JSONArray> jsonArrayListenera = new Response.Listener<JSONArray>() {

        @Override
        public void onResponse(JSONArray response) {
            ArrayList<String> data = new ArrayList<>();

            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject object = response.getJSONObject(i);
                    String ime = object.getString("ime");
                    String opis = object.getString("opis");
                    String datumIzdaje = object.getString("datumIzdaje");


                    data.add(ime + "\n" + "Opis:" + opis + "\n" + "Datum izdaje: " + datumIzdaje);

                } catch (JSONException e) {
                    e.printStackTrace();
                    return;

                }
            }
            albumi.setText("");

            for (String row: data) {
                String currentText = albumi.getText().toString();
                albumi.setText(currentText + "\n\n" + row);
            }

        }
    };
    private Response.ErrorListener errorListenera = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("REST error", error.getMessage());
        }
    };

}