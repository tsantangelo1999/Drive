package com.cyanprinterink.lists;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.*;

public class AddEntry extends AppCompatActivity
{

    @Override public void onBackPressed()
    {
        Intent back = new Intent(this, MainActivity.class);
        startActivity(back);
        finish();
    }

    @Override protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
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
}
