package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView textSavings;
    TextView textFinalInterest;
    TextView textDeposit;
    TextView textInterest;
    TextView textPeriod;

    SeekBar barDeposit;
    SeekBar barInterest;
    SeekBar barPeriod;

    BarChart chart;

    private int savedSum = 0, finalInterest = 0, deposit = 0, interest = 0, period = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textSavings = (TextView) findViewById(R.id.textView1);
        textFinalInterest = (TextView) findViewById(R.id.textView2);
        textDeposit = (TextView) findViewById(R.id.textView3);
        textInterest = (TextView) findViewById(R.id.textView4);
        textPeriod = (TextView) findViewById(R.id.textView5);

        barDeposit = (SeekBar) findViewById(R.id.seekBar1);
        barInterest = (SeekBar) findViewById(R.id.seekBar2);
        barPeriod = (SeekBar) findViewById(R.id.seekBar3);

        chart = (BarChart) findViewById(R.id.barChart);

        Toolbar toolBarMenu = findViewById(R.id.toolBarMenu);
        setSupportActionBar(toolBarMenu);

        calculateAndSetSavingsAndInterest();

        barDeposit.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textDeposit.setText("Vklad: " + String.valueOf(progress));
                deposit = progress;
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
                textInterest.setText("Úrok: " + String.valueOf(progress));
                interest = progress;
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
                textPeriod.setText("Období: " + String.valueOf(progress));
                period = progress;
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

    private void calculateAndSetSavingsAndInterest() {
        savedSum = (int) (deposit * Math.pow(1+ (double) interest /100,period));
        textSavings.setText("Naspořená suma: " + String.valueOf(savedSum));

        finalInterest = savedSum - deposit;
        textFinalInterest.setText("Z toho úroky: " + String.valueOf(finalInterest));

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, deposit));
        entries.add(new BarEntry(1, finalInterest));

        BarDataSet dataSet = new BarDataSet(entries, "Vklad/úroky");
        dataSet.setColors(new int[] {
                Color.rgb(200, 234, 229),
                Color.rgb(141, 204, 194)
        });

        BarData barData = new BarData(dataSet);

        chart.setData(barData);

        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawLabels(false);
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(14f);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTextSize(14f);

        Legend legend = chart.getLegend();
        legend.setTextSize(14f);
        barData.setValueTextSize(14f);

        chart.getAxisLeft().setAxisMinimum(0f);

        chart.invalidate();

    }
}