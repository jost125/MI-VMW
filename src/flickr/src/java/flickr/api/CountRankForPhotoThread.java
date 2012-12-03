package flickr.api;

import flickr.api.image.Color;
import flickr.api.image.SimilarityRanker;
import flickr.rest.response.Photo;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CountRankForPhotoThread extends Thread {
	
	private Photo photo;
	private Color color;
	private SimilarityRanker similarityRanker;

	public CountRankForPhotoThread(Photo photo, Color color, SimilarityRanker similarityRanker) {
		this.photo = photo;
		this.color = color;
		this.similarityRanker = similarityRanker;
	}

	@Override
	public void run() {
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

	private void downloadFile(URL url, File file) throws MalformedURLException, IOException {
		ReadableByteChannel rbc = Channels.newChannel(url.openStream());
		FileOutputStream fos = new FileOutputStream(file);
		fos.getChannel().transferFrom(rbc, 0, 1 << 24);
	}
}
