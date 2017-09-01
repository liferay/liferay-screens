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

            this.assetListScreenlet.ClassNameId = 20015;
            this.assetListScreenlet.Delegate = this;
        }

        public override void DidReceiveMemoryWarning()
        {
            base.DidReceiveMemoryWarning();
        }

		[Export("screenlet:onAssetListError:")]
        public virtual void OnAssetListError(AssetListScreenlet screenlet, NSError error){
            System.Diagnostics.Debug.WriteLine($"Asset list error: {error}");
		}

		[Export("screenlet:onAssetListResponse:")]
        public virtual void OnAssetListResponse(AssetListScreenlet screenlet, Asset[] assets){
            System.Diagnostics.Debug.WriteLine($"Asset list response: {assets}");
		}

		[Export("screenlet:onAssetSelected:")]
        public virtual void OnAssetSelected(AssetListScreenlet screenlet, Asset asset){
            System.Diagnostics.Debug.WriteLine($"Asset selected: {asset.Title}");
		}
    }
}

