package com.example.traveler;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Social extends Activity {
	Context mContext;
	//TESTING
	static String TWITTER_CONSUMER_KEY = "h5h3B3R5FQid7lM5RswwJg";
    static String TWITTER_CONSUMER_SECRET = "z0PJ1SWSeEEg8Mgi4ds5wORGoskXxzFmJwkNhyuZQ";
 
 
    // Preference Constants
    static String PREFERENCE_NAME = "twitter_oauth";
    static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
    static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
    static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLogedIn";
 
    static final String TWITTER_CALLBACK_URL = "oauth://t4jsample";
 
    // Twitter oauth urls
    static final String URL_TWITTER_AUTH = "auth_url";
    static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
    static final String URL_TWITTER_OAUTH_TOKEN = "oauth_token";
 
    // Login button
    Button btnLoginTwitter;
    // Update status button
    Button btnUpdateStatus;
    // Logout button
    Button btnLogoutTwitter;
    // EditText for update
    EditText txtUpdate;
    // lbl update
    TextView lblUpdate;
    TextView lblUserName;
 
    // Progress dialog
    ProgressDialog pDialog;
    
    // Twitter
    private static Twitter twitter;
    private static RequestToken requestToken;
 
    // Shared Preferences
    private static SharedPreferences mSharedPreferences;
 
    // Internet Connection detector
    private ConnectionDetector cd;
 
    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();
 
	
	//TESTING

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);
        
        ///TWITTER TESTING
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        cd = new ConnectionDetector(getApplicationContext());
 
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(mContext, "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }
 
        // Check if twitter keys are set
        if(TWITTER_CONSUMER_KEY.trim().length() == 0 || TWITTER_CONSUMER_SECRET.trim().length() == 0){
            // Internet Connection is not present
            alert.showAlertDialog(mContext, "Twitter oAuth tokens", "Please set your twitter oauth tokens first!", false);
            // stop executing code by return
            return;
        }
 
        // All UI elements
        btnLoginTwitter = (Button) findViewById(R.id.btnLoginTwitter);
        btnUpdateStatus = (Button) findViewById(R.id.btnUpdateStatus);
        btnLogoutTwitter = (Button) findViewById(R.id.btnLogoutTwitter);
        txtUpdate = (EditText) findViewById(R.id.txtUpdateStatus);
        lblUpdate = (TextView) findViewById(R.id.lblUpdate);
        lblUserName = (TextView) findViewById(R.id.lblUserName);
 
        // Shared Preferences
        mSharedPreferences = getApplicationContext().getSharedPreferences(
                "MyPref", 0);
        
        //TWITTER TESTING
        
        //Button tweet = (Button) findViewById(R.id.tweet);
        ImageButton home = (ImageButton) findViewById(R.id.home);
        ImageButton help = (ImageButton) findViewById(R.id.help);
        //Home Button
        home.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
        help.setOnClickListener(new View.OnClickListener() {
			
  	      	@Override
  	      	public void onClick(View v) {
  	      		Intent intent = new Intent(mContext, Help.class);
  	      		startActivity(intent);	
  	      		finish();
  	      	}
        });
        /*
        tweet.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String tweetUrl = "https://twitter.com/intent/tweet?text=PUT TEXT HERE &url="
                        + "https://www.google.com";
				Uri uri = Uri.parse(tweetUrl);
				startActivity(new Intent(Intent.ACTION_VIEW, uri));
			}
		});*/
        
        btnLoginTwitter.setOnClickListener(new View.OnClickListener() {
        	 
            @Override
            public void onClick(View arg0) {
                // Call login twitter function
                loginToTwitter();
            }
        });
     
        /** This if conditions is tested once is
         * redirected from twitter page. Parse the uri to get oAuth
         * Verifier
         * */
        if (!isTwitterLoggedInAlready()) {
            Uri uri = getIntent().getData();
            if (uri != null && uri.toString().startsWith(TWITTER_CALLBACK_URL)) {
                // oAuth verifier
                String verifier = uri
                        .getQueryParameter(URL_TWITTER_OAUTH_VERIFIER);
     
                try {
                    // Get the access token
                    AccessToken accessToken = twitter.getOAuthAccessToken(
                            requestToken, verifier);
     
                    // Shared Preferences
                    Editor e = mSharedPreferences.edit();
     
                    // After getting access token, access token secret
                    // store them in application preferences
                    e.putString(PREF_KEY_OAUTH_TOKEN, accessToken.getToken());
                    e.putString(PREF_KEY_OAUTH_SECRET,
                            accessToken.getTokenSecret());
                    // Store login status - true
                    e.putBoolean(PREF_KEY_TWITTER_LOGIN, true);
                    e.commit(); // save changes
     
                    //Log.e("Twitter OAuth Token", "> " + accessToken.getToken());
     
                    // Hide login button
                    btnLoginTwitter.setVisibility(View.GONE);
     
                    // Show Update Twitter
                    lblUpdate.setVisibility(View.VISIBLE);
                    txtUpdate.setVisibility(View.VISIBLE);
                    btnUpdateStatus.setVisibility(View.VISIBLE);
                    btnLogoutTwitter.setVisibility(View.VISIBLE);
     
                    // Getting user details from twitter
                    // For now i am getting his name only
                    long userID = accessToken.getUserId();
                    User user = twitter.showUser(userID);
                    String username = user.getName();
     
                    // Displaying in xml ui
                    lblUserName.setText(Html.fromHtml("<b>Welcome " + username + "</b>"));
                } catch (Exception e) {
                	Toast.makeText(mContext, "FUCK YOU", Toast.LENGTH_LONG).show();
                    // Check log for login errors
                    //Log.e("Twitter Login Error > " + e.getMessage());
                }
            }
        }
     
    }
    private void loginToTwitter() {
        // Check if already logged in
        if (!isTwitterLoggedInAlready()) {
            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
            builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);
            Configuration configuration = builder.build();
 
            TwitterFactory factory = new TwitterFactory(configuration);
            twitter = factory.getInstance();
 
            try {
                requestToken = twitter
                        .getOAuthRequestToken(TWITTER_CALLBACK_URL);
                this.startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse(requestToken.getAuthenticationURL())));
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        } else {
            // user already logged into twitter
            Toast.makeText(getApplicationContext(),
                    "Already Logged into twitter", Toast.LENGTH_LONG).show();
        }
    }
 
    /**
     * Check user already logged in your application using twitter Login flag is
     * fetched from Shared Preferences
     * */
    private boolean isTwitterLoggedInAlready() {
        // return twitter login status from Shared Preferences
        return mSharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN, false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_social, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.menu_settings:
        	goToMenu();
        	return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    public void goToMenu(){
    	Intent intent = new Intent(mContext, Help.class);
  		startActivity(intent);	
  		finish();
    }
}