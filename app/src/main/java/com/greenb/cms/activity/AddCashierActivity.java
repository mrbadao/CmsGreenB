package com.greenb.cms.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.greenb.cms.R;

public class AddCashierActivity extends ActionBarActivity {
    private EditText mLoginid;
    private EditText mPassword;
    private EditText mRePassword;
    private EditText mDisplayName;
    private EditText mPhone;
    private EditText mEmail;
    private EditText mStatus;
    private EditText mAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cashier);
        //set properties view
        mLoginid = (EditText) findViewById(R.id.editTxt_loginid);
        mPassword = (EditText) findViewById(R.id.editTxt_pwd);
        mRePassword = (EditText) findViewById(R.id.editTxt_pwd_conf);
        mDisplayName = (EditText) findViewById(R.id.editTxt_display_name);
        mPhone = (EditText) findViewById(R.id.editTxt_phone);
        mEmail = (EditText) findViewById(R.id.editTxt_email);
        mAddress = (EditText) findViewById(R.id.editTxt_address);
        mStatus = (EditText) findViewById(R.id.checkBox_status);
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
        switch (id){
            case R.id.action_save:
                atempSaveCashier();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void atempSaveCashier(){

    }
}
