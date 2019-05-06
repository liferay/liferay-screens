package com.liferay.mobile.screens.connectors

import com.liferay.mobile.android.auth.basic.BasicAuthentication
import com.liferay.mobile.android.service.SessionImpl
import com.liferay.mobile.screens.util.ServiceVersionFactory70
import org.junit.Assert.assertFalse

open class BaseTest {

	var serviceProvider = ServiceVersionFactory70()
	var session = SessionImpl("https://liferay-master.wedeploy.io",
		BasicAuthentication("test@liferay.com", System.getenv("MASTER_PASSWORD")))

	fun assertThatServiceExist(executeService: () -> Unit) {
		try {
			executeService()
		} catch (ex: Exception) {
			assertFalse(ex.message?.contains("NoSuchJSONWebServiceException") ?: false)
			assertFalse("Wrong password", ex.message?.contains("SecurityException") ?: false)
			assertFalse(ex is NullPointerException)
		}
	}
}