using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface LROAuthConfig : NSObject
    [BaseType(typeof(NSObject))]
    interface LROAuthConfig
    {
        // @property (nonatomic, strong) NSString * accessTokenURL;
        [Export("accessTokenURL", ArgumentSemantic.Strong)]
        string AccessTokenURL { get; set; }

        // @property (nonatomic, strong) NSString * authorizeTokenURL;
        [Export("authorizeTokenURL", ArgumentSemantic.Strong)]
        string AuthorizeTokenURL { get; set; }

        // @property (nonatomic, strong) NSString * callbackURL;
        [Export("callbackURL", ArgumentSemantic.Strong)]
        string CallbackURL { get; set; }

        // @property (nonatomic, strong) NSString * consumerKey;
        [Export("consumerKey", ArgumentSemantic.Strong)]
        string ConsumerKey { get; set; }

        // @property (nonatomic, strong) NSString * consumerSecret;
        [Export("consumerSecret", ArgumentSemantic.Strong)]
        string ConsumerSecret { get; set; }

        // @property (nonatomic, strong) NSString * nonce;
        [Export("nonce", ArgumentSemantic.Strong)]
        string Nonce { get; set; }

        // @property (readonly, nonatomic, strong) NSDictionary * params;
        [Export("params", ArgumentSemantic.Strong)]
        NSDictionary Params { get; }

        // @property (nonatomic, strong) NSString * requestTokenURL;
        [Export("requestTokenURL", ArgumentSemantic.Strong)]
        string RequestTokenURL { get; set; }

        // @property (nonatomic, strong) NSString * server;
        [Export("server", ArgumentSemantic.Strong)]
        string Server { get; set; }

        // @property (nonatomic, strong) NSString * timestamp;
        [Export("timestamp", ArgumentSemantic.Strong)]
        string Timestamp { get; set; }

        // @property (nonatomic, strong) NSString * token;
        [Export("token", ArgumentSemantic.Strong)]
        string Token { get; set; }

        // @property (nonatomic, strong) NSString * tokenSecret;
        [Export("tokenSecret", ArgumentSemantic.Strong)]
        string TokenSecret { get; set; }

        // @property (nonatomic, strong) NSString * verifier;
        [Export("verifier", ArgumentSemantic.Strong)]
        string Verifier { get; set; }

        // -(id)initWithServer:(NSString *)server consumerKey:(NSString *)consumerKey consumerSecret:(NSString *)consumerSecret callbackURL:(NSString *)callbackURL;
        [Export("initWithServer:consumerKey:consumerSecret:callbackURL:")]
        IntPtr Constructor(string server, string consumerKey, string consumerSecret, string callbackURL);

        // -(id)initWithConsumerKey:(NSString *)consumerKey consumerSecret:(NSString *)consumerSecret token:(NSString *)token tokenSecret:(NSString *)tokenSecret;
        [Export("initWithConsumerKey:consumerSecret:token:tokenSecret:")]
        IntPtr ConstructorToken(string consumerKey, string consumerSecret, string token, string tokenSecret);

        // -(void)setAuthorizeTokenURLWithParams:(NSDictionary *)params;
        [Export("setAuthorizeTokenURLWithParams:")]
        void SetAuthorizeTokenURLWithParams(NSDictionary @params);
    }
}
