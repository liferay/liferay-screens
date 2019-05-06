package com.liferay.mobile.screens.connectors

import org.junit.Test

class FileEntryConnectorsTest : BaseTest() {

	@Test
	fun fileEntryConnectorsShouldExist() {
		val dlAppService = serviceProvider.getDLAppConnector(session)

		assertThatServiceExist { dlAppService.getFileEntries(0, 0, null, 0, 0, null) }
		assertThatServiceExist { dlAppService.getFileEntriesCount(0, 0, null) }
		assertThatServiceExist { dlAppService.deleteFileEntry(0) }
	}
}