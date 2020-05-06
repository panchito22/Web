package com.fxjb.web;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Pelicula extends AppCompatActivity {


    TextView tv1,tv2,tv3,tv4,tv5,tv6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelicula);
        tv1 = (TextView)findViewById(R.id.txt_res_titulo);
        tv2 = (TextView)findViewById(R.id.txt_res__descrip);
        tv3 = (TextView)findViewById(R.id.txt_res__director);
        tv4 = (TextView)findViewById(R.id.txt_res_producer);
        tv5 = (TextView)findViewById(R.id.txt_res_fecha);
        tv6 = (TextView)findViewById(R.id.txt_res_puntaje);

        String id = getId();

        String url = "https://ghibliapi.herokuapp.com/films/"+id;


        RequestQueue queue =  Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.length() > 0 ){
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        showData(jsonObject);
                    } catch (JSONException jsnEx3) {
                        jsnEx3.printStackTrace();
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

    }

    private String getId() {
        String id ="";
        Bundle receive = getIntent().getExtras();
        if(receive != null){
            id=receive.getString("id");
        }

        return id;
    }

    public void showData (JSONObject jsonObject){
        try {
            String title = jsonObject.getString("title");
            String description = jsonObject.getString("description");
            String director = jsonObject.getString("director");
            String producer = jsonObject.getString("producer");
            String releaseData = jsonObject.getString("release_date");
            String score = jsonObject.getString("rt_score");
            tv1.setText(title);
            tv2.setText(description);
            tv3.setText(director);
            tv4.setText(producer);
            tv5.setText(releaseData);
            tv6.setText(score);
        } catch (JSONException jsnEx4) {
            jsnEx4.printStackTrace();
        }
    }
}
