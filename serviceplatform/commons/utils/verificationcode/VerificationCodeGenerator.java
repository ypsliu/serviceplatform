/**
 * 
 */
package com.ruixue.serviceplatform.commons.utils.verificationcode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * the VerificationCode Generator
 * 
 * @author shangchunming@rkylin.com.cn
 * 
 */
public final class VerificationCodeGenerator {

	public static final int DEFAULT_CODE_LENGTH = 4;

	public static final int DEFAULT_IMAGE_WIDTH = 102;

	public static final int DEFAULT_IAMGE_HEIGHT = 28;

	private static final char[] CODE_SEQUENCE = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P',
			'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9' };

	private static final Color COLORS[] = new Color[] { new Color(113, 31, 71), new Color(37, 0, 37),
			new Color(111, 33, 36), new Color(0, 0, 112), new Color(14, 51, 16), new Color(1, 1, 1),
			new Color(72, 14, 73), new Color(65, 67, 29), new Color(116, 86, 88), new Color(41, 75, 71) };

	private static final Random RANDOM = new Random();

	/**
	 * to generate a random code string
	 * 
	 * @param length
	 *            the length
	 * @return the code
	 */
	public static String generateRandomCode(int length) {
		StringBuilder buf = new StringBuilder();
		for (int i = 1; i <= length; i++) {
			buf.append(CODE_SEQUENCE[RANDOM.nextInt(32)]);
		}
		return buf.toString();
	}

	/**
	 * to generate a random color
	 * 
	 * @return the color
	 */
	public static Color generateRandomColor() {
		return COLORS[RANDOM.nextInt(10)];
	}

	/**
	 * to generate a random VerificationCode
	 * 
	 * @return the VerificationCode
	 */
	public static VerificationCode generate() {
		return generate(generateRandomCode(DEFAULT_CODE_LENGTH), DEFAULT_IMAGE_WIDTH, DEFAULT_IAMGE_HEIGHT);
	}

	/**
	 * to generate a VerificationCode by code
	 * 
	 * @param code
	 *            the code
	 * @return the VerificationCode
	 */
	public static VerificationCode generate(String code, int width, int height) {
		// check
		if (code == null) {
			return null;
		}
		// code length
		int len = code.length();
		// offset
		int xOffset = width / (len + 1);
		int yOffset = height - 4;
		// new image
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// graphic
		Graphics2D graphics = img.createGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, width, height);
		graphics.setFont(new Font("Fixedsys", Font.BOLD, height - 2));
		graphics.drawRect(0, 0, width - 1, height - 1);
		// draw the codes
		for (int i = 0; i < len; i++) {
			graphics.setColor(generateRandomColor());
			graphics.drawString(String.valueOf(code.charAt(i)), (i + 1) * xOffset, yOffset);
		}
		// draw disturb lines
		for (int i = 0; i < 10; i++) {
			graphics.setColor(generateRandomColor());
			int x1 = RANDOM.nextInt(width);
			int y1 = RANDOM.nextInt(height);
			int dx = RANDOM.nextInt(width);
			int dy = RANDOM.nextInt(height);
			graphics.drawLine(x1, y1, x1 + dx, y1 + dy);
		}
		// twist the image
		double dMultValue = RANDOM.nextInt(7) + 3;
		double dPhase = RANDOM.nextInt(6);
		BufferedImage dest = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < dest.getWidth(); i++) {
			for (int j = 0; j < dest.getHeight(); j++) {
				int nOldX = getXPosition4Twist(dPhase, dMultValue, dest.getHeight(), i, j);
				int nOldY = j;
				if (nOldX >= 0 && nOldX < dest.getWidth() && nOldY >= 0 && nOldY < dest.getHeight()) {
					dest.setRGB(nOldX, nOldY, img.getRGB(i, j));
				}
			}
		}
		return new VerificationCode(code, dest);
	}

	private static int getXPosition4Twist(double dPhase, double dMultValue, int height, int xPosition, int yPosition) {
		double dx = Math.PI * 1.2 * yPosition / height + dPhase;
		double dy = Math.sin(dx);
		return xPosition + (int) (dy * dMultValue);
	}

}
