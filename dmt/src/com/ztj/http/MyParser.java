package com.ztj.http;

import java.net.HttpURLConnection;

import org.htmlparser.Parser;
import org.htmlparser.PrototypicalNodeFactory;
import org.htmlparser.Tag;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.ParserFeedback;

/**
 * @（#）:MyParser.java
 * @description: 继承自Parser的类,对Parser进行扩展
 * @author: zx 2010-12-9
 */
@SuppressWarnings("serial")
public class MyParser extends Parser {
	private static PrototypicalNodeFactory factory = null;

	 
	 //注册自定义标签
	static{ factory = new PrototypicalNodeFactory();
	 // factory.registerTag(new StrongTag());
	 // factory.registerTag(new ITag());
	 // factory.registerTag(new ITag()); 
	 // factory.registerTag(new HTag());
	 // factory.registerTag(new EmTag());
	  }
	 

	public MyParser() {
		super();
		setNodeFactory(factory);
	}

	public MyParser(HttpURLConnection connection) throws ParserException {
		super(connection);
		setNodeFactory(factory);
	}

	public MyParser(Lexer lexer, ParserFeedback fb) {
		super(lexer, fb);
		setNodeFactory(factory);
	}

}
