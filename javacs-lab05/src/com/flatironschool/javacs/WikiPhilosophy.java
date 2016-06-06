package com.flatironschool.javacs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import org.jsoup.select.Elements;

class WikiPageParser {
	public String getFirstValidLink(Elements paragraphs) {
		return "hptts://en.wikipedia.org";
	}

}

public class WikiPhilosophy {
	
	final WikiFetcher wf = new WikiFetcher();
	final List visitedPages = new ArrayList<String>();
	final WikiPageParser parser = new WikiPageParser();
	static final int MAX_HOPS = 10;
	
	public boolean canConnect(final String sourceUrl, final String destUrl) {
		visitedPages.add(sourceUrl);
		String nextUrl = sourceUrl;
		for (int hops = 0; hops < MAX_HOPS; hops++) {
			System.out.println("-------------Fetching a new page------------");
			System.out.println(nextUrl);
			try {
				Elements paragraphs = wf.fetchWikipedia(nextUrl);
				Element firstPara = paragraphs.get(0);
				Iterable<Node> iter = new WikiNodeIterable(firstPara);
				for (Node node: iter) {
					if (node instanceof TextNode) {
						System.out.print(node);
					}
		        }
			} catch (Exception e) {
				return false;
			}
			
			System.out.println("-------------Fetching complete--------------");
			try {
				String firstValidLink = parser.getFirstValidLink(wf.readWikipedia(nextUrl));
				System.out.println("The first valid link is:" + firstValidLink);
			} catch (Exception e) {
				return false;
			}

			return false;
		}
		return false;
	}
	
	/**
	 * Tests a conjecture about Wikipedia and Philosophy.
	 * 
	 * https://en.wikipedia.org/wiki/Wikipedia:Getting_to_Philosophy
	 * 
	 * 1. Clicking on the first non-parenthesized, non-italicized link
     * 2. Ignoring external links, links to the current page, or red links
     * 3. Stopping when reaching "Philosophy", a page with no links or a page
     *    that does not exist, or when a loop occurs
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
        // some example code to get you started

		String sourceUrl = "https://en.wikipedia.org/wiki/Java_(programming_language)";
		String destUrl = "https://en.wikipedia.org/wiki/Philosophy";
		
		new WikiPhilosophy().canConnect(sourceUrl, destUrl);

	}
}
