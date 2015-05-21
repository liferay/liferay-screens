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
import SwiftTryCatch


private var currentIndentationLevel = 1

private var lastDoneEvent: (name: String, result: AnyObject?)?


public enum Action {
	case TestNow
	case Pending
	case Skip
	case TestAndWaitFor(String, XCTestCase)
}

public func given(str: String, code: Void -> Void) {
	doPrint("\(currentIndentation())\(currentIcons().given) Given \(str)\n")
	code()
}

public func when(str: String, code: Void -> Void) {
	doPrint("\(currentIndentation())\(currentIcons().when) When \(str)\n")
	code()
}

public func then(str: String, code: Void -> Void) {
	then(str, code, .TestNow)
}

public func then(str: String, code: Void -> Void, action: Action) {
	let icons = currentIcons()
	let indentation = currentIndentation()

	doPrint("\(indentation)\(icons.then) Then \(str)\n")

	switch action {
		case .TestAndWaitFor:
			doPrint("ERROR: TestAndWaitFor is not supported with given.when.then. Use given.when.eventually instead\n")
		case .Skip:
			doPrint("\(indentation)\(icons.skipped) SKIPPED\n")
		case .Pending:
			doPrint("\(indentation)\(icons.pending) PENDING\n")
		case .TestNow:
			SwiftTryCatch.try({
				code()
				doPrint("\(indentation)\(icons.passed) PASSED\n")
			}, catch: { error in
				doPrint("\(indentation)\(icons.failed) FAILED\n")
			}, finally: nil)
	}
}

public func eventually(str: String, code: AnyObject? -> Void, action: Action) {
	let icons = currentIcons()
	let indentation = currentIndentation()

	switch action {
		case .TestAndWaitFor(let notificationName, let testCase):
			if lastDoneEvent?.name == notificationName {
				// already called "done"
				SwiftTryCatch.try({
					code(lastDoneEvent?.result)
					doPrint("\(indentation)\(icons.passed) PASSED\n")
				}, catch: { error in
					doPrint("\(indentation)\(icons.failed) FAILED\n")
				}, finally: nil)
			}
			else {
				let expectation = testCase.expectationWithDescription("")

				var signaled = false

				let observer = NSNotificationCenter.defaultCenter().addObserverForName(notificationName,
						object: nil,
						// why is this working using main queue?
						queue: NSOperationQueue.mainQueue(),
						usingBlock: { notif in

					SwiftTryCatch.try({
						code(notif.object)
						doPrint("\(indentation)\(icons.passed) PASSED\n")
					}, catch: { error in
						doPrint("\(indentation)\(icons.failed) FAILED\n")
					}, finally: nil)

					signaled = true
					expectation.fulfill()
				})

				doPrint("\(indentation)\(icons.eventually) Eventually \(str)\n")

				testCase.waitForExpectationsWithTimeout(5, handler: nil)

				if !signaled {
					doPrint("\(indentation)\(icons.failed) FAILED (timeout)\n")
				}

				NSNotificationCenter.defaultCenter().removeObserver(observer)
			}

			lastDoneEvent = nil
		default:
			then(str, {
				code(nil)
			}, action)
	}
}

public func done(notificationName: String, withResult result: AnyObject?) {
	lastDoneEvent = (notificationName, result)
	NSNotificationCenter.defaultCenter().postNotificationName(notificationName, object: result)
}

public func scenario(scenario: String, code:Void->Void) {
	doPrint("\(currentIndentation())\(currentIcons().scenario) \(scenario)\n")

	currentIndentationLevel++
	code()
	currentIndentationLevel--
}

public func with(text: String, code: Void -> Void) {
	withSugar("with", text, level: 1, code)
}

public func that(text: String, code: Void -> Void) {
	withSugar("that", text, level: 1, code)
}

public func and(text: String, code: Void -> Void) {
	withSugar("and", text, level: 1, code)
}

public func but(text: String, code: Void -> Void) {
	withSugar("but", text, level: 1, code)
}

public func it(text: String, code: Void -> Void) {
	withSugar("it", text, level: 1, code)
}

public func perform(text: String, code: Void -> Void) {
	withSugar("perform", text, level: 1, code)
}

private func withSugar(sugar: String, text: String, #level: Int, code: Void -> Void) {
	doPrint("\(indentation(currentIndentationLevel + level))\(currentIcons().secondLevel) \(sugar) \(text)")
	code()
}

private func doPrint(str: String) {
	// Xcode 6.3 (6D570) hang using Swift's print.
	// Use NSLog as workaround
	NSLog("%@", str)
}

public func assertThat(text: String, code: Void -> Void) {
	SwiftTryCatch.try({
		code()
		doPrint("\(indentation(currentIndentationLevel + 1))\(currentIcons().assertPassed) assert that \(text)\n")
	}, catch: { error in
		doPrint("\(indentation(currentIndentationLevel + 1))\(currentIcons().assertFailed) assert that \(text)\n")
		SwiftTryCatch.throwException(error)
	}, finally: nil)
}

private func currentIndentation() -> String {
	return indentation(currentIndentationLevel)
}

private func indentation(level:Int) -> String {
	return String(count: level, repeatedValue:Character("\t"))
}

public var currentIcons = simpleIcons

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
	secondLevel: Character,
	assertPassed: Character,
	assertFailed: Character)


func asciiIcons() -> Icons {
	return (
		passed: "v",
		failed: "x",
		given: "-",
		when: "-",
		then: "-",
		eventually: "-",
		timeout: "x",
		skipped: "!",
		pending: "?",
		scenario: "*",
		secondLevel: "·",
		assertPassed: "v",
		assertFailed: "x")
}

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
		secondLevel: "·",
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
		secondLevel: "✓",
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
		secondLevel: "📍",
		assertPassed: "😄",
		assertFailed: "😱")
}
