using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    interface ISignUpViewModel {}

    // @protocol SignUpViewModel
    [BaseType(typeof(NSObject))]
    [Protocol, Model]
    interface SignUpViewModel
    {
        // @required @property (copy, nonatomic) NSString * _Nullable emailAddress;
        [Abstract]
        [NullAllowed, Export("emailAddress")]
        string EmailAddress { get; set; }

        // @required @property (copy, nonatomic) NSString * _Nullable screenName;
        [Abstract]
        [NullAllowed, Export("screenName")]
        string ScreenName { get; set; }

        // @required @property (copy, nonatomic) NSString * _Nullable password;
        [Abstract]
        [NullAllowed, Export("password")]
        string Password { get; set; }

        // @required @property (copy, nonatomic) NSString * _Nullable firstName;
        [Abstract]
        [NullAllowed, Export("firstName")]
        string FirstName { get; set; }

        // @required @property (copy, nonatomic) NSString * _Nullable middleName;
        [Abstract]
        [NullAllowed, Export("middleName")]
        string MiddleName { get; set; }

        // @required @property (copy, nonatomic) NSString * _Nullable lastName;
        [Abstract]
        [NullAllowed, Export("lastName")]
        string LastName { get; set; }

        // @required @property (copy, nonatomic) NSString * _Nullable jobTitle;
        [Abstract]
        [NullAllowed, Export("jobTitle")]
        string JobTitle { get; set; }

        // @required @property (nonatomic) BOOL editCurrentUser;
        [Abstract]
        [Export("editCurrentUser")]
        bool EditCurrentUser { get; set; }
    }
}
