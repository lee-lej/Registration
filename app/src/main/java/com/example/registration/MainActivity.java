package com.example.registration;

import androidx.core.widget.NestedScrollView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends Activity implements View.OnClickListener {

    TextInputLayout txtLName;
    TextInputLayout txtLSurname;
    TextInputLayout txtLKonPassword;
    TextInputLayout txtLPassword;
    TextInputLayout txtLEmail;
    TextInputLayout txtLAdress;
    TextInputLayout txtLCel;
    NestedScrollView nestedScrollView;
    ProgressDialog mProgressDialog;

    EditText txtName;
    EditText txtSurname;
    EditText txtKonPassword;
    EditText txtPassword;
    EditText txtEmail;
    EditText txtAdress;
    EditText txtCel;
    TextView txtLogIn;
    Button btnSignUp;
    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initListeners();
        initObjects();
    }

    private void initListeners() {
        btnSignUp.setOnClickListener(this);
        txtLogIn.setOnClickListener(this);

    }

    private void initObjects() {

        inputValidation = new InputValidation(MainActivity.this);
        databaseHelper = new DatabaseHelper(MainActivity.this);
        user = new User();
    }

    private void initViews() {
        sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);

        txtName = (EditText) findViewById(R.id.txtName);
        txtSurname = (EditText) findViewById(R.id.txtSurname);
        txtKonPassword = (EditText) findViewById(R.id.txtKonPassword);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtAdress = (EditText) findViewById(R.id.txtAdress);
        txtCel = (EditText) findViewById(R.id.txtCel);
        txtLogIn = (TextView) findViewById(R.id.txtLogIn);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        txtLName = (TextInputLayout) findViewById(R.id.txtLName);
        txtLSurname = (TextInputLayout) findViewById(R.id.txtLSurname);
        txtLKonPassword = (TextInputLayout) findViewById(R.id.txtLKonPassword);
        txtLPassword = (TextInputLayout) findViewById(R.id.txtLPassword);
        txtLEmail = (TextInputLayout) findViewById(R.id.txtLEmail);
        txtLAdress = (TextInputLayout) findViewById(R.id.txtLAdress);
        txtLCel = (TextInputLayout) findViewById(R.id.txtLCel);

    }

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnSignUp:
                postDataToSQLite();
                break;

            case R.id.txtLogIn:


                break;


        }
    }

    private void postDataToSQLite() {

        mProgressDialog = new ProgressDialog(this);
        //  mProgressDialog.setCancelable(false);
        mProgressDialog.setIcon(R.drawable.ic_launcher_background);
        mProgressDialog.setTitle("Green Star Hotel");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage("Please Wait ...");
        mProgressDialog.show();
        if (!inputValidation.isInputEditTextFilled((EditText) txtName, txtLName, getString(R.string.error_message_name))) {
            mProgressDialog.dismiss();
            return;
        }
        if (!inputValidation.isInputEditTextFilled((EditText) txtSurname, txtLSurname, getString(R.string.error_message_lastname))) {
            mProgressDialog.dismiss();
            return;
        }
        if (!inputValidation.isInputEditTextEmail(txtEmail, txtLEmail, getString(R.string.error_message_email))) {
            mProgressDialog.dismiss();
            return;
        }

        if (!inputValidation.isInputEditTextFilled((EditText) txtPassword, txtLPassword, getString(R.string.error_message_password))) {
            mProgressDialog.dismiss();
            return;
        }


        if (!inputValidation.isInputEditTextMatches((EditText) txtPassword, (EditText) txtKonPassword, txtLKonPassword, getString(R.string.error_message_konfPassword))) {
            mProgressDialog.dismiss();
            return;
        }


        if (!databaseHelper.checkUser(txtEmail.getText().toString().trim())) {

            user.setName(txtName.getText().toString().trim());
            user.setSurname(txtSurname.getText().toString().trim());
            user.setEmail(txtEmail.getText().toString().trim());
            //user.setUser(txtKonPassword.getText().toString().trim());
            user.setPassword(txtPassword.getText().toString().trim());


            user.setAdress(txtAdress.getText().toString().trim());
            user.setCel(txtCel.getText().toString().trim());

            databaseHelper.addUser(user);

            // Snack Bar to show success message that record saved successfully

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Email", txtEmail.getText().toString().trim());
            editor.apply();


            emptyInputEditText();
            new Handler().postDelayed(new Runnable() {

                /*
                 * Showing splash screen with a timer. This will be useful when you
                 * want to show case your app logo / company
                 */

                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    mProgressDialog.dismiss();
                    Snackbar snackbar = Snackbar.make(nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.GREEN);
                    textView.setTextSize(16);
                    snackbar.show();
                }
            }, 3000);


            new Handler().postDelayed(new Runnable() {

                /*
                 * Showing splash screen with a timer. This will be useful when you
                 * want to show case your app logo / company
                 */

                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    Intent i = new Intent(MainActivity.this, UserActivity.class);
                    i.putExtra("email", txtEmail.getText().toString());
                    startActivity(i);

                    // close this activity
                    finish();
                }
            }, 4000);


        } else {
            // Snack Bar to show error message that record already exists
            mProgressDialog.dismiss();
            Snackbar snackbar = Snackbar.make(nestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
            textView.setTextColor(Color.RED);
            textView.setTextSize(16);

            snackbar.show();

        }


    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        txtName.setText(null);
        txtSurname.setText(null);
        txtEmail.setText(null);
        txtPassword.setText(null);
        txtKonPassword.setText(null);


        txtAdress.setText(null);
        txtCel.setText(null);
    }
}
