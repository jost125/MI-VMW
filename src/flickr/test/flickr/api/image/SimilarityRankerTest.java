package flickr.api.image;

import java.io.File;
import junit.framework.TestCase;

public class SimilarityRankerTest extends TestCase {
	private SimilarityRanker instance;

	protected void setUp() throws Exception {
		instance = new SimilarityRanker();
	}

	public void testGetRank_photo() throws CannonGetRankException {
		File image = new File(getClass().getResource("img.jpg").getFile());
		short red = 255;
		short green = 255;
		short blue = 255;
		Color color = new Color(red, green, blue);
		
		double rank = instance.getRank(image, color);

		assertTrue(rank > 50);
	}

	public void testGetRank_red() throws CannonGetRankException {
		File image = new File(getClass().getResource("red.png").getFile());
		short red = 255;
		short green = 0;
		short blue = 0;
		Color color = new Color(red, green, blue);

		double rank = instance.getRank(image, color);

		assertTrue(rank < 0.1);
	}
}
