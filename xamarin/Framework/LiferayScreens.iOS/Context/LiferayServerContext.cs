using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface LiferayServerContext : NSObject
    [BaseType(typeof(NSObject))]
    interface LiferayServerContext
    {
        // @property (copy, nonatomic, class) NSString * _Nonnull server;
        [Static]
        [Export("server")]
        string Server { get; set; }

        // @property (nonatomic, class) enum LiferayServerVersion serverVersion;
        [Static]
        [Export("serverVersion", ArgumentSemantic.Assign)]
        LiferayServerVersion ServerVersion { get; set; }

        // @property (nonatomic, class) int64_t companyId;
        [Static]
        [Export("companyId")]
        long CompanyId { get; set; }

        // @property (nonatomic, class) int64_t groupId;
        [Static]
        [Export("groupId")]
        long GroupId { get; set; }

        // @property (nonatomic, strong, class) id<ScreensFactory> _Nonnull factory;
        //[Static]
        //[Export("factory", ArgumentSemantic.Strong)]
        //ScreensFactory Factory { get; set; }

        // @property (nonatomic, strong, class) id<LiferayConnectorFactory> _Nonnull connectorFactory;
        //[Static]
        //[Export("connectorFactory", ArgumentSemantic.Strong)]
        //LiferayConnectorFactory ConnectorFactory { get; set; }

        // +(void)setPropertyValue:(id _Nonnull)value forKey:(NSString * _Nonnull)key;
        [Static]
        [Export("setPropertyValue:forKey:")]
        void SetPropertyValue(NSObject value, string key);

        // +(id _Nonnull)propertyForKey:(NSString * _Nonnull)key __attribute__((warn_unused_result));
        [Static]
        [Export("propertyForKey:")]
        NSObject PropertyForKey(string key);

        // +(NSNumber * _Nonnull)numberPropertyForKey:(NSString * _Nonnull)key __attribute__((warn_unused_result));
        [Static]
        [Export("numberPropertyForKey:")]
        NSNumber NumberPropertyForKey(string key);

        // +(int64_t)longPropertyForKey:(NSString * _Nonnull)key __attribute__((warn_unused_result));
        [Static]
        [Export("longPropertyForKey:")]
        long LongPropertyForKey(string key);

        // +(NSInteger)intPropertyForKey:(NSString * _Nonnull)key __attribute__((warn_unused_result));
        [Static]
        [Export("intPropertyForKey:")]
        nint IntPropertyForKey(string key);

        // +(BOOL)booleanPropertyForKey:(NSString * _Nonnull)key __attribute__((warn_unused_result));
        [Static]
        [Export("booleanPropertyForKey:")]
        bool BooleanPropertyForKey(string key);

        // +(NSDate * _Nonnull)datePropertyForKey:(NSString * _Nonnull)key __attribute__((warn_unused_result));
        [Static]
        [Export("datePropertyForKey:")]
        NSDate DatePropertyForKey(string key);

        // +(NSString * _Nonnull)stringPropertyForKey:(NSString * _Nonnull)key __attribute__((warn_unused_result));
        [Static]
        [Export("stringPropertyForKey:")]
        string StringPropertyForKey(string key);
    }
}
