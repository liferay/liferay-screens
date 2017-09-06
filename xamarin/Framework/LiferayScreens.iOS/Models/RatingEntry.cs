using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface RatingEntry : NSObject <NSCoding>
    [BaseType(typeof(NSObject))]
    [DisableDefaultCtor]
    interface RatingEntry : INSCoding
    {
        // @property (readonly, copy, nonatomic) NSDictionary<NSString *,id> * _Nonnull attributes;
        [Export("attributes", ArgumentSemantic.Copy)]
        NSDictionary<NSString, NSObject> Attributes { get; }

        // @property (readonly, nonatomic) NSInteger totalCount;
        [Export("totalCount")]
        nint TotalCount { get; }

        // @property (readonly, nonatomic) double average;
        [Export("average")]
        double Average { get; }

        // @property (readonly, nonatomic) double userScore;
        [Export("userScore")]
        double UserScore { get; }

        // @property (readonly, nonatomic) int64_t classPK;
        [Export("classPK")]
        long ClassPK { get; }

        // @property (readonly, copy, nonatomic) NSString * _Nonnull className;
        [Export("className")]
        string ClassName { get; }

        // @property (readonly, copy, nonatomic) NSArray<NSNumber *> * _Nonnull ratings;
        [Export("ratings", ArgumentSemantic.Copy)]
        NSNumber[] Ratings { get; }

        // -(void)encodeWithCoder:(NSCoder * _Nonnull)aCoder;
        [Export("encodeWithCoderRatingEntry:")]
        void EncodeWithCoder(NSCoder aCoder);

        // -(instancetype _Nonnull)initWithAttributes:(NSDictionary<NSString *,id> * _Nonnull)attributes __attribute__((objc_designated_initializer));
        [Export("initWithAttributes:")]
        [DesignatedInitializer]
        IntPtr Constructor(NSDictionary<NSString, NSObject> attributes);
    }
}
