using System;
using UIKit;
using LiferayScreens;
using Foundation;

namespace ShowcaseiOS.ViewController
{
        public partial class RatingViewController : UIViewController, IRatingScreenletDelegate
        {
        public RatingViewController(IntPtr handle) : base(handle) { }

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();

            //FIXME: RatingScreenlet doesn't work with emojis view
            this.ratingScreenletStars.ClassName = LiferayServerContext.StringPropertyForKey("fileClassName");
            this.ratingScreenletStars.ClassPK = LiferayServerContext.LongPropertyForKey("ratingClassPK");
            this.ratingScreenletStars.ThemeName = LiferayServerContext.StringPropertyForKey("ratingThemeName");
            this.ratingScreenletStars.Editable = true;

            this.ratingScreenletStars.Delegate = this;
        }

        /* IRatingScreenletDelegate */

        [Export("screenlet:onRatingDeleted:")]
        public virtual void OnRatingDeleted(RatingScreenlet screenlet, RatingEntry rating)
        {
            Console.WriteLine("Rating delete");
        }

        [Export("screenlet:onRatingError:")]
        public virtual void OnRatingError(RatingScreenlet screenlet, NSError error)
        {
            Console.WriteLine($"Rating error: {error.Description}");
        }

        [Export("screenlet:onRatingRetrieve:")]
        public virtual void OnRatingRetrieve(RatingScreenlet screenlet, RatingEntry rating)
        {
            Console.WriteLine($"Rating retrive: {rating.Attributes}");
        }

        [Export("screenlet:onRatingUpdated:")]
        public virtual void OnRatingUpdated(RatingScreenlet screenlet, RatingEntry rating)
        {
            Console.WriteLine($"Rating updated: {rating.Average}");
        }
    }
}
