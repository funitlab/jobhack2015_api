package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class JobHackImageDrawable {
	public static final String FOLDER_NAME = "Goal Gallery";

	public static void setImageDrawble(ImageView imageView, Drawable drawable) {
		int sdk = android.os.Build.VERSION.SDK_INT;
		if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN)
			imageView.setImageDrawable(drawable);
		else
			imageView.setImageDrawable(drawable);
	}

	public static void setBackgroundDrawble(View view, Drawable drawable) {
		int sdk = android.os.Build.VERSION.SDK_INT;
		if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN)
			view.setBackgroundDrawable(drawable);
		else
			view.setBackground(drawable);
	}

	// Save Image to SDcard
	public static String saveImageToInternalStorage(Context context,
			Bitmap bitmap, String imageName) {
		String imagePath = "/data/data/com.mmsoft.timo_project/app_" + imageName;
		ContextWrapper cw = new ContextWrapper(context);
		// path to /data/data/yourapp/app_<imageName>
		File directory = cw.getDir(imageName, Context.MODE_PRIVATE);
		// Create imageDir
		File mypath = new File(directory, imageName + ".jpg");

		FileOutputStream fos = null;
		try {

			fos = new FileOutputStream(mypath);

			// Use the compress method on the BitMap object to write image to
			// the OutputStream
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			fos.close();
		} catch (Exception e) {
			imagePath = "";
			e.printStackTrace();
		}
		return imagePath;

	}

	// Get Image from SDcard
	public static Bitmap getImagePathFromInternaStorage(String imageName) {
		Bitmap b = null;
		try {
			String imagePath = "/data/data/com.mmsoft.timo_project/app_"
					+ imageName;
			Log.d(imageName, imagePath);

			File f = new File(imagePath, imageName + ".jpg");
			b = BitmapFactory.decodeStream(new FileInputStream(f));
			
			b = BitmapUtil.rotateBitmap(b, getCameraPhotoOrientation(imagePath));

		} catch (FileNotFoundException e) {
		}
		return b;

	}
	

	
	public static int getCameraPhotoOrientation(String imagePath){
	    ExifInterface exif = null;
	    try {
	        exif = new ExifInterface(imagePath);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }  
	    int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 
	                                           ExifInterface.ORIENTATION_UNDEFINED);
	    return orientation;
	}

}
