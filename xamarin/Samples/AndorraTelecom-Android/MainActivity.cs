using Android.App;
using Android.OS;

namespace AndorraTelecomAndroid
{
    [Activity(MainLauncher = true, Icon = "@mipmap/icon")]
    public class MainActivity : Activity
    {
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.Main);
        }
    }
}

