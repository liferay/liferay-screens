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

            this.assetDisplayScreenlet.Delegate = this;

			//Blog
			//this.assetDisplayScreenlet.AssetEntryId = 57367;
			this.assetDisplayScreenlet.ClassPK = 57366;
			this.assetDisplayScreenlet.ClassName = "com.liferay.blogs.kernel.model.BlogsEntry";

			//Image
			//this.assetDisplayScreenlet.ClassPK = 54498;
			//this.assetDisplayScreenlet.ClassName = "com.liferay.document.library.kernel.model.DLFileEntry";
			//this.assetDisplayScreenlet.AssetEntryId = 54500;

			//Audio
			//this.assetDisplayScreenlet.ClassPK = 57433;
			//this.assetDisplayScreenlet.ClassName = "com.liferay.document.library.kernel.model.DLFileEntry";
			//this.assetDisplayScreenlet.AssetEntryId = 57435;

			//Video
			//this.assetDisplayScreenlet.ClassPK = 38943;
			//this.assetDisplayScreenlet.ClassName = "com.liferay.document.library.kernel.model.DLFileEntry";
			//this.assetDisplayScreenlet.AssetEntryId = 38945;

			//PDF
			//this.assetDisplayScreenlet.ClassPK = 38930;
			//this.assetDisplayScreenlet.ClassName = "com.liferay.document.library.kernel.model.DLFileEntry";
			//this.assetDisplayScreenlet.AssetEntryId = 38932;

			//WebContent - FALLO
			//this.assetDisplayScreenlet.ClassPK = 39226;
			//this.assetDisplayScreenlet.ClassName = "com.liferay.journal.model.JournalArticle";
			//this.assetDisplayScreenlet.AssetEntryId = ;

			this.assetDisplayScreenlet.AutoLoad = false;
            this.assetDisplayScreenlet.Load();
        }

        public override void DidReceiveMemoryWarning()
        {
            base.DidReceiveMemoryWarning();
        }

        [Export("screenlet:onAssetResponse:")]
        public virtual void Screenlet(AssetDisplayScreenlet screenlet, Asset asset)
        {
            Debug.WriteLine($"Asset display response: {asset}");
        }

		[Export("screenlet:onAssetError:")]
        public virtual void Screenlet(AssetDisplayScreenlet screenlet, NSError error)
        {
            Debug.WriteLine($"Asset display error: {error}");
		}

		[Export("screenlet:onConfigureScreenlet:onAsset:")]
        public virtual void Screenlet(AssetDisplayScreenlet screenlet, BaseScreenlet childScreenlet, Asset asset)
        {
			Debug.WriteLine($"Configure Asset display: {asset}");
		}

		[Export("screenlet:onAsset:")]
        public virtual UIView ScreenletCustomAsset(AssetDisplayScreenlet screenlet, Asset asset){
            return screenlet;
        }

    }
}

