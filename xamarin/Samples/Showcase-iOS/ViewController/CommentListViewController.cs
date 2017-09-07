using Foundation;
using LiferayScreens;
using System;
using UIKit;
using System.Diagnostics;

namespace ShowcaseiOS.ViewController
{
    public partial class CommentListViewController : UIViewController, ICommentListScreenletDelegate
    {
        public CommentListViewController(IntPtr handle) : base(handle) { }

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();

            this.commentListScreenlet.ClassPK = 54473;
            this.commentListScreenlet.ClassName = "com.liferay.document.library.kernel.model.DLFileEntry";

            this.commentListScreenlet.Delegate = this;
        }

        /* ICommentListScreenletDelegate */

        [Export("screenlet:onCommentDelete:onError:")]
        public virtual void OnCommentDelete(CommentListScreenlet screenlet, Comment comment, NSError error)
        {
            Debug.WriteLine($"Comment delete error: {error.DebugDescription}");
        }

        [Export("screenlet:onCommentListError:")]
        public virtual void OnCommentListError(CommentListScreenlet screenlet, NSError error)
        {
            Debug.WriteLine($"Comment list error: {error.DebugDescription}");
        }

        [Export("screenlet:onCommentUpdate:onError:")]
        public virtual void OnCommentUpdate(CommentListScreenlet screenlet, Comment comment, NSError error)
        {
            Debug.WriteLine($"Comment update error: {error.DebugDescription}");
        }

        [Export("screenlet:onDeletedComment:")]
        public virtual void OnDeletedComment(CommentListScreenlet screenlet, Comment comment)
        {
            Debug.WriteLine($"Comment delete: {comment.CommentId}");
        }

        [Export("screenlet:onListResponseComments:")]
        public virtual void OnListResponseComments(CommentListScreenlet screenlet, Comment[] comments)
        {
            Debug.WriteLine($"Comment list response: {comments}");
        }

        [Export("screenlet:onSelectedComment:")]
        public virtual void OnSelectedComment(CommentListScreenlet screenlet, Comment comment)
        {
            Debug.WriteLine($"Comment selected: {comment.CommentId}");
        }

        [Export("screenlet:onUpdatedComment:")]
        public virtual void OnUpdatedComment(CommentListScreenlet screenlet, Comment comment)
        {
            Debug.WriteLine($"Comment update: {comment.CommentId}");
        }
    }
}

