platform :ios, '9.0'
use_frameworks!

target 'AndorraTelecomPublic' do
    pod 'LiferayScreens', :git => 'https://github.com/liferay/liferay-screens', :branch => 'develop'
    pod 'Alamofire'
    pod 'DynamicBlurView'
    pod 'Hokusai'
end

post_install do |installer|
	installer.pods_project.targets.each do |target|
		target.build_configurations.each do |config|
			config.build_settings['CONFIGURATION_BUILD_DIR'] = '$PODS_CONFIGURATION_BUILD_DIR'
		end
	end
end
