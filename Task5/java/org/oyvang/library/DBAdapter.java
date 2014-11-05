package org.oyvang.library;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
    static final String KEY_ROWID = "_id";
    static final String KEY_NAME = "name";
    static final String KEY_TITLE = "title";
    static final String KEY_AUTHOR = "author_id";
    static final String KEY_BOOK = "book_id";
    static final String TAG = "DBAdapter";
    static final String DATABASE_NAME = "BooksDb";
    static final String DATABASE_TABLE1 = "author";
    static final String DATABASE_TABLE2 = "book";
    static final String DATABASE_TABLE3 = "author_book";
    static final int DATABASE_VERSION = 1;
    static final String DATABASE_CREATE1 = "create table author (_id integer primary key autoincrement, "
            + "name text unique not null);";
    static final String DATABASE_CREATE2 = "create table book (_id integer primary key autoincrement, "
            + "title text unique not null);";
    static final String DATABASE_CREATE3 = "create table author_book ("
            + "_id integer primary key autoincrement, "
            + "book_id numeric, "
            + "author_id numeric,"
            + "FOREIGN KEY(author_id) REFERENCES author(_id), "
            + "FOREIGN KEY(book_id) REFERENCES book(_id)"
            + ");";
    static final String AUTHOR_BOOK_SELECTION =
            "select book._id, book.title from "
                    + "author, book, author_book where "
                    + "author._id=author_book.author_id and "
                    + "book._id=author_book.book_id and "
                    + "author.name=?";
    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx) {
        context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        DBHelper.close();
    }

    private long insertAuthor(String name) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        return db.insert(DATABASE_TABLE1, null, initialValues);
    }

    private long insertBook(String title) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TITLE, title);
        return db.insert(DATABASE_TABLE2, null, initialValues);
    }

    private long insertAuthor_Book(long author, long book) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_AUTHOR, author);
        initialValues.put(KEY_BOOK, book);
        return db.insert(DATABASE_TABLE3, null, initialValues);
    }

    public long insert(String author, String title) {
        Cursor c = getAuthor(author);
        long author_id;
        if (c == null || c.getCount() == 0) author_id = insertAuthor(author);
        else author_id = c.getLong(0);
        long title_id;
        c = getBook(title);
        if (c == null || c.getCount() == 0) title_id = insertBook(title);
        else title_id = c.getLong(0);
        return insertAuthor_Book(author_id, title_id);
    }

    public Cursor getAllBooks() {
        return db.query(DATABASE_TABLE1, new String[]{KEY_ROWID, KEY_NAME}, null, null, null, null, null, null);
    }

    public Cursor getAllAuthors() {
        return db.query(DATABASE_TABLE2, new String[]{KEY_ROWID, KEY_TITLE}, null, null, null, null, null, null);
    }

    public Cursor getAllBookAuthors() {
        return db.query(true, DATABASE_TABLE3, new String[]{KEY_ROWID, KEY_AUTHOR, KEY_BOOK}, null, null, null, null, null, null);
    }

    public Cursor getAuthor(String name) throws SQLException {
        Cursor cursor =
                db.query(true, DATABASE_TABLE1, new String[]{KEY_ROWID, KEY_NAME}, KEY_NAME + "='" + name + "'", null, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        return cursor;
    }

    public Cursor getBook(String title) throws SQLException {
        Cursor cursor =
                db.query(true, DATABASE_TABLE2, new String[]{KEY_ROWID, KEY_TITLE}, KEY_TITLE + "='" + title + "'", null, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        return cursor;
    }

    public Cursor getBooksByAuthor(String author) throws SQLException {
        Cursor c = db.rawQuery(AUTHOR_BOOK_SELECTION, new String[]{author});
        return c;
    }

    public void reset() {
        context.deleteDatabase("BooksDb");

    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(DATABASE_CREATE1);
                db.execSQL(DATABASE_CREATE2);
                db.execSQL(DATABASE_CREATE3);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgraing database from version " + oldVersion + " to " + newVersion
                    + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }
    }

}
