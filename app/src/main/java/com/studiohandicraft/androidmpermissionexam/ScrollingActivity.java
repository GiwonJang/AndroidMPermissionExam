package com.studiohandicraft.androidmpermissionexam;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ScrollingActivity extends AppCompatActivity {

	private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scrolling);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				if (ContextCompat.checkSelfPermission(ScrollingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
						!= PackageManager.PERMISSION_GRANTED) {

					// Should we show an explanation?
					if (ActivityCompat.shouldShowRequestPermissionRationale(ScrollingActivity.this,
							Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

						// Show an expanation to the user *asynchronously* -- don't block
						// this thread waiting for the user's response! After the user
						// sees the explanation, try again to request the permission.
						Snackbar.make(view, "show expanation", Snackbar.LENGTH_LONG).setAction("Action", null).show();

					} else {

						// No explanation needed, we can request the permission.

						ActivityCompat.requestPermissions(ScrollingActivity.this,
								new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
								MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

						// MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
						// app-defined int constant. The callback method gets the
						// result of the request.
					}

					return;
				}

				writeTestFile();


			}
		});
	}

	private void writeTestFile() {
		final File dir = getApplicationContext().getFilesDir();

		OutputStreamWriter osw = null;
		FileOutputStream fos = null;
		try {
			//osw = new OutputStreamWriter(openFileOutput("test.txt", MODE_PRIVATE));

			File file = new File(Environment.getExternalStorageDirectory(), "test.txt");
			fos = new FileOutputStream(file);
			osw = new OutputStreamWriter(fos);
			osw.write("test text");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (osw != null) {
					osw.close();
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		File file = new File(getFilesDir(), "test.txt");

		Snackbar.make(findViewById(android.R.id.content), Environment.getExternalStorageDirectory().toString(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_scrolling, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
		switch (requestCode) {
			case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
				// If request is cancelled, the result arrays are empty.
				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {

					// permission was granted, yay! Do the
					// contacts-related task you need to do.
					writeTestFile();

				} else {

					// permission denied, boo! Disable the
					// functionality that depends on this permission.

					Snackbar.make(findViewById(android.R.id.content), "permission denied", Snackbar.LENGTH_LONG).setAction("Action", null).show();
				}
				return;
			}

			// other 'case' lines to check for other
			// permissions this app might request
		}
	}
}
