package a;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

public abstract class ItsmBasePatternModel extends BasePatternModel {

	public ItsmBasePatternModel(String url) {
		super(url);
	
	}

	public boolean isLogin(String returnString) {

		if (returnString.contains("服务器已经丢失与您的登陆信息"))
			return false;
		else {
			return true;
		}

	}

	public void login()  throws IOException{

		HttpPost post = new HttpPost("http://132.121.130.146:8603/");

		post.setHeader(
				"User-Agent",
				"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/534.3 (KHTML, like Gecko) Chrome/6.0.472.63 Safari/534.3");

		// 登录表单的信息

		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair("loginId", "lzliang"));
		qparams.add(new BasicNameValuePair("pass", "xxxxx"));
		qparams.add(new BasicNameValuePair("sysValidateWay", "false"));
		qparams.add(new BasicNameValuePair("verfiySmsCode", "false"));
		qparams.add(new BasicNameValuePair("passSuccess", "false"));
		UrlEncodedFormEntity params = new UrlEncodedFormEntity(qparams, "UTF-8");
		post.setEntity(params);
		ItsmBasePatternModel.getClient().execute(post);
	}

}
