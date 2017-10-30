using Foundation;
using ObjCRuntime;

namespace LiferayScreens
{
    // @protocol AssetListScreenletDelegate <BaseScreenletDelegate>
    [BaseType(typeof(BaseScreenletDelegate))]
    [Protocol, Model]
    interface AssetListScreenletDelegate
    {
        // @optional -(void)screenlet:(AssetListScreenlet * _Nonnull)screenlet onAssetListResponse:(NSArray<Asset *> * _Nonnull)assets;
        [Export("screenlet:onAssetListResponse:")]
        void OnAssetListResponse(AssetListScreenlet screenlet, Asset[] assets);

        // @optional -(void)screenlet:(AssetListScreenlet * _Nonnull)screenlet onAssetListError:(NSError * _Nonnull)error;
        [Export("screenlet:onAssetListError:")]
        void OnAssetListError(AssetListScreenlet screenlet, NSError error);

        // @optional -(void)screenlet:(AssetListScreenlet * _Nonnull)screenlet onAssetSelected:(Asset * _Nonnull)asset;
        [Export("screenlet:onAssetSelected:")]
        void OnAssetSelected(AssetListScreenlet screenlet, Asset asset);
    }
}
