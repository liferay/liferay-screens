using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface WebContentDisplayScreenlet : BaseScreenlet
    [BaseType(typeof(BaseScreenlet))]
    interface WebContentDisplayScreenlet
    {
        // @property (nonatomic) int64_t groupId;
        [Export("groupId")]
        long GroupId { get; set; }

        // @property (copy, nonatomic) NSString * _Nonnull articleId;
        [Export("articleId")]
        string ArticleId { get; set; }

        // @property (nonatomic) int64_t templateId;
        [Export("templateId")]
        long TemplateId { get; set; }

        // @property (nonatomic) int64_t structureId;
        [Export("structureId")]
        long StructureId { get; set; }

        // @property (nonatomic) BOOL autoLoad;
        [Export("autoLoad")]
        bool AutoLoad { get; set; }

        // @property (copy, nonatomic) NSString * _Nullable offlinePolicy;
        [NullAllowed, Export("offlinePolicy")]
        string OfflinePolicy { get; set; }

        // @property (copy, nonatomic) NSString * _Nonnull customCssFile;
        [Export("customCssFile")]
        string CustomCssFile { get; set; }

        [Wrap("WeakWebContentDisplayDelegate")]
        [NullAllowed]
        WebContentDisplayScreenletDelegate WebContentDisplayDelegate { get; }

        // @property (readonly, nonatomic, strong) id<WebContentDisplayScreenletDelegate> _Nullable webContentDisplayDelegate;
        [NullAllowed, Export("webContentDisplayDelegate", ArgumentSemantic.Strong)]
        NSObject WeakWebContentDisplayDelegate { get; }

        // @property (readonly, nonatomic, strong) id<WebContentDisplayViewModel> _Nullable webContentDisplayViewModel;
        [NullAllowed, Export("webContentDisplayViewModel", ArgumentSemantic.Strong)]
        IWebContentDisplayViewModel WebContentDisplayViewModel { get; }

        // -(BOOL)loadWebContent;
        [Export("loadWebContent")]
        bool LoadWebContent();

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
        [Export("initWithFrame:themeName:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame, [NullAllowed] string themeName);
    }
}
