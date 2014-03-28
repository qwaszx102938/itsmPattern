package a;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

/**
 * 以 dict.cn 网站为例的爬虫
 * 
 * @author Winter Lau
 */
public class A {

	private final static HttpClient client = new DefaultHttpClient();

	public static void main(String[] args) throws IOException {

		login("<用户名>", "<密码>", true);
		String s = get("http://132.121.130.146:8603/workorder/ToDoList!query.do");
		System.out.println(s);
		ArrayList<BillLink> ss = getBillList(s);
		
		for(BillLink bl:ss){
			System.out.println(bl.getLinkString());
			get(bl.getLinkString());
			
		}
		

		// get("http://132.121.130.146:8603/openNode.do?nodeLsh=00a76940dfc94107a7704eccce5f881a");
		//get("http://132.121.130.146:8603/form!getFormIns.do?curNodeLsh=00a76940dfc94107a7704eccce5f881a");

	}

	/**
	 * 
	 * 抓取网页
	 * 
	 * @param url
	 * 
	 * @throws IOException
	 */

	static String get(String url) throws IOException {

		HttpGet get = new HttpGet(url);

		HttpResponse response = client.execute(get);

		System.out.println(response.getStatusLine());

		HttpEntity entity = response.getEntity();

		return dump(entity);

	}

	/**
	 * 
	 * 执行登录过程
	 * 
	 * @param user
	 * 
	 * @param pwd
	 * 
	 * @param debug
	 * 
	 * @throws IOException
	 */

	static void login(String user, String pwd, boolean debug)
			throws IOException {

		HttpPost post = new HttpPost("http://132.121.130.146:8603/");

		post.setHeader(
				"User-Agent",
				"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/534.3 (KHTML, like Gecko) Chrome/6.0.472.63 Safari/534.3");

		// 登录表单的信息

		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair("loginId", "lzliang"));
		qparams.add(new BasicNameValuePair("pass", "xxx"));
		qparams.add(new BasicNameValuePair("sysValidateWay", "false"));
		qparams.add(new BasicNameValuePair("verfiySmsCode", "false"));
		qparams.add(new BasicNameValuePair("passSuccess", "false"));
		UrlEncodedFormEntity params = new UrlEncodedFormEntity(qparams, "UTF-8");

		post.setEntity(params);

		// Execute the request

		HttpResponse response = client.execute(post);

		if (debug) {

			// Examine the response status

			System.out.println(response.getStatusLine());

			// Get hold of the response entity

			HttpEntity entity = response.getEntity();

			dump(entity);

		}

	}

	/**
	 * 
	 * 打印页面
	 * 
	 * @param entity
	 * 
	 * @throws IOException
	 */

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

	private static ArrayList<BillLink> getBillList(String billUrl) {

		ArrayList<String> list = getPatternList(billUrl,
				"openNode\\('[^>]+'\\)");
		ArrayList<BillLink> listn = new ArrayList<BillLink>();
		for (String s : list) {
			s = s.replaceAll("openNode\\('", "");
			s = s.replaceAll("'\\)", "");
			s = "http://132.121.130.146:8603/form!getFormIns.do?curNodeLsh="
					+ s;
			BillLink bl = new BillLink(s);
			if (!listn.contains(bl)) {
				listn.add(bl);
			}
			[ <font color='#000000'>442分钟</font>/<font color='#0000FF'>960分钟</font>]&nbsp;
			
			ArrayList<String> Timelist = getPatternList(billUrl,
					"openNode\\('[^>]+'\\)");
			
		}
		return listn;
	}

	private static ArrayList<String> getPatternList(String s, String matcher) {
		ArrayList<String> l = new ArrayList<String>();
		Matcher mr = Pattern.compile(matcher).matcher(s);
		while (mr.find()) {
			l.add(mr.group());
		}
		return l;

	}

}