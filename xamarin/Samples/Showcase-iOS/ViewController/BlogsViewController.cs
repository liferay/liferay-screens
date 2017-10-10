using Foundation;
using LiferayScreens;
using System;
using UIKit;

namespace ShowcaseiOS.ViewController
{
    public partial class BlogsViewController : UIViewController, IBlogsEntryDisplayScreenletDelegate
    {
        public BlogsViewController(IntPtr handle) : base(handle) { }

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();

            this.blogsDisplayScreenlet.ClassPK = 40515;

            //FIXME: BlogsEntryDisplayScreenlet doesn't work if the blog has images in its body
            //this.blogsDisplayScreenlet.ClassPK = 57546;

            this.blogsDisplayScreenlet.Delegate = this;
        }

        /* IBlogsEntryDisplayScreenletDelegate */

        [Export("screenlet:onBlogEntryError:")]
        public virtual void OnBlogEntryError(BlogsEntryDisplayScreenlet screenlet, NSError error)
        {
            Console.WriteLine($"Blog entry error: {error.DebugDescription}");
        }

        [Export("screenlet:onBlogEntryResponse:")]
        public virtual void OnBlogEntryResponse(BlogsEntryDisplayScreenlet screenlet, BlogsEntry blogEntry)
        {
            Console.WriteLine($"Blog entry response: {blogEntry.EntryId}");
        }
    }
}
