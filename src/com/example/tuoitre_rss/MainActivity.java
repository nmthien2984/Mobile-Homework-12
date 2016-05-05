package com.example.tuoitre_rss;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	String[] links = {"http://tuoitre.vn/rss/tt-tin-moi-nhat.rss",
					  "http://tuoitre.vn/rss/tt-chinh-tri-xa-hoi.rss",
					  "http://tuoitre.vn/rss/tt-the-gioi.rss",
					  "http://tuoitre.vn/rss/tt-phap-luat.rss",
					  "http://tuoitre.vn/rss/tt-kinh-te.rss",
					  "http://tuoitre.vn/rss/tt-song-khoe.rss",
					  "http://tuoitre.vn/rss/tt-giao-duc.rss",
					  "http://tuoitre.vn/rss/tt-the-thao.rss",
					  "http://tuoitre.vn/rss/tt-van-hoa-giai-tri.rss",
					  "http://tuoitre.vn/rss/tt-nhip-song-tre.rss",
					  "http://tuoitre.vn/rss/tt-nhip-song-so.rss",
					  "http://tuoitre.vn/rss/tt-ban-doc.rss",
					  "http://tuoitre.vn/rss/tt-du-lich.rss"};
	
	String[] captions = {"Trang chủ", "Chính trị - Xã hội", "Thế giới", "Pháp luật",
						 "Kinh tế", "Sống khoẻ", "Giáo dục", "Thể thao", 
						 "Văn hoá - Giải trí", "Nhịp sống trẻ", "Nhịp sống số",
						 "Bạn đọc", "Du lịch"};
	

	ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.setTitle("Tuổi trẻ Online");
		
		ListView lvChannels = (ListView)findViewById(R.id.listView);
		lvChannels.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent showHeadlines = new Intent(MainActivity.this, ShowHeadlinesActivity.class);
				
				Bundle data = new Bundle();
				data.putString("link", links[position]);
				data.putString("caption", captions[position]);
				showHeadlines.putExtras(data);
				
				startActivity(showHeadlines);
			}
			
		});
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, captions);
		lvChannels.setAdapter(adapter);
	}
}
