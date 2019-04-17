package com.itechro.sampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;
import org.java_websocket.client.WebSocketClient;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

    static final int PAY_USING_IPOS = 1001;  // The request code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText amount = (EditText) findViewById(R.id.edit_text_amount);
        Button payButton = (Button) findViewById(R.id.button_pay);
        Button paySocketButton = (Button) findViewById(R.id.button_pay_socket);
        Button rejectButton = (Button) findViewById(R.id.button_clear);

        Button alipayButton = (Button) findViewById(R.id.button_alipay);
        Button alipaySocketButton = (Button) findViewById(R.id.button_alipay_socket);

        Button weChatButton = (Button) findViewById(R.id.button_wechat);
        Button weChatSocketButton = (Button) findViewById(R.id.button_wechat_socket);


        final EditText editTextServerIp = findViewById(R.id.edit_text_server_ip);
        final EditText editTextServerPort = findViewById(R.id.edit_text_server_port);

        editTextServerIp.setText("192.168.1.15");
        editTextServerPort.setText("8888");

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callIntent(amount, AppConstants.PaymentType.Card);
            }
        });

        alipayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callIntent(amount, AppConstants.PaymentType.AliPay);
            }
        });

        weChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callIntent(amount, AppConstants.PaymentType.WeChatPay);
            }
        });

        paySocketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callSocket(editTextServerIp, editTextServerPort, amount, AppConstants.PaymentType.Card);
            }
        });

        alipaySocketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callSocket(editTextServerIp, editTextServerPort, amount, AppConstants.PaymentType.AliPay);
            }
        });

        weChatSocketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callSocket(editTextServerIp, editTextServerPort, amount, AppConstants.PaymentType.WeChatPay);
            }
        });


        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount.setText("");
            }
        });
    }

    private void callSocket(EditText editTextServerIp, EditText editTextServerPort, EditText amount, AppConstants.PaymentType aliPay) {
        final String hostIp = editTextServerIp.getText().toString();
        final String hostPort = editTextServerPort.getText().toString();

        if (!StringUtils.isEmpty(hostIp) && !StringUtils.isEmpty(hostPort)) {
            try {

                String serverUrl = "ws://" + hostIp + ":" + hostPort;

                StringBuilder builder = new StringBuilder();
                builder.append(AppConstants.PaymentInfo.PaymentType.getDescription()).append("=").append(aliPay.getDescription()).append("&")
                        .append(AppConstants.PaymentInfo.PaymentAmount.getDescription()).append("=").append(amount.getText().toString()).append("&")
                        .append(AppConstants.PaymentInfo.PaymentSource.getDescription()).append("=").append("YOUR_APP_NAME/IDENTIFIER").append("&")
                        .append(AppConstants.PaymentInfo.PaymentReference.getDescription()).append("=").append("YOUR_TRANSACTION_REFERENCE");

                WebSocketClient client = new EmptyClient(new URI(serverUrl));

                ((EmptyClient) client).setContext(MainActivity.this);
                ((EmptyClient) client).setOutMessage(builder.toString());

                client.connect();

            } catch (Exception e) {
                // Handle activity not found exception
                Toast.makeText(MainActivity.this, "Payment app not exists", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void callIntent(EditText amount, AppConstants.PaymentType aliPay) {
        try {

            Intent sendIntent = new Intent();
            sendIntent.setAction(AppConstants.EXTERNAL_PAYMENT_INTENT_NAME);

            sendIntent.putExtra(AppConstants.PaymentInfo.PaymentType.getDescription(),
                    aliPay.getDescription());

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
        } catch (Exception e) {
            // Handle activity not found exception
            Toast.makeText(MainActivity.this, "Payment app not exists", Toast.LENGTH_SHORT).show();
        }
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
                String transactionIdString = bundle.getString(AppConstants.PaymentInfo.PaymentTransactionId.getDescription(), "");
                String tcString = bundle.getString(AppConstants.PaymentInfo.PaymentTc.getDescription(), "");
                String cardNoString = bundle.getString(AppConstants.PaymentInfo.PaymentCardNo.getDescription(), "");
                String expiryDateString = bundle.getString(AppConstants.PaymentInfo.PaymentCardExpiry.getDescription(), "");
                String cardTypeString = bundle.getString(AppConstants.PaymentInfo.PaymentCardType.getDescription(), "");
                String terminalIdString = bundle.getString(AppConstants.PaymentInfo.PaymentTerminalId.getDescription(), "");

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
