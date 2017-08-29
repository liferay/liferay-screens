using CoreGraphics;
using Foundation;
using System;

namespace LiferayScreens
{
    // @interface DDLFormView_default : DDLFormTableView
    [BaseType(typeof(DDLFormTableView))]
    interface DDLFormView_default
    {
        // -(void)onCreated;
        [Export("onCreated")]
        void OnCreated();

        // -(void)onFinishInteraction:(id _Nullable)result error:(NSError * _Nullable)error;
        [Export("onFinishInteraction:error:")]
        void OnFinishInteraction([NullAllowed] NSObject result, [NullAllowed] NSError error);

        // -(id<ProgressPresenter> _Nonnull)createProgressPresenter __attribute__((warn_unused_result));
        [Export("createProgressPresenter")]
        ProgressPresenter CreateProgressPresenter();

        // -(void)changeEditable:(BOOL)editable;
        [Export("changeEditable:")]
        void ChangeEditable(bool editable);

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame __attribute__((objc_designated_initializer));
        [Export("initWithFrame:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame);
    }
}
