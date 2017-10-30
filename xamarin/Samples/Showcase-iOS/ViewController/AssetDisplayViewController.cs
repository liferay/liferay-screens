using Foundation;
using LiferayScreens;
using System;
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
            //this.assetDisplayScreenlet.AssetEntryId = LiferayServerContext.LongPropertyForKey("blogEntryId");
            //this.assetDisplayScreenlet.ClassPK = LiferayServerContext.LongPropertyForKey("blogClassPK");
            //this.assetDisplayScreenlet.ClassName = LiferayServerContext.StringPropertyForKey("blogClassName");

            //Image
            //this.assetDisplayScreenlet.AssetEntryId = LiferayServerContext.LongPropertyForKey("imageEntryId");
            //this.assetDisplayScreenlet.ClassPK = LiferayServerContext.LongPropertyForKey("imageClassPK");
            //this.assetDisplayScreenlet.ClassName = LiferayServerContext.StringPropertyForKey("fileClassName");

            //Audio
            this.assetDisplayScreenlet.AssetEntryId = LiferayServerContext.LongPropertyForKey("audioEntryId");
            //this.assetDisplayScreenlet.ClassPK = LiferayServerContext.LongPropertyForKey("audioClassPK");
            //this.assetDisplayScreenlet.ClassName = LiferayServerContext.StringPropertyForKey("fileClassName");

            //Video
            //this.assetDisplayScreenlet.AssetEntryId = LiferayServerContext.LongPropertyForKey("videoEntryId");
            //this.assetDisplayScreenlet.ClassPK = LiferayServerContext.LongPropertyForKey("videoClassPK");
            //this.assetDisplayScreenlet.ClassName = LiferayServerContext.StringPropertyForKey("fileClassName");

            //PDF
            //this.assetDisplayScreenlet.AssetEntryId = LiferayServerContext.LongPropertyForKey("pdfEntryId");
            //this.assetDisplayScreenlet.ClassPK = LiferayServerContext.LongPropertyForKey("pdfClassPK");
            //this.assetDisplayScreenlet.ClassName = LiferayServerContext.StringPropertyForKey("fileClassName");

            //FIXME: AssetDisplayScreenlet doesn't work with WebContent
            //this.assetDisplayScreenlet.AssetEntryId = LiferayServerContext.LongPropertyForKey("webContentEntryId");
            //this.assetDisplayScreenlet.ClassPK = LiferayServerContext.LongPropertyForKey("webContentClassPK");
            //this.assetDisplayScreenlet.ClassName = LiferayServerContext.StringPropertyForKey("webContentClassName");

            this.assetDisplayScreenlet.Load();

            this.assetDisplayScreenlet.Delegate = this;
        }

        /* IAssetDisplayScreenletDelegate */

        [Export("screenlet:onAssetResponse:")]
        public virtual void Screenlet(AssetDisplayScreenlet screenlet, Asset asset)
        {
            Console.WriteLine($"Asset display response: {asset.Attributes}");
        }

        [Export("screenlet:onAssetError:")]
        public virtual void Screenlet(AssetDisplayScreenlet screenlet, NSError error)
        {
            Console.WriteLine($"Asset display error: {error.DebugDescription}");
        }

        [Export("screenlet:onConfigureScreenlet:onAsset:")]
        public virtual void Screenlet(AssetDisplayScreenlet screenlet, BaseScreenlet childScreenlet, Asset asset)
        {
            Console.WriteLine($"Configure Asset display: {asset}");
        }

        [Export("screenlet:onAsset:")]
        public virtual UIView ScreenletCustomAsset(AssetDisplayScreenlet screenlet, Asset asset)
        {
            Console.WriteLine($"Asset display custom asset: {asset.Attributes}");
            return screenlet;
        }
    }
}

