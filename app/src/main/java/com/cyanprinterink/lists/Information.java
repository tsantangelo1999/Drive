package com.cyanprinterink.lists;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.io.IOException;

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
        getSupportActionBar().setTitle("Information");

        Log.d("fileText", String.valueOf(line));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.action_edit:
                Log.d("fileText", "edit");
                return true;

            case R.id.action_delete:
                Log.d("fileText", "delete");
                /*new AlertDialog.Builder(this)
                        .setTitle("Title")
                        .setMessage("Do you really want to whatever?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {

                            }})
                        .setNegativeButton(android.R.string.no, null).show();*/
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(true);
                builder.setTitle("Title");
                builder.setMessage("Message");
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        try
                        {
                            AddEntry.deleteEntry(line);
                        }
                        catch(IOException e)
                        {
                            Log.d("fileText", "whoops");
                        }
                        Intent intent = new Intent(Information.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                    }
                });
                builder.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
