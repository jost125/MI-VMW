package flickr.api.image;

import java.io.File;
import junit.framework.TestCase;

public class SimilarityRankerTest extends TestCase {
	private SimilarityRanker instance;

	@Override
	protected void setUp() throws Exception {
		instance = new SimilarityRanker();
	}

	public void testGetRank_photo() throws CannotGetRankException {
		File image = new File(getClass().getResource("img.jpg").getFile());
		Color color = Color.getInstaceFromHex("FFFFFF");
		
		double rank = instance.getRank(image, color);

		assertTrue(rank > 50);
	}

	public void testGetRank_red() throws CannotGetRankException {
		File image = new File(getClass().getResource("red.png").getFile());
		Color color = Color.getInstaceFromHex("FF0000");

		double rank = instance.getRank(image, color);

		assertTrue(rank < 0.1);
	}
}
