package com.itechro.sampleapp;

import android.app.Activity;
import android.widget.Toast;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class EmptyClient extends WebSocketClient {

    public String outMessage;

    private Activity context;

    public void setContext(Activity context) {
        this.context = context;
    }

    public void setOutMessage(String outMessage) {
        this.outMessage = outMessage;
    }

    public EmptyClient(URI serverUri, Draft draft) {
        super(serverUri, draft);
    }

    public EmptyClient(URI serverURI) {
        super(serverURI);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        send(outMessage);
        System.out.println("new connection opened");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("closed with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onMessage(String message) {
        System.out.println("received message: " + message);

        Map<String, String> map = new HashMap<String, String>();

        String[] strArray = message.split("&");
        for (int i = 0; i < strArray.length; i++) {

            String data = strArray[i];
            String[] keyValue = data.split("=");
            if (keyValue.length > 1) {
                map.put(keyValue[0], keyValue[1]);
            } else {
                map.put(keyValue[0], "");
            }
        }

        if (context != null) {
            String statusString = map.get(AppConstants.PaymentInfo.PaymentStatus.getDescription());
            final String errorMessageString = map.get(AppConstants.PaymentInfo.PaymentErrorMessage.getDescription());
            String errorCodeString = map.get(AppConstants.PaymentInfo.PaymentErrorCode.getDescription());
            String referenceString = map.get(AppConstants.PaymentInfo.PaymentReference.getDescription());
            String amountString = map.get(AppConstants.PaymentInfo.PaymentAmount.getDescription());
            String sourceString = map.get(AppConstants.PaymentInfo.PaymentSource.getDescription());

            final AppConstants.PaymentStatus paymentStatus = AppConstants.PaymentStatus.getByDescription(statusString);

            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    switch (paymentStatus) {
                        case Success:
                            Toast.makeText(context, "Payment success", Toast.LENGTH_SHORT).show();
                            break;
                        case Fail:
                            Toast.makeText(context, "Payment fail", Toast.LENGTH_SHORT).show();
                            break;
                        case Error:
                            Toast.makeText(context, errorMessageString, Toast.LENGTH_SHORT).show();
                            break;

                    }
                }
            });
        }
    }


    @Override
    public void onMessage(ByteBuffer message) {
        System.out.println("received ByteBuffer");
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("an error occurred:" + ex);
    }
}