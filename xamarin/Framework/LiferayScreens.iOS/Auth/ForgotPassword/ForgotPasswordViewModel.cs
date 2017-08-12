using Foundation;
using ObjCRuntime;

namespace LiferayScreens
{
    interface IForgotPasswordViewModel {}

    // @protocol ForgotPasswordViewModel <BasicAuthBasedType>
    [BaseType(typeof(NSObject))]
    [Protocol, Model]
    interface ForgotPasswordViewModel : BasicAuthBasedType
    {
        // @required @property (copy, nonatomic) NSString * _Nullable userName;
        [Abstract]
        [NullAllowed, Export("userName")]
        string UserName { get; set; }
    }
}
