using Foundation;
using LiferayScreens;
using System;
using UIKit;

namespace ShowcaseiOS.ViewController
{
    public partial class AssetListViewController : UIViewController, IAssetListScreenletDelegate
    {
        public AssetListViewController(IntPtr handle) : base(handle) { }

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();

            //With classNameId
            //this.assetListScreenlet.ClassNameId = 20015;

            //With porletItemName
            this.assetListScreenlet.PortletItemName = "dynamic";

            this.assetListScreenlet.Delegate = this;
        }

        /* IAssetListScreenletDelegate */

        [Export("screenlet:onAssetListError:")]
        public virtual void OnAssetListError(AssetListScreenlet screenlet, NSError error)
        {
            Console.WriteLine($"Asset list error: {error.DebugDescription}");
        }

        [Export("screenlet:onAssetListResponse:")]
        public virtual void OnAssetListResponse(AssetListScreenlet screenlet, Asset[] assets)
        {
            Console.WriteLine($"Asset list response: {assets.Length} entries");
        }

        [Export("screenlet:onAssetSelected:")]
        public virtual void OnAssetSelected(AssetListScreenlet screenlet, Asset asset)
        {
            Console.WriteLine($"Asset selected: {asset.Title}");
        }
    }
}

