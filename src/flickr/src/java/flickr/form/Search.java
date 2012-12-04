package flickr.form;

/**
 *
 * @author Ondrej Karas
 */
public class Search {
	private String term;
	private String color;
	private int numberOfResults;

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getNumberOfResults() {
		return numberOfResults;
	}

	public void setNumberOfResults(int numberOfResults) {
		this.numberOfResults = numberOfResults;
	}

	@Override
	public String toString() {
		return "Search{" + "term=" + term + ", color=" + color + ", numberOfResults=" + numberOfResults + '}';
	}

}
