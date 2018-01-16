using Foundation;
using LiferayScreens;
using System;
using UIKit;

namespace ShowcaseiOS.ViewController
{
    public partial class CommentListViewController : UIViewController, ICommentListScreenletDelegate
    {
        public CommentListViewController(IntPtr handle) : base(handle) { }

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();

            this.commentListScreenlet.ClassPK = LiferayServerContext.LongPropertyForKey("commentClassPK");
            this.commentListScreenlet.ClassName = LiferayServerContext.StringPropertyForKey("fileClassName");

            this.commentListScreenlet.Delegate = this;
        }

        /* ICommentListScreenletDelegate */

        [Export("screenlet:onCommentDelete:onError:")]
        public virtual void OnCommentDelete(CommentListScreenlet screenlet, Comment comment, NSError error)
        {
            Console.WriteLine($"Comment delete error: {error.DebugDescription}");
        }

        [Export("screenlet:onCommentListError:")]
        public virtual void OnCommentListError(CommentListScreenlet screenlet, NSError error)
        {
            Console.WriteLine($"Comment list error: {error.DebugDescription}");
        }

        [Export("screenlet:onCommentUpdate:onError:")]
        public virtual void OnCommentUpdate(CommentListScreenlet screenlet, Comment comment, NSError error)
        {
            Console.WriteLine($"Comment update error: {error.DebugDescription}");
        }

        [Export("screenlet:onDeletedComment:")]
        public virtual void OnDeletedComment(CommentListScreenlet screenlet, Comment comment)
        {
            Console.WriteLine($"Comment delete: {comment.CommentId}");
        }

        [Export("screenlet:onListResponseComments:")]
        public virtual void OnListResponseComments(CommentListScreenlet screenlet, Comment[] comments)
        {
            Console.WriteLine($"Comment list response: {comments}");
        }

        [Export("screenlet:onSelectedComment:")]
        public virtual void OnSelectedComment(CommentListScreenlet screenlet, Comment comment)
        {
            Console.WriteLine($"Comment selected: {comment.CommentId}");
        }

        [Export("screenlet:onUpdatedComment:")]
        public virtual void OnUpdatedComment(CommentListScreenlet screenlet, Comment comment)
        {
            Console.WriteLine($"Comment update: {comment.CommentId}");
        }
    }
}

