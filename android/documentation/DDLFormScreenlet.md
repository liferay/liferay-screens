# DDLFormScreenlet for Android

## Important Note

*This product is under heavy development and its features aren't ready for use in production. It's being made public only to allow developers to preview the technology.*

## Requirements

- Android SDK 4.0 (API Level 14) and above
- Liferay Portal 6.2 CE or EE
- Mobile Widgets plugin installed

## Compatibility

- Android SDK 4.0 (API Level 14) and above

## Features

The `DDLFormScreenlet` can be used to show a collection of fields so that a user can fill in their values. Initial or existing values may be shown in the fields. Fields of the following data types are supported:

- *Boolean*: A two state value typically shown using a checkbox.
- *Date*: A formatted date value. The format will depend on the current device's locale.
- *Decimal, Integer, and Number*: A numeric value.
- *Document & Media*: A file stored on the current device. It can be uploaded to a specific portal repository.
- *Radio*: A set of options to choose from. A single option must be chosen. 
- *Select*: A dropdown list of options to choose from. A single option must be chosen.
- *Text*: A single line of text.
- *Text Areax*: Supports multiple lines of text.

The `DDLFormScreenlet` also supports the following features:

- Stored records can support a specific workflow.
- A Submit button can be shown at the end of the form.
- Required values and validation for fields can be used. 
- Users can traverse the form fields from the keyboard.
- Supports i18n in record values and labels.

There are also a few limitations that you should be aware of when using `DDLFormScreenlet`. They are listed here:

- Nested fields in the data definition aren't supported.
- Selection of multiple items in the Radio and Select data types isn't supported yet.

## Module

- DDL

## Views

The Default view uses a standard vertial `ScrollView` to show a scrollable list of fields. Other views may use a different component, such as `ViewPager` or others, to show the fields. You can find a sample of this implementation in the `DDLFormScreenletPagerView` class.

TODO image
![The `DDLForm` screenlet using Default theme.](Images/ddlform.png)

### Editor types

A field defines an editor type and you have to define the layout to be used for each editor type. These are defined in the next attributes:

  - *checkboxFieldLayoutId*: layout to be used for Boolean fields.
  - *dateFieldLayoutId*: layout to be used for Date fields.
  - *numberFieldLayoutId*: layout to be used for Number, Decimal or Integer fields.
  - *radioFieldLayoutId*: layout to be used for Radio fields.
  - *selectFieldLayoutId*: layout to be used for Select fields.
  - *textFieldLayoutId*: layout to be used for Text fields.
  - *textAreaFieldLayoutId*: layout to be used for Text Box fields.
  - *textDocumentFieldLayoutId*: layout to be used for Document & Media fields.

If you don't define the layout for these types in the attributes of `DDLFormScreent`, layout named `ddlfield_xxx_default` will be used, where `xxx` is the name of the editor type.
Anyway, at any point you'll be able to change the layout to be used in for any editor type setting any of those attributes.

### Custom editors

What if you want to have an unique appearance for one specific field? No problem! You can customize your field's editor view by calling the screenlet's method `setCustomFieldLayoutId(fieldName, layoutId)`, where the first paramater is the name of the field customize and the second the layout to be used. You can create easily custom editor views. For more details about this, check files `ddlfield_custom_rating_number.xml` and `CustomRatingNumberView.java` as an example of how to implement your own custom field views.

## Portal Configuration

Dynamic Data Lists and Data Types should be configured properly in the portal. Refer to the [Defining Data Types](https://dev.liferay.com/discover/portal/-/knowledge_base/6-2/building-a-list-platform-in-liferay-and-defining-data-) and [Creating Data Lists](https://dev.liferay.com/discover/portal/-/knowledge_base/6-2/creating-data-lists) sections of the User Guide for more details. If Workflow is required, it also must be configured. Please see the [Using Workflow](https://dev.liferay.com/discover/portal/-/knowledge_base/6-2/using-workflow) section of the User Guide for details.

### Permissions

To add new records using this screenlet, you must grant the Add Record permission in the used Dynamic Data List:

![The Add Record permission.](../../ios/Documentation/Images/portal-permission-record-add.png)

If you want to view or edit record values, you must also grant the View and Update permissions, respectively:

![The permissions for viewing and editing records.](../../ios/Documentation/Images/portal-permission-record-edit.png)

Also, if your form includes at least one Documents and Media field, you must grant permissions in the target repository and folder. For more details, see the `repositoryId` and `folderId` attributes below.

![The permission for adding a folder.](../../ios/Documentation/Images/portal-permission-folder-add.png)

For more details, please see the User Guide sections [Defining Data Types](https://dev.liferay.com/discover/portal/-/knowledge_base/6-2/building-a-list-platform-in-liferay-and-defining-data-), [Creating Data Lists](https://dev.liferay.com/discover/portal/-/knowledge_base/6-2/creating-data-lists), and [Using Workflow](https://dev.liferay.com/discover/portal/-/knowledge_base/6-2/using-workflow).

## Attributes

| Attribute | Data Type | Explanation |
|-----------|-----------|-------------| 
|  `layoutId` | `@layout` | The layout to be used to show the view.|
|  `checkboxFieldLayoutId ` | `@layout` | The layout to be used to show the view for Boolean fields.|
|  `dateFieldLayoutId ` | `@layout` | The layout to be used to show the view for Date fields.|
|  `numberFieldLayoutId ` | `@layout` | The layout to be used to show the view for Number, Decimal and Integer fields.|
|  `radioFieldLayoutId ` | `@layout` | The layout to be used to show the view for Radio fields.|
|  `selectFieldLayoutId ` | `@layout` | The layout to be used to show the view for Select fields.|
|  `textFieldLayoutId ` | `@layout` | The layout to be used to show the view for Text fields.|
|  `textAreaFieldLayoutId ` | `@layout` | The layout to be used to show the view for Text Box fields.|
|  `textDocumentFieldLayoutId ` | `@layout` | The layout to be used to show the view for Document & Media fields.|
| `structureId` | `number` | This is the identifier of a data definition for your site in Liferay. To find the identifiers for your data definitions, click *Admin* from the Dockbar and select *Content*. Then click *Dynamic Data Lists* and click the *Manage Data Definitions* button. The identifier of each data definition is in the ID column of the table that appears. |
| `groupId` | `number` | The site (group) identifier where the record is stored. If this value is `0`, the `groupId` specified in `LiferayServerContext` is used. |
| `recordSetId` | `number` | The identifier of a dynamic data list. To find the identifiers for your dynamic data lists, click *Admin* from the Dockbar and select *Content*. Then click *Dynamic Data Lists*. The identifier of each dynamic data list is in the ID column of the table that appears. |
|  `recordId` | `number` | The identifier of the record you want to show. You can also allow editing of its values. This identifier can be obtained from other methods or delegates. |
|  `repositoryId` | `number` | The identifier of the Documents and Media repository to upload to. If this value is `0`, the default repository for the site specified in `groupId` is used. |
|  `folderId` | `number` | The identifier of the folder where Documents and Media files are uploaded. If this value is `0`, the root file will be used. |
|  `filePrefix` | `string` | The prefix to attach to the names of files uploaded to a Documents and Media repository. The date of the upload followed by the original name of the file is appended following the prefix. |
|  `autoLoad` | `boolean` | Sets whether or not the form is loaded when the screenlet is shown. If `recordId` is set, the record value is loaded together with the form definition. Default value is `false`.|
|  `autoScrollOnValidation` | `boolean` | Sets whether or not the form automatically scrolls to the first failed field when validation is used. Default value is `true`.|
|  `showSubmitButton` | `boolean` | Sets whether or not the form shows a submit button at the bottom. If this is set to `false`, you should call the `submitForm()` method. Default value is `true`.|

## Methods

| Method | Return Type | Explanation |
|-----------|-----------|-------------| 
| `loadForm()` | `void` | Starts the request to load the form definition. The form fields are shown when the response is received.|
| `loadRecord()` | `void` | Starts the request to load the record specified in `recordId`. If needed, it will also load the form definition. The form fields are shown filled with record values when the response is received.|
| `load()` | `void` | Starts the request to load the record if `recordId` is specified or loads the form definition otherwise.|
| `submitForm()` | `void` | Starts the request to submit form values to the dynamic data list specified in `recordSetId`. If the record is new, it will add a new record. If it was loaded with `loadRecord` (or was already added), it will update its values. It will validate all the fields before and will show the validations errors detected stopping the submit process.|

## Listener

The `DDLFormScreenlet` delegates some events in an object that implements to the `DDLFormScreenletListener` interface. This interface lets you implement the following methods:

| Method | Explanation |
|-----------|-------------| 
|  <pre>onDDLFormLoaded(Record record)</pre> | Called when the form definition is successfully loaded.|
|  <pre>onDDLFormRecordLoaded(Record record)</pre> | Called when the form record data is successfully loaded. |
|  <pre>onDDLFormRecordAdded(Record record)</pre> | Called when the form record is successfully added. |
|  <pre>onDDLFormRecordUpdated(Record record)</pre> | Called when the form record data is successfully updated. |
|  <pre>onDDLFormLoadFailed(Exception e)</pre> | Called when an error occurs in the load form definition request.|
|  <pre>onDDLFormRecordLoadFailed(Exception e)</pre> | Called when an error occurs in the load form record request. |
|  <pre>onDDLFormRecordAddFailed(Exception e)</pre> | Called when an error occurs in the submit to add a new record request. |
|  <pre>onDDLFormUpdateRecordFailed(Exception e)</pre> | Called when an error occurs in the submit to update an existing record request. |
|  <pre>onDDLFormDocumentUploaded(DocumentField field)</pre> | Called when an upload has been completed for specified document field. |
|  <pre>onDDLFormDocumentUploadFailed(<br/>        DocumentField field,<br/>        Exception e)</pre> | Called when an upload is failed for specified document field. |
