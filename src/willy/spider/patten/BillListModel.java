package willy.spider.patten;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import a.BillLink;
import a.ItsmBasePatternModel;

public class BillListModel extends ItsmBasePatternModel {
	private ArrayList<BillLink> listn;

	public ArrayList<BillLink> getListn() {
		return listn;
	}

	public void setListn(ArrayList<BillLink> listn) {
		this.listn = listn;
	}

	public BillListModel(String url) {
		super(url);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void pattern(String result) {
		ArrayList<String> list = getPatternList(result, "openNode\\('[^>]+'\\)");
		listn = new ArrayList<BillLink>();
		for (String s : list) {
			s = s.replaceAll("openNode\\('", "");
			s = s.replaceAll("'\\)", "");
			s = "http://132.121.130.146:8603/form!getFormIns.do?curNodeLsh="
					+ s;
			BillLink bl = new BillLink(s);
			if (!listn.contains(bl)) {
				listn.add(bl);
			}
		}
		

	}

	private ArrayList<String> getPatternList(String s, String matcher) {
		ArrayList<String> l = new ArrayList<String>();
		Matcher mr = Pattern.compile(matcher).matcher(s);
		while (mr.find()) {
			l.add(mr.group());
		}
		return l;
	}
}
