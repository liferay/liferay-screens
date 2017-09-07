using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface BlogsEntryDisplayScreenlet : BaseScreenlet
    [BaseType(typeof(BaseScreenlet))]
    interface BlogsEntryDisplayScreenlet
    {
        // @property (nonatomic) int64_t assetEntryId;
        [Export("assetEntryId")]
        long AssetEntryId { get; set; }

        // @property (nonatomic) int64_t classPK;
        [Export("classPK")]
        long ClassPK { get; set; }

        // @property (nonatomic) BOOL autoLoad;
        [Export("autoLoad")]
        bool AutoLoad { get; set; }

        // @property (copy, nonatomic) NSString * _Nullable offlinePolicy;
        [NullAllowed, Export("offlinePolicy")]
        string OfflinePolicy { get; set; }

        [Wrap("WeakBlogsEntryDisplayDelegate")]
        [NullAllowed]
        BlogsEntryDisplayScreenletDelegate BlogsEntryDisplayDelegate { get; }

        // @property (readonly, nonatomic, strong) id<BlogsEntryDisplayScreenletDelegate> _Nullable blogsEntryDisplayDelegate;
        [NullAllowed, Export("blogsEntryDisplayDelegate", ArgumentSemantic.Strong)]
        NSObject WeakBlogsEntryDisplayDelegate { get; }

        // @property (readonly, nonatomic, strong) id<BlogsDisplayViewModel> _Nullable blogsEntryViewModel;
        [NullAllowed, Export("blogsEntryViewModel", ArgumentSemantic.Strong)]
        IBlogsDisplayViewModel BlogsEntryViewModel { get; }

        // @property (nonatomic, strong) BlogsEntry * _Nullable blogsEntry;
        [NullAllowed, Export("blogsEntry", ArgumentSemantic.Strong)]
        BlogsEntry BlogsEntry { get; set; }

        // -(void)onShow;
        [Export("onShow")]
        void OnShow();

        // -(Interactor * _Nullable)createInteractorWithName:(NSString * _Nonnull)name sender:(id _Nullable)sender __attribute__((warn_unused_result));
        [Export("createInteractorWithName:sender:")]
        [return: NullAllowed]
        Interactor CreateInteractorWithName(string name, [NullAllowed] NSObject sender);

        // -(BOOL)load;
        [Export("load")]
        bool Load();

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
        [Export("initWithFrame:themeName:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame, [NullAllowed] string themeName);
    }
}
