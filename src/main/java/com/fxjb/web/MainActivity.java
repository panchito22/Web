package com.fxjb.web;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    List<String> listaTitulos;
    List<String> listaId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv= (ListView)findViewById(R.id.listViewTitulos);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url="https://ghibliapi.herokuapp.com/films/";

        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.length() > 0) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        showTitles(jsonArray);
                    } catch (JSONException jsnExl) {
                        jsnExl.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(stringRequest);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String peliculaSelecion = lv.getItemAtPosition(position).toString();
                for (int i = 0; i < listaId.size(); i++) {
                    for (int j = 0; j < listaTitulos.size(); j++) {
                        if (peliculaSelecion == listaTitulos.get(i)) {
                            String idPelicula = listaId.get(i);
                            sendFilm(idPelicula);
                            break;
                        }
                    }
                }
            }
        });

    }

    public void sendFilm(String id){
        Intent screenFilm = new Intent(this,Pelicula.class);
        screenFilm.putExtra("id",id);
        startActivity(screenFilm);
    }
    public void showTitles(JSONArray jsonArray){
        listaTitulos = new ArrayList<>();
        listaId = new ArrayList<>();
        for (int i=0;i<jsonArray.length();i++){
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String title = jsonObject.getString("title");
                listaId.add(id);
                listaTitulos.add(title);
            }catch (JSONException jsnEx2){
                jsnEx2.printStackTrace();
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,listaTitulos);
        lv.setAdapter(adapter);
    }
}
