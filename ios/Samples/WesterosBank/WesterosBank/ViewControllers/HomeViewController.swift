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

var tourCompleted = false


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

		self.settingsView.layer.zPosition = -1000
		self.issuesDeck.layer.zPosition = 0
		self.reportIssueCard.layer.zPosition = 1000

		reportIssueCard.normalHeight = issuesDeck.frame.size.height - 50

		issuesCard.currentState = .Hidden
		reportIssueCard.currentState = .Hidden

		issuesCard.resetToCurrentState()
		reportIssueCard.resetToCurrentState()

		issuesCard.hidden = true
		reportIssueCard.hidden = true
    }

	override func viewWillAppear(animated: Bool) {
		if SessionContext.hasSession {
			issuesCard.nextState = .Maximized
			reportIssueCard.nextState = .Minimized

			issuesCard.hidden = false

			issuesCard.changeToNextState() { Bool -> Void in
				self.reportIssueCard.currentState = .Hidden
				self.reportIssueCard.resetToCurrentState()
				self.reportIssueCard.hidden = false
				self.reportIssueCard.changeToNextState()
			}

			UIView.animateWithDuration(1.5) {
				self.settingsView.alpha = 1.0
			}
		}
	}

	override func viewDidAppear(animated: Bool) {
		if !SessionContext.hasSession {
			if tourCompleted {
				self.performSegueWithIdentifier("onboarding", sender: nil)
			}
			else {
				self.performSegueWithIdentifier("tour", sender: nil)
			}
		}
	}

	@IBAction func accountSettingsAction(sender: AnyObject) {
		self.performSegueWithIdentifier("account", sender: nil)
	}

	@IBAction func callAction(sender: AnyObject) {
	}

	@IBAction func sendMessageAction(sender: AnyObject) {

	}

	@IBAction func signOutAction(sender: AnyObject) {
		SessionContext.clearSession()
		self.performSegueWithIdentifier("onboarding", sender: nil)
	}


}
