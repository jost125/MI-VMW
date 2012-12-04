package flickr.timemeasure;

public class StopWatch {

	long startTime;
	long endTime;

	public void start() {
		startTime = System.nanoTime();
	}

	public void stop() {
		endTime = System.nanoTime();
	}

	public long getDuration() {
		return endTime - startTime;
	}

}
