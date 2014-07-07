// nameOfClass.swift
//
// Copyright (c) 2014 Sven Korset
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.

import Foundation

/**
Returns the name of a Class as a string.

This method is a counterpart for Objective-C's NSStringFromClass() written in swift and compatible with every class in swift.
Make sure not to call this method with a struct or any other type apart from a class, i.e. calling this method with String will result in an error.

let className = nameOfClass(MyClass)
let myObject = MyClass()
let objectClassName = nameOfClass(myObject.dynamicType)

Due to namespaces in swift the macro NSStringFromClass() does not return the simple class name, but a name including the module name.
Swift's classes begin typically with "_TtC", followed by the number of how many characters the module name has which follows as the string followed again by a number which describes the number of characters of the class name which follows.
As an example if MyClass in the app named MyProject is given then the class name will be "_TtC9MyProject7MyClass".
This method uses NSStringFromClass(), parses the returned string and returns only the name of the class without any prefixes, numbers and module names.

However, a swift class may also be marked with @objc and a custom name in parenthesis to name the class without a module name.
So if MyClass has a leading @objc(MyObjCClass) this will result in the classes' name to be "MyObjCClass" instead of "MyClass".
In this case the name already has the valid form and is returned without further string processing.

@param classType: A class type.
@return The classes' name as a String object.
*/
func nameOfClass(classType: AnyClass) -> String {
	let stringOfClassType: String = NSStringFromClass(classType)

	// parse the returned string
	let swiftClassPrefix = "_TtC"
	if stringOfClassType.hasPrefix(swiftClassPrefix) {
		// convert the string into an array for easyer access to the characters in it
		let characters = Array(stringOfClassType)
		var ciphersForModule = String()
		// parse the ciphers for the module name's length
		var index = countElements(swiftClassPrefix)
		while index < characters.count {
			let character = characters[index++]
			if String(character).toInt() {
				// character is a cipher
				ciphersForModule += character
			} else {
				// no cipher, module name begins
				break
			}
		}
		// create a number from the ciphers
		if let numberOfCharactersOfModuleName = ciphersForModule.toInt() {
			// ciphers contains a valid number, so skip the module name minus 1 because we already read one character f the module name
			index += numberOfCharactersOfModuleName - 1
			var ciphersForClass = String()
			while index < characters.count {
				let character = characters[index++]
				if String(character).toInt() {
					// character is a cipher
					ciphersForClass += character
				} else {
					// no cipher, class name begins
					break
				}
			}
			// create a number from the ciphers
			if let numberOfCharactersOfClassName = ciphersForClass.toInt() {
				// number parsed, but make sure it does not exceeds the string's length
				if numberOfCharactersOfClassName > 0 && index - 1 + numberOfCharactersOfClassName <= characters.count {
					// valid number, get the substring which should be the classes' name
					let range = NSRange(location: index - 1, length: numberOfCharactersOfClassName)
					let nameOfClass = (stringOfClassType as NSString).substringWithRange(range)
					return nameOfClass
				}
			}
		}
	}

	// string couldn't be parsed so just return the returned string
	return stringOfClassType
}