package com.cyanprinterink.lists;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;

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

    private String blockCharacterSet = ".";
    private InputFilter filter = new InputFilter()
    {
        @Override public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend)
        {
            if(source != null && blockCharacterSet.contains(("" + source)))
            {
                return "";
            }
            return null;
        }
    };

    @Override protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Entry");
        ConstraintLayout cL = (ConstraintLayout) findViewById(R.id.constraintLayout);
        ScrollView sV = (ScrollView) findViewById(R.id.scrollboi);

        //date picker
        final Button date = (Button) findViewById(R.id.date);
        final Calendar cal = Calendar.getInstance();
        final int cYear = cal.get(Calendar.YEAR);
        final int cMonth = cal.get(Calendar.MONTH);
        final int cDay = cal.get(Calendar.DAY_OF_MONTH);
        date.setText((cMonth + 1) + "/" + cDay + "/" + cYear);
        dateStr = date.getText().toString();
        date.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String[] dates = date.getText().toString().split("/");
                int[] dateNums = {Integer.parseInt(dates[0]), Integer.parseInt(dates[1]), Integer.parseInt(dates[2])};
                DatePickerDialog dpg = new DatePickerDialog(AddEntry.this, new DatePickerDialog.OnDateSetListener()
                {
                    @Override public void onDateSet(DatePicker view, int year,
                            int month, int dayOfMonth)
                    {
                        date.setText((month + 1) + "/" + dayOfMonth + "/" + year);
                        dateStr = date.getText().toString();

                    }
                }, dateNums[2], dateNums[0] - 1, dateNums[1]);
                dpg.show();
            }
        });

        //time driven stuff
        timeDrivenHr = (EditText) findViewById(R.id.timeDrivenHr);
        timeDrivenMin = (EditText) findViewById(R.id.timeDrivenMin);
        timeDrivenHr.setFilters(new InputFilter[] {filter, new InputFilter.LengthFilter(2)});
        timeDrivenMin.setFilters(new InputFilter[] {filter, new InputFilter.LengthFilter(2)});
        day = (CheckBox) findViewById(R.id.lightCondDay);
        night = (CheckBox) findViewById(R.id.lightCondNight);
        clear = (CheckBox) findViewById(R.id.weatherClear);
        rain = (CheckBox) findViewById(R.id.weatherRain);
        snow = (CheckBox) findViewById(R.id.weatherSnow);
        hail = (CheckBox) findViewById(R.id.weatherHail);
        fog = (CheckBox) findViewById(R.id.weatherFog);
    }

    String dateStr;
    EditText timeDrivenHr;
    EditText timeDrivenMin;
    CheckBox day;
    CheckBox night;
    CheckBox clear;
    CheckBox rain;
    CheckBox snow;
    CheckBox hail;
    CheckBox fog;

    public void writeText(View view) throws IOException
    {
        Log.d("fileText", "printing");
        File file = new File(MainActivity.context.getFilesDir(), "info.tsv");
        FileWriter fw = new FileWriter(file, true);
        PrintWriter pw = new PrintWriter(fw);
        int timeVal = Integer.parseInt(timeDrivenHr.getText().toString()) * 60 + Integer.parseInt(timeDrivenMin.getText().toString());
        pw.println(dateStr + "\t" + timeVal + "\t" + day.isChecked() + "," + night.isChecked() + "\t" + clear.isChecked() + "," + rain.isChecked() + "," + snow.isChecked() + "," + hail.isChecked() + "," + fog.isChecked() + "\t");
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
                pw.println(MainActivity.entries[i].docVal());
            }
        }
        pw.close();
        fw.close();
    }
}
