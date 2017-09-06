using Foundation;
using LiferayScreens;
using System;
using UIKit;
using System.Diagnostics;

namespace ShowcaseiOS.ViewController
{
    public partial class CommentDisplayViewController : UIViewController, ICommentDisplayScreenletDelegate
    {
        public CommentDisplayViewController(IntPtr handle) : base(handle) { }

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();

            this.commentDisplayScreenlet.CommentId = 63710;
            this.commentDisplayScreenlet.Editable = true;

            this.commentDisplayScreenlet.Delegate = this;
        }

        /* ICommentDisplayScreenletDelegate */

        [Export("screenlet:onCommentDeleted:")]
        public virtual void OnCommentDeleted(CommentDisplayScreenlet screenlet, Comment comment)
        {
            Debug.WriteLine($"Comment deleted: {comment.CommentId}");
        }

        [Export("screenlet:onCommentLoaded:")]
        public virtual void OnCommentLoaded(CommentDisplayScreenlet screenlet, Comment comment)
        {
            Debug.WriteLine($"Comment loaded: {comment.CommentId}");
        }

        [Export("screenlet:onCommentUpdated:")]
        public virtual void OnCommentUpdated(CommentDisplayScreenlet screenlet, Comment comment)
        {
            Debug.WriteLine($"Comment updated: {comment.CommentId}");
        }

        [Export("screenlet:onDeleteComment:onError:")]
        public virtual void OnDeleteComment(CommentDisplayScreenlet screenlet, Comment comment, NSError error)
        {
            Debug.WriteLine($"Delete comment failed: {comment.CommentId} - {error.DebugDescription}");
        }

        [Export("screenlet:onLoadCommentError:")]
        public virtual void OnLoadCommentError(CommentDisplayScreenlet screenlet, NSError error)
        {
            Debug.WriteLine($"Load comment failed: {error.DebugDescription}");
        }

        [Export("screenlet:onUpdateComment:onError:")]
        public virtual void OnUpdateComment(CommentDisplayScreenlet screenlet, Comment comment, NSError error)
        {
            Debug.WriteLine($"Update comment failed: {comment.CommentId} - {error.DebugDescription}");
        }
    }
}

