package com.example.dataoftoday;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

class OneDayDecorator implements DayViewDecorator {
    private CalendarDay date;
    private final Drawable drawable;
    public OneDayDecorator(Calendar context){
        date=CalendarDay.today();
        drawable=context.getResources().getDrawable(R.drawable.today); //오늘을 표시하는 동그라미 drawable
    }

    public boolean shouldDecorate(CalendarDay day){
        return day.equals(date);
    }

    public void decorate(DayViewFacade view){
        view.addSpan(new StyleSpan(Typeface.BOLD));
        view.addSpan(new RelativeSizeSpan(1));
        view.addSpan(new ForegroundColorSpan(Color.YELLOW));
        view.setSelectionDrawable(drawable);
    }
    public void setDate(Date date){
        this.date=CalendarDay.from(date);
    }
}

class SaturdayDecorator implements DayViewDecorator {
    private final java.util.Calendar calendar= java.util.Calendar.getInstance();

    public SaturdayDecorator(){
    }
    @Override
    public boolean shouldDecorate(CalendarDay day) {
        day.copyTo(calendar);
        int weekDay=calendar.get(java.util.Calendar.DAY_OF_WEEK);
        return weekDay== java.util.Calendar.SATURDAY;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new ForegroundColorSpan(Color.BLUE));
    }
}

class SundayDecorator implements DayViewDecorator {
    private final java.util.Calendar calendar= java.util.Calendar.getInstance();
    public SundayDecorator(){

    }
    @Override
    public boolean shouldDecorate(CalendarDay day) {
        day.copyTo(calendar);
        int weekDay=calendar.get(java.util.Calendar.DAY_OF_WEEK);

        return weekDay== java.util.Calendar.SUNDAY;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new ForegroundColorSpan(Color.RED));
    }
}

class EventDecorator implements DayViewDecorator {


    private final Drawable drawable;
    private int color;
    private HashSet<CalendarDay> dates;
    private TextView textView;
    public EventDecorator(Collection<CalendarDay> dates, Activity context, TextView textView) {
        drawable = context.getResources().getDrawable(R.drawable.calendar_background);

        this.dates = new HashSet<>(dates);
        this.textView = textView;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day){
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);
    }

    public void setText(String text){
        textView.setText(text);
    }

}

public class Calendar extends Fragment{
   MaterialCalendarView materialCalendarView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup;
        viewGroup=(ViewGroup) inflater.inflate(R.layout.calendar,container,false);
        materialCalendarView= viewGroup.findViewById(R.id.calendarView);
        OneDayDecorator oneDayDecorator=new OneDayDecorator(this);

        materialCalendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(),
                oneDayDecorator
        );

        ArrayList<CalendarDay> calendarDayList = new ArrayList<>();
        calendarDayList.add(CalendarDay.today());
        calendarDayList.add(CalendarDay.from(2020, 11, 25));



        return viewGroup;
    }

}
