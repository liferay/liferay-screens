using Foundation;
using ObjCRuntime;
using System;
using UIKit;

namespace LiferayScreens
{
    // @interface ImageEntryUpload : NSObject <NSCoding>
    [BaseType(typeof(NSObject))]
    [DisableDefaultCtor]
    interface ImageEntryUpload : INSCoding
    {
        // @property (readonly, nonatomic, strong) UIImage * _Nonnull image;
        [Export("image", ArgumentSemantic.Strong)]
        UIImage Image { get; }

        // @property (readonly, nonatomic, strong) UIImage * _Nullable thumbnail;
        [NullAllowed, Export("thumbnail", ArgumentSemantic.Strong)]
        UIImage Thumbnail { get; }

        // @property (readonly, copy, nonatomic) NSString * _Nonnull title;
        [Export("title")]
        string Title { get; }

        // @property (readonly, copy, nonatomic) NSString * _Nonnull notes;
        [Export("notes")]
        string Notes { get; }

        // -(instancetype _Nonnull)initWithImage:(UIImage * _Nonnull)image thumbnail:(UIImage * _Nullable)thumbnail title:(NSString * _Nonnull)title notes:(NSString * _Nonnull)notes __attribute__((objc_designated_initializer));
        [Export("initWithImage:thumbnail:title:notes:")]
        [DesignatedInitializer]
        IntPtr Constructor(UIImage image, [NullAllowed] UIImage thumbnail, string title, string notes);

        // -(void)encodeWithCoder:(NSCoder * _Nonnull)aCoder;
        [Export("encodeWithCoderImageEntryUpload:")]
        void EncodeWithCoder(NSCoder aCoder);
    }
}
