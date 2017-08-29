using CoreGraphics;
using Foundation;
using ObjCRuntime;
using System;

namespace LiferayScreens
{
    // @interface DDLFormScreenlet : BaseScreenlet
    [BaseType(typeof(BaseScreenlet))]
    interface DDLFormScreenlet
    {
        // @property (readonly, copy, nonatomic, class) NSString * _Nonnull LoadFormAction;
        [Static]
        [Export("LoadFormAction")]
        string LoadFormAction { get; }

        // @property (readonly, copy, nonatomic, class) NSString * _Nonnull LoadRecordAction;
        [Static]
        [Export("LoadRecordAction")]
        string LoadRecordAction { get; }

        // @property (readonly, copy, nonatomic, class) NSString * _Nonnull SubmitFormAction;
        [Static]
        [Export("SubmitFormAction")]
        string SubmitFormAction { get; }

        // @property (readonly, copy, nonatomic, class) NSString * _Nonnull UploadDocumentAction;
        [Static]
        [Export("UploadDocumentAction")]
        string UploadDocumentAction { get; }

        // @property (nonatomic) int64_t structureId;
        [Export("structureId")]
        long StructureId { get; set; }

        // @property (nonatomic) int64_t groupId;
        [Export("groupId")]
        long GroupId { get; set; }

        // @property (nonatomic) int64_t recordSetId;
        [Export("recordSetId")]
        long RecordSetId { get; set; }

        // @property (nonatomic) int64_t recordId;
        [Export("recordId")]
        long RecordId { get; set; }

        // @property (nonatomic) int64_t userId;
        [Export("userId")]
        long UserId { get; set; }

        // @property (nonatomic) int64_t repositoryId;
        [Export("repositoryId")]
        long RepositoryId { get; set; }

        // @property (nonatomic) int64_t folderId;
        [Export("folderId")]
        long FolderId { get; set; }

        // @property (copy, nonatomic) NSString * _Nonnull filePrefix;
        [Export("filePrefix")]
        string FilePrefix { get; set; }

        // @property (nonatomic) BOOL autoLoad;
        [Export("autoLoad")]
        bool AutoLoad { get; set; }

        // @property (nonatomic) BOOL autoscrollOnValidation;
        [Export("autoscrollOnValidation")]
        bool AutoscrollOnValidation { get; set; }

        // @property (nonatomic) BOOL showSubmitButton;
        [Export("showSubmitButton")]
        bool ShowSubmitButton { get; set; }

        // @property (nonatomic) BOOL editable;
        [Export("editable")]
        bool Editable { get; set; }

        // @property (copy, nonatomic) NSString * _Nullable offlinePolicy;
        [NullAllowed, Export("offlinePolicy")]
        string OfflinePolicy { get; set; }

        [Wrap("WeakDdlFormDelegate")]
        [NullAllowed]
        DDLFormScreenletDelegate DdlFormDelegate { get; }

        // @property (readonly, nonatomic, strong) id<DDLFormScreenletDelegate> _Nullable ddlFormDelegate;
        [NullAllowed, Export("ddlFormDelegate", ArgumentSemantic.Strong)]
        NSObject WeakDdlFormDelegate { get; }

        // @property (readonly, nonatomic, strong) id<DDLFormViewModel> _Nonnull viewModel;
        [Export("viewModel", ArgumentSemantic.Strong)]
        IDDLFormViewModel ViewModel { get; }

        // @property (readonly, nonatomic) BOOL isFormLoaded;
        [Export("isFormLoaded")]
        bool IsFormLoaded { get; }

        // -(void)onCreated;
        [Export("onCreated")]
        void OnCreated();

        // -(void)onShow;
        [Export("onShow")]
        void OnShow();

        // -(Interactor * _Nullable)createInteractorWithName:(NSString * _Nonnull)name sender:(id _Nullable)sender __attribute__((warn_unused_result));
        [Export("createInteractorWithName:sender:")]
        [return: NullAllowed]
        Interactor CreateInteractorWithName(string name, [NullAllowed] NSObject sender);

        // -(BOOL)onActionWithName:(NSString * _Nonnull)name interactor:(Interactor * _Nonnull)interactor sender:(id _Nullable)sender __attribute__((warn_unused_result));
        [Export("onActionWithName:interactor:sender:")]
        bool OnActionWithName(string name, Interactor interactor, [NullAllowed] NSObject sender);

        // -(BOOL)loadForm;
        [Export("loadForm")]
        bool LoadForm();

        // -(void)clearForm;
        [Export("clearForm")]
        void ClearForm();

        // -(BOOL)loadRecord;
        [Export("loadRecord")]
        bool LoadRecord();

        // -(BOOL)submitForm;
        [Export("submitForm")]
        bool SubmitForm();

        // -(instancetype _Nonnull)initWithFrame:(CGRect)frame themeName:(NSString * _Nullable)themeName __attribute__((objc_designated_initializer));
        [Export("initWithFrame:themeName:")]
        [DesignatedInitializer]
        IntPtr Constructor(CGRect frame, [NullAllowed] string themeName);
    }
}