package com.cyanprinterink.lists;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.io.*;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity
{
    public static Context context;

    @Override protected void onCreate(Bundle savedInstanceState)
    {
        context = getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View view)
            {
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
                finish();
            }
        });

        Button[] entries = null;
        LinearLayout layout = (LinearLayout) findViewById(R.id.set);
        ScrollView sv = (ScrollView) findViewById(R.id.scrollboi);

        try
        {
            entries = getEntries();
        }
        catch(FileNotFoundException e)
        {
            Log.d("FixError", "File not found");
        }
        catch(IOException e)
        {
            Log.d("FixError", "IOException");
        }

        if(entries != null)
            for(int i = 0; i < entries.length; i++)
            {
                layout.addView(entries[i]);
                final int line = i;
                entries[i].setOnClickListener(new View.OnClickListener()
                {
                    @Override public void onClick(View view)
                    {
                        startActivity(new Intent(MainActivity.this, Information.class));
                        Information.line = line;
                        finish();
                    }
                });
            }
    }

    @Override protected void onResume()
    {
        super.onResume();
    }

    private Button[] getEntries() throws FileNotFoundException, IOException
    {
        File file = new File(this.getFilesDir(), "info.tsv");
        Button[] ret = null;
        if(!file.createNewFile())
        {
            Scanner sc = new Scanner(file);
            Log.d("fileText", "starting read");
            //while(sc.hasNextLine())Log.d("fileText", sc.nextLine());
            LineNumberReader lnr = new LineNumberReader(new FileReader(file));
            lnr.skip(Long.MAX_VALUE);
            ret = new Button[lnr.getLineNumber()];
            for(int i = 0; i < ret.length; i++)
            {
                ret[i] = new Button(this);
                ret[i].setText(sc.nextLine());
                ret[i].setLayoutParams(new LinearLayoutCompat.LayoutParams(context.getResources().getDisplayMetrics().widthPixels, 200));
            }
            sc.close();
            lnr.close();
        }
        return ret;
    }
}
