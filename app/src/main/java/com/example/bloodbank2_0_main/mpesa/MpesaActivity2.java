package com.example.bloodbank2_0_main.mpesa;

import static com.example.bloodbank2_0_main.mpesa.Constants.BUSINESS_SHORT_CODE;
import static com.example.bloodbank2_0_main.mpesa.Constants.CALLBACKURL;
import static com.example.bloodbank2_0_main.mpesa.Constants.PARTYB;
import static com.example.bloodbank2_0_main.mpesa.Constants.PASSKEY;
import static com.example.bloodbank2_0_main.mpesa.Constants.TRANSACTION_TYPE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bloodbank2_0_main.DashboardActivity;
import com.example.bloodbank2_0_main.R;
import com.example.bloodbank2_0_main.mpesa.model.AccessToken;
import com.example.bloodbank2_0_main.mpesa.model.STKPush;
import com.example.bloodbank2_0_main.mpesa.services.DarajaApiClient;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MpesaActivity2 extends AppCompatActivity implements View.OnClickListener{



        private DarajaApiClient mApiClient;
        private ProgressDialog mProgressDialog;


        EditText mAmount;
        EditText mPhone;
        Button mPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        mPhone = findViewById(R.id.etPhoneNumber);
        mAmount = findViewById(R.id.etAmount);
        mPay = findViewById(R.id.bPay);

        ButterKnife.bind(this);

        mProgressDialog = new ProgressDialog(this);
        mApiClient = new DarajaApiClient();
        mApiClient.setIsDebug(true); //Set True to enable logging, false to disable.

//        mPay.setOnClickListener((View.OnClickListener) this);
        mPay.setOnClickListener(this);

        getAccessToken();
    }

    public void getAccessToken() {
        mApiClient.setGetAccessToken(true);
        mApiClient.mpesaService().getAccessToken().enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(@NonNull Call<AccessToken> call, @NonNull Response<AccessToken> response) {

                if (response.isSuccessful()) {
                    mApiClient.setAuthToken(response.body().accessToken);
                }
            }

            @Override
            public void onFailure(@NonNull Call<AccessToken> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == mPay){
            String phone_number = mPhone.getText().toString();
            String amount = mAmount.getText().toString();
            performSTKPush(phone_number,amount);
        }

    }

    public void performSTKPush(String phone_number,String amount) {
        mProgressDialog.setMessage("Processing your request");
        mProgressDialog.setTitle("Please Wait...");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();
        String timestamp = Utils.getTimeStamp();
        STKPush stkPush = new STKPush(
                BUSINESS_SHORT_CODE,
                Utils.getPassword(BUSINESS_SHORT_CODE, PASSKEY, timestamp),
                timestamp,
                TRANSACTION_TYPE,
                String.valueOf(amount),
                Utils.sanitizePhoneNumber(phone_number),
                PARTYB,
                Utils.sanitizePhoneNumber(phone_number),
                CALLBACKURL,
                "MPESA Android Test", //Account reference
                "Testing"  //Transaction description
        );

        mApiClient.setGetAccessToken(false);

        //Sending the data to the Mpesa API, remember to remove the logging when in production.
        mApiClient.mpesaService().sendPush(stkPush).enqueue(new Callback<STKPush>() {
            @Override
            public void onResponse(@NonNull Call<STKPush> call, @NonNull Response<STKPush> response) {
                mProgressDialog.dismiss();
                try {
                    if (response.isSuccessful()) {
                        Timber.d("post submitted to API. %s", response.body());
                        startActivity(new Intent(MpesaActivity2.this, DashboardActivity.class));
                    } else {
                        Timber.e("Response %s", response.errorBody().string());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<STKPush> call, @NonNull Throwable t) {
                mProgressDialog.dismiss();
                Timber.e(t);
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
//        super.onPointerCaptureChanged(hasCapture);
    }
}