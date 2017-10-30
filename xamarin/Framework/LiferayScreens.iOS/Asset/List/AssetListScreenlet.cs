using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface AssetListScreenlet : BaseListScreenlet
    [BaseType(typeof(BaseListScreenlet))]
    interface AssetListScreenlet
    {
        // @property (nonatomic) int64_t groupId;
        [Export("groupId")]
        long GroupId { get; set; }

        // @property (nonatomic) int64_t classNameId;
        [Export("classNameId")]
        long ClassNameId { get; set; }

        // @property (copy, nonatomic) NSString * _Nullable portletItemName;
        [NullAllowed, Export("portletItemName")]
        string PortletItemName { get; set; }

        // @property (copy, nonatomic) NSString * _Nullable offlinePolicy;
        [NullAllowed, Export("offlinePolicy")]
        string OfflinePolicy { get; set; }

        [Wrap("WeakAssetListDelegate")]
        [NullAllowed]
        AssetListScreenletDelegate AssetListDelegate { get; }

        // @property (readonly, nonatomic, strong) id<AssetListScreenletDelegate> _Nullable assetListDelegate;
        [NullAllowed, Export("assetListDelegate", ArgumentSemantic.Strong)]
        NSObject WeakAssetListDelegate { get; }

        // @property (copy, nonatomic) NSDictionary<NSString *,id> * _Nullable customEntryQuery;
        [NullAllowed, Export("customEntryQuery", ArgumentSemantic.Copy)]
        NSDictionary<NSString, NSObject> CustomEntryQuery { get; set; }

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
        [Export("initWithFrame:themeName:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame, [NullAllowed] string themeName);
    }
}
