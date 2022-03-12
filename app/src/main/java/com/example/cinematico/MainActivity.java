package com.example.cinematico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

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
                                // Pegando resultados da api e alocando os em um objeto que será renderizado pelo listview
                                JSONObject jsonObject = resultsArray.getJSONObject(i);
                                String title = jsonObject.getString("title");
                                String grade = "Nota Média: " + String.valueOf(jsonObject.getDouble("vote_average"));
                                HashMap<String, String> movies = new HashMap<>();
                                movies.put("grade", grade);
                                movies.put("title", title);
                                listaDeFilmes.add(movies);
                                //overview
                                //vote average
                                //vote count
                                //release date
                                //title
                            }
                            ListAdapter adapter = new SimpleAdapter(
                                    MainActivity.this,
                                    listaDeFilmes,
                                    R.layout.row_layout,
                                    new String[]{"title", "grade"},
                                    new int[]{R.id.textView, R.id.textView2});
                            lv.setAdapter(adapter);
                            lv.setClickable(true);
                            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Log.d("aqui", "Entrou");
                                    Intent intention = new Intent(MainActivity.this, MovieActivity.class);
                                    try {
                                        intention.putExtra("title", resultsArray.getJSONObject(i).getString("title"));
                                        intention.putExtra("grade", resultsArray.getJSONObject(i).getString("vote_average"));
                                        intention.putExtra("vote_count", resultsArray.getJSONObject(i).getString("vote_count"));
                                        intention.putExtra("overview", resultsArray.getJSONObject(i).getString("overview"));
                                        startActivity(intention);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });

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