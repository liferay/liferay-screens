package com.liferay.mobile.screens.util;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.assetlist.operation.AssetEntryOperation;
import com.liferay.mobile.screens.assetlist.operation.ScreensAssetEntryOperation;
import com.liferay.mobile.screens.auth.forgotpassword.operation.ForgotPasswordOperation;
import com.liferay.mobile.screens.auth.login.operation.CurrentUserOperation;
import com.liferay.mobile.screens.auth.login.operation.UserOperation;
import com.liferay.mobile.screens.ddl.form.operation.DDLRecordOperation;
import com.liferay.mobile.screens.ddl.form.operation.DDLRecordSetOperation;
import com.liferay.mobile.screens.ddl.form.operation.DDMStructureOperation;
import com.liferay.mobile.screens.ddl.form.operation.DLAppOperation;
import com.liferay.mobile.screens.ddl.form.operation.ScreensDDLRecordOperation;
import com.liferay.mobile.screens.webcontentdisplay.operation.JournalContentOperation;
import com.liferay.mobile.screens.webcontentdisplay.operation.ScreensJournalContentOperation;

/**
 * @author Javier Gamarra
 */
public interface ServiceVersionFactory {

	ForgotPasswordOperation getForgotPasswordOperations(Session session);

	UserOperation getUserOperations(Session session);

	CurrentUserOperation getCurrentUserOperation(Session session);

	ScreensJournalContentOperation getScreensJournalContentOperation(Session session);

	DDLRecordOperation getDDLRecordOperation(Session session);

	JournalContentOperation getJournalContentOperation(Session session);

	DDMStructureOperation getDDMStructureOperation(Session session);

	ScreensDDLRecordOperation getScreensDDLRecordOperation(Session session);

	ScreensAssetEntryOperation getScreensAssetEntryOperation(Session session);

	DLAppOperation getDLAppOperation(Session session);

	DDLRecordSetOperation getDDLRecordSetOperation(Session session);

	AssetEntryOperation getAssetEntryOperation(Session session);

}
