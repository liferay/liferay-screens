using Foundation;
using ObjCRuntime;
using System;
using UIKit;

namespace LiferayScreens
{
    // @interface ImageEntry : Asset
    [BaseType(typeof(Asset))]
    interface ImageEntry
    {
        // @property (nonatomic, strong) UIImage * _Nullable image;
        [NullAllowed, Export("image", ArgumentSemantic.Strong)]
        UIImage Image { get; set; }

        // @property (readonly, copy, nonatomic) NSString * _Nonnull thumbnailUrl;
        [Export("thumbnailUrl")]
        string ThumbnailUrl { get; }

        // @property (readonly, copy, nonatomic) NSString * _Nonnull imageUrl;
        [Export("imageUrl")]
        string ImageUrl { get; }

        // @property (readonly, nonatomic) int64_t imageEntryId;
        [Export("imageEntryId")]
        long ImageEntryId { get; }

        // -(instancetype _Nonnull)initWithAttributes:(NSDictionary<NSString *,id> * _Nonnull)attributes __attribute__((objc_designated_initializer));
        [Export("initWithAttributes:")]
        [DesignatedInitializer]
        IntPtr Constructor(NSDictionary<NSString, NSObject> attributes);
    }
}
