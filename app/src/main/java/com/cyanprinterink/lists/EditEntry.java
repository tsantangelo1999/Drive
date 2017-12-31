package com.cyanprinterink.lists;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.IDNA;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

public class EditEntry extends AppCompatActivity
{

    @Override public void onBackPressed()
    {
        Intent back = new Intent(EditEntry.this, Information.class);
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
        setContentView(R.layout.activity_edit_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Entry");
        ConstraintLayout cL = (ConstraintLayout) findViewById(R.id.constraintLayout);
        ScrollView sV = (ScrollView) findViewById(R.id.scrollboi);

        Entry info = null;
        try
        {
            info = MainActivity.getEntries()[Information.line];
        }
        catch(IOException e)
        {
            Log.d("fileText", "oh");
        }

        //date picker
        final Button date = (Button) findViewById(R.id.date);
        final Calendar cal = Calendar.getInstance();
        date.setText(info.month + "/" + info.day + "/" + info.year);
        dateStr = date.getText().toString();
        date.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String[] dates = date.getText().toString().split("/");
                int[] dateNums = {Integer.parseInt(dates[0]), Integer.parseInt(dates[1]), Integer.parseInt(dates[2])};
                DatePickerDialog dpg = new DatePickerDialog(EditEntry.this, new DatePickerDialog.OnDateSetListener()
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
        timeDrivenHr.setText("" + info.hours);
        timeDrivenMin = (EditText) findViewById(R.id.timeDrivenMin);
        timeDrivenMin.setText("" + info.minutes);
        timeDrivenHr.setFilters(new InputFilter[] {filter, new InputFilter.LengthFilter(2)});
        timeDrivenMin.setFilters(new InputFilter[] {filter, new InputFilter.LengthFilter(2)});

        //light stuff
        day = (CheckBox) findViewById(R.id.lightCondDay);
        day.setChecked(info.lightBooleans[0].equalsIgnoreCase("true"));
        night = (CheckBox) findViewById(R.id.lightCondNight);
        night.setChecked(info.lightBooleans[1].equalsIgnoreCase("true"));

        //weather stuff
        clear = (CheckBox) findViewById(R.id.weatherClear);
        clear.setChecked(info.weatherBooleans[0].equalsIgnoreCase("true"));
        rain = (CheckBox) findViewById(R.id.weatherRain);
        rain.setChecked(info.weatherBooleans[1].equalsIgnoreCase("true"));
        snow = (CheckBox) findViewById(R.id.weatherSnow);
        snow.setChecked(info.weatherBooleans[2].equalsIgnoreCase("true"));
        hail = (CheckBox) findViewById(R.id.weatherHail);
        hail.setChecked(info.weatherBooleans[3].equalsIgnoreCase("true"));
        fog = (CheckBox) findViewById(R.id.weatherFog);
        fog.setChecked(info.weatherBooleans[4].equalsIgnoreCase("true"));
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
        Log.d("fileText", "editing line " + Information.line);
        File file = new File(MainActivity.context.getFilesDir(), "info.tsv");
        FileWriter fw = new FileWriter(file);
        PrintWriter pw = new PrintWriter(fw);
        for(int i = 0; i < MainActivity.entries.length; i++)
        {
            if(!(i == Information.line))
            {
                pw.println(MainActivity.entries[i].docVal());
            }
            else
            {
                int timeVal = Integer.parseInt(timeDrivenHr.getText().toString()) * 60 + Integer.parseInt(timeDrivenMin.getText().toString());
                pw.println(dateStr + "\t" + timeVal + "\t" + day.isChecked() + "," + night.isChecked() + "\t" + clear.isChecked() + "," + rain.isChecked() + "," + snow.isChecked() + "," + hail.isChecked() + "," + fog.isChecked() + "\t");
            }
        }
        pw.close();
        fw.close();
        Intent refresh = new Intent(this, Information.class);
        startActivity(refresh);
        finish();
    }
}
