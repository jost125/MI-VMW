package flickr.api;

import flickr.api.image.Color;
import flickr.api.image.SimilarityRanker;
import flickr.parallel.CubbyHole;
import flickr.rest.response.Photo;
import flickr.timemeasure.FileLogger;
import flickr.timemeasure.StopWatch;
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
	private CubbyHole cubbyHole;
	private FileLogger fileLogger;

	public CountRankForPhotoThread(Photo photo, Color color, SimilarityRanker similarityRanker, CubbyHole cubbyHole, FileLogger fileLogger) {
		this.photo = photo;
		this.color = color;
		this.similarityRanker = similarityRanker;
		this.cubbyHole = cubbyHole;
		this.fileLogger = fileLogger;
	}

	@Override
	public void run() {
		File file = null;
		try {
			StopWatch stopWatch = new StopWatch();
			stopWatch.start();

			URL url = new URL("http://farm" + photo.getFarm() + ".staticflickr.com/" + photo.getServer() + "/" + photo.getId() + "_" + photo.getSecret() + "_s.jpg");
			file = File.createTempFile(photo.getId() + "_" + photo.getSecret() + "_s", ".jpg");
			downloadFile(url, file);

			stopWatch.stop();
			fileLogger.log("photoDownload: " + stopWatch.getDuration());

			stopWatch = new StopWatch();
			stopWatch.start();

			photo.setRank(similarityRanker.getRank(file, color));

			stopWatch.stop();
			fileLogger.log("photoRankCount: " + stopWatch.getDuration());
		} catch (Exception ex) {
			Logger.getLogger(FlickrApi.class.getName()).log(Level.SEVERE, null, ex);
			photo.setRank(Double.POSITIVE_INFINITY);
		} finally {
			finish(file);
		}
	}

	private void downloadFile(URL url, File file) throws MalformedURLException, IOException {
		ReadableByteChannel rbc = Channels.newChannel(url.openStream());
		FileOutputStream fos = new FileOutputStream(file);
		fos.getChannel().transferFrom(rbc, 0, 1 << 24);
		fos.close();
	}

	private synchronized void finish(File file) {
		if (file != null) {
			file.delete();
		}
		cubbyHole.putRankedPhoto(photo);
	}
}
