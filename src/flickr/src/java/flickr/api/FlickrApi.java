package flickr.api;

import flickr.api.image.Color;
import flickr.api.image.PhotoComparator;
import flickr.api.image.SimilarityRanker;
import flickr.rest.RestClient;
import flickr.rest.response.Photo;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FlickrApi {

	private RestClient restClient;
	private SimilarityRanker similarityRanker;
	private List<Thread> threadsPool;

	public FlickrApi(RestClient restClient, SimilarityRanker similarityRanker) {
		this.restClient = restClient;
		this.similarityRanker = similarityRanker;
		this.threadsPool = new ArrayList<Thread>();
	}

	public Queue<Photo> getPhotos(String keyword, Color color, Integer limit) {
		Queue<Photo> orderedPhotos = new PriorityQueue<Photo>(limit, new PhotoComparator());
		Integer numberOfPages = (int)Math.ceil((double)limit / 500.0);
		for (int i = 0; i < numberOfPages; i++) {
			List<Photo> photos = restClient.searchPhotosByKeyword(keyword, i + 1, (i + 1 == numberOfPages) ? limit : 500);
			for (Photo photo : photos) {
				CountRankForPhotoThread thread = new CountRankForPhotoThread(photo, color, similarityRanker);
				threadsPool.add(thread);
				thread.start();
			}
			waitForThreadsToFinish();
			orderedPhotos.addAll(photos);
		}

		return orderedPhotos;
	}

	private void waitForThreadsToFinish() {
		for (Thread thread : threadsPool) {
			try {
				thread.join();
			} catch (InterruptedException ex) {
				Logger.getLogger(FlickrApi.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}
