package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import android.content.Intent;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    private int activeTab = R.id.menuHome;

    TextView textSavings;
    TextView textFinalInterest;
    TextView textStartDeposit;
    TextView textDeposit;
    TextView textInterest;
    TextView textPeriod;

    SeekBar barStartDeposit;
    SeekBar barDeposit;
    SeekBar barInterest;
    SeekBar barPeriod;

    BarChart barChart;
    PieChart pieChart;
    LineChart lineChart;
    String selectedChart;

    Button btnAddToHistory;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private long savedSum = 0, finalInterest = 0, finalDeposit = 0;
    private int startDeposit = 0, deposit = 0, interest = 0, period = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        getWindow().setStatusBarColor(Color.parseColor(sharedPreferences.getString("toolBarColor", "#03DAC6")));

        Toolbar toolBarMenu = findViewById(R.id.toolBarMenu);
        setSupportActionBar(toolBarMenu);
        toolBarMenu.setBackgroundColor(Color.parseColor(sharedPreferences.getString("toolBarColor", "#03DAC6")));

        startDeposit = sharedPreferences.getInt("lastStartDepositValue", 0);
        deposit = sharedPreferences.getInt("lastDepositValue", 0);
        interest = sharedPreferences.getInt("lastInterestValue", 0);
        period = sharedPreferences.getInt("lastPeriodValue", 0);

        textSavings = findViewById(R.id.textView1);

        textFinalInterest = findViewById(R.id.textView2);

        textStartDeposit = findViewById(R.id.textView3);
        textStartDeposit.setText("Počáteční vklad: " + String.valueOf(startDeposit));

        textDeposit = findViewById(R.id.textView4);
        textDeposit.setText("Měsíční vklad: " + String.valueOf(deposit));

        textInterest = findViewById(R.id.textView5);
        textInterest.setText("Úrok: " + String.valueOf(interest));

        textPeriod = findViewById(R.id.textView6);
        textPeriod.setText("Období: " + String.valueOf(period));

        ColorStateList colorList = ColorStateList.valueOf(Color.parseColor(sharedPreferences.getString("barColor", "#03DAC6")));

        barStartDeposit = findViewById(R.id.seekBar1);
        barStartDeposit.setProgress(startDeposit);
        barStartDeposit.setMax(sharedPreferences.getInt("bar1Max", 1000000));
        barStartDeposit.setProgressTintList(colorList);
        barStartDeposit.setThumbTintList(colorList);

        barDeposit = findViewById(R.id.seekBar2);
        barDeposit.setProgress(deposit);
        barDeposit.setMax(sharedPreferences.getInt("bar2Max", 100000));
        barDeposit.setProgressTintList(colorList);
        barDeposit.setThumbTintList(colorList);

        barInterest = findViewById(R.id.seekBar3);
        barInterest.setProgress(interest);
        barInterest.setMax(sharedPreferences.getInt("bar3Max", 10));
        barInterest.setProgressTintList(colorList);
        barInterest.setThumbTintList(colorList);

        barPeriod = findViewById(R.id.seekBar4);
        barPeriod.setProgress(period);
        barPeriod.setMax(sharedPreferences.getInt("bar4Max", 100));
        barPeriod.setProgressTintList(colorList);
        barPeriod.setThumbTintList(colorList);

        barChart = findViewById(R.id.barChart);
        barChart.setVisibility(View.INVISIBLE);

        pieChart = findViewById(R.id.pieChart);
        pieChart.setVisibility(View.INVISIBLE);

        lineChart = findViewById(R.id.lineChart);
        lineChart.setVisibility(View.INVISIBLE);

        btnAddToHistory = findViewById(R.id.btnAddToHistory);
        btnAddToHistory.setBackgroundColor(Color.parseColor(sharedPreferences.getString("barColor", "#03DAC6")));

        selectedChart = sharedPreferences.getString("selected chart", "BarChart");

        switch(selectedChart) {
            case "BarChart": {
                barChart.setVisibility(View.VISIBLE);
            }break;
            case "PieChart": {
                pieChart.setVisibility(View.VISIBLE);
            }break;
            case "LineChart": {
                lineChart.setVisibility(View.VISIBLE);
            }break;
        }

        calculateAndSetSavingsAndInterest();

        btnAddToHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences historySharedPreferencies = getSharedPreferences("MyAppHistoryPrefs", MODE_PRIVATE);
                SharedPreferences.Editor historyEditor = historySharedPreferencies.edit();

                Gson gson = new Gson();
                HistoryItem item = new HistoryItem(startDeposit, period, interest, deposit, finalInterest, finalDeposit);

                String json = gson.toJson(item);

                historyEditor.putString("historyItem_" + System.currentTimeMillis(), json);
                historyEditor.apply();
            }
        });

        barStartDeposit.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int stepSize = sharedPreferences.getInt("bar1Step", 1);
                int adjustedValue = Math.round(progress / (float) stepSize) * stepSize;
                seekBar.setProgress(adjustedValue);

                textStartDeposit.setText("Počáteční vklad: " + String.valueOf(adjustedValue));
                startDeposit = adjustedValue;
                editor.putInt("lastStartDepositValue", adjustedValue);
                editor.apply();

                calculateAndSetSavingsAndInterest();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        barDeposit.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int stepSize = sharedPreferences.getInt("bar2Step", 1);
                int adjustedValue = Math.round(progress / (float) stepSize) * stepSize;
                seekBar.setProgress(adjustedValue);

                textDeposit.setText("Měsíční vklad: " + String.valueOf(adjustedValue));
                deposit = adjustedValue;
                editor.putInt("lastDepositValue", adjustedValue);
                editor.apply();

                calculateAndSetSavingsAndInterest();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        barInterest.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int stepSize = sharedPreferences.getInt("bar3Step", 1);
                int adjustedValue = Math.round(progress / (float) stepSize) * stepSize;
                seekBar.setProgress(adjustedValue);

                textInterest.setText("Úrok: " + String.valueOf(adjustedValue));
                interest = adjustedValue;
                editor.putInt("lastInterestValue", adjustedValue);
                editor.apply();

                calculateAndSetSavingsAndInterest();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        barPeriod.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int stepSize = sharedPreferences.getInt("bar4Step", 1);
                int adjustedValue = Math.round(progress / (float) stepSize) * stepSize;
                seekBar.setProgress(adjustedValue);

                textPeriod.setText("Období: " + String.valueOf(adjustedValue));
                period = adjustedValue;
                editor.putInt("lastPeriodValue", adjustedValue);
                editor.apply();

                calculateAndSetSavingsAndInterest();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

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

        if(item.getItemId() == R.id.menuHome) {

            activeTab = item.getItemId();

            //Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            //startActivity(intent);

            return true;
        }else if(item.getItemId() == R.id.menuHistory) {

            activeTab = item.getItemId();

            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);

            return true;
        }else if(item.getItemId() == R.id.menuChartType) {

            activeTab = item.getItemId();

            Intent intent = new Intent(MainActivity.this, ChartTypeActivity.class);
            startActivity(intent);

            return true;
        }else if(item.getItemId() == R.id.menuSettings) {

            activeTab = item.getItemId();

            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);

            return true;
        } else return super.onOptionsItemSelected(item);
    }

    private void calculateAndSetSavingsAndInterest() {
        double monthlyInterestRate = (double) interest / 100 / 12;
        long totalMonths = period * 12;

        savedSum = (long) (startDeposit * Math.pow(1 + monthlyInterestRate, totalMonths))
                + (long) (deposit * ((Math.pow(1 + monthlyInterestRate, totalMonths) - 1) / monthlyInterestRate));

        textSavings.setText("Naspořená suma: " + savedSum);

        finalInterest = savedSum - (startDeposit + (deposit * totalMonths));
        textFinalInterest.setText("Z toho úroky: " + finalInterest);
        finalDeposit = savedSum - finalInterest;

        showChart();
    }

    private void showChart() {

        switch(selectedChart) {
            case "BarChart": {
                ArrayList<BarEntry> entries = new ArrayList<>();
                entries.add(new BarEntry(0, finalDeposit));
                entries.add(new BarEntry(1, finalInterest));

                BarDataSet dataSet = new BarDataSet(entries, "Vklad/úroky");
                dataSet.setColors(new int[] {
                        Color.parseColor(sharedPreferences.getString("chartColor1", "#C8EAE5")),
                        Color.parseColor(sharedPreferences.getString("chartColor2", "#8DCCB2"))
                });

                BarData barData = new BarData(dataSet);

                barChart.setData(barData);

                XAxis xAxis = barChart.getXAxis();
                xAxis.setDrawLabels(false);
                xAxis.setDrawGridLines(false);
                xAxis.setTextSize(14f);

                YAxis leftAxis = barChart.getAxisLeft();
                leftAxis.setTextSize(14f);

                Legend legend = barChart.getLegend();
                legend.setTextSize(14f);
                barData.setValueTextSize(14f);

                barChart.getAxisLeft().setAxisMinimum(0f);

                barChart.getDescription().setEnabled(false);

                barChart.invalidate();
            }break;
            case "PieChart": {
                ArrayList<PieEntry> entries = new ArrayList<>();
                entries.add(new PieEntry(finalDeposit, "Vklad"));
                entries.add(new PieEntry(finalInterest, "Úroky"));

                PieDataSet dataSet = new PieDataSet(entries, "");
                dataSet.setColors(new int[] {
                        Color.parseColor(sharedPreferences.getString("chartColor1", "#C8EAE5")),
                        Color.parseColor(sharedPreferences.getString("chartColor2", "#8DCCB2"))
                });

                PieData pieData = new PieData(dataSet);
                pieData.setValueTextSize(14f);

                pieChart.setData(pieData);
                pieChart.getLegend().setTextSize(14f);

                pieChart.getDescription().setEnabled(false);

                pieChart.invalidate();
            }break;
            case "LineChart": {
                ArrayList<Entry> depositEntries = new ArrayList<>();
                ArrayList<Entry> interestEntries = new ArrayList<>();

                float totalDeposit = startDeposit;
                float totalInterest = 0f;

                for (int year = 0; year <= period; year++) {
                    if (year > 0) {
                        totalDeposit += deposit * 12;
                        totalInterest += (totalDeposit * ((float)interest / 100));
                        System.out.println("DEPOSIT: " + deposit + "  INTEREST: " + interest + "  TOTAL DEPOSIT: " + totalDeposit + "  ROK: " + year + "  TOTAL INTEREST: " + totalInterest);
                    }

                    depositEntries.add(new Entry(year, totalDeposit));
                    interestEntries.add(new Entry(year, totalInterest));
                }

                LineDataSet depositDataSet = new LineDataSet(depositEntries, "Vklad");
                depositDataSet.setColor(Color.parseColor(sharedPreferences.getString("chartColor1", "#C8EAE5")));
                depositDataSet.setValueTextSize(14f);
                depositDataSet.setLineWidth(5f);
                depositDataSet.setDrawCircles(false);
                depositDataSet.setDrawValues(false);

                LineDataSet interestDataSet = new LineDataSet(interestEntries, "Úroky");
                interestDataSet.setColor(Color.parseColor(sharedPreferences.getString("chartColor2", "#8DCCB2")));
                interestDataSet.setValueTextSize(14f);
                interestDataSet.setLineWidth(5f);
                interestDataSet.setDrawCircles(false);
                interestDataSet.setDrawValues(false);

                LineData lineData = new LineData(depositDataSet, interestDataSet);
                lineChart.setData(lineData);

                lineChart.getLegend().setTextSize(14f);
                lineChart.getAxisLeft().setAxisMinimum(0f);

                lineChart.getDescription().setEnabled(false);

                lineChart.invalidate();

            }break;
        }
    }
}