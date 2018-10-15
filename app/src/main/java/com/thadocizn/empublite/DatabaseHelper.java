package com.thadocizn.empublite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Process;
import android.support.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "empublite.db";
    private static final int SCHEMA_VERSION = 1;
    private static DatabaseHelper singleton = null;

    private DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    synchronized static DatabaseHelper getInstance(Context context){
        if (singleton == null){
            singleton = new DatabaseHelper(context.getApplicationContext());
        }

        return (singleton);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE notes(position INTEGER PRIMARY KEY, prose TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        throw new RuntimeException("This should not be called");
    }

    void loadNote(int position){
        new LoadThread(position).start();
    }

    void updateNote(int position, String prose){
        new UpdateThread(position, prose).start();
    }

    private class LoadThread extends Thread{
        private int position = -1;

        @Override
        public void run() {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

            String[] args = {String.valueOf(position)};
            Cursor cursor = getReadableDatabase().rawQuery("SELECT prose FROM notes WHERE position = ? ", args);

            if (cursor.getCount() > 0){
                cursor.moveToFirst();

                EventBus.getDefault().post(new NoteLoadedEvent(position, cursor.getString(0)));
            }

            cursor.close();
        }

        LoadThread(int position){
            super();
            this.position = position;
        }
    }

    private class UpdateThread extends Thread{
        private int position = -1;
        private String prose = null;

        @Override
        public void run() {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

            String[] args = {String.valueOf(position), prose};
            getWritableDatabase().execSQL("INSERT OR REPLACE INTO notes(position, prose) VALUES(?, ?)", args);
        }

        UpdateThread(int position, String prose) {
            super();
            this.position = position;
            this.prose = prose;
        }
    }
}
