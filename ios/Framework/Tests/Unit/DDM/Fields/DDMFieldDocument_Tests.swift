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
import UIKit

class DDMFieldDocument_Tests: XCTestCase {

	fileprivate let spanishLocale = Locale(identifier: "es_ES")

	// MARK: Parse

	func test_XSDParse_ShouldExtractValues() {
		let xsd =
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

		let fields = DDMXSDParser().parse(xsd, locale: spanishLocale)

		XCTAssertTrue(fields![0] is DDMFieldDocument)
		let docField = fields![0] as! DDMFieldDocument

		XCTAssertEqual(DDMField.DataType.ddmDocument, docField.dataType)
		XCTAssertEqual(DDMField.Editor.document, docField.editorType)
		XCTAssertNil(docField.predefinedValue)
		XCTAssertEqual("A_Document", docField.name)
		XCTAssertEqual("A Document", docField.label)
	}

	func test_JSONParse_ShouldExtractValues() {
		let json = "{\"availableLanguageIds\": [\"en_US\"]," +
			"\"defaultLanguageId\": \"en_US\"," +
			"\"fields\": [{" +
			"\"label\": {\"en_US\": \"A Document\"}," +
			"\"predefinedValue\": {\"en_US\": \"\"}," +
			"\"dataType\": \"document-library\"," +
			"\"indexType\": \"keyword\"," +
			"\"localizable\": true," +
			"\"name\": \"A_Document\"," +
			"\"readOnly\": false," +
			"\"repeatable\": true," +
			"\"required\": false," +
			"\"showLabel\": true," +
			"\"fieldNamespace\": \"ddm\"," +
		"\"type\": \"ddm-documentlibrary\"}]}"

		let fields = DDMJSONParser().parse(json, locale: spanishLocale)

		XCTAssertTrue(fields![0] is DDMFieldDocument)
		let docField = fields![0] as! DDMFieldDocument

		XCTAssertEqual(DDMField.DataType.ddmDocument, docField.dataType)
		XCTAssertEqual(DDMField.Editor.document, docField.editorType)
		XCTAssertNil(docField.predefinedValue)
		XCTAssertEqual("A_Document", docField.name)
		XCTAssertEqual("A Document", docField.label)
	}

	// MARK: Validate

	func test_Validate_ShouldFail_WhenRequiredValueIsNil() {
		let fields = DDMXSDParser().parse(requiredDocumentElementXSD, locale: spanishLocale)
		let docField = fields![0] as! DDMFieldDocument

		XCTAssertTrue(docField.currentValue == nil)
		XCTAssertFalse(docField.validate())
	}

	func test_Validate_ShouldFail_WhenUploadFailed() {
		let fields = DDMXSDParser().parse(requiredDocumentElementXSD, locale: spanishLocale)
		let docField = fields![0] as! DDMFieldDocument

		docField.currentValue = UIImage(contentsOfFile: testResourcePath("tinyImage", ext: "png"))
		docField.uploadStatus = .failed(NSError(domain: "", code: 0, userInfo: nil))

		XCTAssertFalse(docField.validate())
	}

	// MARK: MimeType

	func test_MimeType_ShouldReturnNil_WhenCurrentValueIsEmpty() {
		let fields = DDMXSDParser().parse(requiredDocumentElementXSD, locale: spanishLocale)
		let docField = fields![0] as! DDMFieldDocument

		XCTAssertNil(docField.mimeType)
	}

	func test_MimeType_ShouldReturnPNGImageType_WhenCurrentValueIsImage() {
		let fields = DDMXSDParser().parse(requiredDocumentElementXSD, locale: spanishLocale)
		let docField = fields![0] as! DDMFieldDocument

		docField.currentValue = UIImage(contentsOfFile: testResourcePath("tinyImage", ext: "png"))

		XCTAssertEqual("image/png", docField.mimeType ?? "nil mimeType")
	}

	func test_MimeType_ShouldReturnMPEGVideoType_WhenCurrentValueIsURL() {
		let fields = DDMXSDParser().parse(requiredDocumentElementXSD, locale: spanishLocale)
		let docField = fields![0] as! DDMFieldDocument

		docField.currentValue = URL(fileURLWithPath: "/this/is/a/path/to/video.mpg", isDirectory: false)

		XCTAssertEqual("video/mpeg", docField.mimeType ?? "nil mimeType")
	}

	// MARK: Stream

	func test_Stream_ShouldReturnNil_WhenCurrentValueIsEmpty() {
		let fields = DDMXSDParser().parse(requiredDocumentElementXSD, locale: spanishLocale)
		let docField = fields![0] as! DDMFieldDocument

		var size: Int64 = 0
		XCTAssertNil(docField.getStream(&size))
		XCTAssertEqual(Int64(0), size)
	}

	func test_Stream_ShouldReturnStream_WhenCurrentValueIsImage() {
		let fields = DDMXSDParser().parse(requiredDocumentElementXSD, locale: spanishLocale)
		let docField = fields![0] as! DDMFieldDocument

		let image = UIImage(contentsOfFile: testResourcePath("tinyImage", ext: "png"))
		let imageBytes = image!.pngData()
		let imageLength = Int64(imageBytes!.count)

		docField.currentValue = image

		var size: Int64 = 0
		let stream = docField.getStream(&size)
		XCTAssertNotNil(stream)
		XCTAssertEqual(imageLength, size)
	}

	func test_Stream_ShouldReturnStream_WhenCurrentValueIsURL() {
		let fields = DDMXSDParser().parse(requiredDocumentElementXSD, locale: spanishLocale)
		let docField = fields![0] as! DDMFieldDocument

		let url = Bundle(for: type(of: self)).url(forResource: "tinyImage", withExtension: "png")
		let attributes =
				try? FileManager.default.attributesOfItem(atPath: url!.path)
		let imageLength = attributes![FileAttributeKey.size] as! NSNumber

		docField.currentValue = url

		var size: Int64 = 0
		let stream = docField.getStream(&size)
		XCTAssertNotNil(stream)
		XCTAssertEqual(imageLength.int64Value, size)
	}

	// MARK: currentValueAsLabel

	func test_CurrentValueAsLabel_ShouldReturnNil_WhenCurrentValueIsNil() {
		let fields = DDMXSDParser().parse(requiredDocumentElementXSD, locale: spanishLocale)
		let docField = fields![0] as! DDMFieldDocument

		XCTAssertNil(docField.currentValueAsLabel)
	}

	func test_CurrentValueAsLabel_ShouldReturnImage_WhenCurrentValueIsImage() {
		let fields = DDMXSDParser().parse(requiredDocumentElementXSD, locale: spanishLocale)
		let docField = fields![0] as! DDMFieldDocument

		docField.currentValue = UIImage(contentsOfFile: testResourcePath("tinyImage", ext: "png"))

		XCTAssertEqual("An image has been selected", docField.currentValueAsLabel!)
	}

	func test_CurrentValueAsLabel_ShouldReturnVideo_WhenCurrentValueIsURL() {
		let fields = DDMXSDParser().parse(requiredDocumentElementXSD, locale: spanishLocale)
		let docField = fields![0] as! DDMFieldDocument

		docField.currentValue =
				URL(fileURLWithPath: "/this/is/a/path/to/video.mpg", isDirectory: false)

		XCTAssertEqual("A video has been selected", docField.currentValueAsLabel!)
	}

	// MARK: CurrentValueAsString

	func test_CurrentValueAsString_ShouldReturnNil_WhenUploadStatusIsPending() {
		let fields = DDMXSDParser().parse(requiredDocumentElementXSD, locale: spanishLocale)
		let docField = fields![0] as! DDMFieldDocument

		docField.uploadStatus = .pending

		XCTAssertNil(docField.currentValueAsString)
	}

	func test_CurrentValueAsString_ShouldReturnNil_WhenUploadStatusIsUploading() {
		let fields = DDMXSDParser().parse(requiredDocumentElementXSD, locale: spanishLocale)
		let docField = fields![0] as! DDMFieldDocument

		docField.uploadStatus = .uploading(1, 10)

		XCTAssertNil(docField.currentValueAsString)
	}

	func test_CurrentValueAsString_ShouldReturnNil_WhenUploadStatusIsFailed() {
		let fields = DDMXSDParser().parse(requiredDocumentElementXSD, locale: spanishLocale)
		let docField = fields![0] as! DDMFieldDocument

		docField.uploadStatus = .failed(nil)

		XCTAssertNil(docField.currentValueAsString)
	}

	func test_CurrentValueAsString_ShouldReturnDDLJSON_WhenUploadStatusIsUploaded() {
		let fields = DDMXSDParser().parse(requiredDocumentElementXSD, locale: spanishLocale)
		let docField = fields![0] as! DDMFieldDocument

		let json: [String: AnyObject] = [
			"groupId": 1234 as AnyObject,
			"uuid": "abcd" as AnyObject,
			"version": "1.0" as AnyObject,
			"blablabla": "blebleble" as AnyObject]

		docField.uploadStatus = .uploaded(json)

		let expectedResult = "{\"groupId\":1234,\"uuid\":\"abcd\",\"version\":\"1.0\"}"
		XCTAssertEqual(expectedResult, docField.currentValueAsString!)
	}

	// MARK: UploadStatus

	func test_UploadStatus_ShouldBeUploaded_WhenSetJSONTocurrentValueAsString() {
		let fields = DDMXSDParser().parse(requiredDocumentElementXSD, locale: spanishLocale)
		let docField = fields![0] as! DDMFieldDocument

		let json = "{\"groupId\":1234,\"uuid\":\"abcd\",\"version\":\"1.0\"}"

		docField.currentValueAsString = json

		switch docField.uploadStatus {
		case .uploaded(let uploadedAttributes):
			let expectedJson: [String: AnyObject] = [
					"groupId": 1234 as AnyObject,
					"uuid": "abcd" as AnyObject,
					"version": "1.0" as AnyObject]
			for (k, v) in expectedJson {
				XCTAssertEqual("\(v)", "\(uploadedAttributes[k]!)")
			}

		default:
			XCTFail("Upload status is expected to be 'Uploaded'")
		}
	}

	fileprivate let requiredDocumentElementXSD =
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
