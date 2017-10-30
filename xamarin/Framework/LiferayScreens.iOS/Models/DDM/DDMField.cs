using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface DDMField : NSObject <NSCoding>
    [BaseType(typeof(NSObject))]
    [DisableDefaultCtor]
    interface DDMField : INSCoding
    {
        // @property (copy, nonatomic) void (^ _Nullable)(BOOL) onPostValidation;
        [NullAllowed, Export("onPostValidation", ArgumentSemantic.Copy)]
        Action<bool> OnPostValidation { get; set; }

        // @property (nonatomic, strong) id _Nullable currentValue;
        [NullAllowed, Export("currentValue", ArgumentSemantic.Strong)]
        NSObject CurrentValue { get; set; }

        // @property (copy, nonatomic) NSString * _Nullable currentValueAsString;
        [NullAllowed, Export("currentValueAsString")]
        string CurrentValueAsString { get; set; }

        // @property (copy, nonatomic) NSString * _Nullable currentValueAsLabel;
        [NullAllowed, Export("currentValueAsLabel")]
        string CurrentValueAsLabel { get; set; }

        // @property (copy, nonatomic) NSLocale * _Nonnull currentLocale;
        [Export("currentLocale", ArgumentSemantic.Copy)]
        NSLocale CurrentLocale { get; set; }

        // -(instancetype _Nonnull)initWithAttributes:(NSDictionary<NSString *,id> * _Nonnull)attributes locale:(NSLocale * _Nonnull)locale __attribute__((objc_designated_initializer));
        [Export("initWithAttributes:locale:")]
        [DesignatedInitializer]
        IntPtr Constructor(NSDictionary<NSString, NSObject> attributes, NSLocale locale);

        // -(BOOL)validate __attribute__((warn_unused_result));
        [Export("validate")]
        bool Validate();
    }
}
