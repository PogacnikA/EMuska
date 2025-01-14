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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*EdgeToEdge.enable(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        }); */
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        izvajalci = (TextView) findViewById(R.id.izvajalci);


    }

    public void prikaziIzvajalce(View view) {
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

}