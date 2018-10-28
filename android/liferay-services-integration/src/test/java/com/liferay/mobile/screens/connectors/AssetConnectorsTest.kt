package com.liferay.mobile.screens.connectors

import org.junit.Test

class AssetConnectorsTest : BaseTest() {

	@Test
	fun assetEntryConnectorsShouldExist() {
		val screensAssetEntryConnector = serviceProvider.getScreensAssetEntryConnector(session)

		assertThatServiceExist { screensAssetEntryConnector.getAssetEntry(0, "") }
		assertThatServiceExist { screensAssetEntryConnector.getAssetEntry("", 0, "") }
		assertThatServiceExist { screensAssetEntryConnector.getAssetEntries(null, "") }
		assertThatServiceExist { screensAssetEntryConnector.getAssetEntries(0, 0, "", "", 0) }
		assertThatServiceExist { serviceProvider.getAssetEntryConnector(session).getEntriesCount(null) }
	}
}
