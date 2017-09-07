using Android.App;
using Android.OS;
using Android.Widget;
using Com.Liferay.Mobile.Screens.Comment;
using Com.Liferay.Mobile.Screens.Comment.Add;

namespace ShowcaseAndroid
{
    [Activity]
    public class CommentAddActivity : Activity, ICommentAddListener
    {
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.CommentAddView);

            CommentAddScreenlet commentAddScreenlet =
                (CommentAddScreenlet) FindViewById(Resource.Id.comment_add_screenlet);
            commentAddScreenlet.Listener = this;
        }

        /* ICommentAddListener */

        public void Error(Java.Lang.Exception p0, string p1)
        {
            System.Diagnostics.Debug.WriteLine($"Comment add error: {p0.Message}");
        }

        public void OnAddCommentSuccess(CommentEntry p0)
        {
            Toast.MakeText(this, "Comment add success: " + p0.CommentId, ToastLength.Short).Show();
        }
    }
}
