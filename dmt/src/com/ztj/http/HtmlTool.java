package com.ztj.http;

import java.util.ArrayList;
import java.util.List;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;


public class HtmlTool {


	/**
	 * 提取某个属性值的标签列表(多个)
	 * @param <T>
	 * @param html 被提取的html文本
	 * @param tagType 标签的类型
	 * @param attributeName 某个属性的名称
	 * @param attributeValue 某个属性的值
	 * @return List<T>
	 */
	public static <T extends TagNode> List<T> parseTags(String html,final Class<T> tagType,final String attributeName,final String attributeValue)
	{		
		try {
			//创建一个HTML解释器
			//Parser parser = new Parser();
			MyParser parser=new MyParser();
			parser.setInputHTML(html);
			NodeList tagList = parser.parse(new NodeFilter() {
				
				public boolean accept(Node node) {
					if(node.getClass() == tagType)
					{
						T tn = (T)node;
						if(attributeName == null)
						{
							return true;
						}
						String attrValue = tn.getAttribute(attributeName);
						if(attrValue!=null && attrValue.equals(attributeValue))
						{
							return true;
						}
					}
				
					return false;
				}
			});
			List<T> tags = new ArrayList<T>();
			for (int i = 0; i < tagList.size(); i++) {
				T t = (T)tagList.elementAt(i);
				tags.add(t);
			}
			return tags;
		} catch (ParserException e) {
			//throw new SpiderException(errcode, e)
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @param <T>
	 * @param html
	 * @param tagType
	 * @return
	 */
	public static <T extends TagNode> List<T> parseTags(String html,final Class<T> tagType)
	{
		return parseTags(html, tagType, null, null);
	}
	/**
	 * 提取某个属性值的标签列表(单个)
	 * @param <T>
	 * @param html 被提取的html文本
	 * @param tagType 标签的类型
	 * @param attributeName 某个属性的名称
	 * @param attributeValue 某个属性的值
	 * @return List<T>
	 */
	public static <T extends TagNode> T parseTag(String html,final Class<T> tagType,final String attributeName,final String attributeValue)
	{
		List<T> tags = parseTags(html, tagType, attributeName, attributeValue);
		if(tags !=null && tags.size()>0)
		{
			return tags.get(0);
		}
		return null;
	}
	
	/**
	 * 
	 * @param <T>
	 * @param html
	 * @param tagType
	 * @return
	 */
	public static <T extends TagNode> T parseTag(String html,final Class<T> tagType)
	{
		return parseTag(html, tagType, null, null);
	}

}
