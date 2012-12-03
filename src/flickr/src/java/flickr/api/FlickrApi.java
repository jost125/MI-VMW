package flickr.api;

import flickr.api.image.Color;
import flickr.api.image.PhotoComparator;
import flickr.api.image.SimilarityRanker;
import flickr.rest.RestClient;
import flickr.rest.response.Photo;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.List;
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
		Queue<Photo> orderedPhotos = new PriorityQueue<Photo>(2000, new PhotoComparator());
		Integer numberOfPages = (int)Math.ceil((double)limit / 500.0);
		for (int i = 0; i < numberOfPages; i++) {
			List<Photo> photos = restClient.searchPhotosByKeyword(keyword, i + 1, (i + 1 == numberOfPages) ? limit : 500);
			for (Photo photo : photos) {
				countRankForPhoto(photo, color);
			}
			orderedPhotos.addAll(photos);
		}

		return orderedPhotos;
	}

	private void downloadFile(URL url, File file) throws MalformedURLException, IOException {
		ReadableByteChannel rbc = Channels.newChannel(url.openStream());
		FileOutputStream fos = new FileOutputStream(file);
		fos.getChannel().transferFrom(rbc, 0, 1 << 24);
	}

	private void countRankForPhoto(Photo photo, Color color) {
		String fileUrl = "http://farm" + photo.getFarm() + ".staticflickr.com/" + photo.getServer() + "/" + photo.getId() + "_" + photo.getSecret() + "_s.jpg";
		try {
			URL url = new URL(fileUrl);
			File file = File.createTempFile(photo.getId() + "_" + photo.getSecret() + "_s", ".jpg");
			System.out.println(file.getAbsolutePath());
			downloadFile(url, file);
			photo.setRank(similarityRanker.getRank(file, color));
			file.delete();
		} catch (Exception ex) {
			Logger.getLogger(FlickrApi.class.getName()).log(Level.SEVERE, null, ex);
			photo.setRank(Double.POSITIVE_INFINITY);
		}
	}

}
