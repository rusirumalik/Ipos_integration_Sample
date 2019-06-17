# Ipos_integration_Sample

For Payment type and Error codes : 
        Ipos_integration_Sample/app/src/main/java/com/itechro/sampleapp/AppConstants.java
      
# Return Codes

Return Code | Description 
-- | --
00| Approved or completed successfully
01| Refer to the card issuer.
02| Refer to the card issuer’s special conditions.
03| Invalid merchant.
04| Pick-up.
05| Do not honor.
06| Error.
08| Honor with identification.( successfully)
11| Approved (VIP). (successfully)
12| Invalid transaction.
13| Invalid amount.
14| Invalid card number (no such number).
15| No such issuer.
20| Invalid response.
21| No action taken.
22| Suspected malfunction.
30| Format error.
31| Bank not supported by switch.
33| Expired card, capture.
34| Suspected fraud, capture.
35| Card acceptor contact acquirer, capture.
36| Restricted card, capture.
37| Card acceptor call acquirer security, capture.
38| Allowable PIN tries exceeded, capture.
39| No credit account.
40| Requested function not supported.
41| Lost card, capture.
42| No universal account.
43| Stolen card, capture.
51| Do not honor.
52| No checking account.
53| No savings account.
54| Expired card, decline.
55| Incorrect PIN.
56| No card record.
57| Transaction not permitted to cardholder.
58| Transaction not permitted to terminal.
61| Exceeds withdrawal amount limit.
62| Restricted card, decline.
63| Security violation.
65| Exceeds withdrawal frequency limit (Please insert the card).
66| Card acceptor call acquirer’s security department.
75| Allowable number of PIN tries exceeded, decline.
91| Issuer or switch is inoperative.
92| No routing available.
93| Transaction cannot be completed. Violation of law.
94| Duplicate transmission
95| Reconcile error.
96| System malfunction.


# Alipay/We chat

Return | Description 
-- | --
SUCCESS | Payment successful
REFUND | Order to be refunded
NOTPAY | Order not paid
CLOSED | Order closed
REVOKED |order revoked
USERPAYING |consumer paying
PAYERROR |Payment failed (payment status failed to be returned by bank or other reasons)
