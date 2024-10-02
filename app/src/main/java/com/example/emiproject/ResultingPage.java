package com.example.emiproject;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;


public class ResultingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resulting_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView emiResult = findViewById(R.id.resultingEMI);
        TextView principleResult = findViewById(R.id.principleResult);
        TextView interestRateResult = findViewById(R.id.interestRateResult);
        TextView amortizationResult = findViewById(R.id.amortizationPeriodResult);
        TextView frequencyResult = findViewById(R.id.frequencyResult);
        Button restart = findViewById(R.id.restart);

        String emi = getIntent().getStringExtra("EMI");
        double principle = getIntent().getDoubleExtra("Principle", 0.0);
        double monthlyRate = getIntent().getDoubleExtra("Interest Rate", 0.0);
        int amortizationMonths = getIntent().getIntExtra("Months of Amortization", 0);
        String frequency = "Monthly";

        String formattedEMI = "$" + emi;
        String formattedPrinciple = "Principle: " + principle;
        String formattedInterestRate = "Interest Rate: " + monthlyRate + "% yearly";
        String formattedAmortizationMonths = "For " + amortizationMonths + " months";
        String formattedFrequency = "Payed " + frequency;

        emiResult.setText(formattedEMI);
        principleResult.setText(formattedPrinciple);
        interestRateResult.setText(formattedInterestRate);
        amortizationResult.setText(formattedAmortizationMonths);
        frequencyResult.setText(formattedFrequency);

        restart.setOnClickListener(v -> {
            Intent returnToMainPage = new Intent(ResultingPage.this, MainPage.class);
            startActivity(returnToMainPage);
            finish();
        });


    }
}