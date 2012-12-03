package flickr.api.image;

import flickr.rest.response.Photo;
import java.util.Comparator;

public class PhotoComparator implements Comparator<Photo> {

	@Override
	public int compare(Photo p1, Photo p2) {
		return p1.getRank().compareTo(p2.getRank());
	}
	
}
