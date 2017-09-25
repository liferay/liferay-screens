using Foundation;
using System;

namespace LiferayScreens
{
    // @interface DDMFieldDocument : DDMField
    [BaseType(typeof(DDMField))]
    interface DDMFieldDocument
    {
        // @property (readonly, copy, nonatomic) NSString * _Nullable mimeType;
        [NullAllowed, Export("mimeType")]
        string MimeType { get; }

        // @property (readonly, copy, nonatomic) NSString * _Nullable cachedKey;
        [NullAllowed, Export("cachedKey")]
        string CachedKey { get; }

        // -(instancetype _Nonnull)initWithAttributes:(NSDictionary<NSString *,id> * _Nonnull)attributes locale:(NSLocale * _Nonnull)locale __attribute__((objc_designated_initializer));
        [Export("initWithAttributes:locale:")]
        [DesignatedInitializer]
        IntPtr Constructor(NSDictionary<NSString, NSObject> attributes, NSLocale locale);
    }
}