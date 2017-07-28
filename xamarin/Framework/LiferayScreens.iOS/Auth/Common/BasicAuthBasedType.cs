using Foundation;

namespace LiferayScreens
{
    interface IBasicAuthBasedType {}

    // @protocol BasicAuthBasedType
    [BaseType(typeof(NSObject))]
    [Protocol, Model]
    interface BasicAuthBasedType
    {
        // @required @property (copy, nonatomic) NSString * _Nullable basicAuthMethod;
        [Abstract]
        [NullAllowed, Export("basicAuthMethod")]
        string BasicAuthMethod { get; set; }
    }
}
