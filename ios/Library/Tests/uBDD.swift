/*
The MIT License (MIT)

Copyright (c) 2015 Jose Manuel Navarro

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
import UIKit
import XCTest


private var currentIndentationLevel = 1


public enum Action {
	case TestNow
	case Pending
	case Skip
	case TestAndWaitFor(String, XCTestCase)
}


public func given(givenStr: String, givenCode: Void -> Void,
	when whenStr: String, whenCode: Void -> Void,
	eventually eventuallyStr: String, eventuallyCode: AnyObject? -> Void,
	action: Action,
	timeoutSeconds: NSTimeInterval = 5) {

	let icons = currentIcons()
	let indentation = currentIndentation()

	switch action {
		case .TestAndWaitFor(let notificationName, let testCase):
			let expectation = testCase.expectationWithDescription("")

			var signaled = false
			var resultObject: AnyObject?

			let observer = NSNotificationCenter.defaultCenter().addObserverForName(notificationName,
					object: nil,
					queue: NSOperationQueue.mainQueue(),  // why is this working using main queue?
					usingBlock: { notif in

				signaled = true
				resultObject = notif.object

				expectation.fulfill()
			})

			print("\(indentation)\(icons.given) Given \(givenStr)\n")
			givenCode()

			print("\(indentation)\(icons.when) When \(whenStr)\n")
			whenCode()

			print("\(indentation)\(icons.eventually) Eventually \(eventuallyStr)\n")

			testCase.waitForExpectationsWithTimeout(timeoutSeconds, handler: nil)

			if signaled {
				SwiftTryCatch.try({
					eventuallyCode(resultObject)
					print("\(indentation)\(icons.passed) PASSED\n")
				}, catch: { error in
					print("\(indentation)\(icons.failed) FAILED\n")
				}, finally: nil)
			}
			else {
				print("\(indentation)\(icons.failed) FAILED (timeout)\n")
			}

			NSNotificationCenter.defaultCenter().removeObserver(observer)

		default:
			given(givenStr, givenCode,
				when:whenStr, whenCode,
				then:eventuallyStr, {
					eventuallyCode(nil)
				}, action)
	}
}

public func given(givenStr: String, givenCode: Void -> Void,
	when whenStr: String, whenCode: Void -> Void,
	then thenStr: String, thenCode: Void -> Void,
	_ action: Action = .TestNow) {

	let icons = currentIcons()
	let indentation = currentIndentation()

	func fullHeader() {
		print("\(indentation)\(icons.given) Given \(givenStr)\n")
		print("\(indentation)\(icons.when) When \(whenStr)\n")
		print("\(indentation)\(icons.then) Then \(thenStr)\n")
	}

	switch action {
		case .TestAndWaitFor:
			println("ERROR: TestAndWaitFor is not supported with given.when.then. Use given.when.eventually instead")
		case .Skip:
			fullHeader()
			print("\(indentation)\(icons.skipped) SKIPPED\n")
		case .Pending:
			fullHeader()
			print("\(indentation)\(icons.pending) PENDING\n")
		case .TestNow:
			print("\(indentation)\(icons.given) Given \(givenStr)\n")
			givenCode()

			print("\(indentation)\(icons.when) When \(whenStr)\n")
			whenCode()

			print("\(indentation)\(icons.then) Then \(thenStr)\n")

			SwiftTryCatch.try({
				thenCode()
				print("\(indentation)\(icons.passed) PASSED\n")
			}, catch: { error in
				print("\(indentation)\(icons.failed) FAILED\n")
			}, finally: nil)
	}
}

public func completed(notificationName: String, withResult result: AnyObject?) {
	NSNotificationCenter.defaultCenter().postNotificationName(notificationName, object: result)
}

public func scenario(scenario: String, code:Void->Void) {
	print("\(currentIndentation())\(currentIcons().scenario) \(scenario)\n")

	currentIndentationLevel++
	code()
	currentIndentationLevel--
}

public func with(text: String, code: Void -> Void) {
	println("\(indentation(currentIndentationLevel + 1))\(currentIcons().with) with \(text)")
	code()
}

public func that(text: String, code: Void -> Void) {
	println("\(indentation(currentIndentationLevel + 1))\(currentIcons().that) that \(text)")
	code()
}

public func and(text: String, code: Void -> Void) {
	println("\(indentation(currentIndentationLevel + 1))\(currentIcons().and) and \(text)")
	code()
}

public func it(text: String, code: Void -> Void) {
	println("\(indentation(currentIndentationLevel + 1))\(currentIcons().and) it \(text)")
	code()
}

public func perform(text: String, code: Void -> Void) {
	println("\(indentation(currentIndentationLevel + 1))\(currentIcons().and) perform \(text)")
	code()
}


public func assertThat(text: String, code: Void -> Void) {
	SwiftTryCatch.try({
		code()
		println("\(indentation(currentIndentationLevel + 1))\(currentIcons().assertPassed) assert that \(text)")
	}, catch: { error in
		println("\(indentation(currentIndentationLevel + 1))\(currentIcons().assertFailed) assert that \(text)")
		SwiftTryCatch.throwException(error)
	}, finally: nil)
}

private func currentIndentation() -> String {
	return indentation(currentIndentationLevel)
}

private func indentation(level:Int) -> String {
	return String(count: level, repeatedValue:Character("\t"))
}


public typealias Icons =
	(passed: Character,
	failed: Character,
	given: Character,
	when: Character,
	then: Character,
	eventually: Character,
	timeout: Character,
	skipped: Character,
	pending: Character,
	scenario: Character,
	with: Character,
	that: Character,
	and: Character,
	assertPassed: Character,
	assertFailed: Character)

public var currentIcons = simpleIcons

func simpleIcons() -> Icons {
	return (
		passed: "✓",
		failed: "✘",
		given: "-",
		when: "-",
		then: "-",
		eventually: "-",
		timeout: "✘",
		skipped: "!",
		pending: "?",
		scenario: "*",
		with: "·",
		that: "·",
		and: "·",
		assertPassed: "✓",
		assertFailed: "✘")
}

func emojiIcons() -> Icons {
	return (
		passed: "✅",
		failed: "❌",
		given: "➜",
		when: "➜",
		then: "➜",
		eventually: "➜",
		timeout: "❌",
		skipped: "☔️",
		pending: "⚠️",
		scenario: "➜",
		with: "✓",
		that: "✓",
		and: "✓",
		assertPassed: "✓",
		assertFailed: "✘")
}

func funnyEmojiIcons() -> Icons {
	return (
		passed: "👍",
		failed: "💩",
		given: "👉",
		when: "👉",
		then: "👉",
		eventually: "👉",
		timeout: "💤",
		skipped: "🔕",
		pending: "🔜",
		scenario: "👉",
		with: "📍",
		that: "📍",
		and: "📍",
		assertPassed: "😄",
		assertFailed: "😱")
}
