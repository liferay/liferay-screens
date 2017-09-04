using Foundation;
using LiferayScreens;
using System;
using System.Diagnostics;
using UIKit;

namespace ShowcaseiOS.ViewController
{
    public partial class AudioDisplayViewController : UIViewController, IAssetDisplayScreenletDelegate
    {
        public AudioDisplayViewController(IntPtr handle) : base(handle) { }

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();

            this.audioDisplayScreenlet.ClassPK = 57433;
            this.audioDisplayScreenlet.ClassName = "com.liferay.document.library.kernel.model.DLFileEntry";

            this.audioDisplayScreenlet.Delegate = this;
        }

        /* IAssetDisplayScreenletDelegate */

        [Export("screenlet:onAssetResponse:")]
        public virtual void Screenlet(AssetDisplayScreenlet screenlet, Asset asset)
        {
            Debug.WriteLine($"Audio display response: {asset}");
        }

        [Export("screenlet:onAssetError:")]
        public virtual void Screenlet(AssetDisplayScreenlet screenlet, NSError error)
        {
        	Debug.WriteLine($"Audio display error: {error}");
        }

        [Export("screenlet:onConfigureScreenlet:onAsset:")]
        public virtual void Screenlet(AssetDisplayScreenlet screenlet, BaseScreenlet childScreenlet, Asset asset)
        {
        	Debug.WriteLine($"Configure Audio display: {asset}");
        }

        [Export("screenlet:onAsset:")]
        public virtual UIView ScreenletCustomAsset(AssetDisplayScreenlet screenlet, Asset asset)
        {
            Debug.WriteLine($"Audio display custom asset: {asset.Attributes}");
        	return screenlet;
        }
    }
}

