package com.example.emiproject;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.text.DecimalFormat;
import java.util.HashMap;

import android.content.Intent;

public class MainPage extends AppCompatActivity {

    TextView textView;
    EditText principleAmount;
    Spinner interestRateSpinner;
    Spinner amortizationPeriodSpinner;
    TextView resultBox;
    HashMap<String, Double> interests;
    HashMap<String, Double> yearsMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textView = findViewById(R.id.pageHeader);
        principleAmount = findViewById(R.id.principleAmountInput);
        interestRateSpinner = findViewById(R.id.spinnerInterest);
        amortizationPeriodSpinner = findViewById(R.id.spinnerAmortizationPeriod);
        resultBox = findViewById(R.id.resultBox);

        interests = new HashMap<>();
        interests.put("3 Year Fixed Rate 4.84%", 4.84);
        interests.put("5 Year Fixed Rate 4.74%", 4.74);
        interests.put("0.5 Year closed 7.74%", 7.74);
        interests.put("1 Year Closed 7.74%", 7.74);
        interests.put("1 Year Open 8%", 8.0);
        interests.put("2 Year Closed 7.34%", 7.34);
        interests.put("3 Year Closed 6.94%", 6.94);
        interests.put("4 Year Closed 6.74%", 6.74);
        interests.put("5 Year Closed 6.79%", 6.79);
        interests.put("6 Year Closed 6.99%", 6.99);
        interests.put("7 Year Closed 7.1%", 7.1);
        interests.put("10 Year Closed 7.25%", 7.25);

        ArrayAdapter<String> interestRateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, interests.keySet().toArray(new String[0]));
        interestRateSpinner.setAdapter(interestRateAdapter);

        yearsMap = new HashMap<>();
        yearsMap.put("2 Years", 2.0);
        yearsMap.put("3 Years", 3.0);
        yearsMap.put("4 Years", 4.0);
        yearsMap.put("5 Years", 5.0);
        yearsMap.put("6 Years", 6.0);
        yearsMap.put("7 Years", 7.0);
        yearsMap.put("8 Years", 8.0);
        yearsMap.put("9 Years", 9.0);
        yearsMap.put("10 Years", 10.0);
        yearsMap.put("15 Years", 15.0);
        yearsMap.put("20 Years", 20.0);
        yearsMap.put("30 Years", 30.0);

        ArrayAdapter<String> amortizationPeriodAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, yearsMap.keySet().toArray(new String[0]));
        amortizationPeriodSpinner.setAdapter(amortizationPeriodAdapter);

    }
    public void calculateEMI(View view) {
        String principleInput = principleAmount.getText().toString();
        String principleError = "Please enter a valid principle value";

        if (principleInput.isEmpty()) {
            resultBox.setText(principleError);
        }

        String interestRateInput = interestRateSpinner.getSelectedItem().toString();
        String amortizationPeriodInput = amortizationPeriodSpinner.getSelectedItem().toString();

        double yearlyInterestRate = interests.get(interestRateInput);
        double years = yearsMap.get(amortizationPeriodInput);
        double principle = Double.parseDouble(principleInput);

        double monthlyInterestRate = yearlyInterestRate / 12.0 / 100.0;
        int amortizationMonths = (int)years * 12;

        System.out.println("principle: " + principle);
        System.out.println("monthly " + monthlyInterestRate);
        System.out.println("amortization months: " + amortizationMonths);

        double emi = principle * (monthlyInterestRate * Math.pow(1+monthlyInterestRate, amortizationMonths)) / (Math.pow(1 + monthlyInterestRate, amortizationMonths) - 1);

        DecimalFormat decFormat = new DecimalFormat("#.##");
        String formattedFinalAnswer = decFormat.format(emi);
        String result = formattedFinalAnswer + "/month";

        String formattedYearlyInterestRate = decFormat.format(yearlyInterestRate);
        String formattedInterestRate = decFormat.format(monthlyInterestRate);

        Intent finalPage = new Intent(MainPage.this, ResultingPage.class);
        finalPage.putExtra("EMI", result);
        finalPage.putExtra("Principle", principle);
        finalPage.putExtra("Interest Rate", formattedYearlyInterestRate);
        finalPage.putExtra("Months of Amortization", amortizationMonths);

        startActivity(finalPage);
    }
}