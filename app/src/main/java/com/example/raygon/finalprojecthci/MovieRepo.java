package com.example.raygon.finalprojecthci;

/**
 * Created by RAYGON on 5/25/2015.
 */
import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import java.util.ArrayList;
        import java.util.HashMap;

public class MovieRepo {
    private DBHelper dbHelper;

    public MovieRepo(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(Movie movie) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Movie.KEY_year, movie.year);
        values.put(Movie.KEY_genre,movie.genre);
        values.put(Movie.KEY_title, movie.title);

        // Inserting Row
        long movie_Id = db.insert(Movie.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) movie_Id;
    }

    public void delete(int movie_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Movie.TABLE, Movie.KEY_ID + "= ?", new String[] { String.valueOf(movie_Id) });
        db.close(); // Closing database connection
    }

    public void update(Movie movie) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Movie.KEY_year, movie.year);
        values.put(Movie.KEY_genre,movie.genre);
        values.put(Movie.KEY_title, movie.title);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Movie.TABLE, values, Movie.KEY_ID + "= ?", new String[] { String.valueOf(movie.movie_ID) });
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap<String, String>>  getMovieList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Movie.KEY_ID + "," +
                Movie.KEY_title + "," +
                Movie.KEY_genre + "," +
                Movie.KEY_year +
                " FROM " + Movie.TABLE;

        //Student student = new Student();
        ArrayList<HashMap<String, String>> movieList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> movie = new HashMap<String, String>();
                movie.put("id", cursor.getString(cursor.getColumnIndex(Movie.KEY_ID)));
                movie.put("title", cursor.getString(cursor.getColumnIndex(Movie.KEY_title)));
                movieList.add(movie);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return movieList;

    }

    public Movie getMovieById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Movie.KEY_ID + "," +
                Movie.KEY_title + "," +
                Movie.KEY_genre + "," +
                Movie.KEY_year +
                " FROM " + Movie.TABLE
                + " WHERE " +
                Movie.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        Movie movie = new Movie();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                movie.movie_ID =cursor.getInt(cursor.getColumnIndex(Movie.KEY_ID));
                movie.title =cursor.getString(cursor.getColumnIndex(Movie.KEY_title));
                movie.genre  =cursor.getString(cursor.getColumnIndex(Movie.KEY_genre));
                movie.year =cursor.getInt(cursor.getColumnIndex(Movie.KEY_year));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return movie;
    }
}
