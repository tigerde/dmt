package com.ztj.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.protocol.Protocol;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.FormTag;
import org.htmlparser.tags.InputTag;
import org.htmlparser.tags.LinkTag;

public class DmtLogin {
	// private static Logger logger = Logger.getLogger(SinaLogin.class);

	/**
	 * 模拟登陆
	 * 
	 * @param account
	 * @param password
	 * @return
	 */

	public static String login(String account, String password) {

		Protocol myhttps = new Protocol("https", new MySSLSocketFactory(), 443);
		Protocol.registerProtocol("https", myhttps);
		HttpClient client = new HttpClient();

		GetMethod get = null;

		String mentity = null;

		try {
			// 打开首页
			get = new GetMethod("http://dmt.p.cn/Default.aspx");
			client.executeMethod(get);
			mentity = get.getResponseBodyAsString();
			// 跳转至登录界面
			get = new GetMethod(
					"https://sso2.p.cn/cuo/login?service=http://dmt.p.cn/Default.aspx");
			client.executeMethod(get);
			mentity = get.getResponseBodyAsString();

			// 执行登录
			PostMethod postMethod = new PostMethod(
					"https://sso2.p.cn/cuo/login.aspx?service=http%3a%2f%2fdmt.p.cn%2fDefault.aspx");

			postMethod
					.addParameter(
							"__VIEWSTATE",
							"/wEPDwULLTIxMzA1ODI3NTNkGAEFHl9fQ29udHJvbHNSZXF1aXJlUG9zdEJhY2tLZXlfXxYBBQlpYnRuTG9naW7aI71gcyo2/Xu/j+xb2VjRNqN+cg==");
			postMethod
					.addParameter("__EVENTVALIDATION",
							"/wEWBALatPzxDgKvpuq2CALyveCRDwKBo5SvBSAwXIblA3rzO9nUcWBIuW/qCBd8");
			postMethod.addParameter("username", "18653211535");
			postMethod.addParameter("password", "123456");
			postMethod.addParameter("ibtnLogin.x", "8");
			postMethod.addParameter("ibtnLogin.y", "17");
			postMethod.getParams().setContentCharset("UTF-8");
			client.executeMethod(postMethod);

			String entity = postMethod.getResponseBodyAsString();
			LinkTag link = HtmlTool.parseTag(entity, LinkTag.class);

			// 登录后首页
			get = new GetMethod(link.getLink());
			client.executeMethod(get);
			entity = get.getResponseBodyAsString();
			Div div = HtmlTool.parseTag(entity, Div.class, "class", "userinfo");

			// 跳转到我的津贴页面
			get = new GetMethod(
					"http://dmt.p.cn/reportv2/report/GetSubsidy.aspx");
			client.executeMethod(get);
			entity = get.getResponseBodyAsString();

			FormTag ft = HtmlTool.parseTag(entity, FormTag.class);

			InputTag imit = HtmlTool.parseTag(ft.getChildrenHTML(),
					InputTag.class, "type", "image");
			if (imit.getAttribute("disabled") == null) {
				InputTag it = HtmlTool.parseTag(ft.getChildrenHTML(),
						InputTag.class, "id", "__VIEWSTATE");
				postMethod = new PostMethod("http://dmt.p.cn/reportv2/report/"
						+ ft.getFormLocation());

				postMethod
						.addParameter("__VIEWSTATE", it.getAttribute("value"));
				postMethod.addParameter("btnGetSubsidy.x", "13");
				postMethod.addParameter("btnGetSubsidy.y", "10");
				postMethod.getParams().setContentCharset("UTF-8");
				// System.exit(0);
				client.executeMethod(postMethod);
				entity = postMethod.getResponseBodyAsString();
				System.out.println("success");
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;
	}

	private static String getServerTime() {
		long servertime = new Date().getTime() / 1000;
		return String.valueOf(servertime);
	}

	public static void main(String args[]) {

		/*InputTag imit = HtmlTool
				.parseTag(
						"<a href=\"#\">"
								+ "<input type=\"image\" name=\"btnGetSubsidy\" id=\"btnGetSubsidy\" disabled=\"disabled\" src=\"images/lc.jpg\" border=\"0\" /></a>",
						InputTag.class, "type", "image");
		InputTag imit2 = HtmlTool
				.parseTag(
						"<a href=\"#\">"
								+ "<input type=\"image\" name=\"btnGetSubsidy\" id=\"btnGetSubsidy\" src=\"images/lc.jpg\" border=\"0\" /></a>",
						InputTag.class, "type", "image");
		System.out.println(imit.getAttribute("disabled"));
		System.out.println(imit2.getAttribute("disabled"));*/
		 login(null,null);

	}
}
