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

public class MainPage extends AppCompatActivity {

    TextView textView;
    EditText principleAmount;
    Spinner interestRateSpinner;
    Spinner amortizationPeriodSpinner;
    TextView resultBox;

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

        String[] interestRates = {
                "3 Year Fixed Rate 4.84%",
                "5 Year Fixed Rate 4.74%",
                "0.5 Year closed 7.74%",
                "1 Year Closed 7.74%",
                "1 Year Open 8%",
                "2 Year Closed 7.34%",
                "3 Year Closed 6.94%",
                "4 Year Closed 6.74%",
                "5 Year Closed 6.79%",
                "6 Year Closed 6.99%",
                "7 Year Closed 7.1%",
                "10 Year Closed 7.25%"
        };
        ArrayAdapter<String> interestRateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, interestRates);
        interestRateSpinner.setAdapter(interestRateAdapter);

        String[] years = {
                "2 years",
                "3 years",
                "4 years",
                "5 years",
                "6 years",
                "7 years",
                "8 years",
                "9 years",
                "10 years",
                "15 years",
                "20 years",
                "25 years",
                "30 years"
        };
        ArrayAdapter<String> amortizationPeriodAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, years);
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

        double interestRate = Double.parseDouble(interestRateInput.replaceAll("[^0-9]",""));
        int years = Integer.parseInt(amortizationPeriodInput.replaceAll("[^0-9]",""));
        double principle = Double.parseDouble(principleInput);

        double monthlyRate = interestRate / 12 / 100;
        double amortizationMonths = years * 12;

        double emi = ((principle * monthlyRate * Math.pow((1 + monthlyRate), amortizationMonths))/(Math.pow(1 + monthlyRate, amortizationMonths)) - 1);

        DecimalFormat decFormat = new DecimalFormat("#.##");
        String formattedFinalAnswer = decFormat.format(emi);
        String result = formattedFinalAnswer + "/month";
        resultBox.setText(result);
    }
}