package com.greenb.cms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.greenb.cms.R;
import com.greenb.cms.models.Cashier;

import java.io.Serializable;

public class ViewCashierActivity extends ActionBarActivity {
    private Cashier mCashier;

    private TextView mLoginid;
    private TextView mDisplayname;
    private TextView mPhone;
    private TextView mEmail;
    private TextView mAddress;
    private TextView mStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent extraIntent = getIntent();
        mCashier = extraIntent.hasExtra("Cashier") ? (Cashier) extraIntent.getSerializableExtra("Cashier") : null;
        if (mCashier == null) finish();
        setContentView(R.layout.activity_view_cashier);

        mLoginid = (TextView) findViewById(R.id.editTxt_loginid);
        mDisplayname = (TextView) findViewById(R.id.editTxt_display_name);
        mPhone = (TextView) findViewById(R.id.editTxt_phone);
        mEmail = (TextView) findViewById(R.id.editTxt_email);
        mAddress = (TextView) findViewById(R.id.editTxt_address);
        mStatus = (TextView) findViewById(R.id.editTxt_status);

        renderCashierInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_cashier, menu);
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
            case R.id.action_edit:
                Intent intent = new Intent(ViewCashierActivity.this, EditCashierActivity.class);
                intent.putExtra("Cashier", (Serializable) mCashier);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void renderCashierInfo() {
        mLoginid.setText(mCashier.loginid);
        mDisplayname.setText(mCashier.display_name);
        mPhone.setText(mCashier.phone);
        mEmail.setText(mCashier.email);
        mAddress.setText(mCashier.address);
        String Status = mCashier.status.equals("1") ? "Yes" : "No";
        mStatus.setText(Status);
    }
}
