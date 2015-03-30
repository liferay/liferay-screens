#!/bin/sh

xctool clean test -workspace Liferay-iOS-Screens.xcworkspace -scheme Liferay-iOS-Screens -sdk iphonesimulator ONLY_ACTIVE_ARCH=NO
