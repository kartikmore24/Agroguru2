package com.example.agroguru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Analysis extends AppCompatActivity {

    PieChart pieChart;
    Spinner mSpinner, ySpinner;
    Button show;

    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_analysis);

        database = FirebaseFirestore.getInstance();
        mSpinner = findViewById(R.id.mSpinner);
        ySpinner = findViewById(R.id.ySpinner);
        pieChart = findViewById(R.id.piechart);
        show = findViewById(R.id.show);

        setUpPieChart();

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String month = mSpinner.getSelectedItem().toString();
                String year = ySpinner.getSelectedItem().toString();

                if (!month.equals("Month") && !year.equals("Year")) {
                    loadPieChartData(month, year);
                }
                else {
                    Toast.makeText(Analysis.this, "Select Month and Year", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setUpPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Crops Grown");
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    private void loadPieChartData(String month, String year) {
            database.collection("harvestDetails")
                    .whereEqualTo("month", month)
                    .whereEqualTo("year", year)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                Map<String, Integer> map = new HashMap<String, Integer>();
                                ArrayList<PieEntry> entries = new ArrayList<>();
                                Integer totalProduction = 0;

                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String cropType = document.getData().get("cropType").toString();
                                    map.put(cropType, 0);
                                }
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String cropType = document.getData().get("cropType").toString();
                                    Integer production = Integer.parseInt(document.getData().get("production").toString());
                                    totalProduction = totalProduction + production;
                                    map.put(cropType, (map.get(cropType) + production));
                                }

                                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                                    entries.add(new PieEntry(entry.getValue().floatValue() / totalProduction.floatValue(), entry.getKey()));
                                }

                                ArrayList<Integer> colors = new ArrayList<>();
                                for (int color: ColorTemplate.MATERIAL_COLORS) {
                                    colors.add(color);
                                }

                                for (int color: ColorTemplate.VORDIPLOM_COLORS) {
                                    colors.add(color);
                                }

                                PieDataSet dataSet = new PieDataSet(entries, "Crops");
                                dataSet.setColors(colors);

                                PieData data = new PieData(dataSet);
                                data.setDrawValues(true);
                                data.setValueFormatter(new PercentFormatter(pieChart));
                                data.setValueTextSize(12f);
                                data.setValueTextColor(Color.BLACK);

                                pieChart.setData(data);
                                pieChart.invalidate();

                                pieChart.animateY(1400, Easing.EaseInOutQuad);
                            }
                            else {
                                pieChart.clear();
                                Toast.makeText(Analysis.this, "No data available for Selected Time Period", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

    }
}