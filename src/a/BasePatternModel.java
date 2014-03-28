package a;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public abstract class BasePatternModel implements PatternInterFace,LoginInterFace {
	private final static HttpClient client = new DefaultHttpClient();

	public BasePatternModel(String url) {
	    try {
			this.pattern(get(url));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private  String get(String url) throws IOException {

		HttpGet get = new HttpGet(url);

		HttpResponse response = client.execute(get);

		System.out.println(response.getStatusLine());

		HttpEntity entity = response.getEntity();

		String rs= dump(entity);
		
		if(!this.isLogin(rs)){
			this.login();
			return get(url);
		}
		else{
			return rs;
		}

	}
	
	private static String dump(HttpEntity entity) throws IOException {

		BufferedReader br = new BufferedReader(

		new InputStreamReader(entity.getContent(), "GBK"));

		String temp = "";
		String s = "";
		while ((temp = br.readLine()) != null) {
			s = s + temp;
		}
		System.out.println(s);
		return s;
	}
	
	public static HttpClient getClient(){
		return client;
	}
	
}
