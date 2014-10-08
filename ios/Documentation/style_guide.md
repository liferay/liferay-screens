# Liferay's guide to Swift style and code conventions.

----

## Files

* Prefer one file per class. If you need other auxiliar classes, prefer nested/inner classes intead of more than one class in the same .swift file.

* Use the same name for the .swift file and for the enclosed class.

## Whitespace

 * Tabs, not spaces.
 * End files with a newline (diff algorithms work better)
 * Don’t leave trailing whitespace.
   * Not even leading indentation on blank lines.


## Vertical spacing

* Two blank lines befone any type definition:

| Preferred|Not Preferred|
|----------|-------------| 
| <pre>import UIKit<br/><br/><br/>public class MyClass {<br/>… | <pre>import UIKit<br/><br/>public class MyClass {<br/>… |

* Exception to previous rule: use only once blank line for first inner type definitions:

| Preferred|Not Preferred|
|----------|-------------| 
| <pre>public class MyClass {<br/><br/>    public enum MyEnum {<br/>… | <pre>public class MyClass {<br/><br/><br/>    public enum MyEnum {<br/>… |


* Leading and trailing blank lines in body of enclosing types

| Preferred|Not Preferred|
|----------|-------------| 
| <pre>public class MyClass {<br/><br/>    var a = 0<br/><br/>} | <pre>public class MyClass {<br/>    var a = 0<br/>} |

## Marks

* Use //MARK: to group the following elements. Avoid it if there is only one group.

	* Attributes with IBOutlets: 
		* `//MARK: Outlets`
	* Methods with IBActions:
		* `//MARK: Actions`
	* Overwritten methods:
		* `//MARK: <the name of the parent class>`
	* Implemented methods from protocols:
		* `//MARK: <the name of the protocol>`
	* Public methods
		* `//MARK: Public methods`
	* Internal methods
		* `//MARK: Internal methods`
	* Private methods
		* `//MARK: Private methods`
	
* Put colon just after MARK. Don't put space between MARK and //

* Put two leading blank lines before MARK

| Preferred|Not Preferred|
|----------|-------------| 
| <pre>let a = 1<br><br><br>//MARK: Public methods | <pre>let a = 1<br><br>//MARK: Public methods |


## Line breaks

* Break at 100 columns
* After break, insert two tabs

| Preferred|Not Preferred|
|----------|-------------| 
| <pre>let myConstant =<br>        1 + (2 + 3) | <pre>let myConstant =<br>    1 + (2 + 3) |

* In methods invocation and declaration, break on every named parameter unless all of them fit in one line. 

| Preferred|Not Preferred|
|----------|-------------| 
| <pre>fooWithInt(1,<br>        arg2: 2,<br>        arg3: 3,<br>        arg4: 4) | <pre>fooWithInt(1, arg2: 2, arg3: 3<br>        arg4:4)</pre><pre>fooWithInt(1,<br>        arg2: 2, arg3: 3, arg4: 4) |

* In methods declaration the return type should be in its own line, unless it fits in a single line together with all the parameters

| Preferred|Not Preferred|
|----------|-------------| 
| <pre>fooWithInt(1,arg2: 2) -> Bool</pre> <pre>fooWithInt(1,<br>        arg2: 2,<br>        arg3: 3,<br>        arg4: 4) <br>        -> Bool| <pre>fooWithInt(1, arg2: 2, arg3: 3<br>        arg4:4) -> Bool</pre><pre>fooWithInt(1,<br>        arg2: 2,<br>        arg3: 3,<br>        arg4: 4) -> Bool  |


* When break a line, try to do it without cutting expressions

| Preferred|Not Preferred|
|----------|-------------| 
| <pre>myVar =<br>    1 + (2 + 3) | <pre>myVar = 1 + (2 +<br>    3) |
| 
| <pre>if let value = <br>        foo(1, 2, 3) { | <pre>if let value = foo(1,<br>        2, 3) { |

* When you break a function prototype, insert a blank line before first line of code

| Preferred|Not Preferred|
|----------|-------------| 
| <pre>public func fooWithInt(arg1: Int, arg2: Int, arg3: Int,<br>        arg4: Int) {<br><br>    let firstLine = 1 | <pre>public func fooWithInt(arg1: Int, arg2: Int, arg3: Int,<br>        arg4: Int) {<br>    let firstLine = 1|

## Control structures

* Method braces and other braces (`if`/`switch`/`while` etc.) always open on the same line as the statement but close on a new line.

| Preferred|Not Preferred|
|----------|-------------| 
| <pre>public func foo() {<br>    ...</pre><pre>if x {<br>    ...</pre>| <pre>public func foo()<br>{<br>    ...</pre><pre>if x<br>{    <br>    ...</pre>|

* Indent `case` clauses in `switch` structure:

| Preferred|Not Preferred|
|----------|-------------| 
| <pre>switch x {<br>    case A:<br>        statement<br>    default:<br>        statement<br>}| <pre>switch x {<br>case A:<br>    statement<br>default:<br>    statement<br>}|

* Prefer computed properies and subscripts over explicit getters/setters

| Preferred|Not Preferred|
|----------|-------------| 
|<pre>var myValue: Int {<br>    get {<br>        return 0<br>    }<br>    set {<br>        ...store newValue somewhere...<br>    }<br>}</pre>|<pre>func getMyValue() -> Int {<br>    return 0<br>}<br><br>func setMyValue(newValue: Int) {<br>    ...store newValue somewhere...<br>}</pre>|


* When possible, omit the `get` keyword on read-only computed properties and
read-only subscripts.

| Preferred|Not Preferred|
|----------|-------------| 
| <pre>var property: Int {<br>    return 0<br>}</pre>|<pre>var property: Int {<br>    get {<br>        return 0<br>    }<br>}</pre>|

* Use `if let` structure when possible. Suffix with `Value` the variable used in let:

| Preferred|Not Preferred|
|----------|-------------| 
| <pre>if let xValue = x {<br>    ...<br>}|<pre>if x != nil {<br>    ... (use x! expression)<br>}|

## Scope

* Always specify the scope explicitly, both in functions and attributes

| Preferred|Not Preferred|
|----------|-------------| 
| <pre>internal let a = 1</pre><pre>public func foo()</pre>| <pre>let a = 1</pre><pre>func foo()</pre>|

* For `@IBOutlet` and `@IBAction`, specify the most restrictive scope possible.

| Preferred|Not Preferred|
|----------|-------------| 
| <pre>@IBOutlet private var button: UIButton?</pre><pre>@IBAction private func foo(sender: AnyObject?)</pre>| <pre>@IBOutlet var button: UIButton?</pre><pre>@IBAction func foo(sender: AnyObject?)</pre>|

* `@Inspectable` properties should be always public

| Preferred|Not Preferred|
|----------|-------------| 
| <pre>@Inspectable public var property = 1| <pre>@Inspectable private var property = 1|


## Naming

* Use descriptive names with camel case for classes, methods, variables, etc. Class names and constants in module scope should be capitalized, while method names and variables should start with a lower case letter.

| Preferred|Not Preferred|
|----------|-------------| 
| <pre>let MaximumWidgetCount = 100<br><br>class WidgetContainer {<br><br>    var widgetButton: UIButton<br>    let widgetHeightPercentage = 0.85<br><br>} | <pre>let MAX_WIDGET_COUNT = 100<br><br>class app_widgetContainer {<br><br>    var wBut: UIButton<br>    let wHeightPct = 0.85<br>}|

* For functions and methods, prefer named parameters for all arguments unless the context is very clear. Include external parameter names if it makes function calls more readable.

| Preferred|Not Preferred|
|----------|-------------| 
| <pre>func convert(#column: Int, #row: Int) -> CGPoint</pre><pre>func dateFromString(dateString: NSString) -> NSDate</pre>| <pre>func convertColumn(#column: Int, #row: Int) -> CGPoint</pre><pre>func dateFromString(#dateString: NSString) -> NSDate |

Invocations will look like...

| Preferred|Not Preferred|
|----------|-------------| 
| <pre>convert(column: 1, row: 2)</pre><pre>dateFromString("2004-06-19")</pre>| <pre>convertColumn(1, row: 2)</pre><pre>dateFromString(dateString: "2004-06-19") |

* For external names, prefer the `#` sintax to use the same name internally and externally

| Preferred|Not Preferred|
|----------|-------------| 
| <pre>convert(#column: Int, #row: Int)</pre>| <pre>convert(forColumn column: Int, andRow row: Int)</pre> |


## Closures

* Use trailing closure syntax wherever possible.

| Preferred|Not Preferred|
|----------|-------------| 
| <pre>let filtered = filter(collection) { $0 == 1 }| <pre>let filtered = filter(collection, { $0 == 1 }) |

* When the closure has only one parameter, prefer the implicit `$0` parameter instead of the explicit one.

| Preferred|Not Preferred|
|----------|-------------| 
| <pre>let filtered = filter(collection) { $0 == 1 }| <pre>let filtered = filter(collection) {number in<br>     number == 1<br>} |

* If the closure has only one expression, use inline sintax: implicit return and no new line

| Preferred|Not Preferred|
|----------|-------------| 
| <pre>let filtered = filter(collection) { $0 == 1 }| <pre>let filtered = filter(collection) {<br>    return $0 == 1<br>} |

* Prefer Void sintax over () for the closure return type. It makes the optional closure sintax cleaner:

| Preferred|Not Preferred|
|----------|-------------| 
| <pre>var c: Void -> Void</pre><pre>var c: (Void -> Void)?</pre>| <pre>var c: () -> ()</pre><pre>var c: (() -> ())?</pre> |

* When the closure has only one parameter, don't enclose it in parenthesis. Don't enclose the return type in parenthesis neither:

| Preferred|Not Preferred|
|----------|-------------| 
| <pre>var c: Int -> Int</pre>| <pre>var c: (Int) -> (Int)</pre> |

## Types 

* Use concise data types when possible

| Preferred|Not Preferred|
|----------|-------------| 
| <pre>[String]</pre><pre>[String:Int]</pre><pre>String?</pre>| <pre>Array\<String></pre><pre>Dictionary\<String, Int></pre><pre>Optional\<String></pre> |


* Use always type inference, except when you need to cast to one specific type

| Preferred|Not Preferred|
|----------|-------------| 
| <pre>let a = "my string"</pre><pre>let f: CGFloat = 1</pre><pre>let e = MyEnum.Value</pre>| <pre>let a: String = "my string"</pre><pre>let f = 1 as CGFloat</pre><pre>let e: MyEnum = .Value</pre> |


* Put a space after the colon for types

| Preferred|Not Preferred|
|----------|-------------| 
| <pre>func foo(a: String)</pre>| <pre>func foo(a:String)</pre><pre>func foo(a : String)</pre> |


## Self

* Use self implicitly, except in the following scenarios:
	* Within an extension, to refer to extended class attributes
	* Within a closure (forced by the compiler)
	* To disambiguate name conflicts

## Strings

* Prefer strings interpolation over concatenation

| Preferred|Not Preferred|
|----------|-------------| 
| <pre>"hello \\(name), my friend"</pre>| <pre>"hello " + name + ", my friend"</pre> |

* Don't use leading or trailing space in string interpolation expressions

| Preferred|Not Preferred|
|----------|-------------| 
|<pre>"one plus one is \\(1 + 1)"</pre>|<pre>"one plus one is \\( 1 + 1 )"</pre>|

## Sorting

Inside a class, follow next order:

1. Inner types
   1. public
   1. internal
   1. private

2. Attributes/properties:
   2. @Inspectables
   2. @IBOutlets
   2. The rest: first sorted by scope, then by type (let, var, computed var)

3. Methods:
   3. Class methods
   3. inits/deinits
   3. @IBActions
   3. Overwritten methods (sorted by scope)
   3. Protocol methods
   3. The rest sorted by scope	



## Idioms

Try to use same idioms in the same project

### Singleton

Have in mind that [Singletons may be Evil](http://c2.com/cgi/wiki?SingletonsAreEvil) although could be useful only where you need some global state across your application.

A simple `struct` using static fields and methods could be enough in most case, but if you really need a singleton, follow this idiomatic pattern:

```swift
//MARK: Singleton

class var instance: TheClass {
	struct Singleton {
		static var instance: TheClass? = nil
		static var onceToken: dispatch_once_t = 0
	}

	dispatch_once(&Singleton.onceToken) {
		Singleton.instance = self()
	}

	return Singleton.instance!
}

required override public init() {
	super.init()
}

// == Invocation ==

TheClass.instance

```
