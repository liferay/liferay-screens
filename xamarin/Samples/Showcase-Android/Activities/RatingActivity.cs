using Android.App;
using Android.OS;
using Android.Widget;
using Com.Liferay.Mobile.Screens.Rating;

namespace ShowcaseAndroid
{
    [Activity]
    public class RatingActivity : Activity, IRatingListener
    {
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.RatingView);

            RatingScreenlet ratingScreenlet = 
                (RatingScreenlet) FindViewById(Resource.Id.rating_screenlet);
            ratingScreenlet.Listener = this;

        }

        /* IRatingListener */

        public void Error(Java.Lang.Exception p0, string p1)
        {
            Android.Util.Log.Debug("RatingScreenlet", $"Rating error: {p0.Message}");
        }

        public void OnRatingOperationSuccess(AssetRating p0)
        {
        	Toast.MakeText(this, "Rating success", ToastLength.Short).Show();
        }
    }
}
