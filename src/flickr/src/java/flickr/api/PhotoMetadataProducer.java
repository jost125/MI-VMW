package flickr.api;

import flickr.parallel.CubbyHole;
import flickr.rest.RestClient;
import flickr.rest.response.Photo;
import flickr.timemeasure.FileLogger;
import flickr.timemeasure.StopWatch;
import java.util.List;

public class PhotoMetadataProducer extends Thread {
	private CubbyHole cubbyHole;
	private RestClient restClient;
	private String keyword;
	private FileLogger fileLogger;
	private int limit;

	public PhotoMetadataProducer(CubbyHole cubbyHole, FileLogger fileLogger, RestClient restClient, String keyword, int limit) {
		this.cubbyHole = cubbyHole;
		this.restClient = restClient;
		this.keyword = keyword;
		this.limit = limit;
		this.fileLogger = fileLogger;
	}

	@Override
	public void run() {
		Integer numberOfPages = (int)Math.ceil((double)limit / 500.0);
		for (int i = 0; i < numberOfPages; i++) {
			StopWatch stopWatch = new StopWatch();
			stopWatch.start();

			int perPage = (i + 1 == numberOfPages) ? limit : 500;
			List<Photo> photos = restClient.searchPhotosByKeyword(keyword, i + 1, perPage);
			cubbyHole.putUnrankedPhotos(photos);

			stopWatch.stop();
			fileLogger.log("restApiRequest: " + stopWatch.getDuration() + " perPage: " + perPage);
		}
		cubbyHole.noMoreUnrankedPhotos();
	}
}
