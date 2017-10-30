using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface LROAuth : NSObject
    [BaseType(typeof(NSObject))]
    interface LROAuth
    {
        // @property (nonatomic, strong) LROAuthConfig * config;
        [Export("config", ArgumentSemantic.Strong)]
        LROAuthConfig Config { get; set; }

        // -(id)initWithConfig:(LROAuthConfig *)config;
        [Export("initWithConfig:")]
        IntPtr Constructor(LROAuthConfig config);

        // -(id)initWithConsumerKey:(NSString *)consumerKey consumerSecret:(NSString *)consumerSecret token:(NSString *)token tokenSecret:(NSString *)tokenSecret;
        [Export("initWithConsumerKey:consumerSecret:token:tokenSecret:")]
        IntPtr Constructor(string consumerKey, string consumerSecret, string token, string tokenSecret);

        // +(NSString *)escape:(NSString *)string;
        [Static]
        [Export("escape:")]
        string Escape(string @string);

        // +(NSMutableDictionary *)extractRequestParams:(NSString *)query;
        [Static]
        [Export("extractRequestParams:")]
        NSMutableDictionary ExtractRequestParams(string query);
    }
}
