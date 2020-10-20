Pod::Spec.new do |s|
	s.name         = 'LiferayScreens'
	s.module_name  = 'LiferayScreens'
	s.version      = '5.1.0'
	s.summary      = 'A family of visual components called screenlets that are connected to the Liferay Platform used as a backend'
	s.homepage     = 'https://www.liferay.com/liferay-screens'
	s.license = { 
		:type => 'LGPL 2.1', 
		:file => 'LICENSE.md'
	}
	s.source = {
		:git => 'https://github.com/liferay/liferay-screens.git',
		:tag => s.version.to_s
	}
	s.authors = {
		'Jose Manuel Navarro' => 'jose.navarro@liferay.com',
		'Javier Gamarra' => 'javier.gamarra@liferay.com'
	}
	
	s.swift_version = '5.0'	
	s.platform = :ios, '10.0'
	s.requires_arc = true

	s.ios.frameworks = [
		'CoreGraphics',
		'Foundation',
		'MobileCoreServices',
		'QuartzCore',
		'Security',
		'SystemConfiguration',
		'UIKit'
	]
	s.source_files = [
		'ios/Framework/Core/**/*.{h,m,swift}',
		'ios/Framework/Themes/**/*.{h,m,swift}'
	]
	s.exclude_files = [
		'ios/Framework/Core/liferay-screens-bridge.h',
		'ios/Framework/Tests/**/*.*',
		'ios/Framework/Pods/**/*.*',
		'ios/Framework/Themes/Flat7/**/*.*'
	]

	s.resource_bundle = {
		'LiferayScreens-core' => 'ios/Framework/Core/**/*.{plist,lproj,js,html,css}',
		'LiferayScreens-default' => 'ios/Framework/Themes/Default/**/*.{xib,png,plist,lproj}'
	}
	
	# Core
	s.dependency 'Liferay-iOS-SDK', '~> 7.3'
	s.dependency 'MBProgressHUD', '~> 0.9.1'
	s.dependency 'SMXMLDocument', '~> 1.1'
	s.dependency 'YapDatabase/SQLCipher', '2.9.2'
	s.dependency 'Kingfisher', '~> 5.2'

	# Login & signup: save credentials
	s.dependency 'KeychainAccess', '~> 3.0'

	# DDLForm
	s.dependency 'DTPickerPresenter', '~> 0.2.0'
	s.dependency 'TNRadioButtonGroup', '~> 0.4'
	s.dependency 'MDRadialProgress', '~> 1.3.2'
end
