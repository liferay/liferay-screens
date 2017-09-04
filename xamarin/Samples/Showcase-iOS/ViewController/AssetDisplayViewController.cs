using Foundation;
using LiferayScreens;
using System;
using System.Diagnostics;
using UIKit;

namespace ShowcaseiOS.ViewController
{
    public partial class AssetDisplayViewController : UIViewController, IAssetDisplayScreenletDelegate
    {
        public AssetDisplayViewController(IntPtr handle) : base(handle) { }

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();

            //AssetDisplayScreenlet works with AssetEntryId o ClassName and ClassPK
            this.assetDisplayScreenlet.AutoLoad = false;

            //Blog
            //this.assetDisplayScreenlet.AssetEntryId = 40516;
            //this.assetDisplayScreenlet.ClassPK = 40515;
            //this.assetDisplayScreenlet.ClassName = "com.liferay.blogs.kernel.model.BlogsEntry";

            //Image
            //this.assetDisplayScreenlet.AssetEntryId = 54500;
            //this.assetDisplayScreenlet.ClassPK = 54498;
            //this.assetDisplayScreenlet.ClassName = "com.liferay.document.library.kernel.model.DLFileEntry";

            //Audio
            this.assetDisplayScreenlet.AssetEntryId = 57435;
            //this.assetDisplayScreenlet.ClassPK = 57433;
            //this.assetDisplayScreenlet.ClassName = "com.liferay.document.library.kernel.model.DLFileEntry";

            //Video
            //this.assetDisplayScreenlet.AssetEntryId = 38945;
            //this.assetDisplayScreenlet.ClassPK = 38943;
            //this.assetDisplayScreenlet.ClassName = "com.liferay.document.library.kernel.model.DLFileEntry";

            //PDF
            //this.assetDisplayScreenlet.AssetEntryId = 38932;
            //this.assetDisplayScreenlet.ClassPK = 38930;
            //this.assetDisplayScreenlet.ClassName = "com.liferay.document.library.kernel.model.DLFileEntry";

            //FIXME: WebContent failed
            //this.assetDisplayScreenlet.AssetEntryId = 39228;
            //this.assetDisplayScreenlet.ClassPK = 39226;
            //this.assetDisplayScreenlet.ClassName = "com.liferay.journal.model.JournalArticle";

            this.assetDisplayScreenlet.Load();

            this.assetDisplayScreenlet.Delegate = this;
        }

        /* IAssetDisplayScreenletDelegate */

        [Export("screenlet:onAssetResponse:")]
        public virtual void Screenlet(AssetDisplayScreenlet screenlet, Asset asset)
        {
            Debug.WriteLine($"Asset display response: {asset.Attributes}");
        }

        [Export("screenlet:onAssetError:")]
        public virtual void Screenlet(AssetDisplayScreenlet screenlet, NSError error)
        {
            Debug.WriteLine($"Asset display error: {error.DebugDescription}");
        }

        [Export("screenlet:onConfigureScreenlet:onAsset:")]
        public virtual void Screenlet(AssetDisplayScreenlet screenlet, BaseScreenlet childScreenlet, Asset asset)
        {
            Debug.WriteLine($"Configure Asset display: {asset}");
        }

        [Export("screenlet:onAsset:")]
        public virtual UIView ScreenletCustomAsset(AssetDisplayScreenlet screenlet, Asset asset)
        {
            Debug.WriteLine($"Asset display custom asset: {asset.Attributes}");
            return screenlet;
        }
    }
}

