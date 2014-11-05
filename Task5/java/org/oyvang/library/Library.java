package org.oyvang.library;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Library extends Activity {

    private static final int RESULT_SETTINGS = 1;
    private boolean isBookImported = false;
    private DBAdapter db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setIcon(
                new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        getActionBar().setDisplayShowTitleEnabled(false);
        setContentView(R.layout.activity_library);
        showAllBookTitles();
    }

    public void showAllBookTitles() {
        openDB();
        Cursor c = db.getAllBooks();
        ListFragment frag = (ListFragment) (this.getFragmentManager().findFragmentById(R.id.frag1));
        String[] columns = new String[]{c.getColumnName(0), c.getColumnName(1)};
        int[] to = new int[]{R.id.textView1, R.id.textView2};
        SimpleCursorAdapter sca = new SimpleCursorAdapter(this, R.layout.list_layout, c, columns, to);
        frag.setListAdapter(sca);
        closeDB();
    }

    public void showAllBookAuthors() {
        openDB();
        Cursor c = db.getAllAuthors();
        ListFragment frag = (ListFragment) (this.getFragmentManager().findFragmentById(R.id.frag1));
        String[] columns = new String[]{c.getColumnName(0), c.getColumnName(1)};
        int[] to = new int[]{R.id.textView1, R.id.textView2};
        SimpleCursorAdapter sca = new SimpleCursorAdapter(this, R.layout.list_layout, c, columns, to);
        frag.setListAdapter(sca);
        closeDB();
    }

    public void importFile() {
        openDB();
        ArrayList<Book> books = readFile(R.raw.top10);
        long id;
        for (int i = 0; i < books.size(); i++) {
            id = db.insert(books.get(i).author, books.get(i).getTitle());
        }
        showAllBookTitles();
        closeDB();
    }

    private ArrayList<Book> readFile(int id) {
        ArrayList<Book> booksFromFile = new ArrayList<Book>();
        try {
            InputStream is = getResources().openRawResource(id);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = reader.readLine();
            while (line != null) {
                booksFromFile.add(createBook(line));
                line = reader.readLine();
            }
            reader.close();

        } catch (IOException e) {
        }
        return booksFromFile;
    }

    private Book createBook(String book) {
        String[] addBook = book.split(";;");
        return new Book(addBook[0], addBook[1]);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_SETTINGS:
                activateSettings();
                break;
        }
    }

    private void activateSettings() {
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);

        String color = sharedPrefs.getString("colorPref", "FFFFFF");
        ViewGroup main = (ViewGroup) findViewById(R.id.main);
        if (!color.equals("white")) {
            main.setBackgroundColor(Color.parseColor("#" + color));
        }
        Boolean importBook = sharedPrefs.getBoolean("top10", false);
        if (importBook) {
            if (!isBookImported) {
                importFile();
                isBookImported = true;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.library, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settingsButton:
                Intent i = new Intent(this, UserSettingActivity.class);
                startActivityForResult(i, RESULT_SETTINGS);
                return true;
            case R.id.showTitle:
                showAllBookTitles();
                return true;
            case R.id.showAuthors:
                showAllBookAuthors();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openDB(){
        db = new DBAdapter(this);
        db.open();
    }
    private void closeDB(){
        db.close();
    }

    @Override
    public void onDestroy() {
        db.close();
        super.onDestroy();
    }

}
