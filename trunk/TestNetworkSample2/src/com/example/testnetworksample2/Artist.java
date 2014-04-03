package com.example.testnetworksample2;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.example.testnetworksample2.parser.SaxParserHandler;
import com.example.testnetworksample2.parser.SaxResultParser;

public class Artist implements SaxParserHandler {
	int artistId;
	String artistName;
	@Override
	public String getTagName() {
		// TODO Auto-generated method stub
		return "artist";
	}
	@Override
	public void parseStartElement(String tagName, Attributes attributes,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		
	}
	@Override
	public void parseEndElement(String tagName, Object content,
			String namespaceUri, String qualifiedName, SaxResultParser parser)
			throws SAXException {
		if (tagName.equalsIgnoreCase("artistId")) {
			this.artistId = Integer.parseInt((String)content);
		} else if (tagName.equalsIgnoreCase("artistName")) {
			this.artistName = (String)content;
		}
		
	}
	@Override
	public Object getParseResult() {
		// TODO Auto-generated method stub
		return this;
	}
}