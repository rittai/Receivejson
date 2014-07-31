package com.example.receivejson;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;


public class MainActivity extends Activity implements OnClickListener,Runnable{
	
	private static final String URL = "http://10.0.2.2/json.php";
	 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
         
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(this);    
    }
 
    @Override
    public void onClick(View v) {   
        Thread thread = new Thread(this);
        thread.start(); 
    }
 
    @Override
    public void run() { 
        try{
            HttpUriRequest httpGet = new HttpGet(URL);  
            DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
            HttpResponse httpResponse = defaultHttpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
             
            JSONObject object = new JSONObject(EntityUtils.toString(entity));
            JSONArray jsonArray = object.getJSONArray("BOOKS");
             
            for(int i = 0; i < jsonArray.length();i++){
                JSONObject book = jsonArray.getJSONObject(i);
                 
                String title = book.getString("TITLE");
                String price = book.getString("PRICE");
                 
                Log.d("BOOKS","タイトル:" + title + "/価格:" + price + "円");      
            }       
        }catch(IOException e){
            System.out.println("通信失敗");
        }catch(JSONException e){
            System.out.println("JSON取得に失敗");
        }   
    }



}
