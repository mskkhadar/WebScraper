package com.webscrap.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebScraperService {
	public static final String FOLDER_PATH = "./Web_Scrap/";

	public static void main(String[] args) {
		if (args.length > 0) {
			String URL = args[0];
			boolean metadata = args.length > 1 ? args[1].equals("--metadata") : false;
			try {
				initiateWebScrap(URL, metadata);
			} catch (IOException e) {
				print("Unable to do web-scrap process for %s: ", URL);
			}
		}

	}

	private static void initiateWebScrap(String URL, boolean metadata) throws IOException {
		Date startTime = new Date();
		print("Web-Scrap Last initiated at 	:%s", startTime);

		Document doc = Jsoup.connect(URL).get();
		if (!metadata) {
			// Section 1
			String title = doc.getElementsByTag("title").text();
			Path path = Paths.get(FOLDER_PATH);
			Files.createDirectories(path);
			Files.write(Paths.get(FOLDER_PATH + title + ".html"), doc.toString().getBytes());
			print("Downloaded path of offline website: %s", FOLDER_PATH);
		} else {
			// Section 2
			Elements links = doc.select("a[href]");
			Elements media = doc.select("[src]");
			Elements imports = doc.select("link[href]");

			int imageCount = 0;
			for (Element src : media) {
				if (src.normalName().equals("img"))
					imageCount++;
			}
			print("Images Count			: %d", imageCount);
			print("Imports Links Count		: %d", imports.size());
			print("Links Count			: %d", links.size());
		}
	}

	private static void print(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}
}
