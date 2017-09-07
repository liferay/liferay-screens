using Foundation;
using ObjCRuntime;

namespace LiferayScreens
{
    interface IRatingViewModel {}

    // @protocol RatingViewModel
    [Protocol, Model]
    interface RatingViewModel
    {
        // @required @property (readonly, nonatomic) int32_t defaultRatingsGroupCount;
        [Abstract]
        [Export("defaultRatingsGroupCount")]
        int DefaultRatingsGroupCount { get; }

        // @required @property (nonatomic, strong) RatingEntry * _Nullable ratingEntry;
        [Abstract]
        [NullAllowed, Export("ratingEntry", ArgumentSemantic.Strong)]
        RatingEntry RatingEntry { get; set; }
    }
}
