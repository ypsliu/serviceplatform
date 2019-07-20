/**
 * 
 */
package com.ruixue.serviceplatform.commons.utils.verificationcode;

import java.awt.image.BufferedImage;

/**
 * the verification code
 * 
 * @author shangchunming@rkylin.com.cn
 * 
 */
public final class VerificationCode {

	private final String code;

	private final BufferedImage image;

	public VerificationCode(String code, BufferedImage image) {
		this.code = code;
		this.image = image;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the image
	 */
	public BufferedImage getImage() {
		return image;
	}

}
