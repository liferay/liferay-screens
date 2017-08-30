using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;
using UIKit;

namespace LiferayScreens
{
    // @interface AssetDisplayScreenlet : BaseScreenlet
    [BaseType(typeof(BaseScreenlet))]
    interface AssetDisplayScreenlet
    {
        // @property (nonatomic) int64_t assetEntryId;
        [Export("assetEntryId")]
        long AssetEntryId { get; set; }

        // @property (copy, nonatomic) NSString * _Nonnull className;
        [Export("className")]
        string ClassName { get; set; }

        // @property (nonatomic) int64_t classPK;
        [Export("classPK")]
        long ClassPK { get; set; }

        // @property (copy, nonatomic) NSString * _Nullable portletItemName;
        [NullAllowed, Export("portletItemName")]
        string PortletItemName { get; set; }

        // @property (nonatomic) BOOL autoLoad;
        [Export("autoLoad")]
        bool AutoLoad { get; set; }

        // @property (copy, nonatomic) NSString * _Nullable offlinePolicy;
        [NullAllowed, Export("offlinePolicy")]
        string OfflinePolicy { get; set; }

        [Wrap("WeakAssetDisplayDelegate")]
        [NullAllowed]
        AssetDisplayScreenletDelegate AssetDisplayDelegate { get; }

        // @property (readonly, nonatomic, strong) id<AssetDisplayScreenletDelegate> _Nullable assetDisplayDelegate;
        [NullAllowed, Export("assetDisplayDelegate", ArgumentSemantic.Strong)]
        NSObject WeakAssetDisplayDelegate { get; }

        // @property (readonly, nonatomic, strong) id<AssetDisplayViewModel> _Nullable assetDisplayViewModel;
        [NullAllowed, Export("assetDisplayViewModel", ArgumentSemantic.Strong)]
        IAssetDisplayViewModel AssetDisplayViewModel { get; }

        // @property (nonatomic, strong) Asset * _Nullable assetEntry;
        [NullAllowed, Export("assetEntry", ArgumentSemantic.Strong)]
        Asset AssetEntry { get; set; }

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

        // -(UIView * _Nullable)createInnerScreenlet:(Asset * _Nonnull)asset __attribute__((warn_unused_result));
        [Export("createInnerScreenlet:")]
        [return: NullAllowed]
        UIView CreateInnerScreenlet(Asset asset);

        // -(void)configureInnerScreenlet:(BaseScreenlet * _Nonnull)innerScreenlet asset:(Asset * _Nonnull)asset;
        [Export("configureInnerScreenlet:asset:")]
        void ConfigureInnerScreenlet(BaseScreenlet innerScreenlet, Asset asset);

        // -(void)removeInnerScreenlet;
        [Export("removeInnerScreenlet")]
        void RemoveInnerScreenlet();

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
        [Export("initWithFrame:themeName:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame, [NullAllowed] string themeName);
    }
}
