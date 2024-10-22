package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.dhaval2404.colorpicker.ColorPickerDialog;
import com.github.dhaval2404.colorpicker.listener.ColorListener;
import com.github.dhaval2404.colorpicker.model.ColorShape;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity  {

    Context context;
    private int activeTab = R.id.menuSettings;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        context = this;

        sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        getWindow().setStatusBarColor(Color.parseColor(sharedPreferences.getString("toolBarColor", "#03DAC6")));

        Toolbar toolBarMenu = findViewById(R.id.toolBarMenu);
        setSupportActionBar(toolBarMenu);
        toolBarMenu.setBackgroundColor(Color.parseColor(sharedPreferences.getString("toolBarColor", "#03DAC6")));

        initBarChart();

        Button btnColorPickerSeekBar = findViewById(R.id.btnColorPickerSeekBar);
        btnColorPickerSeekBar.setBackgroundColor(Color.parseColor(sharedPreferences.getString("barColor", "#03DAC6")));
        btnColorPickerSeekBar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new ColorPickerDialog
                        .Builder(context)
                        .setTitle("Pick Theme")
                        .setColorShape(ColorShape.SQAURE)
                        .setDefaultColor("#f6e58d")
                        .setColorListener(new ColorListener() {
                            @Override
                            public void onColorSelected(int color, @NotNull String colorHex) {
                                editor.putString("barColor", colorHex);
                                editor.apply();
                                btnColorPickerSeekBar.setBackgroundColor(color);
                            }
                        })
                        .show();
            }
        });

        EditText inputMaxRange1 = findViewById(R.id.inputMaxRange1);
        inputMaxRange1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                editor.putInt("bar1Max", Integer.parseInt(String.valueOf(inputMaxRange1.getText())));
                editor.apply();
            }
        });

        EditText inputStepSize1 = findViewById(R.id.inputStepSize1);
        inputStepSize1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                editor.putInt("bar1Step", Integer.parseInt(String.valueOf(inputStepSize1.getText())));
                editor.apply();
            }
        });

        EditText inputMaxRange2 = findViewById(R.id.inputMaxRange2);
        inputMaxRange2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                editor.putInt("bar2Max", Integer.parseInt(String.valueOf(inputMaxRange2.getText())));
                editor.apply();
            }
        });

        EditText inputStepSize2 = findViewById(R.id.inputStepSize2);
        inputStepSize2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                editor.putInt("bar2Step", Integer.parseInt(String.valueOf(inputStepSize2.getText())));
                editor.apply();
            }
        });

        EditText inputMaxRange3 = findViewById(R.id.inputMaxRange3);
        inputMaxRange3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                editor.putInt("bar3Max", Integer.parseInt(String.valueOf(inputMaxRange3.getText())));
                editor.apply();
            }
        });

        EditText inputStepSize3 = findViewById(R.id.inputStepSize3);
        inputStepSize3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                editor.putInt("bar3Step", Integer.parseInt(String.valueOf(inputStepSize3.getText())));
                editor.apply();
            }
        });

        EditText inputMaxRange4 = findViewById(R.id.inputMaxRange4);
        inputMaxRange4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                editor.putInt("bar4Max", Integer.parseInt(String.valueOf(inputMaxRange4.getText())));
                editor.apply();
            }
        });

        EditText inputStepSize4 = findViewById(R.id.inputStepSize4);
        inputStepSize4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                editor.putInt("bar4Step", Integer.parseInt(String.valueOf(inputStepSize4.getText())));
                editor.apply();
            }
        });

        Button btnColorPickerChart1 = findViewById(R.id.btnColorPickerChart1);
        btnColorPickerChart1.setBackgroundColor(Color.parseColor(sharedPreferences.getString("chartColor1", "#C8EAE5")));
        btnColorPickerChart1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new ColorPickerDialog
                        .Builder(context)
                        .setTitle("Pick Theme")
                        .setColorShape(ColorShape.SQAURE)
                        .setDefaultColor("#f6e58d")
                        .setColorListener(new ColorListener() {
                            @Override
                            public void onColorSelected(int color, @NotNull String colorHex) {
                                editor.putString("chartColor1", colorHex);
                                editor.apply();
                                btnColorPickerChart1.setBackgroundColor(color);
                                initBarChart();
                            }
                        })
                        .show();
            }
        });

        Button btnColorPickerChart2 = findViewById(R.id.btnColorPickerChart2);
        btnColorPickerChart2.setBackgroundColor(Color.parseColor(sharedPreferences.getString("chartColor2", "#03DAC6")));
        btnColorPickerChart2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new ColorPickerDialog
                        .Builder(context)
                        .setTitle("Pick Theme")
                        .setColorShape(ColorShape.SQAURE)
                        .setDefaultColor("#f6e58d")
                        .setColorListener(new ColorListener() {
                            @Override
                            public void onColorSelected(int color, @NotNull String colorHex) {
                                editor.putString("chartColor2", colorHex);
                                editor.apply();
                                btnColorPickerChart2.setBackgroundColor(color);
                                initBarChart();
                            }
                        })
                        .show();
            }
        });

        Button btnColorPickerToolBar = findViewById(R.id.btnColorPickerToolBar);
        btnColorPickerToolBar.setBackgroundColor(Color.parseColor(sharedPreferences.getString("toolBarColor", "#03DAC6")));
        btnColorPickerToolBar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new ColorPickerDialog
                        .Builder(context)
                        .setTitle("Pick Theme")
                        .setColorShape(ColorShape.SQAURE)
                        .setDefaultColor("#f6e58d")
                        .setColorListener(new ColorListener() {
                            @Override
                            public void onColorSelected(int color, @NotNull String colorHex) {
                                editor.putString("toolBarColor", colorHex);
                                editor.apply();
                                btnColorPickerToolBar.setBackgroundColor(color);
                                toolBarMenu.setBackgroundColor(color);
                                getWindow().setStatusBarColor(color);
                            }
                        })
                        .show();
            }
        });

        Button btnResetSettings = findViewById(R.id.btnResetSettings);
        btnResetSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.apply();
                initBarChart();
                btnColorPickerSeekBar.setBackgroundColor(Color.parseColor("#03DAC6"));
                btnColorPickerChart1.setBackgroundColor(Color.parseColor("#C8EAE5"));
                btnColorPickerChart2.setBackgroundColor(Color.parseColor("#8DCCB2"));
                btnColorPickerToolBar.setBackgroundColor(Color.parseColor("#03DAC6"));
                toolBarMenu.setBackgroundColor(Color.parseColor("#03DAC6"));
                getWindow().setStatusBarColor(Color.parseColor("#03DAC6"));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem menuHome = menu.findItem(R.id.menuHome);
        MenuItem menuHistory = menu.findItem(R.id.menuHistory);
        MenuItem menuChartType = menu.findItem(R.id.menuChartType);
        MenuItem menuSettings = menu.findItem(R.id.menuSettings);

        menuHome.setVisible(activeTab != R.id.menuHome);
        menuHistory.setVisible(activeTab != R.id.menuHistory);
        menuChartType.setVisible(activeTab != R.id.menuChartType);
        menuSettings.setVisible(activeTab != R.id.menuSettings);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menuHome) {

            activeTab = item.getItemId();

            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
            startActivity(intent);

            return true;
        } else if (item.getItemId() == R.id.menuHistory) {

            activeTab = item.getItemId();

            Intent intent = new Intent(SettingsActivity.this, HistoryActivity.class);
            startActivity(intent);

            return true;
        } else if (item.getItemId() == R.id.menuChartType) {

            activeTab = item.getItemId();

            Intent intent = new Intent(SettingsActivity.this, ChartTypeActivity.class);
            startActivity(intent);

            return true;
        } else if (item.getItemId() == R.id.menuSettings) {

            activeTab = item.getItemId();

            //Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            //startActivity(intent);

            return true;
        } else return super.onOptionsItemSelected(item);
    }

    private void initBarChart() {
        BarChart chart = findViewById(R.id.barChart);

        chart.setTouchEnabled(false);
        chart.setHighlightPerTapEnabled(false);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 4));
        entries.add(new BarEntry(1, 2));

        BarDataSet dataSet = new BarDataSet(entries, "Vklad/úroky");
        dataSet.setColors(new int[] {
                Color.parseColor(sharedPreferences.getString("chartColor1", "#C8EAE5")),
                Color.parseColor(sharedPreferences.getString("chartColor2", "#03DAC6"))
        });

        BarData barData = new BarData(dataSet);

        chart.setData(barData);

        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawLabels(true);
        xAxis.setDrawGridLines(true);

        chart.getAxisLeft().setEnabled(false);
        chart.getAxisLeft().setAxisMinimum(0f);

        chart.getAxisRight().setEnabled(false);

        chart.getDescription().setEnabled(false);

        chart.invalidate();
    }
}

