using Android.App;
using Android.OS;
using Android.Widget;
using Com.Liferay.Mobile.Screens.Comment;
using Com.Liferay.Mobile.Screens.Comment.Display;

namespace ShowcaseAndroid
{
    [Activity]
    public class CommentDisplayActivity : Activity, ICommentDisplayListener
    {
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.CommentDisplayView);

            CommentDisplayScreenlet commentDisplayScreenlet =
                (CommentDisplayScreenlet) FindViewById(Resource.Id.comment_display_screenlet);
            commentDisplayScreenlet.Listener = this;
        }

        /* ICommentDisplayListener */

        public void Error(Java.Lang.Exception p0, string p1)
        {
            System.Diagnostics.Debug.WriteLine($"Comment display error: {p0.Message}");
        }

        public void OnDeleteCommentSuccess(CommentEntry p0)
        {
            Toast.MakeText(this, "Comment delete success: " + p0.CommentId, ToastLength.Short).Show();
        }

        public void OnLoadCommentSuccess(CommentEntry p0)
        {
        	Toast.MakeText(this, "Comment load success: " + p0.CommentId, ToastLength.Short).Show();
        }

        public void OnUpdateCommentSuccess(CommentEntry p0)
        {
        	Toast.MakeText(this, "Comment update success: " + p0.CommentId, ToastLength.Short).Show();
        }
    }
}
