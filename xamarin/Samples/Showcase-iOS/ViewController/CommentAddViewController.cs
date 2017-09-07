using Foundation;
using LiferayScreens;
using System;
using UIKit;
using System.Diagnostics;

namespace ShowcaseiOS.ViewController
{
    public partial class CommentAddViewController : UIViewController, ICommentAddScreenletDelegate
    {
        public CommentAddViewController(IntPtr handle) : base(handle) { }

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();

            this.commentAddScreenlet.ClassPK = 54473;
            this.commentAddScreenlet.ClassName = "com.liferay.document.library.kernel.model.DLFileEntry";

            this.commentAddScreenlet.Delegate = this;
        }

        /* ICommentAddScreenletDelegate */


        [Export("screenlet:onAddCommentError:")]
        public virtual void OnAddCommentError(CommentAddScreenlet screenlet, NSError error)
        {
            Debug.WriteLine($"Comment add failed: {error.DebugDescription}");
        }

        [Export("screenlet:onCommentAdded:")]
        public virtual void OnCommentAdded(CommentAddScreenlet screenlet, Comment comment)
        {
            Debug.WriteLine($"Comment add success: {comment.CommentId}");
        }

        [Export("screenlet:onCommentUpdated:")]
        public virtual void OnCommentUpdated(CommentAddScreenlet screenlet, Comment comment)
        {
            Debug.WriteLine($"Comment update success: {comment.CommentId}");
        }

        [Export("screenlet:onUpdateCommentError:")]
        public virtual void OnUpdateCommentError(CommentAddScreenlet screenlet, NSError error)
        {
            Debug.WriteLine($"Comment update failed: {error.DebugDescription}");
        }
    }
}

