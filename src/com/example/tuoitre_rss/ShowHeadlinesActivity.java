package com.example.tuoitre_rss;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ShowHeadlinesActivity extends Activity {
	
	ListView _listView;
	ArrayList<SingleItem> _headlines;
	SingleItem _selectedItem;
	String _caption;
	
	private SpannableStringBuilder htmlSpannable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		
		Intent intent = getIntent();
		Bundle data = intent.getExtras();
		_caption = data.getString("caption");
		String link = data.getString("link");
		
		this.setTitle(_caption);
		
		_listView = (ListView)findViewById(R.id.listView);
		_listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				showDialogBox(_headlines.get(position));
			}
			
		});
		
		RssFeedDownloader downloader = new RssFeedDownloader(this);
		downloader.execute(_caption, link);
	}
	
	public void showDialogBox(SingleItem item) {
		String title = item.getTitle();
		String description = item.getDescription();
		
		try {
			final Uri newsLink = Uri.parse(item.getLink());
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			
			builder
			.setIcon(R.drawable.logo)
			.setTitle(Html.fromHtml(_caption))
			.setMessage( title + "\n\n" + Html.fromHtml(description) + "\n" )
			.setPositiveButton("Close", null)
			.setNegativeButton("More", new OnClickListener() {
				public void onClick(DialogInterface dialog, int whichOne) {
				Intent browser = new Intent(Intent.ACTION_VIEW, newsLink);
				startActivity(browser);
				
				}
			})
			.show();
		} catch (Exception e) {
			Log.e("Error DialogBox", e.getMessage() );
		}
	}
	
}
