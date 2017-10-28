package com.cyanprinterink.lists;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Information extends AppCompatActivity
{

    public static int line;

    @Override public void onBackPressed()
    {
        Intent back = new Intent(this, MainActivity.class);
        startActivity(back);
        finish();
    }

    @Override protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d("fileText", String.valueOf(line));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                Log.d("fileText", "edit");
                return true;

            case R.id.action_delete:
                Log.d("fileText", "delete");
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
