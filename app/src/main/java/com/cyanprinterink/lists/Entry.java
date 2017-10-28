package com.cyanprinterink.lists;

public class Entry implements Comparable<Entry>
{
    public int number;

    public Entry(int n)
    {
        number = n;
    }

    public int compareTo(Entry e)
    {
        if(number > e.number)
            return 1;
        else if(number < e.number)
            return -1;
        else
            return 0;
    }

    public String toString()
    {
        return String.valueOf(number);
    }
}
