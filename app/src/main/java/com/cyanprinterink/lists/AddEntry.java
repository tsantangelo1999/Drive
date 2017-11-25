package com.cyanprinterink.lists;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import java.util.Calendar;

import java.io.*;

public class AddEntry extends AppCompatActivity
{

    @Override public void onBackPressed()
    {
        Intent back = new Intent(AddEntry.this, MainActivity.class);
        startActivity(back);
        finish();
    }

    @Override protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Entry");
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        DatePicker dp = new DatePicker(this);
        Calendar cal = Calendar.getInstance();
        dp.init(cal.YEAR, cal.MONTH, cal.DAY_OF_MONTH, null);
        dp.setLayoutParams(new LinearLayoutCompat.LayoutParams(MainActivity.context.getResources().getDisplayMetrics().widthPixels, 200));
        constraintLayout.addView(dp);
    }

    public void writeText(View view) throws IOException
    {
        Log.d("fileText", "printing");
        File file = new File(MainActivity.context.getFilesDir(), "info.tsv");
        FileWriter fw = new FileWriter(file, true);
        PrintWriter pw = new PrintWriter(fw);
        pw.println((int)(Math.random()*100));
        pw.close();
        fw.close();
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        finish();
    }

    public void delete(View view)
    {
        Log.d("fileText", "deleting");
        File file = new File(MainActivity.context.getFilesDir(), "info.tsv");
        file.delete();
    }

    public static void deleteEntry(int line) throws IOException
    {
        Log.d("fileText", "deleting line " + line);
        File file = new File(MainActivity.context.getFilesDir(), "info.tsv");
        FileWriter fw = new FileWriter(file);
        PrintWriter pw = new PrintWriter(fw);
        for(int i = 0; i < MainActivity.entries.length; i++)
        {
            if(!(i == line))
            {
                pw.println(MainActivity.entries[i].number);
            }
        }
        pw.close();
        fw.close();
    }
}
