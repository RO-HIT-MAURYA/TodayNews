package com.rohitm.today.news.activities;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.common.api.GoogleApiClient;
import com.rohitm.today.news.R;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    int MOBILE_CODE = 31;
    int EMAIL_CODE = 47;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    private void prepareNameListWithImage() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("+54     Argentina");
        arrayList.add("+61     Australia");
        arrayList.add("+43     Austria");
        arrayList.add("+32     Belgium");
        arrayList.add("+55     Brazil");
        arrayList.add("+359    Bulgaria");
        arrayList.add("+1      Canada");
        arrayList.add("+86     China");
        arrayList.add("+57     Colombia");
        arrayList.add("+53     Cuba");
        arrayList.add("+420    Czech Republic");
        arrayList.add("+20     Egypt");
        arrayList.add("+33     France");
        arrayList.add("+49     Germany");
        arrayList.add("+30     Greece");
        arrayList.add("+852    Hong Kong");
        arrayList.add("+36     Hungary");

    }

    // Obtain the phone number from the result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
            credential.getId();  //<-- will need to process phone number string

            Log.e("givenNameIs", "" + credential.getGivenName());
            Log.e("nameIs", "" + credential.getName());
            Log.e("profileUriIs", "" + credential.getProfilePictureUri());
            Log.e("idIs", "" + credential.getId());

            if (requestCode == MOBILE_CODE)
                ((EditText) findViewById(R.id.mobileEditText)).setText("" + credential.getId());
            else
                ((EditText) findViewById(R.id.emailEditText)).setText("" + credential.getId());
        }
    }

    public void onClick(View view) {

        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.CREDENTIALS_API)
                .build();
        if (view.getId() == R.id.mobileEditText) {

            HintRequest hintRequest = new HintRequest.Builder()
                    .setPhoneNumberIdentifierSupported(true)
                    .build();

            PendingIntent intent = Auth.CredentialsApi.getHintPickerIntent(googleApiClient, hintRequest);
            try {
                startIntentSenderForResult(intent.getIntentSender(), MOBILE_CODE, null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            HintRequest hintRequest = new HintRequest.Builder()
                    .setEmailAddressIdentifierSupported(true)
                    .build();

            PendingIntent intent = Auth.CredentialsApi.getHintPickerIntent(googleApiClient, hintRequest);
            try {
                startIntentSenderForResult(intent.getIntentSender(), EMAIL_CODE, null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        }
    }
}
