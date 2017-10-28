package com.cyanprinterink.lists;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity
{
    public static Context context;

    public static Entry[] entries;

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
                startActivity(new Intent(MainActivity.this, AddEntry.class));
                finish();
            }
        });

        Button[] buttons = null;
        LinearLayout layout = (LinearLayout) findViewById(R.id.set);
        ScrollView sv = (ScrollView) findViewById(R.id.scrollboi);

        try
        {
            entries = getEntries();
            Arrays.sort(entries);
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
                Button button = new Button(this);
                button.setText(entries[i].toString());
                button.setLayoutParams(new LinearLayoutCompat.LayoutParams(context.getResources().getDisplayMetrics().widthPixels, 200));
                layout.addView(button);
                final int line = i;
                button.setOnClickListener(new View.OnClickListener()
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

    private Entry[] getEntries() throws IOException
    {
        File file = new File(this.getFilesDir(), "info.tsv");
        Entry[] ret = null;
        if(!file.createNewFile())
        {
            Scanner sc = new Scanner(file);
            sc.useDelimiter("\t|\n");
            Log.d("fileText", "starting read");
            //while(sc.hasNextLine())Log.d("fileText", sc.nextLine());
            LineNumberReader lnr = new LineNumberReader(new FileReader(file));
            lnr.skip(Long.MAX_VALUE);
            ret = new Entry[lnr.getLineNumber()];
            for(int i = 0; i < ret.length; i++)
            {
                int number = sc.nextInt();
                ret[i] = new Entry(number);
            }
            sc.close();
            lnr.close();
            Log.d("fileText", "finish read");
        }
        return ret;
    }
}
