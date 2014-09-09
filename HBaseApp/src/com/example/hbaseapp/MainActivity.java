package com.example.hbaseapp;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

	Button b1,b2,b3,b4,b5,b6;
	WebView webView;
	private LocationManager locationManager;
    private LocationListener locationListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
/*
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}*/
		
		 DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		 Date date = new Date();

		 /// Collecting GPS Data
		 LocationManager locationManager =
			        (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			 
			String mlocProvider;
			Criteria hdCrit = new Criteria();
			 
			hdCrit.setAccuracy(Criteria.ACCURACY_COARSE);
			 
			mlocProvider = locationManager.getBestProvider(hdCrit, true);
			 
			Location currentLocation = locationManager.getLastKnownLocation(mlocProvider);
			 
			double currentLatitude = currentLocation.getLatitude();
			double currentLongitude = currentLocation.getLongitude();
		 
		 //// Write both Data to File
		writeFile(dateFormat.format(date)+"\t"+Double.toString(currentLatitude)+"\t"+Double.toString(currentLongitude));	

		 new SendFile().execute("");

		
		webView = (WebView) findViewById(R.id.webView1);
		
		
		 b1 =(Button) findViewById(R.id.button1);
		b1.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				
				webView.setWebViewClient(new WebViewClient());
				webView.loadUrl("http://134.193.136.114:8080/HBaseWS/jaxrs/generic/hbaseCreate/HBase2/Date:Latitude:longitude");
			}
 
		});
		
		b2 =(Button) findViewById(R.id.button2);
		b2.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				
				webView.setWebViewClient(new WebViewClient());
				webView.loadUrl("http://134.193.136.114:8080/HBaseWS/jaxrs/generic/hbaseInsert/HBase2/-home-group1-kishore.txt/Date:Latitude:longitude");
			}
 
		});
		
		b3 =(Button) findViewById(R.id.button3);
		b3.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				
				webView.setWebViewClient(new WebViewClient());
				webView.loadUrl("http://134.193.136.114:8080/HBaseWS/jaxrs/generic/hbaseRetrieveAll/HBase2");
			}
 
		});
		
		b4 =(Button) findViewById(R.id.button4);
		b4.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				
				webView.setWebViewClient(new WebViewClient());
				webView.loadUrl("http://134.193.136.114:8080/HBaseWS/jaxrs/generic/hbasedeletel/HBase2");
			}
 
		});
		
		
		b5 =(Button) findViewById(R.id.button5);
		b5.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
			
				webView.setWebViewClient(new WebViewClient());
				webView.loadUrl("http://134.193.136.114:8080/HDFSWS/jaxrs/generic/hadoopRun/-home-group1-WordCount.jar");
			}
 
		});
		
		
		b6 =(Button) findViewById(R.id.button6);
		b6.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				
				webView.setWebViewClient(new WebViewClient());
				webView.loadUrl("http://134.193.136.114:8080/HDFSWS/jaxrs/generic/viewResult/outputDir");
			}
 
		});
		
		
	}
	
	
	public void writeFile(String time)
    {
		 
		  String root = Environment.getExternalStorageDirectory().toString();
			File myDir = new File(root + "/Data");
			String fname = "kishore.txt";
			myDir.mkdirs();
		    File file = new File (myDir, fname);
		   
		    try {
		           FileOutputStream out = new FileOutputStream(file,true);
		           out.write(time.getBytes());
		           out.write("\n".getBytes());
		           out.flush();
		           out.close();

		    } catch (Exception e) {
		           e.printStackTrace();
		    }

    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}


class SendFile extends AsyncTask<String, Void, String> {
	

    private Exception exception;

    protected String doInBackground(String... urls) {
        try {
           
        	
        	
        	JSch ssh = new JSch();
		        JSch.setConfig("StrictHostKeyChecking", "no");
		        Session session;
				try {
					session = ssh.getSession("group1", "134.193.136.114", 22);
				
		        session.setPassword("group1");
		        session.connect();
		        Channel channel = session.openChannel("sftp");
		        channel.connect();
		        ChannelSftp sftp = (ChannelSftp) channel;
		        
		        File sdCard = Environment.getExternalStorageDirectory(); 
				File directory = new File (sdCard.getAbsolutePath() + "/Data");
			    
				Log.i(null,directory+"/kishore.txt");
				
		        
		        sftp.put(directory+"/kishore.txt", "/home/group1/");
		    	
				} catch (JSchException e) {
					// TODO Auto-generated catch block
					Log.i(null,e.toString());
					e.printStackTrace();
					
				} catch (SftpException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}

        	
        } catch (Exception e) {
            this.exception = e;
            return null;
        }
		return null;
    }

    protected void onPostExecute() {
        // TODO: check this.exception 
        // TODO: do something with the feed
    }

}

