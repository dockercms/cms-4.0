package com.leimingtech.core.util.image;

import java.awt.Dimension;

public interface IThumbnailCreator {
	public Dimension getDimension(String fileName);

	public void scaleRateImageFile(String fromFileName, String toFileName, int toWidth, int toHeight);

	public boolean scaleRateImageFile2(String fromFileName, String toFileName, int toWidth, int toHeight);

	public void scaleRateImageFile(String fromFileName, String toFileName, double rate);

	public void scaleFixedImageFile(String fromFileName, String toFileName, int toWidth, int toHeight);

	public void pressText(String filePath, String pressText, int color, int fontSize, int position);

	public void pressImage(String targetImg, String pressImg, int position);

	public boolean cutImage(String srcPath, String dirPath, String toWidth, String toHeight, String imgX1, String imgY1, double rate);
}
