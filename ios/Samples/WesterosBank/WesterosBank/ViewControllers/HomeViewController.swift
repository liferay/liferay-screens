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
import UIKit
import LiferayScreens


class HomeViewController: UIViewController {

	@IBOutlet weak var issuesDeck: CardDeckView!
	@IBOutlet weak var issuesCard: CardView!
	@IBOutlet weak var reportIssueCard: CardView!
	@IBOutlet weak var settingsView: UIView!

	var issuesController: IssuesViewController?
	var reportIssueController: ReportIssueViewController?


    override func viewDidLoad() {
        super.viewDidLoad()

		issuesController = IssuesViewController(card: issuesCard)

		issuesDeck.topCard = issuesCard

		reportIssueController = ReportIssueViewController(card: reportIssueCard)
		reportIssueController!.onDone = {
		}

		issuesDeck.bottomCard = reportIssueCard

		reportIssueCard.normalHeight = issuesDeck.frame.size.height - 50
    }

	override func viewWillAppear(animated: Bool) {
		if SessionContext.hasSession {
			issuesCard.currentState = .Hidden
			issuesCard.nextState = .Maximized

			reportIssueCard.currentState = .Hidden
			reportIssueCard.nextState = .Minimized

			issuesCard.resetToCurrentState()
			reportIssueCard.resetToCurrentState()

			UIView.animateWithDuration(1.5) {
				self.settingsView.alpha = 1.0
			}

			issuesCard.changeToNextState()
			reportIssueCard.changeToNextState()
		}
	}

	override func viewDidAppear(animated: Bool) {
		if !SessionContext.hasSession {
			self.performSegueWithIdentifier("onboarding", sender: nil)
		}
	}

}
