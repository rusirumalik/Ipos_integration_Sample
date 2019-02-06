package com.itechro.sampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static final int PAY_USING_IPOS = 1001;  // The request code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText amount = (EditText) findViewById(R.id.edit_text_amount);
        Button payButton = (Button) findViewById(R.id.button_pay);
        Button rejectButton = (Button) findViewById(R.id.button_clear);

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    Intent sendIntent = new Intent();
                    sendIntent.setAction(AppConstants.EXTERNAL_PAYMENT_INTENT_NAME);

                    sendIntent.putExtra(AppConstants.PaymentInfo.PaymentType.getDescription(),
                            AppConstants.PaymentType.Card.getDescription());

                    sendIntent.putExtra(AppConstants.PaymentInfo.PaymentAmount.getDescription(),
                            amount.getText().toString()); // In cents, Not mandatory
                    // 1. If amount not sent across, payment app will load Amount input screen
                    // 2. If amount sent, payment app will load card swipe screen

                    sendIntent.putExtra(AppConstants.PaymentInfo.PaymentSource.getDescription(),
                            "YOUR_APP_NAME/IDENTIFIER");

                    sendIntent.putExtra(AppConstants.PaymentInfo.PaymentReference.getDescription(),
                            "YOUR_TRANSACTION_REFERENCE");

                    sendIntent.setType("text/plain");
                    startActivityForResult(sendIntent, PAY_USING_IPOS);
                }
                catch (Exception e) {
                    // Handle activity not found exception
                    Toast.makeText(MainActivity.this, "Payment app not exists", Toast.LENGTH_SHORT).show();
                }
            }
        });

        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount.setText("");
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PAY_USING_IPOS) {
            if (resultCode == RESULT_OK) {

                Bundle bundle = data.getExtras();

                String statusString = bundle.getString(AppConstants.PaymentInfo.PaymentStatus.getDescription(), "");
                String errorMessageString = bundle.getString(AppConstants.PaymentInfo.PaymentErrorMessage.getDescription(), "");
                String errorCodeString = bundle.getString(AppConstants.PaymentInfo.PaymentErrorCode.getDescription(), "");
                String referenceString = bundle.getString(AppConstants.PaymentInfo.PaymentReference.getDescription(), "");
                String amountString = bundle.getString(AppConstants.PaymentInfo.PaymentAmount.getDescription(), "");
                String sourceString = bundle.getString(AppConstants.PaymentInfo.PaymentSource.getDescription(), "");

                AppConstants.PaymentStatus paymentStatus = AppConstants.PaymentStatus.getByDescription(statusString);

                switch (paymentStatus) {
                    case Success:
                        Toast.makeText(this, "Payment success", Toast.LENGTH_SHORT).show();
                        break;
                    case Fail:
                        Toast.makeText(this, "Payment fail", Toast.LENGTH_SHORT).show();
                        break;
                    case Error:
                        Toast.makeText(this, errorMessageString, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }
    }
}
