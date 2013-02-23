package dragonkk.rs2rsps.tools.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WebPage {

	private String url;
	private List<Line> lines;
	private boolean readyToUse;
	private IOException error;
	private String title;
	private String description;
	private String generator;
	private String keywords;
	private String replaceChar;

	public WebPage(String url) {
		this.makePage(url);
		this.setReplaceChar("'");
		readPage();
	}

	public WebPage(String url, String replaceChar) {
		this.makePage(url);
		this.setReplaceChar(replaceChar);
		readPage();
	}

	private final void makePage(String url) {
		if (!url.contains("http://"))
			url = "http://" + url;
		url = url.replace(" ", "_");
		url = url.trim();
		this.setUrl(url);
		this.setLines(new ArrayList<Line>());
	}

	private final void readPage() {
		try {
			BufferedReader page = new BufferedReader(new InputStreamReader(
					new URL(this.getUrl()).openConnection().getInputStream()));
			String text;
			int index = 0;
			while ((text = page.readLine()) != null) {
				boolean edited = false;
				if (this.getReplaceChar() != null) {
					int charindex = text.indexOf(34);
					if (charindex != -1) {
						String delete = String.valueOf(text.charAt(charindex));
						text = text.replaceAll(delete, this.getReplaceChar());
						edited = true;
					}
				}
				this.getLines().add(new Line(text, edited));
				if (this.getTitle() == null
						&& contains(text, "<title>", "</title>"))
					this.setTitle(this.substring(text, "<title>", "</title>"));
				if (this.getDescription() == null
						&& contains(text, "<meta name='description' content='",
								"' />"))
					this.setDescription(this.substring(text,
							"<meta name='description' content='", "' />"));
				if (this.getGenerator() == null
						&& contains(text, "<meta name='generator' content='",
								"' />"))
					this.setGenerator(this.substring(text,
							"<meta name='generator' content='", "' />"));
				if (this.getKeywords() == null
						&& contains(text, "<meta name='keywords' content='",
								"' />"))
					this.setKeywords(this.substring(text,
							"<meta name='keywords' content='", "' />"));
				index++;
			}
			page.close();
			this.setReadyToUse(true);
		} catch (IOException e) {
			this.setReadyToUse(false);
			this.setError(e);
		}
	}

	public String substring(String text, String firstIndex, String secondIndex) {
		return text.substring(text.indexOf(firstIndex) + firstIndex.length(),
				text.lastIndexOf(secondIndex));
	}

	public String substring(String text, String firstIndex) {
		return text.substring(text.indexOf(firstIndex) + firstIndex.length());
	}
	
	public void printWebPage() {
		for (Line line : this.getLines())
			System.out.println(line.getText());
	}

	public final boolean contains(String text, String... parts) {
		for (String part : parts)
			if (!text.contains(part))
				return false;
		return true;
	}
	
	public final boolean startsWith(String text, String part) {
			if (!text.startsWith(part))
				return false;
		return true;
	}

	public int getLineForText(String... parts) {
		int index = 0;
		for (Line line : this.getLines()) {
			if (this.contains(line.getText(), parts))
				return index;
			index++;
		}
		return -1;
	}

	public List<Integer> getAllLinesForText(String... parts) {
		int index = 0;
		List<Integer> lineIds = new ArrayList<Integer>();
		for (Line line : this.getLines()) {
			if (this.contains(line.getText(), parts))
				lineIds.add(index);
			index++;
		}
		return lineIds;
	}
	
	public List<Integer> getAllLinesForStartText(String part) {
		int index = 0;
		List<Integer> lineIds = new ArrayList<Integer>();
		for (Line line : this.getLines()) {
			if (this.startsWith(line.getText(), part))
				lineIds.add(index);
			index++;
		}
		return lineIds;
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((error == null) ? 0 : error.hashCode());
		result = prime * result
				+ ((generator == null) ? 0 : generator.hashCode());
		result = prime * result
				+ ((keywords == null) ? 0 : keywords.hashCode());
		result = prime * result + ((lines == null) ? 0 : lines.hashCode());
		result = prime * result + (readyToUse ? 1231 : 1237);
		result = prime * result
				+ ((replaceChar == null) ? 0 : replaceChar.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WebPage other = (WebPage) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		if (generator == null) {
			if (other.generator != null)
				return false;
		} else if (!generator.equals(other.generator))
			return false;
		if (keywords == null) {
			if (other.keywords != null)
				return false;
		} else if (!keywords.equals(other.keywords))
			return false;
		if (lines == null) {
			if (other.lines != null)
				return false;
		} else if (!lines.equals(other.lines))
			return false;
		if (readyToUse != other.readyToUse)
			return false;
		if (replaceChar == null) {
			if (other.replaceChar != null)
				return false;
		} else if (!replaceChar.equals(other.replaceChar))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	@Override
	public final String toString() {
		return "Page [description=" + description + ", generator=" + generator
				+ ", keywords=" + keywords + ", readyToUse=" + readyToUse
				+ ", replaceChar=" + replaceChar + ", title=" + title
				+ ", url=" + url + "]";
	}

	private final void setLines(List<Line> page) {
		this.lines = page;
	}

	public final List<Line> getLines() {
		return lines;
	}

	private final void setReadyToUse(boolean readyToUse) {
		this.readyToUse = readyToUse;
	}

	public final boolean isReadyToUse() {
		return readyToUse;
	}

	private void setError(IOException error) {
		this.error = error;
	}

	public final IOException getError() {
		return error;
	}

	public final void setUrl(String url) {
		this.url = url;
	}

	public final String getUrl() {
		return url;
	}

	public final void setTitle(String title) {
		this.title = title;
	}

	public final String getTitle() {
		return title;
	}

	private final void setReplaceChar(String replaceChar) {
		this.replaceChar = replaceChar;
	}

	private final String getReplaceChar() {
		return replaceChar;
	}

	private final void setDescription(String description) {
		this.description = description;
	}

	public final String getDescription() {
		return description;
	}

	private final void setGenerator(String generator) {
		this.generator = generator;
	}

	public final String getGenerator() {
		return generator;
	}

	private final void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public final String getKeywords() {
		return keywords;
	}

}
