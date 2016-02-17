package com.liferay.mobile.screens.util;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.assetlist.operation.AssetEntryOperation;
import com.liferay.mobile.screens.assetlist.operation.AssetEntryOperation62;
import com.liferay.mobile.screens.assetlist.operation.ScreensAssetEntryOperation;
import com.liferay.mobile.screens.assetlist.operation.ScreensAssetEntryOperation62;
import com.liferay.mobile.screens.auth.forgotpassword.operation.ForgotPasswordOperation;
import com.liferay.mobile.screens.auth.login.operation.CurrentUserOperation;
import com.liferay.mobile.screens.auth.login.operation.ScreensUserOperation62;
import com.liferay.mobile.screens.auth.login.operation.UserOperation;
import com.liferay.mobile.screens.auth.login.operation.UserOperation62;
import com.liferay.mobile.screens.ddl.form.operation.DDLRecordOperation;
import com.liferay.mobile.screens.ddl.form.operation.DDLRecordOperation62;
import com.liferay.mobile.screens.ddl.form.operation.DDLRecordSetOperation;
import com.liferay.mobile.screens.ddl.form.operation.DDLRecordSetOperation62;
import com.liferay.mobile.screens.ddl.form.operation.DDMStructureOperation;
import com.liferay.mobile.screens.ddl.form.operation.DDMStructureOperation62;
import com.liferay.mobile.screens.ddl.form.operation.DLAppOperation;
import com.liferay.mobile.screens.ddl.form.operation.DLAppOperation62;
import com.liferay.mobile.screens.ddl.form.operation.ScreensDDLRecordOperation;
import com.liferay.mobile.screens.ddl.form.operation.ScreensDDLRecordOperation62;
import com.liferay.mobile.screens.webcontentdisplay.operation.JournalContentOperation;
import com.liferay.mobile.screens.webcontentdisplay.operation.JournalContentOperation62;
import com.liferay.mobile.screens.webcontentdisplay.operation.ScreensJournalContentOperation;
import com.liferay.mobile.screens.webcontentdisplay.operation.ScreensJournalContentOperation62;

/**
 * @author Javier Gamarra
 */
public class ServiceVersionFactory62 implements ServiceVersionFactory {

	public ForgotPasswordOperation getForgotPasswordOperations(Session session) {
		return new ScreensUserOperation62(session);
	}

	public UserOperation getUserOperations(Session session) {
		return new UserOperation62(session);
	}

	public CurrentUserOperation getCurrentUserOperation(Session session) {
		return new ScreensUserOperation62(session);
	}

	public ScreensJournalContentOperation getScreensJournalContentOperation(Session session) {
		return new ScreensJournalContentOperation62(session);
	}

	public DDLRecordOperation getDDLRecordOperation(Session session) {
		return new DDLRecordOperation62(session);
	}

	public JournalContentOperation getJournalContentOperation(Session session) {
		return new JournalContentOperation62(session);
	}

	public DDMStructureOperation getDDMStructureOperation(Session session) {
		return new DDMStructureOperation62(session);
	}

	public ScreensDDLRecordOperation getScreensDDLRecordOperation(Session session) {
		return new ScreensDDLRecordOperation62(session);
	}

	public ScreensAssetEntryOperation getScreensAssetEntryOperation(Session session) {
		return new ScreensAssetEntryOperation62(session);
	}

	public DLAppOperation getDLAppOperation(Session session) {
		return new DLAppOperation62(session);
	}

	public DDLRecordSetOperation getDDLRecordSetOperation(Session session) {
		return new DDLRecordSetOperation62(session);
	}

	public AssetEntryOperation getAssetEntryOperation(Session session) {
		return new AssetEntryOperation62(session);
	}

}
