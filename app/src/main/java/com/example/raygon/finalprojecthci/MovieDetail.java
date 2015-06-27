package com.example.raygon.finalprojecthci;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MovieDetail extends ActionBarActivity implements android.view.View.OnClickListener{

    Button btnSave ,  btnDelete;
    Button btnClose;
    EditText editTextTitle;
    EditText editTextGenre;
    EditText editTextYear;
    private int _Movie_Id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnClose = (Button) findViewById(R.id.btnClose);

        editTextTitle = (EditText) findViewById(R.id.editMovieTitle);
        editTextGenre = (EditText) findViewById(R.id.editMovieGenre);
        editTextYear = (EditText) findViewById(R.id.editMovieYear);

        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);


        _Movie_Id =0;
        Intent intent = getIntent();
        _Movie_Id =intent.getIntExtra("movie_Id", 0);
        MovieRepo repo = new MovieRepo(this);
        Movie  movie = new Movie();
        movie= repo.getMovieById(_Movie_Id);

        editTextYear.setText(String.valueOf(movie.year));
        editTextTitle.setText(movie.title);
        editTextGenre.setText(movie.genre);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        if (view == findViewById(R.id.btnSave)){
            MovieRepo repo = new MovieRepo(this);
            Movie movie = new Movie();
            movie.year = Integer.parseInt(editTextYear.getText().toString());
            movie.genre=editTextGenre.getText().toString();
            movie.title=editTextTitle.getText().toString();
            movie.movie_ID=_Movie_Id;

            if (_Movie_Id==0){
                _Movie_Id = repo.insert(movie);

                Toast.makeText(this,"New Movie Added!",Toast.LENGTH_SHORT).show();
            }else{

                repo.update(movie);
                Toast.makeText(this,"Movie Record updated",Toast.LENGTH_SHORT).show();
            }
        }else if (view== findViewById(R.id.btnDelete)){
            MovieRepo repo = new MovieRepo(this);
            repo.delete(_Movie_Id);
            Toast.makeText(this, "Movie Record Deleted", Toast.LENGTH_SHORT).show();//show inserted by me
            finish();
        }else if (view== findViewById(R.id.btnClose)){
            finish();
        }


    }

}