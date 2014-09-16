/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
import XCTest

class DDLElementDocument_Tests: XCTestCase {

	let parser:DDLParser = DDLParser(locale:NSLocale(localeIdentifier: "es_ES"))

	override func setUp() {
		super.setUp()
	}

	override func tearDown() {
		super.tearDown()
	}

	func test_Parse_ShouldExtractValues() {
		parser.xml =
			"<root available-locales=\"en_US\" default-locale=\"en_US\"> " +
			"<dynamic-element dataType=\"document-library\" " +
				"indexType=\"keyword\" " +
				"name=\"A_Document\" " +
				"readOnly=\"false\" " +
				"repeatable=\"true\" " +
				"required=\"false\" " +
				"showLabel=\"true\" " +
				"type=\"ddm-documentlibrary\" " +
				"width=\"\"> " +
					"<meta-data locale=\"en_US\"> " +
						"<entry name=\"label\"><![CDATA[A Document]]></entry> " +
						"<entry name=\"predefinedValue\"><![CDATA[]]></entry> " +
					"</meta-data> " +
			"</dynamic-element> </root>"

		let elements = parser.parse()

		XCTAssertTrue(elements![0] is DDLElementDocument)
		let docElement = elements![0] as DDLElementDocument

		XCTAssertEqual(DDLElementDataType.DDLDocument, docElement.dataType)
		XCTAssertEqual(DDLElementEditor.Document, docElement.editorType)
		XCTAssertNil(docElement.predefinedValue)
		XCTAssertEqual("A_Document", docElement.name)
		XCTAssertEqual("A Document", docElement.label)
	}

	func test_Validate_ShouldFail_WhenRequiredValueIsNil() {
		parser.xml = requiredDocumentElement
		let elements = parser.parse()
		let docElement = elements![0] as DDLElementDocument

		XCTAssertTrue(docElement.currentValue == nil)
		XCTAssertFalse(docElement.validate())
	}

	func test_Validate_ShouldFail_WhenUploadFailed() {
		parser.xml = requiredDocumentElement
		let elements = parser.parse()
		let docElement = elements![0] as DDLElementDocument

		docElement.currentValue = UIImage(named:"default-field")
		docElement.uploadStatus = .Failed(NSError.errorWithDomain("", code: 0, userInfo:nil))

		XCTAssertFalse(docElement.validate())
	}

	func test_MimeType_ShouldReturnNil_WhenCurrentValueIsEmpty() {
		parser.xml = requiredDocumentElement
		let elements = parser.parse()
		let docElement = elements![0] as DDLElementDocument

		XCTAssertNil(docElement.mimeType)
	}

	func test_MimeType_ShouldReturnPNGImageType_WhenCurrentValueIsImage() {
		parser.xml = requiredDocumentElement
		let elements = parser.parse()
		let docElement = elements![0] as DDLElementDocument

		docElement.currentValue = UIImage(named:"default-field")

		XCTAssertEqual("image/png", docElement.mimeType ?? "nil mimeType")
	}

	func test_MimeType_ShouldReturnMPEGVideoType_WhenCurrentValueIsURL() {
		parser.xml = requiredDocumentElement
		let elements = parser.parse()
		let docElement = elements![0] as DDLElementDocument

		docElement.currentValue = NSURL(fileURLWithPath: "/this/is/a/path/to/video.mpg", isDirectory: false)

		XCTAssertEqual("video/mpeg", docElement.mimeType ?? "nil mimeType")
	}

	func test_Stream_ShouldReturnNil_WhenCurrentValueIsEmpty() {
		parser.xml = requiredDocumentElement
		let elements = parser.parse()
		let docElement = elements![0] as DDLElementDocument

		var size:Int64 = 0
		XCTAssertNil(docElement.getStream(&size))
		XCTAssertEqual(0, size)
	}

	func test_Stream_ShouldReturnStream_WhenCurrentValueIsImage() {
		parser.xml = requiredDocumentElement
		let elements = parser.parse()
		let docElement = elements![0] as DDLElementDocument

		let image = UIImage(named:"default-field")
		let imageBytes = UIImagePNGRepresentation(image)
		let imageLength = Int64(imageBytes.length)

		docElement.currentValue = image

		var size:Int64 = 0
		let stream = docElement.getStream(&size)
		XCTAssertNotNil(stream)
		XCTAssertEqual(imageLength, size)
	}

	func test_Stream_ShouldReturnStream_WhenCurrentValueIsURL() {
		parser.xml = requiredDocumentElement
		let elements = parser.parse()
		let docElement = elements![0] as DDLElementDocument

		let url = NSBundle(forClass: self.dynamicType).URLForResource("default-field", withExtension: "png")
		let attributes = NSFileManager.defaultManager().attributesOfItemAtPath(url?.path, error: nil)
		let imageLength = attributes![NSFileSize] as NSNumber

		docElement.currentValue = url

		var size:Int64 = 0
		let stream = docElement.getStream(&size)
		XCTAssertNotNil(stream)
		XCTAssertEqual(imageLength.longLongValue, size)
	}

	func test_CurrentDocumentLabel_ShouldReturnNil_WhenCurrentValueIsNil() {
		parser.xml = requiredDocumentElement
		let elements = parser.parse()
		let docElement = elements![0] as DDLElementDocument

		XCTAssertNil(docElement.currentDocumentLabel?)
	}

	func test_CurrentDocumentLabel_ShouldReturnImage_WhenCurrentValueIsImage() {
		parser.xml = requiredDocumentElement
		let elements = parser.parse()
		let docElement = elements![0] as DDLElementDocument

		docElement.currentValue = UIImage(named:"default-field")

		XCTAssertEqual("Image", docElement.currentDocumentLabel!)
	}

	func test_CurrentDocumentLabel_ShouldReturnVideo_WhenCurrentValueIsURL() {
		parser.xml = requiredDocumentElement
		let elements = parser.parse()
		let docElement = elements![0] as DDLElementDocument

		docElement.currentValue = NSURL(fileURLWithPath: "/this/is/a/path/to/video.mpg", isDirectory: false)

		XCTAssertEqual("Video", docElement.currentDocumentLabel!)
	}

	func test_CurrentStringValue_ShouldReturnNil_WhenUploadStatusIsPending() {
		parser.xml = requiredDocumentElement
		let elements = parser.parse()
		let docElement = elements![0] as DDLElementDocument

		docElement.uploadStatus = .Pending

		XCTAssertNil(docElement.currentStringValue?)
	}

	func test_CurrentStringValue_ShouldReturnNil_WhenUploadStatusIsUploading() {
		parser.xml = requiredDocumentElement
		let elements = parser.parse()
		let docElement = elements![0] as DDLElementDocument

		docElement.uploadStatus = .Uploading(1,10)

		XCTAssertNil(docElement.currentStringValue?)
	}

	func test_CurrentStringValue_ShouldReturnNil_WhenUploadStatusIsFailed() {
		parser.xml = requiredDocumentElement
		let elements = parser.parse()
		let docElement = elements![0] as DDLElementDocument

		docElement.uploadStatus = .Failed(nil)

		XCTAssertNil(docElement.currentStringValue?)
	}

	func test_CurrentStringValue_ShouldReturnDDLJSON_WhenUploadStatusIsUploaded() {
		parser.xml = requiredDocumentElement
		let elements = parser.parse()
		let docElement = elements![0] as DDLElementDocument

		let json:[String:AnyObject] = [
			"groupId": 1234,
			"uuid": "abcd",
			"version": "1.0",
			"blablabla": "blebleble"]

		docElement.uploadStatus = .Uploaded(json)

		let expectedResult = "{\"groupId\":1234,\"uuid\":\"abcd\",\"version\":\"1.0\"}"
		XCTAssertEqual(expectedResult, docElement.currentStringValue!)
	}


	private let requiredDocumentElement =
		"<root available-locales=\"en_US\" default-locale=\"en_US\"> " +
			"<dynamic-element dataType=\"document-library\" " +
				"indexType=\"keyword\" " +
				"name=\"A_Document\" " +
				"readOnly=\"false\" " +
				"repeatable=\"true\" " +
				"required=\"true\" " +
				"showLabel=\"true\" " +
				"type=\"ddm-documentlibrary\" " +
				"width=\"\"> " +
					"<meta-data locale=\"en_US\"> " +
						"<entry name=\"label\"><![CDATA[A Document]]></entry> " +
						"<entry name=\"predefinedValue\"><![CDATA[]]></entry> " +
					"</meta-data> " +
			"</dynamic-element> </root>"

}
