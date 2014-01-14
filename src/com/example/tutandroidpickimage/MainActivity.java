package com.example.tutandroidpickimage;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	int RESULT_LOAD_IMAGE = 2;
	
	private ImageView imageview;
	private TextView txtFilename;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Set up your components
		final Button btn1 = (Button)findViewById(R.id.btn1);
		imageview = (ImageView)findViewById(R.id.imageview);
		txtFilename = (TextView)findViewById(R.id.filename);
		
		// Add an OnClickListener to the only button in our gui
		btn1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				// Add a nice haptic feedback when the button is pushed
				btn1.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
				// Create an intent
				Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
		});
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == RESULT_LOAD_IMAGE) {
			
			if(resultCode == RESULT_OK) {
				
				Uri selectedImage = data.getData();
				String[] filePathColumn = {MediaStore.Images.Media.DATA};
				Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
				cursor.moveToFirst();
				int columIndex = cursor.getColumnIndex(filePathColumn[0]);
				String picturePath = cursor.getString(columIndex);
				cursor.close();
							
				Bitmap bmp = BitmapFactory.decodeFile(picturePath);
				txtFilename.setText(picturePath);
				
				imageview.setImageBitmap(bmp);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
