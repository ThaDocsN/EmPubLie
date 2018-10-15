package com.thadocizn.empublite;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

public class NoteActivity extends Activity {

    public static final String EXTRA_POSITION = "position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getFragmentManager().findFragmentById(android.R.id.content) == null){
            int position = getIntent().getIntExtra(EXTRA_POSITION, -1);
            if (position >= 0){
                Fragment fragment = NoteFragment.newInstance(position);

                getFragmentManager().beginTransaction()
                        .add(android.R.id.content, fragment).commit();
            }
        }
    }
}