package com.company.calculatorApp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private TextView txtViewHistory, txtViewResults;
    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    private Button btnADD, btnSUB, btnMulti, btnDivide;
    private Button  btnAC, btnDEL, btnDOT, btnEquals;
    private String number = null;

    private double firstNumber = 0;
    private double lastNumber =0;

    private final DecimalFormat myDecimalFormatter = new DecimalFormat("######.######");
    private String history, currentResult;
    private boolean isDot = true;
    private String status = null;
    private boolean operator = true;
    private boolean btnACcontrol = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtViewHistory = findViewById(R.id.textViewHistory);
        txtViewResults = findViewById(R.id.textViewResult);

        btn0 =findViewById(R.id.btn0);
        btn1 =findViewById(R.id.btn1);
        btn2 =findViewById(R.id.btn2);
        btn3 =findViewById(R.id.btn3);
        btn4 =findViewById(R.id.btn4);
        btn5 =findViewById(R.id.btn5);
        btn6 =findViewById(R.id.btn6);
        btn7 =findViewById(R.id.btn7);
        btn8 =findViewById(R.id.btn8);
        btn9 =findViewById(R.id.btn9);

        btnADD =findViewById(R.id.btnAdd);
        btnSUB =findViewById(R.id.btnMinus);
        btnMulti =findViewById(R.id.btnMultiplication);
        btnDivide =findViewById(R.id.btnDiv);

        btnDEL =findViewById(R.id.btnDEL);
        btnAC =findViewById(R.id.btnAC);
        btnDOT =findViewById(R.id.btnDot);
        btnEquals =findViewById(R.id.btnEquals);

        //Number Buttons
       btn0.setOnClickListener(new View.OnClickListener() {
          @Override
           public void onClick(View v) {
               numberClick("0");
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberClick("1");
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberClick("2");
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberClick("3");
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberClick("4");
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberClick("5");
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberClick("6");
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberClick("7");
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberClick("8");
            }
        });
        btn9.setOnClickListener(v -> numberClick("9"));

        //Function Buttons: +, -, *, /
        btnADD.setOnClickListener(v -> {
            if(operator){
                history = txtViewHistory.getText().toString();
                currentResult = txtViewResults.getText().toString();
                txtViewHistory.setText(String.format("%s%s+", history, currentResult));
                if(Objects.equals(status, "multiplication")){
                    multiplication();
                } else if (Objects.equals(status, "division")) {
                    divide();
                } else if (Objects.equals(status, "subtraction")) {
                    minus();
                }else{
                    addition();
                }
            }
            status = "addition";
            operator = false;
            number = null;
        });
        btnSUB.setOnClickListener(v -> {
            if (operator) {
                history = txtViewHistory.getText().toString();
                currentResult = txtViewResults.getText().toString();
                txtViewHistory.setText(String.format("%s%s-", history, currentResult));

                if (Objects.equals(status, "addition")) {
                    addition();
                } else if (Objects.equals(status, "multiplication")) {
                    multiplication();
                } else if (Objects.equals(status, "division")) {
                    divide();
                } else {
                    minus();
                }
            }
            status = "subtraction";
            operator = false;
            number = null;
        });
        btnMulti.setOnClickListener(v -> {
            history = txtViewHistory.getText().toString();
            currentResult = txtViewResults.getText().toString();
            txtViewHistory.setText(String.format("%s%s*", history, currentResult));

            if (operator) {
                if (Objects.equals(status, "addition")) {
                    divide();
                } else if (Objects.equals(status, "division")) {
                    addition();
                } else if (Objects.equals(status, "subtraction")) {
                    minus();
                } else {
                    multiplication();
                }
            }
            status = "multiplication";
            operator = false;
            number = null;
        });
        btnDivide.setOnClickListener(v -> {
            history = txtViewHistory.getText().toString();
            currentResult = txtViewResults.getText().toString();
            txtViewHistory.setText(String.format("%s%s/", history, currentResult));

            if(operator){
                if(Objects.equals(status, "multiplication")){
                    multiplication();
                }else if(Objects.equals(status, "addition")){
                    addition();
                }else if(Objects.equals(status, "subtraction")){
                    minus();
                }else{
                    divide();
                }
            }
            status = "division";
            operator = false;
            number = null;
        });

        //Operator Buttons
        btnAC.setOnClickListener(v -> {
            status = null;
            operator = false;
            txtViewResults.setText("0");
            txtViewHistory.setText("");
            firstNumber = 0;
            lastNumber = 0;
            isDot = true;
            btnACcontrol = true;
        });
        btnDEL.setOnClickListener(v -> {
            if(btnACcontrol){
                txtViewResults.setText("0");
            }else{
                number = number.substring(0, number.length() - 1);
                if(number.isEmpty()){
                    btnDEL.setClickable(false);
                }else if (number.contains(".")) {
                    isDot = false;
                }else{
                    isDot = true;
                }
                txtViewResults.setText(number);
            }



        });
        btnEquals.setOnClickListener(v -> {
            if(operator){
                if(Objects.equals(status, "Multiplication")){
                    multiplication();
                } else if (Objects.equals(status, "division")) {
                        divide();
                } else if (Objects.equals(status, "addition")) {
                    addition();
                } else if (Objects.equals(status, "subtraction")) {
                    minus();
                }else{
                    firstNumber = Double.parseDouble(txtViewResults.getText().toString());
                }
            }
            operator = false;
            status = null;
            number = null;
            firstNumber = 0;
            lastNumber = 0;
        });
        btnDOT.setOnClickListener(v->{
            if(isDot){
                if(number == null){
                    number = "0.";
                }else{
                    number = number + ".";
                }
            }
            txtViewResults.setText(number);
            isDot = false;
        });

    }
    public void numberClick(String view){
       if(number == null){
           number = view;
       }
      else{
          number = number + view;
       }
       txtViewResults.setText(number);
       operator = true;
       btnACcontrol = false;
       btnDEL.setClickable(true);
   }
   public void addition(){
        lastNumber = Double.parseDouble(txtViewResults.getText().toString());
        firstNumber = firstNumber + lastNumber;
        txtViewResults.setText(myDecimalFormatter.format(firstNumber));
        isDot = true;
   }
   public void minus(){
        if(firstNumber == 0){
            firstNumber = Double.parseDouble(txtViewResults.getText().toString());
        }
        else{
            lastNumber = Double.parseDouble(txtViewResults.getText().toString());
            firstNumber = firstNumber - lastNumber;
        }
       txtViewResults.setText(myDecimalFormatter.format(firstNumber));
        isDot = true;
   }
   public void multiplication(){
         if(firstNumber == 0){
             firstNumber = 1;
             lastNumber = Double.parseDouble(txtViewResults.getText().toString()); //the textViewResult will be "0" ZERO b/c the firstNumber was made "1" and now "0" was passed to the lastNumber
         }
         else{
             lastNumber = Double.parseDouble(txtViewResults.getText().toString());
             firstNumber = firstNumber * lastNumber;
         }
       txtViewResults.setText(myDecimalFormatter.format(firstNumber));
         isDot = true;
   }
   public void divide(){
        if(firstNumber == 0){
            lastNumber = Double.parseDouble(txtViewResults.getText().toString()); // this placed "Zero" in the lastNumber. :)
            firstNumber = lastNumber; //this is since the number passed to lastNumber = 0, we make firstNumber = lastNumber = 0.

        }else{
            lastNumber = Double.parseDouble(txtViewResults.getText().toString());
            firstNumber = firstNumber/lastNumber;
        }
       txtViewResults.setText(myDecimalFormatter.format(firstNumber));
        isDot = true;
   }


}