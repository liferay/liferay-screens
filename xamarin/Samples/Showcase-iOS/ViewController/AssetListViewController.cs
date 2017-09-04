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
        public virtual void OnAssetListError(AssetListScreenlet screenlet, NSError error){
            System.Diagnostics.Debug.WriteLine($"Asset list error: {error}");
		}

		[Export("screenlet:onAssetListResponse:")]
        public virtual void OnAssetListResponse(AssetListScreenlet screenlet, Asset[] assets){
            System.Diagnostics.Debug.WriteLine($"Asset list response: {assets.Length}");
		}

		[Export("screenlet:onAssetSelected:")]
        public virtual void OnAssetSelected(AssetListScreenlet screenlet, Asset asset){
            System.Diagnostics.Debug.WriteLine($"Asset selected: {asset.Title}");
		}
    }
}

