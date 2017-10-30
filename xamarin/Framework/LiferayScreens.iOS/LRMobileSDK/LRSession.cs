using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface LRSession : NSObject
    [BaseType(typeof(NSObject))]
    interface LRSession
    {
        // @property (nonatomic, strong) id<LRAuthentication> authentication;
        [Export("authentication", ArgumentSemantic.Strong)]
        LRAuthentication Authentication { get; set; }

        // @property (nonatomic, strong) id<LRCallback> callback;
        //[Export("callback", ArgumentSemantic.Strong)]
        //LRCallback Callback { get; set; }

        // @property (nonatomic) int connectionTimeout;
        [Export("connectionTimeout")]
        int ConnectionTimeout { get; set; }

        // @property (nonatomic, strong) NSDictionary * headers;
        [Export("headers", ArgumentSemantic.Strong)]
        NSDictionary Headers { get; set; }

        // @property (nonatomic, strong) NSString * server;
        [Export("server", ArgumentSemantic.Strong)]
        string Server { get; set; }

        // @property (nonatomic, strong) NSOperationQueue * queue;
        [Export("queue", ArgumentSemantic.Strong)]
        NSOperationQueue Queue { get; set; }

        // -(id)initWithServer:(NSString *)server;
        [Export("initWithServer:")]
        IntPtr Constructor(string server);

        // -(id)initWithServer:(NSString *)server callback:(id<LRCallback>)callback;
        //[Export("initWithServer:callback:")]
        //IntPtr Constructor(string server, LRCallback callback);

        // -(id)initWithServer:(NSString *)server authentication:(id<LRAuthentication>)authentication;
        [Export("initWithServer:authentication:")]
        IntPtr Constructor(string server, LRAuthentication authentication);

        // -(id)initWithServer:(NSString *)server authentication:(id<LRAuthentication>)authentication callback:(id<LRCallback>)callback;
        //[Export("initWithServer:authentication:callback:")]
        //IntPtr Constructor(string server, LRAuthentication authentication, LRCallback callback);

        // -(id)initWithServer:(NSString *)server authentication:(id<LRAuthentication>)authentication connectionTimeout:(int)connectionTimeout callback:(id<LRCallback>)callback;
        //[Export("initWithServer:authentication:connectionTimeout:callback:")]
        //IntPtr Constructor(string server, LRAuthentication authentication, int connectionTimeout, LRCallback callback);

        // -(id)initWithServer:(NSString *)server authentication:(id<LRAuthentication>)authentication connectionTimeout:(int)connectionTimeout callback:(id<LRCallback>)callback queue:(NSOperationQueue *)queue;
        //[Export("initWithServer:authentication:connectionTimeout:callback:queue:")]
        //IntPtr Constructor(string server, LRAuthentication authentication, int connectionTimeout, LRCallback callback, NSOperationQueue queue);

        // -(id)initWithSession:(LRSession *)session;
        [Export("initWithSession:")]
        IntPtr Constructor(LRSession session);

        // -(id)invoke:(NSDictionary *)command error:(NSError **)error;
        [Export("invoke:error:")]
        NSObject Invoke(NSDictionary command, out NSError error);

        // -(void)onSuccess:(LRSuccessBlock)success onFailure:(LRFailureBlock)failure;
        //[Export("onSuccess:onFailure:")]
        //void OnSuccess(LRSuccessBlock success, LRFailureBlock failure);

        // -(id)upload:(NSDictionary *)command error:(NSError **)error;
        [Export("upload:error:")]
        NSObject Upload(NSDictionary command, out NSError error);
    }
}
