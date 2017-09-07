using System.Collections;
using Android.App;
using Android.OS;
using Android.Views;
using Android.Widget;
using Com.Liferay.Mobile.Screens.Comment;
using Com.Liferay.Mobile.Screens.Comment.List;

namespace ShowcaseAndroid
{
    [Activity]
    public class CommentListActivity : Activity/*, ICommentListListener*/
    {
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.CommentListView);

            CommentListScreenlet commentListScreenlet = 
                (CommentListScreenlet) FindViewById(Resource.Id.comment_list_screenlet);
            //commentListScreenlet.Listener = this;
        }

        /* ICommentListListener */

        //FIXME: Listener doesn't compile
        public void Error(Java.Lang.Exception p0, string p1)
		{
			System.Diagnostics.Debug.WriteLine($"Comment list error: {p0}");
		}

		public void OnDeleteCommentSuccess(CommentEntry p0)
		{
            Toast.MakeText(this, $"Success {p0.CommentId}", ToastLength.Short).Show();
		}

		public void OnListItemSelected(Java.Lang.Object p0, View p1)
		{
            Toast.MakeText(this, $"Item selected {p0}", ToastLength.Short).Show();
		}

		public void OnListPageFailed(int p0, Java.Lang.Exception p1)
		{
            System.Diagnostics.Debug.WriteLine($"Comment list page failed: {p1.Message}");
		}

		public void OnListPageReceived(int p0, int p1, IList p2, int p3)
		{
			System.Diagnostics.Debug.WriteLine($"Comment list page received: {p3} entries");
		}

		public void OnUpdateCommentSuccess(CommentEntry p0)
		{
            Toast.MakeText(this, $"Comment updated {p0.CommentId}", ToastLength.Short).Show();
		}
    }
}
