using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @protocol RatingScreenletDelegate <BaseScreenletDelegate>
    [BaseType(typeof(BaseScreenletDelegate))]
    [Protocol, Model]
    interface RatingScreenletDelegate
    {
        // @optional -(void)screenlet:(RatingScreenlet * _Nonnull)screenlet onRatingRetrieve:(RatingEntry * _Nonnull)rating;
        [Export("screenlet:onRatingRetrieve:")]
        void OnRatingRetrieve(RatingScreenlet screenlet, RatingEntry rating);

        // @optional -(void)screenlet:(RatingScreenlet * _Nonnull)screenlet onRatingDeleted:(RatingEntry * _Nonnull)rating;
        [Export("screenlet:onRatingDeleted:")]
        void OnRatingDeleted(RatingScreenlet screenlet, RatingEntry rating);

        // @optional -(void)screenlet:(RatingScreenlet * _Nonnull)screenlet onRatingUpdated:(RatingEntry * _Nonnull)rating;
        [Export("screenlet:onRatingUpdated:")]
        void OnRatingUpdated(RatingScreenlet screenlet, RatingEntry rating);

        // @optional -(void)screenlet:(RatingScreenlet * _Nonnull)screenlet onRatingError:(NSError * _Nonnull)error;
        [Export("screenlet:onRatingError:")]
        void OnRatingError(RatingScreenlet screenlet, NSError error);
    }
}
