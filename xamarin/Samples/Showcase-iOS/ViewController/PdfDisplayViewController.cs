using Foundation;
using LiferayScreens;
using System;
using System.Diagnostics;
using UIKit;

namespace ShowcaseiOS.ViewController
{
    public partial class PdfDisplayViewController : UIViewController, IAssetDisplayScreenletDelegate
    {
        public PdfDisplayViewController(IntPtr handle) : base(handle) { }

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();

            this.pdfDisplayScreenlet.ClassPK = 38930;
			this.pdfDisplayScreenlet.ClassName = "com.liferay.document.library.kernel.model.DLFileEntry";

            this.pdfDisplayScreenlet.Delegate = this;
		}

        /* IAssetDisplayScreenletDelegate */

        //FIXME: Delegate methods are never called
        [Export("screenlet:onAssetResponse:")]
        public virtual void Screenlet(AssetDisplayScreenlet screenlet, Asset asset)
        {
            Debug.WriteLine($"PDF display response: {asset}");
        }

        [Export("screenlet:onAssetError:")]
        public virtual void Screenlet(AssetDisplayScreenlet screenlet, NSError error)
        {
            Debug.WriteLine($"PDF display error: {error}");
        }

        [Export("screenlet:onConfigureScreenlet:onAsset:")]
        public virtual void Screenlet(AssetDisplayScreenlet screenlet, BaseScreenlet childScreenlet, Asset asset)
        {
            Debug.WriteLine($"Configure PDF display: {asset}");
        }

        [Export("screenlet:onAsset:")]
        public virtual UIView ScreenletCustomAsset(AssetDisplayScreenlet screenlet, Asset asset)
        {
            return screenlet;
        }
    }
}

