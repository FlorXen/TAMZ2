package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity  {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    SharedPreferences historySharedPreferences;
    SharedPreferences.Editor historyEditor;

    private int activeTab = R.id.menuHistory;

    ListView listViewHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listViewHistory = findViewById(R.id.listViewHistory);

        sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        historySharedPreferences = getSharedPreferences("MyAppHistoryPrefs", MODE_PRIVATE);
        historyEditor = historySharedPreferences.edit();

        getWindow().setStatusBarColor(Color.parseColor(sharedPreferences.getString("toolBarColor", "#03DAC6")));

        Toolbar toolBarMenu = findViewById(R.id.toolBarMenu);
        setSupportActionBar(toolBarMenu);
        toolBarMenu.setBackgroundColor(Color.parseColor(sharedPreferences.getString("toolBarColor", "#03DAC6")));

        Gson gson = new Gson();
        Map<String, ?> allEntries = historySharedPreferences.getAll();
        ArrayList<HistoryItem> historyList = new ArrayList<>();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getKey().startsWith("historyItem_")) {
                String json = (String) entry.getValue();
                HistoryItem item = gson.fromJson(json, HistoryItem.class);
                historyList.add(item);
            }
        }

        List<String> historyStrings = new ArrayList<>();
        for (HistoryItem item : historyList) {
            String historyEntry = "NASPOŘENO: " + (item.finalInterest+item.finalDeposit) +
                    "\nZ toho úroky: " + item.finalInterest +
                    "\nPARAMETRY:\nPočáteční vklad: " + item.startDeposit +
                    ", Období: " + item.period +
                    "\nÚrok: " + item.interest +
                    ", Měsíční vklad: " + item.deposit;
            historyStrings.add(historyEntry);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                historyStrings
        );

        listViewHistory.setAdapter(adapter);

        Button btnClearHistory = findViewById(R.id.btnClearHistory);
        btnClearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                historyEditor.clear();
                historyEditor.apply();
                historyStrings.clear();
                adapter.notifyDataSetChanged();
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

            Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
            startActivity(intent);

            return true;
        } else if (item.getItemId() == R.id.menuHistory) {

            activeTab = item.getItemId();

            //Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            //startActivity(intent);

            return true;
        } else if (item.getItemId() == R.id.menuChartType) {

            activeTab = item.getItemId();

            Intent intent = new Intent(HistoryActivity.this, ChartTypeActivity.class);
            startActivity(intent);

            return true;
        } else if (item.getItemId() == R.id.menuSettings) {

            activeTab = item.getItemId();

            Intent intent = new Intent(HistoryActivity.this, SettingsActivity.class);
            startActivity(intent);

            return true;
        } else return super.onOptionsItemSelected(item);
    }
}


