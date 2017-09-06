using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface Comment : NSObject <NSCoding>
    [BaseType(typeof(NSObject))]
    [DisableDefaultCtor]
    interface Comment : INSCoding
    {
        // +(NSString * _Nonnull)plainBodyToHtml:(NSString * _Nonnull)plainBody __attribute__((warn_unused_result));
        [Static]
        [Export("plainBodyToHtml:")]
        string PlainBodyToHtml(string plainBody);

        // @property (readonly, copy, nonatomic) NSDictionary<NSString *,id> * _Nonnull attributes;
        [Export("attributes", ArgumentSemantic.Copy)]
        NSDictionary<NSString, NSObject> Attributes { get; }

        // @property (readonly, copy, nonatomic) NSString * _Nonnull originalBody;
        [Export("originalBody")]
        string OriginalBody { get; }

        // @property (readonly, copy, nonatomic) NSString * _Nonnull plainBody;
        [Export("plainBody")]
        string PlainBody { get; }

        // @property (readonly, copy, nonatomic) NSString * _Nonnull htmlBody;
        [Export("htmlBody")]
        string HtmlBody { get; }

        // @property (readonly, nonatomic) BOOL isStyled;
        [Export("isStyled")]
        bool IsStyled { get; }

        // @property (readonly, nonatomic) int64_t commentId;
        [Export("commentId")]
        long CommentId { get; }

        // @property (readonly, copy, nonatomic) NSString * _Nonnull userName;
        [Export("userName")]
        string UserName { get; }

        // @property (readonly, nonatomic) int64_t userId;
        [Export("userId")]
        long UserId { get; }

        // @property (readonly, copy, nonatomic) NSDate * _Nonnull createDate;
        [Export("createDate", ArgumentSemantic.Copy)]
        NSDate CreateDate { get; }

        // @property (readonly, copy, nonatomic) NSDate * _Nonnull modifiedDate;
        [Export("modifiedDate", ArgumentSemantic.Copy)]
        NSDate ModifiedDate { get; }

        // @property (readonly, nonatomic) BOOL canDelete;
        [Export("canDelete")]
        bool CanDelete { get; }

        // @property (readonly, nonatomic) BOOL canEdit;
        [Export("canEdit")]
        bool CanEdit { get; }

        // -(void)encodeWithCoder:(NSCoder * _Nonnull)aCoder;
        [Export("encodeWithCoderComment:")]
        void EncodeWithCoder(NSCoder aCoder);

        // -(instancetype _Nonnull)initWithAttributes:(NSDictionary<NSString *,id> * _Nonnull)attributes __attribute__((objc_designated_initializer));
        [Export("initWithAttributes:")]
        [DesignatedInitializer]
        IntPtr Constructor(NSDictionary<NSString, NSObject> attributes);
    }
}
