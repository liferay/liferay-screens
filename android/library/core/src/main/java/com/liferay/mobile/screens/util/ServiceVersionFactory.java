package com.liferay.mobile.screens.util;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.assetlist.operation.AssetEntryOperation;
import com.liferay.mobile.screens.assetlist.operation.AssetEntryOperation62;
import com.liferay.mobile.screens.assetlist.operation.AssetEntryOperation70;
import com.liferay.mobile.screens.assetlist.operation.ScreensAssetEntryOperation;
import com.liferay.mobile.screens.assetlist.operation.ScreensAssetEntryOperation62;
import com.liferay.mobile.screens.assetlist.operation.ScreensAssetEntryOperation70;
import com.liferay.mobile.screens.auth.forgotpassword.operation.ForgotPasswordOperation;
import com.liferay.mobile.screens.auth.login.operation.CurrentUserOperation;
import com.liferay.mobile.screens.auth.login.operation.ScreensUserOperation62;
import com.liferay.mobile.screens.auth.login.operation.UserOperation;
import com.liferay.mobile.screens.auth.login.operation.UserOperation62;
import com.liferay.mobile.screens.auth.login.operation.UserOperation70;
import com.liferay.mobile.screens.ddl.form.operation.DDLRecordOperation;
import com.liferay.mobile.screens.ddl.form.operation.DDLRecordOperation62;
import com.liferay.mobile.screens.ddl.form.operation.DDLRecordOperation70;
import com.liferay.mobile.screens.ddl.form.operation.DDLRecordSetOperation;
import com.liferay.mobile.screens.ddl.form.operation.DDLRecordSetOperation62;
import com.liferay.mobile.screens.ddl.form.operation.DDLRecordSetOperation70;
import com.liferay.mobile.screens.ddl.form.operation.DDMStructureOperation;
import com.liferay.mobile.screens.ddl.form.operation.DDMStructureOperation62;
import com.liferay.mobile.screens.ddl.form.operation.DDMStructureOperation70;
import com.liferay.mobile.screens.ddl.form.operation.DLAppOperation;
import com.liferay.mobile.screens.ddl.form.operation.DLAppOperation62;
import com.liferay.mobile.screens.ddl.form.operation.DLAppOperation70;
import com.liferay.mobile.screens.ddl.form.operation.ScreensDDLRecordOperation;
import com.liferay.mobile.screens.ddl.form.operation.ScreensDDLRecordOperation62;
import com.liferay.mobile.screens.ddl.form.operation.ScreensDDLRecordOperation70;
import com.liferay.mobile.screens.webcontentdisplay.operation.JournalContentOperation;
import com.liferay.mobile.screens.webcontentdisplay.operation.JournalContentOperation62;
import com.liferay.mobile.screens.webcontentdisplay.operation.JournalContentOperation70;
import com.liferay.mobile.screens.webcontentdisplay.operation.ScreensJournalContentOperation;
import com.liferay.mobile.screens.webcontentdisplay.operation.ScreensJournalContentOperation62;
import com.liferay.mobile.screens.webcontentdisplay.operation.ScreensJournalContentOperation70;

/**
 * @author Javier Gamarra
 */
public class ServiceVersionFactory {

	public static ForgotPasswordOperation getForgotPasswordOperations(Session session) {
		return liferay7 ? new UserOperation70(session) : new ScreensUserOperation62(session);
	}

	public static UserOperation getUserOperations(Session session) {
		return liferay7 ? new UserOperation70(session) : new UserOperation62(session);
	}

	public static CurrentUserOperation getCurrentUserOperation(Session session) {
		return liferay7 ? new UserOperation70(session) : new ScreensUserOperation62(session);
	}

	public static ScreensJournalContentOperation getScreensJournalContentOperation(Session session) {
		return liferay7 ? new ScreensJournalContentOperation70(session) : new ScreensJournalContentOperation62(session);
	}

	public static DDLRecordOperation getDDLRecordOperation(Session session) {
		return liferay7 ? new DDLRecordOperation70(session) : new DDLRecordOperation62(session);
	}

	public static JournalContentOperation getJournalContentOperation(Session session) {
		return liferay7 ? new JournalContentOperation70(session) : new JournalContentOperation62(session);
	}

	public static DDMStructureOperation getDDMStructureOperation(Session session) {
		return liferay7 ? new DDMStructureOperation70(session) : new DDMStructureOperation62(session);
	}

	public static ScreensDDLRecordOperation getScreensDDLRecordOperation(Session session) {
		return liferay7 ? new ScreensDDLRecordOperation70(session) : new ScreensDDLRecordOperation62(session);
	}

	public static ScreensAssetEntryOperation getScreensAssetEntryOperation(Session session) {
		return liferay7 ? new ScreensAssetEntryOperation70(session) : new ScreensAssetEntryOperation62(session);
	}

	public static DLAppOperation getDLAppOperation(Session session) {
		return liferay7 ? new DLAppOperation70(session) : new DLAppOperation62(session);
	}

	public static DDLRecordSetOperation getDDLRecordSetOperation(Session session) {
		return liferay7 ? new DDLRecordSetOperation70(session) : new DDLRecordSetOperation62(session);
	}

	public static boolean isLiferay7() {
		return liferay7;
	}

	public static AssetEntryOperation getAssetEntryOperation(Session session) {
		return liferay7 ? new AssetEntryOperation70(session) : new AssetEntryOperation62(session);
	}

	private static boolean liferay7;
}
