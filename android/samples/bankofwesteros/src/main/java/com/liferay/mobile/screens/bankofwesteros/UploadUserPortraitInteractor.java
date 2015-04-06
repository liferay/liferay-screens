package com.liferay.mobile.screens.bankofwesteros;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.task.callback.typed.JSONObjectAsyncTaskCallback;
import com.liferay.mobile.android.v62.user.UserService;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.util.LiferayLogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Javier Gamarra
 */
public class UploadUserPortraitInteractor {

	//FIXME progress view
	//TODO this should go to the user portrait screenlet

	public void uploadImage(String picturePath, JSONObjectAsyncTaskCallback callback) {

		Session sessionFromCurrentSession = SessionContext.createSessionFromCurrentSession();
		sessionFromCurrentSession.setCallback(callback);

		UserService userService = new UserService(sessionFromCurrentSession);
		try {
			userService.updatePortrait(SessionContext.getLoggedUser().getId(), readContentIntoByteArray(new File(picturePath)));
		}
		catch (Exception e) {
			LiferayLogger.e("Error updating portrait", e);
		}
	}

	private static byte[] readContentIntoByteArray(File file) {
		FileInputStream fileInputStream = null;
		byte[] bFile = new byte[(int) file.length()];
		try {
			//convert file into array of bytes
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (fileInputStream != null) {
					fileInputStream.close();
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bFile;
	}
}
