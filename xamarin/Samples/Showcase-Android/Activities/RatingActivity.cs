using Android.App;
using Android.OS;
using Android.Widget;
using Com.Liferay.Mobile.Screens.Rating;

namespace ShowcaseAndroid
{
    [Activity]
    public class RatingActivity : Activity, IRatingListener
    {

        private RatingScreenlet ratingScreenlet;

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.RatingView);

            ratingScreenlet = (RatingScreenlet)FindViewById(Resource.Id.rating_screenlet);

        }

        public void Error(Java.Lang.Exception p0, string p1)
        {
            System.Diagnostics.Debug.WriteLine($"Rating error: {p0}");
        }

		public void OnRatingOperationSuccess(AssetRating p0)
		{
			Toast.MakeText(this, "Rating success", ToastLength.Short).Show();
		}
    }
}
