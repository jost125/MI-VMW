package flickr.api;

import flickr.api.image.Color;
import flickr.api.image.SimilarityRanker;
import flickr.parallel.CubbyHole;
import flickr.rest.response.Photo;
import flickr.timemeasure.FileLogger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PhotoMetadataConsumer extends Thread {
	private CubbyHole cubbyHole;
	private Color color;
	private SimilarityRanker similarityRanker;
	private List<Thread> threadPool;
	private FileLogger fileLogger;

	public PhotoMetadataConsumer(CubbyHole cubbyHole, FileLogger fileLogger, Color color, SimilarityRanker similarityRanker) {
		this.cubbyHole = cubbyHole;
		this.color = color;
		this.similarityRanker = similarityRanker;
		this.threadPool = new ArrayList<Thread>();
		this.fileLogger = fileLogger;
	}

	@Override
	public void run() {
		while (cubbyHole.hasUnrankedPhotos()) {
			Photo photo = cubbyHole.getNextUnrankedPhoto();
			CountRankForPhotoThread thread = new CountRankForPhotoThread(photo, color, similarityRanker, cubbyHole, fileLogger);
			thread.start();
			threadPool.add(thread);
		}

		for (Thread thread : threadPool) {
			try {
				thread.join();
			} catch (InterruptedException ex) {
				Logger.getLogger(PhotoMetadataConsumer.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}
