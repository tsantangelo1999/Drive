package com.cyanprinterink.lists;

public class Entry implements Comparable<Entry>
{
    public int day;
    public int month;
    public int year;
    public int time;
    public int hours;
    public int minutes;
    public String[] lightBooleans;
    public int light;
    public String[] weatherBooleans;
    public int weather;

    public Entry(String[] date, int timeVal, String[] lightBools, String[] weatherBools)
    {
        month = Integer.parseInt(date[0]);
        day = Integer.parseInt(date[1]);
        year = Integer.parseInt(date[2]);
        time = timeVal;
        hours = timeVal / 60;
        minutes = timeVal % 60;
        light = 0;
        lightBooleans = lightBools;
        for(int i = 0; i < lightBools.length; i++)
        {
            if(lightBools[i].equalsIgnoreCase("true"))
                light += Math.pow(2, i);
        }
        weather = 0;
        weatherBooleans = weatherBools;
        for(int i = 0; i < weatherBools.length; i++)
        {
            if(weatherBools[i].equalsIgnoreCase("true"))
                weather += Math.pow(2, i);
        }
    }

    public String date()
    {
        return month + "/" + day + "/" + year;
    }

    public int compareTo(Entry e)
    {
        if(year > e.year)
            return 1;
        else if(year < e.year)
            return -1;
        else
        {
            if(month > e.month)
                return 1;
            else if(month < e.month)
                return -1;
            else
            {
                if(day > e.day)
                    return 1;
                else if(day < e.day)
                    return -1;
                else
                    return 0;
            }
        }
    }

    public String toString()
    {
        return String.valueOf(month + "/" + day + "/" + year + "\t" + hours + " hr " + minutes + " min");
    }

    public String docVal()
    {
        String lights = "";
        for(int i = 0; i < lightBooleans.length; i++)
        {
            lights += lightBooleans[i];
            if(i != lightBooleans.length - 1)
                lights += ",";
        }
        String weathers = "";
        for(int i = 0; i < weatherBooleans.length; i++)
        {
            weathers += weatherBooleans[i];
            if(i != weatherBooleans.length - 1)
                weathers += ",";
        }
        return String.valueOf(month + "/" + day + "/" + year + "\t" + time + "\t" + lights + "\t" + weathers);
    }
}
