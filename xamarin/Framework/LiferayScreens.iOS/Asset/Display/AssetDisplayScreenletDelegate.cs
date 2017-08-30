using Foundation;
using ObjCRuntime;
using UIKit;

namespace LiferayScreens
{
    // @protocol AssetDisplayScreenletDelegate <BaseScreenletDelegate>
    [BaseType(typeof(BaseScreenletDelegate))]
    [Protocol, Model]
    interface AssetDisplayScreenletDelegate
    {
        // @optional -(void)screenlet:(AssetDisplayScreenlet * _Nonnull)screenlet onAssetResponse:(Asset * _Nonnull)asset;
        [Export("screenlet:onAssetResponse:")]
        void Screenlet(AssetDisplayScreenlet screenlet, Asset asset);

        // @optional -(void)screenlet:(AssetDisplayScreenlet * _Nonnull)screenlet onAssetError:(NSError * _Nonnull)error;
        [Export("screenlet:onAssetError:")]
        void Screenlet(AssetDisplayScreenlet screenlet, NSError error);

        // @optional -(void)screenlet:(AssetDisplayScreenlet * _Nonnull)screenlet onConfigureScreenlet:(BaseScreenlet * _Nullable)childScreenlet onAsset:(Asset * _Nonnull)asset;
        [Export("screenlet:onConfigureScreenlet:onAsset:")]
        void Screenlet(AssetDisplayScreenlet screenlet, [NullAllowed] BaseScreenlet childScreenlet, Asset asset);

        // @optional -(UIView * _Nullable)screenlet:(AssetDisplayScreenlet * _Nonnull)screenlet onAsset:(Asset * _Nonnull)asset __attribute__((warn_unused_result));
        [Export("screenlet:onAsset:")]
        [return: NullAllowed]
        UIView ScreenletCustomAsset(AssetDisplayScreenlet screenlet, Asset asset);
    }
}
