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
import java.util.LinkedHashMap;

import android.content.Intent;

public class MainPage extends AppCompatActivity {

    TextView textView;
    EditText principleAmount;
    Spinner interestRateSpinner;
    Spinner amortizationPeriodSpinner;
    TextView resultBox;
    LinkedHashMap<String, Double> interests;
    LinkedHashMap<String, Double> yearsMap;

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

        interests = new LinkedHashMap<>();
        interests.put("0.5 Year closed 7.74%", 7.74);
        interests.put("3 Year Fixed Rate 4.84%", 4.84);
        interests.put("5 Year Fixed Rate 4.74%", 4.74);
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

        yearsMap = new LinkedHashMap<>();
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
    // function is called when "calculate now" button is pressed (using onClick)
    public void calculateEMI(View view) {
        String principleInput = principleAmount.getText().toString();
        String principleError = "Please enter a valid principle value";

        if (principleInput.isEmpty()) {
            resultBox.setText(principleError);
        }

        String interestRateInput = interestRateSpinner.getSelectedItem().toString(); // get the input from the interest rate spinner (dropdown)
        String amortizationPeriodInput = amortizationPeriodSpinner.getSelectedItem().toString(); // get the input from the amortization period spinne

        double yearlyInterestRate = interests.get(interestRateInput); // use the hashmap to get the interest rate, where the key is the string selected
        double years = yearsMap.get(amortizationPeriodInput); // use the hashmap to get the years, where the key is the string selected
        double principle = Double.parseDouble(principleInput); // get the value of the principle in double format

        double monthlyInterestRate = yearlyInterestRate / 12.0 / 100.0; // converted to monthly interest rate
        int amortizationMonths = (int)years * 12; // converted to months from years

        // calculate the EMI
        double emi =
                principle *
                (monthlyInterestRate * Math.pow(1 + monthlyInterestRate, amortizationMonths)) /
                (Math.pow(1 + monthlyInterestRate, amortizationMonths) - 1);

        DecimalFormat decFormat = new DecimalFormat("#.##"); // create a format with only two decimal points
        String formattedFinalAnswer = decFormat.format(emi); // format the EMI final answer
        String result = formattedFinalAnswer + "/month";

        Intent finalPage = new Intent(MainPage.this, ResultingPage.class);
        finalPage.putExtra("EMI", result);
        finalPage.putExtra("Principle", principle);
        finalPage.putExtra("Interest Rate", yearlyInterestRate);
        finalPage.putExtra("Months of Amortization", amortizationMonths);

        startActivity(finalPage);
    }
}