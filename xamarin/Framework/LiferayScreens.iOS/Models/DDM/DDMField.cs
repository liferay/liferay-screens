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

        // @property (readonly, copy, nonatomic) NSString * _Nonnull debugDescription;
        [Export("debugDescription")]
        string DebugDescription { get; }

        // @property (readonly, copy, nonatomic) NSString * _Nonnull description;
        [Export("description")]
        string Description { get; }

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
