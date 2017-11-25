package com.cyanprinterink.lists;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
        getSupportActionBar().setTitle("Home");
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
            sortEntries();
        }
        catch(FileNotFoundException e)
        {
            Log.d("fileText", "File not found");
        }
        catch(IOException e)
        {
            Log.d("fileText", "IOException");
        }
        catch(NullPointerException e)
        {
            Log.d("fileText", "no file");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.action_delete_all:
                Log.d("fileText", "delete all");
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(true);
                builder.setTitle("Delete All");
                builder.setMessage("Are you sure you want to delete all entries?");
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Log.d("fileText", "deleting");
                        File file = new File(MainActivity.context.getFilesDir(), "info.tsv");
                        file.delete();
                        finish();
                        recreate();
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

    private void sortEntries() throws IOException
    {
        File file = new File(this.getFilesDir(), "info.tsv");
        FileWriter fw = new FileWriter(file);
        PrintWriter pw = new PrintWriter(fw);
        for(Entry e : entries)
        {
            pw.println(e.number);
        }
        pw.close();
        fw.close();
    }
}
