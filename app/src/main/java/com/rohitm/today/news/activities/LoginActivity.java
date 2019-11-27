package com.rohitm.today.news.activities;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.common.api.GoogleApiClient;
import com.rohitm.today.news.R;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity
{
    int CONSTANT_CODE = 31;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prepareNameListWithImage();
    }

    private void prepareNameListWithImage() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("+54     Argentina");
        arrayList.add("+61     Australia");
        arrayList.add("+43     Austria");

    }

    private void requestHint()
    {
        GoogleApiClient googleApiClient =  new GoogleApiClient.Builder(this)
                .addApi(Auth.CREDENTIALS_API)
                .build();

        HintRequest hintRequest = new HintRequest.Builder()
                .setPhoneNumberIdentifierSupported(false)
                .setEmailAddressIdentifierSupported(true)
                .build();

        PendingIntent intent = Auth.CredentialsApi.getHintPickerIntent(googleApiClient, hintRequest);
        try {
            startIntentSenderForResult(intent.getIntentSender(), CONSTANT_CODE, null, 0, 0, 0);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

   /* private void askNumber() {
        PendingIntent intent = Auth.CredentialsApi.getHintPickerIntent(
                googleApiClient, new HintRequest.Builder()
                        .setPhoneNumberIdentifierSupported(true)
                        .setEmailAddressIdentifierSupported(false)
                        .build());
        try {
            startIntentSenderForResult(intent.getIntentSender(),
                    REQUEST_CODE_RESOLVE_PHONE, null, 0, 0, 0);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }*/

    // Obtain the phone number from the result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CONSTANT_CODE) {
            if (resultCode == RESULT_OK) {
                Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                credential.getId();  //<-- will need to process phone number string

                String string = credential.getId();
                Log.e("phoneNumberIs",string+"");
            }
        }
    }

    public void onClick(View view) {
        requestHint();
    }
}
