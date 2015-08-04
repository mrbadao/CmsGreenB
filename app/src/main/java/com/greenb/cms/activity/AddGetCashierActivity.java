package com.greenb.cms.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.greenb.cms.R;
import com.greenb.cms.httpinterface.AddCashierInterface;
import com.greenb.cms.httptask.HttpAddCashierRequest;
import com.greenb.cms.models.Cashier;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class AddGetCashierActivity extends ActionBarActivity implements AddCashierInterface {
    private EditText mLoginid;
    private EditText mPassword;
    private EditText mRePassword;
    private EditText mDisplayName;
    private EditText mPhone;
    private EditText mEmail;
    private CheckBox mStatus;
    private EditText mAddress;
    private HashMap<String, EditText> mHashMapAttributes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cashier);
        mHashMapAttributes = new HashMap<String, EditText>();

        //set properties view
        mLoginid = (EditText) findViewById(R.id.editTxt_loginid);
        mHashMapAttributes.put("loginid", mLoginid);
        mPassword = (EditText) findViewById(R.id.editTxt_pwd);
        mHashMapAttributes.put("password", mPassword);
        mRePassword = (EditText) findViewById(R.id.editTxt_pwd_conf);
        mDisplayName = (EditText) findViewById(R.id.editTxt_display_name);
        mHashMapAttributes.put("display_name", mDisplayName);
        mPhone = (EditText) findViewById(R.id.editTxt_phone);
        mHashMapAttributes.put("phone", mPhone);
        mEmail = (EditText) findViewById(R.id.editTxt_email);
        mHashMapAttributes.put("email", mEmail);
        mAddress = (EditText) findViewById(R.id.editTxt_address);
        mHashMapAttributes.put("address", mAddress);
        mStatus = (CheckBox) findViewById(R.id.checkBox_status);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_cashier, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_save:
                atempSaveCashier();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Save Cashier to DB
    private void atempSaveCashier() {
        View focusView;
        if ((focusView = validateAttributes()) != null) {
            focusView.requestFocus();
        } else {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(AddGetCashierActivity.this);
            String uid = sharedPref.getString("com.greenb.cms.user.uid", "");
            String token = sharedPref.getString("com.greenb.cms.user.token", "");
            JSONObject jsonCashier = new JSONObject();
            try {
                jsonCashier.put("loginid", mLoginid.getText().toString());
                jsonCashier.put("password", mPassword.getText().toString());
                jsonCashier.put("display_name", mDisplayName.getText().toString());
                jsonCashier.put("phone", mPhone.getText().toString());

                String status = mStatus.isChecked() ? "1" : "0";
                jsonCashier.put("status", status);

                if (!TextUtils.isEmpty(mAddress.getText().toString()))
                    jsonCashier.put("address", mAddress.getText().toString());
                if (!TextUtils.isEmpty(mEmail.getText().toString()))
                    jsonCashier.put("email", mEmail.getText().toString());

                new HttpAddCashierRequest(AddGetCashierActivity.this, uid + " " + token, this).execute(jsonCashier);
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(AddGetCashierActivity.this, "Can not add new Cashier.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // validate model Attributes
    private View validateAttributes() {
        View focusView = null;

        String email = mEmail.getText().toString();
        mEmail.setError(null);
        if (!TextUtils.isEmpty(email) && !email.contains("@")) {
            mEmail.setError("Let input a validate email.");
            focusView = mEmail;
        }

        String phone = mPhone.getText().toString();
        mPhone.setError(null);
        if (TextUtils.isEmpty(phone)) {
            mPhone.setError("Let input a validate phone number.");
            focusView = mPhone;
        }

        String displayname = mDisplayName.getText().toString();
        mDisplayName.setError(null);
        if (TextUtils.isEmpty(displayname)) {
            mDisplayName.setError("Let input a validate name.");
            focusView = mDisplayName;
        }

        String password = mPassword.getText().toString();
        mPassword.setError(null);
        if (TextUtils.isEmpty(password) || password.length() <= 5 || password.length() > 32) {
            mPassword.setError("Password too short (min length 6 characters, max length 32 characters).");
            focusView = mPassword;
        }

        String rePassword = mRePassword.getText().toString();
        mRePassword.setError(null);
        if (!rePassword.equals(password)) {
            mRePassword.setError("Two password not match.");
            focusView = mRePassword;
        }

        String loginid = mLoginid.getText().toString();
        mLoginid.setError(null);
        if (TextUtils.isEmpty(loginid) || loginid.length() <= 5 || loginid.length() > 20) {
            mLoginid.setError("Login id too short (min length 6 characters, max length 20 characters).");
            focusView = mLoginid;
        }

        return focusView;
    }

    @Override
    public void onCashierAddSuccessly(Cashier cashier) {
        Toast.makeText(AddGetCashierActivity.this, "You have just been added '" + cashier.display_name + "' as a new Cashier.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCashierValidateFaild(HashMap<String, String> hashMapValidateError) {
        Iterator myVeryOwnIterator = hashMapValidateError.keySet().iterator();

        while (myVeryOwnIterator.hasNext()) {
            String key = (String) myVeryOwnIterator.next();
            if (mHashMapAttributes.containsKey(key)) {
                EditText attr = (EditText) mHashMapAttributes.get(key);
                attr.setError((String) hashMapValidateError.get(key));
            }
        }

    }

    @Override
    public void onCashierConnectFaild() {
        Toast.makeText(AddGetCashierActivity.this, "Faild to connect server.", Toast.LENGTH_SHORT).show();
    }
}
