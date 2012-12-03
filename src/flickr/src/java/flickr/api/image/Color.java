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

	public static Color getInstaceFromHex(String hexColor) {
		String redHex = hexColor.substring(0, 2);
		String greenHex = hexColor.substring(2, 4);
		String blueHex = hexColor.substring(4, 6);

		short red = (short)Integer.parseInt(redHex, 16);
		short green = (short)Integer.parseInt(greenHex, 16);
		short blue = (short)Integer.parseInt(blueHex, 16);

		return new Color(red, green, blue);
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
