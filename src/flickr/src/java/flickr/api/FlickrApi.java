package flickr.api;

import flickr.api.image.Color;
import flickr.api.image.PhotoComparator;
import flickr.api.image.SimilarityRanker;
import flickr.parallel.CubbyHole;
import flickr.rest.RestClient;
import flickr.rest.response.Photo;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FlickrApi {

	private RestClient restClient;
	private SimilarityRanker similarityRanker;

	public FlickrApi(RestClient restClient, SimilarityRanker similarityRanker) {
		this.restClient = restClient;
		this.similarityRanker = similarityRanker;
	}

	public Queue<Photo> getPhotos(String keyword, Color color, Integer limit) {
		Queue<Photo> orderedPhotos = new PriorityQueue<Photo>(limit, new PhotoComparator());

		CubbyHole cubbyHole = new CubbyHole();

		PhotoMetadataProducer producer = new PhotoMetadataProducer(cubbyHole, restClient, keyword, limit);
		PhotoMetadataConsumer consumer = new PhotoMetadataConsumer(cubbyHole, color, similarityRanker);
		producer.start();
		consumer.start();

		try {
			producer.join();
			consumer.join();
		} catch (InterruptedException ex) {
			Logger.getLogger(FlickrApi.class.getName()).log(Level.SEVERE, null, ex);
		}

		orderedPhotos.addAll(cubbyHole.getRankedPhotos());

		return orderedPhotos;
	}
}
