using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface BlogsEntryDisplayView_default : BaseScreenletView <BlogsDisplayViewModel>
    [BaseType(typeof(BaseScreenletView))]
    interface BlogsEntryDisplayView_default : IBlogsDisplayViewModel
    {
        // @property (nonatomic) CGFloat headerImageHeight;
        [Export("headerImageHeight")]
        nfloat HeaderImageHeight { get; set; }

        // @property (readonly, nonatomic, strong) NSDateFormatter * _Nonnull dateFormatter;
        [Export("dateFormatter", ArgumentSemantic.Strong)]
        NSDateFormatter DateFormatter { get; }

        // @property (nonatomic, strong) BlogsEntry * _Nullable blogsEntry;
        [NullAllowed, Export("blogsEntry", ArgumentSemantic.Strong)]
        BlogsEntry BlogsEntry { get; set; }

        // -(void)onSetTranslations;
        [Export("onSetTranslations")]
        void OnSetTranslations();

        // -(void)loadBlog;
        [Export("loadBlog")]
        void LoadBlog();

        // -(void)loadImage;
        [Export("loadImage")]
        void LoadImage();

        // -(void)loadUserInfo;
        [Export("loadUserInfo")]
        void LoadUserInfo();

        // -(void)loadDate;
        [Export("loadDate")]
        void LoadDate();

        // -(void)loadTitleSubtitle;
        [Export("loadTitleSubtitle")]
        void LoadTitleSubtitle();

        // -(void)loadContent;
        [Export("loadContent")]
        void LoadContent();

        // +(NSDictionary<NSString *,NSObject *> * _Nonnull)defaultAttributedTextAttributes __attribute__((warn_unused_result));
        [Static]
        [Export("defaultAttributedTextAttributes")]
        NSDictionary<NSString, NSObject> DefaultAttributedTextAttributes();

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
        [Export("initWithFrame:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame);
    }
}
