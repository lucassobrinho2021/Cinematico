package com.example.cinematico;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String URL ="https://api.themoviedb.org/3/movie/top_rated?api_key=58a6af7c57bd237d8c8b737a26bdd1ce";
        final JSONArray[] resultado = new JSONArray[1];
        setContentView(R.layout.activity_main);



        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response){
                        Log.e("Rest Response", response.toString());
                        try {
                            Log.e("Rest Response", String.valueOf(response.getJSONArray("results")));
                            JSONArray resultsArray = response.getJSONArray("results");
                            ListView lv;
                            lv = findViewById(R.id.listView);
                            ArrayList<HashMap<String,String>> listaDeFilmes;
                            listaDeFilmes = new ArrayList<>();



                            for(int i=0; i< resultsArray.length(); i++){
                                JSONObject jsonObject = resultsArray.getJSONObject(i);
                                Log.println(Log.ASSERT,"Titulo", jsonObject.getString("title"));
                                Log.println(Log.ASSERT,"Vlaor", String.valueOf(jsonObject.getDouble("vote_average")));
                                String title = jsonObject.getString("title");
                                String grade = String.valueOf(jsonObject.getDouble("vote_average"));
                                HashMap<String, String> movies = new HashMap<>();
                                movies.put("grade", grade);
                                movies.put("title", title);
                                listaDeFilmes.add(movies);
                            }
                            ListAdapter adapter = new SimpleAdapter(
                                    MainActivity.this,
                                    listaDeFilmes,
                                    R.layout.row_layout,
                                    new String[]{"title", "grade"},
                                    new int[]{R.id.textView, R.id.textView2});

                            lv.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest Error", error.toString());
                    }
                }
        );

        requestQueue.add(objectRequest);

    }
}