using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface BlogsEntry : Asset
    [BaseType(typeof(Asset))]
    interface BlogsEntry
    {
        // @property (readonly, copy, nonatomic) NSDictionary<NSString *,id> * _Nullable blogsEntry;
        [NullAllowed, Export("blogsEntryAsset", ArgumentSemantic.Copy)]
        NSDictionary<NSString, NSObject> BlogsEntryAsset { get; }

        // @property (readonly, nonatomic) int64_t blogId;
        [Export("blogId")]
        long BlogId { get; }

        // @property (readonly, copy, nonatomic) NSString * _Nonnull subtitle;
        [Export("subtitle")]
        string Subtitle { get; }

        // @property (readonly, copy, nonatomic) NSString * _Nonnull userName;
        [Export("userName")]
        string UserName { get; }

        // @property (readonly, copy, nonatomic) NSDate * _Nullable displayDate;
        [NullAllowed, Export("displayDate", ArgumentSemantic.Copy)]
        NSDate DisplayDate { get; }

        // @property (readonly, copy, nonatomic) NSString * _Nonnull content;
        [Export("content")]
        string Content { get; }

        // @property (readonly, nonatomic) int64_t userId;
        [Export("userId")]
        long UserId { get; }

        // @property (readonly, nonatomic) int64_t coverImageFileEntryId;
        [Export("coverImageFileEntryId")]
        long CoverImageFileEntryId { get; }

        // @property (readonly, copy, nonatomic) NSString * _Nullable mimeType;
        [NullAllowed, Export("mimeType")]
        string MimeType { get; }

        // -(instancetype _Nonnull)initWithAttributes:(NSDictionary<NSString *,id> * _Nonnull)attributes __attribute__((objc_designated_initializer));
        [Export("initWithAttributes:")]
        [DesignatedInitializer]
        IntPtr Constructor(NSDictionary<NSString, NSObject> attributes);
    }
}
