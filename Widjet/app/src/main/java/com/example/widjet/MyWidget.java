package com.example.widjet;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

public class MyWidget extends AppWidgetProvider {

//    вызывается при создании первого экземпляра виджета
    @Override
    public  void onEnabled(Context context)
    {
        super.onEnabled(context);
        Log.d("myWid","onEnabled");

    }
//      выщывается при обновлении виджета(3600000)
    @Override
    public  void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] widgetIds)
    {
        super.onUpdate(context,appWidgetManager,widgetIds);
        Log.e("myWid","onUpdate");
        SharedPreferences pref = context.getSharedPreferences(Config.WIDGET_PREF, Config.MODE_PRIVATE);
        for (int id: widgetIds){
            updateWidget(context, appWidgetManager,pref,id);
        }
    }
    @Override
    public void onDeleted(Context context,int[] widgetIds) {
        super.onDeleted(context, widgetIds);
        Log.i("myWid", "onDeleted");
        SharedPreferences.Editor editor = context.getSharedPreferences(Config.WIDGET_PREF, Config.MODE_PRIVATE).edit();
        for (int widgetID : widgetIds) {
            editor.remove(Config.WIDGET_TEXT + widgetID);
            editor.remove(Config.WIDGET_COLOR+widgetID);
        }
        editor.apply();
    }
    @Override
    public void onDisabled(Context context)
    {
        super.onDisabled(context);
        Log.v("myWid","onDisabled");
    }

    static void updateWidget(Context context,AppWidgetManager appWidgetManager, SharedPreferences pref, int widgetID){
        String widgetText = pref.getString(Config.WIDGET_TEXT + widgetID,null);
        if (widgetText == null) return;
        int widgetColor = pref.getInt(Config.WIDGET_COLOR + widgetID,R.color.colorWhite);

        RemoteViews widgetView = new RemoteViews(context.getPackageName(),R.layout.widget);
        widgetView.setTextViewText(R.id.textView,widgetText);
        widgetView.setInt(R.id.textView,"setBackgroundColor", context.getResources().getColor(widgetColor));
        appWidgetManager.updateAppWidget(widgetID,widgetView);
    }
}
