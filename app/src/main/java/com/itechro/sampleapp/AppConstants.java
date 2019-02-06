package com.itechro.sampleapp;

public class AppConstants {

    public static final String EXTERNAL_PAYMENT_INTENT_NAME = "com.shangri-la.PAY";

    public enum PaymentType {
        Card("CARD"),
        WeChatPay("WE_CHAT_PAY"), //NOT SUPPORTED YET
        AliPay("ALI_PAY"); //NOT SUPPORTED YET

        private String description;

        PaymentType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum PaymentInfo {

        PaymentType("PAYMENT_TYPE"),
        PaymentAmount("PAYMENT_AMOUNT"),
        PaymentSource("PAYMENT_SOURCE"),
        PaymentReference("PAYMENT_REFERENCE"),
        PaymentStatus("PAYMENT_STATUS"),
        PaymentErrorCode("ERROR_CODE"),
        PaymentErrorMessage("ERROR_MESSAGE");

        private String description;

        PaymentInfo(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum PaymentStatus {
        Success("SUCCESS"),
        Fail("FAIL"),
        Error("ERROR");

        private String description;

        PaymentStatus(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public static PaymentStatus getByDescription(String description) {
            PaymentStatus paymentStatus = null;

            for(PaymentStatus pt: PaymentStatus.values()) {
                if(pt.getDescription().equals(description)) {
                    paymentStatus = pt;
                    break;
                }
            }

            return paymentStatus;
        }
    }

    public enum PaymentError {

        None("", ""),
        Required("01", "Required"),
        InvalidInput("02", "Invalid Input"),
        InvalidPaymentType("03", "Invalid Payment Type"),
        PaymentTypeNotSupport("04", "Payment Type Not Support"),
        TransactionFailed("05", "Transaction Failed");

        private String errorCode;
        private String errorMessage;

        public String getErrorCode() {
            return errorCode;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        PaymentError(String errorCode, String errorMessage) {
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
        }
    }


}
