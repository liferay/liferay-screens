using Foundation;

namespace LiferayScreens
{
    interface ILoginViewModel { }

    // @protocol LoginViewModel <BasicAuthBasedType>
    [BaseType(typeof(NSObject))]
    [Protocol, Model]
    interface LoginViewModel : BasicAuthBasedType
    {
        // @required @property (copy, nonatomic) NSString * _Nullable userName;
        [Abstract]
        [NullAllowed, Export("userName")]
        string UserName { get; set; }

        // @required @property (copy, nonatomic) NSString * _Nullable password;
        [Abstract]
        [NullAllowed, Export("password")]
        string Password { get; set; }

        // @required @property (copy, nonatomic) NSString * _Nullable authType;
        [Abstract]
        [NullAllowed, Export("authType")]
        string AuthType { get; set; }
    }
}
