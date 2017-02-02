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

		switch (components.year, components.month, components.day,
		        components.hour, components.minute, components.second) {
		case (1, _, _, _, _, _):
			return LocalizedString("default", key: "time-last-year", obj: self)
		case let (y, _, _, _, _, _) where y > 1:
			return String(format: LocalizedString("default", key: "time-ago-years", obj: self), y)
		case (0, 1, _, _, _, _):
			return LocalizedString("default", key: "time-last-month", obj: self)
		case let (0, m, _, _, _, _) where m > 1:
			return String(format: LocalizedString("default", key: "time-ago-months", obj: self), m)
		case (0, 0, 7...13, _, _, _):
			return LocalizedString("default", key: "time-last-week", obj: self)
		case let (0, 0, d, _, _, _) where d > 13:
			return String(format: LocalizedString("default", key: "time-ago-weeks", obj: self), d/7)
		case (0, 0, 1, _, _, _):
			return LocalizedString("default", key: "time-yesterday", obj: self)
		case let (0, 0, d, _, _, _) where d > 1:
			return String(format: LocalizedString("default", key: "time-ago-days", obj: self), d)
		case (0, 0, 0, 1, _, _):
			return LocalizedString("default", key: "time-ago-hour", obj: self)
		case let (0, 0, 0, h, _, _) where h > 1:
			return String(format: LocalizedString("default", key: "time-ago-hours", obj: self), h)
		case (0, 0, 0, 0, 1, _):
			return LocalizedString("default", key: "time-ago-minute", obj: self)
		case let (0, 0, 0, 0, m, _) where m > 1:
			return String(format: LocalizedString("default", key: "time-ago-minutes", obj: self), m)
		case let (0, 0, 0, 0, 0, s) where s > 5:
			return String(format: LocalizedString("default", key: "time-ago-seconds", obj: self), s)
		default:
			return LocalizedString("default", key: "time-now", obj: self)
		}
	}

	private func dateComponents() -> NSDateComponents {
		let calendar = NSCalendar.currentCalendar()
		let components: NSCalendarUnit = [.Second, .Minute, .Hour, .Day, .Month, .Year]
		return calendar.components(components, fromDate: self, toDate: NSDate(), options: [])
	}

}
