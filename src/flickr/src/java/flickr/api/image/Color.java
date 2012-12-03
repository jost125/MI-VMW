package flickr.api.image;

public class Color {
	private short red;
	private short green;
	private short blue;

	public Color(short red, short green, short blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public short getRed() {
		return red;
	}

	public short getGreen() {
		return green;
	}

	public short getBlue() {
		return blue;
	}
}
