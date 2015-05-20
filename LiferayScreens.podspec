Pod::Spec.new do |s|
	s.name         = 'LiferayScreens'
	s.module_name  = 'LiferayScreens'
	s.version      = '0.3'
	s.summary      = 'A family of visual components called screenlets that are connected to the Liferay Platform used as a backend'
	s.homepage     = 'https://www.liferay.com/liferay-screens'
	s.license = { 
		:type => 'LGPL 2.1', 
		:file => 'LICENSE.md'
	}
	s.source = {
		:git => 'https://github.com/liferay/liferay-screens.git',
		:commit => 'd94ddb98be3ef5e676396d82494449c0eb75f0b5',
		:tag => '0.3'
	}
	s.authors = {
		'Jose Manuel Navarro' => 'jose.navarro@liferay.com'
	}
	
	s.platform = :ios, '8.0'
	s.requires_arc = true

	s.ios.frameworks = 'CoreGraphics', 'Foundation', 'MobileCoreServices', 'QuartzCore', 'Security', 'SystemConfiguration', 'UIKit'
	s.source_files = 'ios/Framework/Core/**/*.{h,m,swift}', 'ios/Framework/Themes/**/*.{h,m,swift}'
	s.exclude_files = [
		'ios/Framework/Core/liferay-screens-bridge.h',
		'ios/Framework/Tests/**/*.*',
		'ios/Framework/Pods/**/*.*'
	]

	s.resource_bundle = {
		'LiferayScreens-core' => 'ios/Framework/Core/**/*.{plist,lproj}',
		'LiferayScreens-default' => 'ios/Framework/Themes/Default/**/*.{xib,png,plist,lproj}',
		'LiferayScreens-flat7' => 'ios/Framework/Themes/Flat7/**/*.{xib,png,plist,lproj}'
	}
	
	s.xcconfig = {
		'GCC_PREPROCESSOR_DEFINITIONS' => 'LIFERAY_SCREENS_FRAMEWORK=1',
		'OTHER_SWIFT_FLAGS' => '"-D" "LIFERAY_SCREENS_FRAMEWORK"'
	}

	# Core
	s.dependency 'Liferay-iOS-SDK', '6.2.0.17'
	s.dependency 'MBProgressHUD', '~> 0.9.1'
	s.dependency 'UICKeyChainStore'
	s.dependency 'SMXMLDocument'
	s.dependency 'ODRefreshControl'

	# UserPortrait
	s.dependency 'CryptoSwift'

	# DDLForm
	s.dependency 'DTPickerPresenter'
	s.dependency 'TNRadioButtonGroup'
	s.dependency 'MDRadialProgress'

end