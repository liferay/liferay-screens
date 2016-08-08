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
import Foundation


extension NSDate {

	public convenience init(millisecondsSince1970 millis: Double) {
		let timeInterval = NSTimeInterval(millis/1000)
		self.init(timeIntervalSince1970: timeInterval)
	}

	public func equalToDate(date: NSDate) -> Bool {
		return self.compare(date) == NSComparisonResult.OrderedSame
	}

	public var timeAgo: String {
		let components = self.dateComponents()

		if components.year > 0 {
			if components.year < 2 {
				return LocalizedString("default", key: "time-last-year", obj: self)
			} else {
				return String(format:
					LocalizedString("default", key: "time-ago-years", obj: self), components.year)
			}
		}

		if components.month > 0 {
			if components.month < 2 {
				return LocalizedString("default", key: "time-last-month", obj: self)
			} else {
				return String(format:
					LocalizedString("default", key: "time-ago-months", obj: self), components.month)
			}
		}

		if components.day >= 7 {
			let week = components.day/7
			if week < 2 {
				return LocalizedString("default", key: "time-last-week", obj: self)
			} else {
				return String(format:
					LocalizedString("default", key: "time-ago-weeks", obj: self), week)
			}
		}

		if components.day > 0 {
			if components.day < 2 {
				return LocalizedString("default", key: "time-yesterday", obj: self)
			} else  {
				return String(format:
					LocalizedString("default", key: "time-ago-days", obj: self), components.day)
			}
		}

		if components.hour > 0 {
			if components.hour < 2 {
				return LocalizedString("default", key: "time-ago-hour", obj: self)
			} else  {
				return String(format:
					LocalizedString("default", key: "time-ago-hours", obj: self), components.hour)
			}
		}

		if components.minute > 0 {
			if components.minute < 2 {
				return LocalizedString("default", key: "time-ago-minute", obj: self)
			} else {
				return String(format:
					LocalizedString("default", key: "time-ago-minutes", obj: self),
						components.minute)
			}
		}

		if components.second > 0 && components.second < 5 {
			return String(format: LocalizedString("default", key: "time-ago-seconds", obj: self),
				components.second)
		}

		return LocalizedString("default", key: "time-now", obj: self)
	}

	private func dateComponents() -> NSDateComponents {
		let calendar = NSCalendar.currentCalendar()
		let components: NSCalendarUnit = [.Second, .Minute, .Hour, .Day, .Month, .Year]
		return calendar.components(components, fromDate: self, toDate: NSDate(), options: [])
	}

}
