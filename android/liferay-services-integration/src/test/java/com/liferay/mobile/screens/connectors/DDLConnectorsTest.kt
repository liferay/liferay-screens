package com.liferay.mobile.screens.connectors

import org.junit.Test

class DDLConnectorsTest : BaseTest() {

	@Test
	fun ddlFormConnectorsShouldExist() {
		assertThatServiceExist { serviceProvider.getDDLRecordConnector(session).addRecord(0, 0, 0, null, null) }
		assertThatServiceExist { serviceProvider.getDDLRecordConnector(session).updateRecord(0, 0, null, false, null) }

		assertThatServiceExist {
			serviceProvider.getDLAppConnector(session).addFileEntry(0, 0, "", "", "", "", "", byteArrayOf(), null)
		}
		assertThatServiceExist { serviceProvider.getScreensDDLRecordConnector(session).getDdlRecord(0, "") }
		assertThatServiceExist { serviceProvider.getScreensDDLRecordConnector(session).getDdlRecord(0, "") }

		assertThatServiceExist { serviceProvider.getDDMStructureConnector(session).getStructure(0) }
	}

	@Test
	fun ddlListConnectorsShouldExist() {
		val screensDDLRecordConnector = serviceProvider.getScreensDDLRecordConnector(session)

		assertThatServiceExist { screensDDLRecordConnector.getDdlRecords(0, "", 0, 0, null) }
		assertThatServiceExist { screensDDLRecordConnector.getDdlRecords(0, 0, "", 0, 0, null) }

		assertThatServiceExist { screensDDLRecordConnector.getDdlRecordsCount(0) }
		assertThatServiceExist { screensDDLRecordConnector.getDdlRecordsCount(0, 0) }
	}
}