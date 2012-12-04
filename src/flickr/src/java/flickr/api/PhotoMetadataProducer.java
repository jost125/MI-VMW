package flickr.api;

import flickr.parallel.CubbyHole;
import flickr.rest.RestClient;
import flickr.rest.response.Photo;
import java.util.List;

public class PhotoMetadataProducer extends Thread {
	private CubbyHole cubbyHole;
	private RestClient restClient;
	private String keyword;
	private int limit;

	public PhotoMetadataProducer(CubbyHole cubbyHole, RestClient restClient, String keyword, int limit) {
		this.cubbyHole = cubbyHole;
		this.restClient = restClient;
		this.keyword = keyword;
		this.limit = limit;
	}

	@Override
	public void run() {
		Integer numberOfPages = (int)Math.ceil((double)limit / 500.0);
		for (int i = 0; i < numberOfPages; i++) {
			List<Photo> photos = restClient.searchPhotosByKeyword(keyword, i + 1, (i + 1 == numberOfPages) ? limit : 500);
			cubbyHole.putUnrankedPhotos(photos);
		}
		cubbyHole.noMoreUnrankedPhotos();
	}
}
