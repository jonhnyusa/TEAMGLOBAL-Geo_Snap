package edu.ltu.ase.projec2.teamglobal_geo_snap;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;


/**
 * A login screen that offers login via userid/password.
 */
public class LoginSigninActivity extends AppCompatActivity{

    int NOT_FOUND=-1;

    GEOSNAP_DatabaseHandler dbHandler;


    // UI references.
    private EditText loginregisterUserIDView;
    private EditText loginregisterPasswordView;
    private View mProgressView;
    private View mLoginFormView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signin);


        loginregisterUserIDView = (EditText) findViewById(R.id.loginsingin_userid);
        loginregisterPasswordView = (EditText) findViewById(R.id.password);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        Button loginregisterEmailSignInButton = (Button) findViewById(R.id.loginsingin_sign_in_button);

        loginregisterEmailSignInButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                User lrUser = new User();
                lrUser.UserID = loginregisterUserIDView.getText().toString();
                lrUser.Password = loginregisterPasswordView.getText().toString();
                //if (dbHandler.findUser(lrUser)!=NOT_FOUND) {
                    //Todo: call next
                    //Intent add_user =  Intent(LoginSigninActivity.this, next.class);
                    //add_user.putExtra("called", "add");
                    //add_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    //startActivity(add_user);
                    //finish();
           //     }
                //else{
                    //
                //}
            }
        });
    }


 }

