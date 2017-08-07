using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    interface IAnonymousBasicAuthType {}

    // @protocol AnonymousBasicAuthType
    [BaseType(typeof(NSObject))]
    [Protocol, Model]
    interface AnonymousBasicAuthType
    {
        // @required @property (copy, nonatomic) NSString * _Nullable anonymousApiUserName;
        [Abstract]
        [NullAllowed, Export("anonymousApiUserName")]
        string AnonymousApiUserName { get; set; }

        // @required @property (copy, nonatomic) NSString * _Nullable anonymousApiPassword;
        [Abstract]
        [NullAllowed, Export("anonymousApiPassword")]
        string AnonymousApiPassword { get; set; }
    }
}
