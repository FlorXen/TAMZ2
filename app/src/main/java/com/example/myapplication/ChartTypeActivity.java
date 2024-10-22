package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

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

public class ChartTypeActivity extends AppCompatActivity  {

    private int activeTab = R.id.menuChartType;
    private CardView highlightedCardView = null;
    private ColorStateList defaultColor;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_type);

        sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        getWindow().setStatusBarColor(Color.parseColor(sharedPreferences.getString("toolBarColor", "#03DAC6")));

        Toolbar toolBarMenu = findViewById(R.id.toolBarMenu);
        setSupportActionBar(toolBarMenu);
        toolBarMenu.setBackgroundColor(Color.parseColor(sharedPreferences.getString("toolBarColor", "#03DAC6")));

        CardView cardView1 = findViewById(R.id.cardView1);
        defaultColor = cardView1.getCardBackgroundColor();

        initCardViewListeners();

        initBarChart();
        initPieChart();
        initLineChart();

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

            Intent intent = new Intent(ChartTypeActivity.this, MainActivity.class);
            startActivity(intent);

            return true;
        } else if (item.getItemId() == R.id.menuHistory) {

            activeTab = item.getItemId();

            Intent intent = new Intent(ChartTypeActivity.this, HistoryActivity.class);
            startActivity(intent);

            return true;
        } else if (item.getItemId() == R.id.menuChartType) {

            activeTab = item.getItemId();

            //Intent intent = new Intent(MainActivity.this, ChartTypeActivity.class);
            //startActivity(intent);

            return true;
        } else if (item.getItemId() == R.id.menuSettings) {

            activeTab = item.getItemId();

            Intent intent = new Intent(ChartTypeActivity.this, SettingsActivity.class);
            startActivity(intent);

            return true;
        } else return super.onOptionsItemSelected(item);
    }

    private void initCardViewListeners() {
        CardView cardView1 = findViewById(R.id.cardView1);
        CardView cardView2 = findViewById(R.id.cardView2);
        CardView cardView3 = findViewById(R.id.cardView3);

        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String selectedChart = sharedPreferences.getString("selected chart", "BarChart");


        switch(selectedChart) {
            case "BarChart": {
                highlightedCardView = cardView1;
                cardView1.setCardBackgroundColor(Color.rgb(220, 254, 249));
            }break;
            case "PieChart": {
                highlightedCardView = cardView2;
                cardView2.setCardBackgroundColor(Color.rgb(220, 254, 249));
            }break;
            case "LineChart": {
                highlightedCardView = cardView3;
                cardView3.setCardBackgroundColor(Color.rgb(220, 254, 249));
            }break;
        }

        cardView1.setOnClickListener(v -> handleCardClick(cardView1));
        cardView2.setOnClickListener(v -> handleCardClick(cardView2));
        cardView3.setOnClickListener(v -> handleCardClick(cardView3));
    }

    private void handleCardClick(CardView selectedCardView) {
        if (highlightedCardView != null) {
            highlightedCardView.setCardBackgroundColor(defaultColor);
        }

        selectedCardView.setCardElevation(8f);

        selectedCardView.setCardBackgroundColor(Color.rgb(220, 254, 249));

        selectedCardView.postDelayed(() -> {
            selectedCardView.setCardElevation(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getResources().getDisplayMetrics()));
        }, 200);

        highlightedCardView = selectedCardView;

        if(selectedCardView.getId() == R.id.cardView1) {
            editor.putString("selected chart", "BarChart");
            editor.apply();
        } else if(selectedCardView.getId() == R.id.cardView2) {
            editor.putString("selected chart", "PieChart");
            editor.apply();
        } else if(selectedCardView.getId() == R.id.cardView3) {
            editor.putString("selected chart", "LineChart");
            editor.apply();
        }
    }


    private void initBarChart() {
        BarChart chart = findViewById(R.id.barChart);

        chart.setTouchEnabled(false);
        chart.setHighlightPerTapEnabled(false);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 20000000));
        entries.add(new BarEntry(1, 303010));

        BarDataSet dataSet = new BarDataSet(entries, "Vklad/úroky");
        dataSet.setColors(new int[] {
                Color.parseColor(sharedPreferences.getString("chartColor1", "#C8EAE5")),
                Color.parseColor(sharedPreferences.getString("chartColor2", "#03DAC6"))
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

        chart.getDescription().setEnabled(false);

        chart.invalidate();
    }

    private void initPieChart() {
        PieChart pieChart = findViewById(R.id.pieChart);

        pieChart.setTouchEnabled(false);
        pieChart.setHighlightPerTapEnabled(false);

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(20000000, "Vklad"));
        entries.add(new PieEntry(303010, "Úroky"));

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(new int[] {
                Color.parseColor(sharedPreferences.getString("chartColor1", "#C8EAE5")),
                Color.parseColor(sharedPreferences.getString("chartColor2", "#03DAC6"))
        });

        PieData pieData = new PieData(dataSet);
        pieData.setValueTextSize(14f);

        pieChart.setData(pieData);
        pieChart.getLegend().setTextSize(14f);

        pieChart.getDescription().setEnabled(false);

        pieChart.invalidate();
    }

    private void initLineChart() {
        LineChart lineChart = findViewById(R.id.lineChart);

        lineChart.setTouchEnabled(false);
        lineChart.setHighlightPerTapEnabled(false);

        ArrayList<Entry> depositEntries = new ArrayList<>();
        ArrayList<Entry> interestEntries = new ArrayList<>();

        depositEntries.add(new Entry(0, 500000));
        depositEntries.add(new Entry(1, 1000000));
        depositEntries.add(new Entry(2, 1500000));
        depositEntries.add(new Entry(3, 2000000));

        interestEntries.add(new Entry(0, 0));
        interestEntries.add(new Entry(1, 100000));
        interestEntries.add(new Entry(2, 201000));
        interestEntries.add(new Entry(3, 303010));

        LineDataSet depositDataSet = new LineDataSet(depositEntries, "Vklad");
        depositDataSet.setColor(Color.parseColor(sharedPreferences.getString("chartColor1", "#C8EAE5")));
        depositDataSet.setValueTextSize(14f);
        depositDataSet.setLineWidth(5f);
        depositDataSet.setDrawCircles(false);
        depositDataSet.setDrawValues(false);

        LineDataSet interestDataSet = new LineDataSet(interestEntries, "Úroky");
        interestDataSet.setColor(Color.parseColor(sharedPreferences.getString("chartColor2", "#03DAC6")));
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
    }



}

