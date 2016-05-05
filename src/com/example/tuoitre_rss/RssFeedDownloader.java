package com.example.tuoitre_rss;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

public class RssFeedDownloader extends AsyncTask<String, Void, ArrayList<SingleItem>> {

	ShowHeadlinesActivity _context;
	String _caption;
	String _link;
	ProgressDialog _dialog;
	
	public RssFeedDownloader(Context context) {
		_context = (ShowHeadlinesActivity) context;
		_dialog = new ProgressDialog(context);
	}

	@Override
	protected void onPreExecute() {
		_dialog.setMessage("Đang tải...");
		_dialog.setCancelable(false);
		_dialog.show();
	}
	
	@Override
	protected ArrayList<SingleItem> doInBackground(String... params) {
		ArrayList<SingleItem> newsList = new ArrayList<SingleItem>();
		_caption = params[0]; 
		_link = params[1];
		
		_dialog.setMessage("Đang tải " + _caption + "...");
		
		try {
			URL url = new URL(_link);
			HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
			
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				
				InputStream in = httpConnection.getInputStream();
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				
				Document dom = builder.parse(in);
				Element treeElements = dom.getDocumentElement();
	
				newsList.clear();
				NodeList itemNodes = treeElements.getElementsByTagName("item");
				if (itemNodes != null) {
					for (int i = 0; i < itemNodes.getLength(); i++) {
						newsList.add( getSingleItem(itemNodes, i) );
					}
				}
			}
			
			httpConnection.disconnect();
		} catch (Exception e) {
			Log.e("Error>> ", e.getMessage() );
		}
		
		return newsList;
	}	

	@Override
	protected void onPostExecute(ArrayList<SingleItem> result) {
		super.onPostExecute(result);
		_context._headlines = result;
		
		ArrayAdapter<SingleItem> adapter = new ArrayAdapter<SingleItem>(_context, android.R.layout.simple_list_item_1, result);
		_context._listView.setAdapter(adapter);
		
		_dialog.dismiss();
	}
	
	protected SingleItem getSingleItem(NodeList nodeList, int i) {
			Element item = (Element) nodeList.item(i);
			Element title = (Element) item.getElementsByTagName("title").item(0);
			Element description = (Element) item.getElementsByTagName("description").item(0);
			Element pubDate = (Element) item.getElementsByTagName("pubDate").item(0);
			Element link = (Element) item.getElementsByTagName("link").item(0);
			
			SingleItem result = new SingleItem(title.getFirstChild().getNodeValue(),
											   description.getFirstChild().getNodeValue(),
											   pubDate.getFirstChild().getNodeValue(),
											   link.getFirstChild().getNodeValue());
			return result;
	}
}
