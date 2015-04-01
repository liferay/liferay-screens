#!/bin/sh

xctool clean test -workspace LiferayScreens.xcworkspace -scheme LiferayScreens -sdk iphonesimulator ONLY_ACTIVE_ARCH=NO
