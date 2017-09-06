using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface WebContent : Asset
    [BaseType(typeof(Asset))]
    interface WebContent
    {
        // @property (readonly, nonatomic, strong) DDMStructure * _Nullable structure;
        [NullAllowed, Export("structure", ArgumentSemantic.Strong)]
        DDMStructure Structure { get; }

        // @property (readonly, nonatomic, strong) DDLRecord * _Nullable structuredRecord;
        [NullAllowed, Export("structuredRecord", ArgumentSemantic.Strong)]
        DDLRecord StructuredRecord { get; }

        // @property (readonly, copy, nonatomic) NSString * _Nullable html;
        [NullAllowed, Export("html")]
        string Html { get; }

        // @property (readonly, copy, nonatomic) NSString * _Nonnull debugDescription;
        [Export("debugDescription")]
        string DebugDescription { get; }

        // +(BOOL)isWebContentClassName:(NSString * _Nonnull)className __attribute__((warn_unused_result));
        [Static]
        [Export("isWebContentClassName:")]
        bool IsWebContentClassName(string className);

        // -(instancetype _Nonnull)initWithAttributes:(NSDictionary<NSString *,id> * _Nonnull)attributes __attribute__((objc_designated_initializer));
        [Export("initWithAttributes:")]
        [DesignatedInitializer]
        IntPtr Constructor(NSDictionary<NSString, NSObject> attributes);

        // -(void)encodeWithCoder:(NSCoder * _Nonnull)aCoder;
        [Export("encodeWithCoderWebContent:")]
        void EncodeWithCoder(NSCoder aCoder);
    }
}
