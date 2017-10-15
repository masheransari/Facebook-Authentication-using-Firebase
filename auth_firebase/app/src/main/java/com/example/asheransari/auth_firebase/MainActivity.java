package com.example.asheransari.auth_firebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    private ProgressDialog mAuthProgressDialog;

//    private FacebookSdk facebookSdk;
    private CallbackManager callbackManager;
    private AccessToken facebookAccessToken;
    private AccessTokenTracker mFacebookAccessTokenTracker;
    private LoginButton loginButton;
    private FirebaseAuth firebaseAuth;
//    private AuthData mAuthData;

    /* Listener for Firebase session changes */
  //  private Firebase.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         firebaseAuth = FirebaseAuth.getInstance();
         FacebookSdk.sdkInitialize(getApplicationContext());
         setContentView(R.layout.activity_main);
         loginButton = (LoginButton)findViewById(R.id.btnFacebookLogin);
//         callbackManager = CallbackManager.Factory.create();
//
//         mFacebookAccessTokenTracker = new AccessTokenTracker() {
//             @Override
//             protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
//                 MainActivity.this.onFacebookAccessTokenChange(currentAccessToken);
//             }
//         };
         initializeFacebookLogin();
     }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // if user logged in with Facebook, stop tracking their token
        if (mFacebookAccessTokenTracker != null) {
            mFacebookAccessTokenTracker.stopTracking();
        }

        // if changing configurations, stop tracking firebase session.
//        mFirebaseRef.removeAuthStateListener(mAuthStateListener);
    }


//    private void onFacebookAccessTokenChange(AccessToken token) {
//        if (token != null) {
//            mAuthProgressDialog.show();
//            mFirebaseRef.authWithOAuthToken("facebook", token.getToken(), new AuthResultHandler("facebook"));
//        } else {
//            // Logged out of Facebook and currently authenticated with Firebase using Facebook, so do a logout
//            if (this.mAuthData != null && this.mAuthData.getProvider().equals("facebook")) {
//                mFirebaseRef.unauth();
//                setAuthenticatedUser(null);
//            }
//        }
//    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null){
            Intent i = new Intent(MainActivity.this,ab.class);
            startActivity(i);
            finish();
        }
    }
    private void initializeFacebookLogin(){
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email","public_profile","user_friends");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                facebookAccessToken = loginResult.getAccessToken();
                handleFacebookAccessToken(facebookAccessToken);
                Intent i = new Intent(MainActivity.this,ab.class);
                startActivity(i);
                finish();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }


//    private void handleFacebookAccessToken(AccessToken token) {
//        Log.d("abcd", "handleFacebookAccessToken:" + token);
//
//        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
//        firebaseAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                // Sign in success, update UI with the signed-in user's information
//                                Log.d("a=bcd", "signInWithCredential:success");
//                                FirebaseUser user = firebaseAuth.getCurrentUser();
//                                updateUI(user);
//
//                    }}
//
//                    @Override
//                    public void onComplete(Task,
//                            AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d("abcd", "signInWithCredential:success");
//                            FirebaseUser user = firebaseAuth.getCurrentUser();
//                            updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w("abcd", "signInWithCredential:failure", task.getException());
//                            Toast.makeText(MainActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
//                        }
//
//                    });
//    }

//    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("abcd", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("abcd", "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("abcd", "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser user){
        Toast.makeText(this, "getData" + user.getProviderId(), Toast.LENGTH_SHORT).show();
    }

}

