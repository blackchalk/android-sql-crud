package com.example.raygon.finalprojecthci;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends ListActivity  implements android.view.View.OnClickListener{

    Button btnAdd,btnGetAll;
    TextView movie_Id;
    EditText editTextsearch;

    @Override
    public void onClick(View view) {
        if (view== findViewById(R.id.btnAdd)){

            Intent intent = new Intent(this,MovieDetail.class);
            intent.putExtra("movie_Id",0);
            startActivity(intent);

        }else {

            MovieRepo repo = new MovieRepo(this);

            ArrayList<HashMap<String, String>> movieList =  repo.getMovieList();
            if(movieList.size()!=0) {
                ListView lv = getListView();
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view1,int position, long id) {
                        movie_Id = (TextView) view1.findViewById(R.id.movie_Id);
                        String movieId = movie_Id.getText().toString();
                        Intent objIndent = new Intent(getApplicationContext(),
                                MovieDetail.class);
                        objIndent.putExtra("movie_Id", Integer.parseInt( movieId));
                        startActivity(objIndent);
                    }
                });
                ListAdapter adapter = new SimpleAdapter( MainActivity.this,movieList, R.layout.activity_view_movie_entry, new String[] { "id","title"}, new int[] {R.id.movie_Id, R.id.movie_title});
                setListAdapter(adapter);
            }else{
                Toast.makeText(this,"No movies!",Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnGetAll = (Button) findViewById(R.id.btnGetAll);
        btnGetAll.setOnClickListener(this);


        editTextsearch = (EditText)findViewById(R.id.mysearch);
        editTextsearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    String searchme = v.getText().toString();
                    Intent objString = new Intent(getApplication(),DbSearchActivity.class);
                    objString.putExtra("SEARCH",searchme);
                    startActivity(objString);
                    Toast.makeText(getApplicationContext(),searchme,Toast.LENGTH_SHORT).show();
                }
                return false;

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
