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
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

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

        TextView dateV = (TextView) findViewById(R.id.DateView);
        TextView timeV = (TextView) findViewById(R.id.TimeView);
        TextView lightV = (TextView) findViewById(R.id.LightView);
        TextView weatherV = (TextView) findViewById(R.id.WeatherView);
        Entry info = null;

        try
        {
            info = MainActivity.getEntries()[line];
        }
        catch(IOException e)
        {
            Log.d("fileText", "oops");
        }
        Log.d("fileText", info.docVal());
        dateV.setText(info.date());
        timeV.setText(info.hours + " hours, " + info.minutes + " minutes");
        String light = "";
        if(info.light % 2 >= 1)
            light += "Day, ";
        if(info.light % 4 >= 2)
            light += "Night, ";
        if(light.length() > 0)
            light = light.substring(0, light.length() - 2);
        lightV.setText(light);
        String weather = "";
        if(info.weather % 2 >= 1)
            weather += "Clear, ";
        if(info.weather % 4 >= 2)
            weather += "Rain, ";
        if(info.weather % 8 >= 4)
            weather += "Snow, ";
        if(info.weather % 16 >= 8)
            weather += "Hail, ";
        if(info.weather % 32 >= 16)
            weather += "Fog, ";
        if(weather.length() > 0)
            weather = weather.substring(0, weather.length() - 2);
        weatherV.setText(weather);
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
                Intent intent = new Intent(Information.this, EditEntry.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.action_delete:
                Log.d("fileText", "delete");
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(true);
                builder.setTitle("Delete");
                builder.setMessage("Are you sure you want to delete this entry?");
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
