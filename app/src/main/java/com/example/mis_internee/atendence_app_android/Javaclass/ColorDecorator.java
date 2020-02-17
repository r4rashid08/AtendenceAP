package com.example.mis_internee.atendence_app_android.Javaclass;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;


import com.example.mis_internee.atendence_app_android.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Date;

/**
 * Created by Navruz on 17.06.2016.
 */
public class ColorDecorator implements DayViewDecorator {

    private Drawable drawable;

    CalendarDay currentDay = CalendarDay.from(new Date());

    public ColorDecorator(Activity context) {
        drawable = ContextCompat.getDrawable(context,     R.drawable.first_day_month);

    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return day.equals(currentDay);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);
    }
}