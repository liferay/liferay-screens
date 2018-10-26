package com.liferay.mobile.screens.ddl.form.connector;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.service.v70.DDLRecordSetService;

/**
 * @author Javier Gamarra
 */
public class DDLRecordSetConnector70 implements DDLRecordSetConnector {

    private final DDLRecordSetService ddlRecordSetService;

    public DDLRecordSetConnector70(Session session) {
        ddlRecordSetService = new DDLRecordSetService(session);
    }

    @Override
    public void getRecordSet(long recordSetId) throws Exception {
        ddlRecordSetService.getRecordSet(recordSetId);
    }
}
