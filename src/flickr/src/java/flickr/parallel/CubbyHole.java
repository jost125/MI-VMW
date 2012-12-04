package flickr.parallel;

import flickr.rest.response.Photo;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CubbyHole {

	private Queue<Photo> unrankedPhotos;
	private Queue<Photo> rankedPhotos;
	private int unrankedOut = 0;
	private boolean unrankedPhotosWillBeProduced = true;

	public CubbyHole() {
		this.unrankedPhotos = new LinkedList<Photo>();
		this.rankedPhotos = new LinkedList<Photo>();
	}

	public synchronized Photo getNextUnrankedPhoto() {
		while (unrankedPhotos.isEmpty() || unrankedOut > 400) {
			try {
				wait();
			} catch (InterruptedException e) {
				Logger.getLogger(CubbyHole.class.getName()).log(Level.SEVERE, null, e);
			}
		}
		unrankedOut++;
		Photo photo = unrankedPhotos.poll();
		notifyAll();
		return photo;
	}

	public synchronized void putUnrankedPhotos(Collection<Photo> photos) {
		unrankedPhotos.addAll(photos);
		notifyAll();
	}

	public synchronized void putRankedPhoto(Photo photo) {
		unrankedOut--;
		rankedPhotos.add(photo);
		notifyAll();
	}

	public synchronized boolean hasUnrankedPhotos() {
		if (unrankedPhotosWillBeProduced) {
			while (unrankedPhotos.isEmpty()) {
				try {
					wait();
				} catch (InterruptedException e) {
					Logger.getLogger(CubbyHole.class.getName()).log(Level.SEVERE, null, e);
				}
			}
		}

		return unrankedPhotosWillBeProduced || !unrankedPhotos.isEmpty();
	}

	public synchronized void noMoreUnrankedPhotos() {
		unrankedPhotosWillBeProduced = false;
	}

	public Collection<Photo> getRankedPhotos() {
		return rankedPhotos;
	}

}
