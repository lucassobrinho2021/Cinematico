package com.example.cinematico;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cinematico.databinding.ActivityMovieBinding;

public class MovieActivity extends AppCompatActivity {

    ActivityMovieBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = this.getIntent();

        if(intent != null){
            String name = intent.getStringExtra("title");
            String grade = intent.getStringExtra("grade");
            String overview = intent.getStringExtra("overview");
            String voteCount = "Votos  ";
            voteCount += intent.getStringExtra("vote_count");

            binding.nome.setText(name);
            binding.grade.setText(grade);
            binding.overview.setText(overview);
            binding.voteCount.setText(voteCount);

        }


        binding.back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intention = new Intent(MovieActivity.this, MainActivity.class);
                startActivity(intention);
            }
        });


    }
}
