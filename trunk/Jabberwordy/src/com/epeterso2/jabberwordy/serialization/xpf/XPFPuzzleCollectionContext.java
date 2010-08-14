package com.epeterso2.jabberwordy.serialization.xpf;

import org.jdom.Document;

/**
 * @author <a href="http://www.epeterso2.com">Eric Peterson</a>
 * @see <a href="http://www.xwordinfo.com/XPF/">XWordInfo XPF Universal Crossword Puzzle Format</a>
 */
public class XPFPuzzleCollectionContext {
	
	private Document document = null;

	public XPFPuzzleCollectionContext( Document document )
	{
		this.document = document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public Document getDocument() {
		return document;
	}

}
