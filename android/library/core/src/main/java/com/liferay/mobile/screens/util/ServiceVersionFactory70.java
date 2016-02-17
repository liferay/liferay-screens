package com.liferay.mobile.screens.util;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.assetlist.operation.AssetEntryOperation;
import com.liferay.mobile.screens.assetlist.operation.AssetEntryOperation70;
import com.liferay.mobile.screens.assetlist.operation.ScreensAssetEntryOperation;
import com.liferay.mobile.screens.assetlist.operation.ScreensAssetEntryOperation70;
import com.liferay.mobile.screens.auth.forgotpassword.operation.ForgotPasswordOperation;
import com.liferay.mobile.screens.auth.login.operation.CurrentUserOperation;
import com.liferay.mobile.screens.auth.login.operation.UserOperation;
import com.liferay.mobile.screens.auth.login.operation.UserOperation70;
import com.liferay.mobile.screens.ddl.form.operation.DDLRecordOperation;
import com.liferay.mobile.screens.ddl.form.operation.DDLRecordOperation70;
import com.liferay.mobile.screens.ddl.form.operation.DDLRecordSetOperation;
import com.liferay.mobile.screens.ddl.form.operation.DDLRecordSetOperation70;
import com.liferay.mobile.screens.ddl.form.operation.DDMStructureOperation;
import com.liferay.mobile.screens.ddl.form.operation.DDMStructureOperation70;
import com.liferay.mobile.screens.ddl.form.operation.DLAppOperation;
import com.liferay.mobile.screens.ddl.form.operation.DLAppOperation70;
import com.liferay.mobile.screens.ddl.form.operation.ScreensDDLRecordOperation;
import com.liferay.mobile.screens.ddl.form.operation.ScreensDDLRecordOperation70;
import com.liferay.mobile.screens.webcontentdisplay.operation.JournalContentOperation;
import com.liferay.mobile.screens.webcontentdisplay.operation.JournalContentOperation70;
import com.liferay.mobile.screens.webcontentdisplay.operation.ScreensJournalContentOperation;
import com.liferay.mobile.screens.webcontentdisplay.operation.ScreensJournalContentOperation70;

/**
 * @author Javier Gamarra
 */
public class ServiceVersionFactory70 implements ServiceVersionFactory {

	public ForgotPasswordOperation getForgotPasswordOperations(Session session) {
		return new UserOperation70(session);
	}

	public UserOperation getUserOperations(Session session) {
		return new UserOperation70(session);
	}

	public CurrentUserOperation getCurrentUserOperation(Session session) {
		return new UserOperation70(session);
	}

	public ScreensJournalContentOperation getScreensJournalContentOperation(Session session) {
		return new ScreensJournalContentOperation70(session);
	}

	public DDLRecordOperation getDDLRecordOperation(Session session) {
		return new DDLRecordOperation70(session);
	}

	public JournalContentOperation getJournalContentOperation(Session session) {
		return new JournalContentOperation70(session);
	}

	public DDMStructureOperation getDDMStructureOperation(Session session) {
		return new DDMStructureOperation70(session);
	}

	public ScreensDDLRecordOperation getScreensDDLRecordOperation(Session session) {
		return new ScreensDDLRecordOperation70(session);
	}

	public ScreensAssetEntryOperation getScreensAssetEntryOperation(Session session) {
		return new ScreensAssetEntryOperation70(session);
	}

	public DLAppOperation getDLAppOperation(Session session) {
		return new DLAppOperation70(session);
	}

	public DDLRecordSetOperation getDDLRecordSetOperation(Session session) {
		return new DDLRecordSetOperation70(session);
	}

	public AssetEntryOperation getAssetEntryOperation(Session session) {
		return new AssetEntryOperation70(session);
	}
}
