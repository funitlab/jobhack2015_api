package com.funitlab.jobhack;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.EnumSet;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import alertview.LinkedinDialog;
import alertview.LinkedinDialog.OnVerifyListener;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.code.linkedinapi.client.LinkedInApiClient;
import com.google.code.linkedinapi.client.LinkedInApiClientFactory;
import com.google.code.linkedinapi.client.enumeration.ProfileField;
import com.google.code.linkedinapi.client.oauth.LinkedInAccessToken;
import com.google.code.linkedinapi.client.oauth.LinkedInOAuthService;
import com.google.code.linkedinapi.client.oauth.LinkedInOAuthServiceFactory;
import com.google.code.linkedinapi.client.oauth.LinkedInRequestToken;
import com.google.code.linkedinapi.schema.Person;
import com.squareup.picasso.Picasso;
import common.Common;


public class LinkedInActivity extends Activity {
	private Button login;
	private Button share;
	private EditText et;
	private TextView name,profile;
	private ImageView photo;

	final LinkedInOAuthService oAuthService = LinkedInOAuthServiceFactory
            .getInstance().createLinkedInOAuthService(
                    Common.LINKEDIN_CONSUMER_KEY,Common.LINKEDIN_CONSUMER_SECRET);
	final LinkedInApiClientFactory factory = LinkedInApiClientFactory
			.newInstance(Common.LINKEDIN_CONSUMER_KEY,
					Common.LINKEDIN_CONSUMER_SECRET);
	private LinkedInRequestToken liToken;
	private LinkedInApiClient client;
	private LinkedInAccessToken accessToken = null;

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		if( Build.VERSION.SDK_INT >= 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy); 
		}
		share = (Button) findViewById(R.id.share);
		name = (TextView) findViewById(R.id.name);
		profile = (TextView) findViewById(R.id.profile);
		et = (EditText) findViewById(R.id.et_share);
		login = (Button) findViewById(R.id.login);
		photo = (ImageView) findViewById(R.id.photo);

		login.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				linkedInLogin();
			}
		});

		// share on linkedin
		share.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				String share = et.getText().toString();
				if (null != share && !share.equalsIgnoreCase("")) {
					OAuthConsumer consumer = new CommonsHttpOAuthConsumer(Common.LINKEDIN_CONSUMER_KEY, Common.LINKEDIN_CONSUMER_SECRET);
				    consumer.setTokenWithSecret(accessToken.getToken(), accessToken.getTokenSecret());
					DefaultHttpClient httpclient = new DefaultHttpClient();
					HttpPost post = new HttpPost("https://api.linkedin.com/v1/people/~/shares");
					try {
						consumer.sign(post);
					} catch (OAuthMessageSignerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (OAuthExpectationFailedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (OAuthCommunicationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} // here need the consumer for sign in for post the share
					post.setHeader("content-type", "text/XML");
					String myEntity = "<share><comment>"+ share +"</comment><visibility><code>anyone</code></visibility></share>";
					try {
						post.setEntity(new StringEntity(myEntity));
						org.apache.http.HttpResponse response = httpclient.execute(post);
						Toast.makeText(LinkedInActivity.this,
								"Shared sucessfully", Toast.LENGTH_SHORT).show();
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else {
					Toast.makeText(LinkedInActivity.this,
							"Please enter the text to share",
							Toast.LENGTH_SHORT).show();
				}
				
		
			}
		});
	}

	private void linkedInLogin() {
		ProgressDialog progressDialog = new ProgressDialog(
				LinkedInActivity.this);

		LinkedinDialog d = new LinkedinDialog(LinkedInActivity.this,
				progressDialog);
		d.show();

		// set call back listener to get oauth_verifier value
		d.setVerifierListener(new OnVerifyListener() {
			@SuppressLint("NewApi")
			public void onVerify(String verifier) {
				try {
					Log.i("LinkedinSample", "verifier: " + verifier);

					accessToken = LinkedinDialog.oAuthService
							.getOAuthAccessToken(LinkedinDialog.liToken,
									verifier);
					LinkedinDialog.factory.createLinkedInApiClient(accessToken);
					client = factory.createLinkedInApiClient(accessToken);
					// client.postNetworkUpdate("Testing by Mukesh!!! LinkedIn wall post from Android app");
					Log.i("LinkedinSample",
							"ln_access_token: " + accessToken.getToken());
					Log.i("LinkedinSample",
							"ln_access_token: " + accessToken.getTokenSecret());
					//Person p = client.getProfileForCurrentUser();
					Person p = 	client.getProfileForCurrentUser(EnumSet.of(
							ProfileField.ID, ProfileField.FIRST_NAME,
							ProfileField.PHONE_NUMBERS, ProfileField.LAST_NAME,
							ProfileField.HEADLINE, ProfileField.INDUSTRY,
							ProfileField.PICTURE_URL, ProfileField.DATE_OF_BIRTH,
							ProfileField.LOCATION_NAME, ProfileField.MAIN_ADDRESS,
								ProfileField.LOCATION_COUNTRY));
					Log.e("create access token secret", client.getAccessToken()
							.getTokenSecret());

					if(p!=null) {
						name.setText("Welcome " + p.getFirstName() + " "
								+ p.getLastName());
						name.setVisibility(0);
						profile.setText("Profile:"+p.getHeadline());
						profile.setVisibility(0);
						String id = p.getId();
						String url = p.getPictureUrl();
						if(url != null && !url.isEmpty()) {
							Picasso.with(LinkedInActivity.this).load(url).into(photo);
							photo.setVisibility(0);
						}
						login.setVisibility(4);
						share.setVisibility(0);
						et.setVisibility(0);
					}

				} catch (Exception e) {
					Log.i("LinkedinSample", "error to get verifier");
					e.printStackTrace();
				}
			}
		});

		// set progress dialog
		progressDialog.setMessage("Loading...");
		progressDialog.setCancelable(true);
		progressDialog.show();
	}
}

