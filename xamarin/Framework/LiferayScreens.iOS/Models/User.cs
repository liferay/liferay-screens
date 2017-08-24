using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface User : NSObject <NSCoding>
    [BaseType(typeof(NSObject))]
    [DisableDefaultCtor]
    interface User : INSCoding
    {
        // @property (readonly, copy, nonatomic) NSDictionary<NSString *,id> * _Nonnull attributes;
        [Export("attributes", ArgumentSemantic.Copy)]
        NSDictionary<NSString, NSObject> Attributes { get; }

        // @property (readonly, copy, nonatomic) NSString * _Nonnull firstName;
        [Export("firstName")]
        string FirstName { get; }

        // @property (readonly, copy, nonatomic) NSString * _Nonnull lastName;
        [Export("lastName")]
        string LastName { get; }

        // @property (readonly, copy, nonatomic) NSString * _Nonnull middleName;
        [Export("middleName")]
        string MiddleName { get; }

        // @property (readonly, copy, nonatomic) NSString * _Nonnull screenName;
        [Export("screenName")]
        string ScreenName { get; }

        // @property (readonly, copy, nonatomic) NSString * _Nonnull greeting;
        [Export("greeting")]
        string Greeting { get; }

        // @property (readonly, copy, nonatomic) NSString * _Nonnull jobTitle;
        [Export("jobTitle")]
        string JobTitle { get; }

        // @property (readonly, copy, nonatomic) NSString * _Nonnull email;
        [Export("email")]
        string Email { get; }

        // @property (readonly, nonatomic) int64_t userId;
        [Export("userId")]
        long UserId { get; }

        // -(instancetype _Nonnull)initWithAttributes:(NSDictionary<NSString *,id> * _Nonnull)attributes __attribute__((objc_designated_initializer));
        [Export("initWithAttributes:")]
        [DesignatedInitializer]
        IntPtr Constructor(NSDictionary<NSString, NSObject> attributes);

        // -(int64_t)int64Attribute:(NSString * _Nonnull)key __attribute__((warn_unused_result));
        [Export("int64Attribute:")]
        long Int64Attribute(string key);

        // -(NSString * _Nonnull)stringAttribute:(NSString * _Nonnull)key __attribute__((warn_unused_result));
        [Export("stringAttribute:")]
        string StringAttribute(string key);

        // -(void)encodeWithCoder:(NSCoder * _Nonnull)aCoder;
        [Export("encodeWithCoderUser:")]
        void EncodeWithCoderUser(NSCoder aCoder);
    }
}
