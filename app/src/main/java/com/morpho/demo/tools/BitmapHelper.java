package com.morpho.demo.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@SuppressLint("NewApi")
public class BitmapHelper {

	public static byte[] bitmapToByteArray(Bitmap bitmap) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
		return stream.toByteArray();
	}

	public static Bitmap byteArrayToBitmap(byte[] byteArray) {
		if (byteArray == null) {
			return null;
		} else {
			return BitmapFactory
					.decodeByteArray(byteArray, 0, byteArray.length);
		}
	}

	public static Bitmap shrinkBitmap(Bitmap bm, int maxLengthOfEdge) {
		return shrinkBitmap(bm, maxLengthOfEdge, 0);
	}

	public static Bitmap shrinkBitmap(Bitmap bm, int maxLengthOfEdge,
									  int rotateXDegree) {
		if (maxLengthOfEdge > bm.getWidth() && maxLengthOfEdge > bm.getHeight()) {
			return bm;
		} else {
			// shrink image
			float scale = (float) 1.0;
			if (bm.getHeight() > bm.getWidth()) {
				scale = ((float) maxLengthOfEdge) / bm.getHeight();
			} else {
				scale = ((float) maxLengthOfEdge) / bm.getWidth();
			}
			// CREATE A MATRIX FOR THE MANIPULATION
			Matrix matrix = new Matrix();
			// RESIZE THE BIT MAP
			matrix.postScale(scale, scale);
			matrix.postRotate(rotateXDegree);

			// RECREATE THE NEW BITMAP
			bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(),
					matrix, false);

			matrix = null;
			System.gc();

			return bm;
		}
	}

	@SuppressLint("NewApi")
	public static Bitmap readBitmap(Context context, Uri selectedImage) {
		Bitmap bm = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		options.inScaled = false;
		// options.inSampleSize = 3;
		AssetFileDescriptor fileDescriptor = null;
		try {
			fileDescriptor = context.getContentResolver()
					.openAssetFileDescriptor(selectedImage, "r");
		} catch (FileNotFoundException e) {
			return null;
		} finally {
			try {
				bm = BitmapFactory.decodeFileDescriptor(
						fileDescriptor.getFileDescriptor(), null, options);
				fileDescriptor.close();
			} catch (IOException e) {
				return null;
			}
		}
		return bm;
	}

	public static void clearBitmap(Bitmap bm) {
		bm.recycle();
		System.gc();
	}


	public static Bitmap cropBitmap(Bitmap original, int height, int width) {
		//Bitmap croppedImage = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
		Bitmap croppedImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(croppedImage);

		Rect srcRect = new Rect(0, 0, original.getWidth(), original.getHeight());
		Rect dstRect = new Rect(0, 0, width, height);

		int dx = (srcRect.width() - dstRect.width()) / 2;
		int dy = (srcRect.height() - dstRect.height()) / 2;



		// If the srcRect is too big, use the center part of it.
		srcRect.inset(Math.max(0, dx), Math.max(0, dy));

		// If the dstRect is too big, use the center part of it.
		dstRect.inset(Math.max(0, -dx), Math.max(0, -dy));

		// Draw the cropped bitmap in the center
		canvas.drawBitmap(original, srcRect, dstRect, null);

		original.recycle();

		return croppedImage;
	}

	public static Bitmap RotateBitmap(Bitmap source, float angle)
	{
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
	}

}

