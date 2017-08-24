using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface Asset : NSObject <NSCoding>
    [BaseType(typeof(NSObject))]
    [DisableDefaultCtor]
    interface Asset : INSCoding
    {
        // @property (readonly, copy, nonatomic) NSDictionary<NSString *,id> * _Nonnull attributes;
        [Export("attributes", ArgumentSemantic.Copy)]
        NSDictionary<NSString, NSObject> Attributes { get; }

        // @property (readonly, copy, nonatomic) NSString * _Nonnull title;
        [Export("title")]
        string Title { get; }

        // @property (readonly, copy, nonatomic) NSString * _Nullable mimeType;
        [NullAllowed, Export("mimeType")]
        string MimeType { get; }

        // @property (readonly, copy, nonatomic) NSString * _Nonnull description;
        [Export("description")]
        string Description { get; }

        // @property (readonly, nonatomic) int64_t classNameId;
        [Export("classNameId")]
        long ClassNameId { get; }

        // @property (readonly, nonatomic) int64_t classPK;
        [Export("classPK")]
        long ClassPK { get; }

        // @property (readonly, nonatomic) int64_t groupId;
        [Export("groupId")]
        long GroupId { get; }

        // @property (readonly, nonatomic) int64_t companyId;
        [Export("companyId")]
        long CompanyId { get; }

        // @property (readonly, copy, nonatomic) NSString * _Nonnull url;
        [Export("url")]
        string Url { get; }

        // @property (readonly, nonatomic) int64_t entryId;
        [Export("entryId")]
        long EntryId { get; }

        // @property (readonly, copy, nonatomic) NSDate * _Nonnull createDate;
        [Export("createDate", ArgumentSemantic.Copy)]
        NSDate CreateDate { get; }

        // @property (readonly, copy, nonatomic) NSDate * _Nonnull modifiedDate;
        [Export("modifiedDate", ArgumentSemantic.Copy)]
        NSDate ModifiedDate { get; }

        // @property (readonly, copy, nonatomic) NSString * _Nonnull debugDescription;
        [Export("debugDescription")]
        string DebugDescription { get; }

        // -(instancetype _Nonnull)initWithAttributes:(NSDictionary<NSString *,id> * _Nonnull)attributes __attribute__((objc_designated_initializer));
        [Export("initWithAttributes:")]
        [DesignatedInitializer]
        IntPtr Constructor(NSDictionary<NSString, NSObject> attributes);

        // -(void)encodeWithCoder:(NSCoder * _Nonnull)aCoder;
        [Export("encodeWithCoderAsset:")]
        void EncodeWithCoder(NSCoder aCoder);
    }
}
