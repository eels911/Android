package com.example.widjet;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

public class Config extends Activity {

    int widgetID = AppWidgetManager.INVALID_APPWIDGET_ID;
    Intent resultValue;
    EditText editText;
    RadioGroup radio;

    public final static String WIDGET_PREF = "WIDGET_PREF";
    public final static String WIDGET_TEXT = "WIDGET_TEXT";
    public final static String WIDGET_COLOR = "WIDGET_COLOR";

    @Override
    public void onCreate(Bundle saved)
    {
        super.onCreate(saved);

        //получение айди виджета
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null)
        {
            widgetID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        if (widgetID == AppWidgetManager.INVALID_APPWIDGET_ID){
            finish();
        }
        resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,widgetID);
        setResult(RESULT_CANCELED, resultValue);

        setContentView(R.layout.config);
        editText = findViewById(R.id.editText);
        radio = findViewById(R.id.radio);
    }

    public void  onClick(View view)
    {
        int color = R.color.colorWhite;
        switch (radio.getCheckedRadioButtonId()){
            case R.id.radioRed:
                color = R.color.colorRed;
                break;
            case R.id.radioOrange:
                color = R.color.colorOrange;
                break;
            case R.id.radioYellow:
                color = R.color.colorYellow;
                break;
            case R.id.radioGreen:
                color = R.color.colorGreen;
                break;
            case R.id.radioLightBlue:
                color = R.color.colorLightBlue;
                break;
            case R.id.radioDarkBlue:
                color = R.color.colorDarkBlue;
                break;
            case R.id.radioViolet:
                color = R.color.colorViolet;
                break;
        }
        SharedPreferences pref = getSharedPreferences(WIDGET_PREF,MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(WIDGET_TEXT + widgetID,editText.getText().toString());
        editor.putInt(WIDGET_COLOR + widgetID,color);
        editor.commit();

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        MyWidget.updateWidget(this, appWidgetManager,pref,widgetID);

        setResult(RESULT_OK, resultValue);
        finish();
    }
}


