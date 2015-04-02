#!/bin/sh

xctool clean test -workspace LiferayScreens.xcworkspace -scheme LiferayScreens -sdk iphonesimulator8.1 ONLY_ACTIVE_ARCH=NO
