using Foundation;
using LiferayScreens;
using System;
using System.Diagnostics;
using UIKit;

namespace ShowcaseiOS.ViewController
{
    public partial class VideoDisplayViewController : UIViewController, IAssetDisplayScreenletDelegate
    {
        public VideoDisplayViewController(IntPtr handle) : base(handle) { }

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();

            this.videoDisplayScreenlet.ClassPK = 38943;
            this.videoDisplayScreenlet.ClassName = "com.liferay.document.library.kernel.model.DLFileEntry";

            this.videoDisplayScreenlet.Delegate = this;
        }

        /* IAssetDisplayScreenletDelegate */

        [Export("screenlet:onAssetResponse:")]
        public virtual void Screenlet(AssetDisplayScreenlet screenlet, Asset asset)
        {
        	Debug.WriteLine($"Video display response: {asset}");
        }

        [Export("screenlet:onAssetError:")]
        public virtual void Screenlet(AssetDisplayScreenlet screenlet, NSError error)
        {
        	Debug.WriteLine($"Video display error: {error}");
        }

        [Export("screenlet:onConfigureScreenlet:onAsset:")]
        public virtual void Screenlet(AssetDisplayScreenlet screenlet, BaseScreenlet childScreenlet, Asset asset)
        {
        	Debug.WriteLine($"Configure Video display: {asset}");
        }

        [Export("screenlet:onAsset:")]
        public virtual UIView ScreenletCustomAsset(AssetDisplayScreenlet screenlet, Asset asset)
        {
        	return screenlet;
        }
    }
}

