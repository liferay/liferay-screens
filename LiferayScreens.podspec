Pod::Spec.new do |s|
	s.name         = 'LiferayScreens'
	s.module_name  = 'LiferayScreens'
	s.version      = '2.1.2'
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
	
	s.platform = :ios, '8.0'
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
		'LiferayScreens-core' => 'ios/Framework/Core/**/*.{plist,lproj,css}',
		'LiferayScreens-default' => 'ios/Framework/Themes/Default/**/*.{xib,png,plist,lproj}'
	}
	
	s.xcconfig = {
		'GCC_PREPROCESSOR_DEFINITIONS' => 'LIFERAY_SCREENS_FRAMEWORK=1',
		'OTHER_SWIFT_FLAGS' => '"-D" "LIFERAY_SCREENS_FRAMEWORK"'
	}

	# Core
	s.dependency 'Liferay-iOS-SDK', '~> 7.0.9'
	s.dependency 'Liferay-OAuth', '~> 1.2.0'
	s.dependency 'MBProgressHUD', '~> 0.9.1'
	s.dependency 'SMXMLDocument', '~> 1.1'
	s.dependency 'YapDatabase/SQLCipher', '2.9.2'
	s.dependency 'Kingfisher', '~> 3.0'

	# Login & signup: save credentials
	s.dependency 'KeychainAccess', '~> 3.0'

	# UserPortrait
	s.dependency 'CryptoSwift', '~> 0.6.0'

	# DDLForm
	s.dependency 'DTPickerPresenter', '~> 0.2.0'
	s.dependency 'TNRadioButtonGroup', '~> 0.4'
	s.dependency 'MDRadialProgress', '~> 1.3.2'

	# RatingBar
	s.dependency 'Cosmos', '~> 8.0'

end
