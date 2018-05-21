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
	@IBOutlet weak var goBackCard: CardView!
	@IBOutlet weak var settingsView: UIView!

	var issuesController: IssuesViewController?
	var reportIssueController: ReportIssueViewController?

    override func viewDidLoad() {
        super.viewDidLoad()

		issuesController = IssuesViewController(card: issuesCard)
		issuesController?.onEditIssue = onEditIssue
		issuesController?.onViewIssue = onViewIssue
		issuesDeck.topCard = issuesCard

		reportIssueCard.normalHeight = issuesDeck.frame.size.height - 50
		reportIssueCard.frame.size.height = reportIssueCard.normalHeight - reportIssueCard.minimizedHeight
		reportIssueController = ReportIssueViewController(card: reportIssueCard)
		reportIssueController!.onDone = {
		}

		issuesDeck.bottomCard = reportIssueCard

		self.settingsView.layer.zPosition = -1000
		self.issuesDeck.layer.zPosition = 0
		self.reportIssueCard.layer.zPosition = 1000

		issuesCard.currentState = .hidden
		reportIssueCard.currentState = .hidden

		issuesCard.resetToCurrentState()
		reportIssueCard.resetToCurrentState()

		issuesCard.isHidden = true
		reportIssueCard.isHidden = true

		goBackCard.createButton(UIColor.white)
				.addTarget(self,
					action: #selector(HomeViewController.goBackAction(_:)),
					for: UIControlEvents.touchUpInside)

		issuesDeck.onButtonTouched = { card in
			if card === self.reportIssueCard && !card.currentState.isVisible {
				self.reportIssueController?.issueRecord = nil
				self.reportIssueController?.editable = true
			}
		}
    }

	override func viewWillAppear(_ animated: Bool) {
		if SessionContext.isLoggedIn {
			issuesCard.nextState = .maximized
			reportIssueCard.nextState = .minimized

			issuesCard.isHidden = false

			issuesCard.changeToNextState { _ in
				self.reportIssueCard.currentState = .hidden
				self.reportIssueCard.resetToCurrentState()
				self.reportIssueCard.isHidden = false
				self.reportIssueCard.changeToNextState(nil, delay: 0.5)
			}

			UIView.animate(withDuration: 1.5, animations: {
				self.settingsView.alpha = 1.0
			})
		}
	}

	override func viewDidAppear(_ animated: Bool) {
		if !SessionContext.isLoggedIn {
			if tourCompleted {
				self.performSegue(withIdentifier: "onboarding", sender: nil)
			}
			else {
				self.performSegue(withIdentifier: "tour", sender: nil)
			}
		}
	}

	@IBAction func accountSettingsAction(_ sender: AnyObject) {
		self.performSegue(withIdentifier: "account", sender: nil)
	}

	@IBAction func callAction(_ sender: AnyObject) {
	}

	@IBAction func sendMessageAction(_ sender: AnyObject) {
	}

	@IBAction func signOutAction(_ sender: AnyObject) {
		SessionContext.logout()
		self.performSegue(withIdentifier: "onboarding", sender: nil)
	}

	fileprivate func onEditIssue(_ record: DDLRecord) {
		issuesCard.nextState = .background
		reportIssueCard.nextState = .normal // .Maximized

		reportIssueController?.issueRecord = record
		reportIssueController?.editable = true

		issuesCard.changeToNextState()
		reportIssueCard.changeToNextState()
	}

	fileprivate func onViewIssue(_ record: DDLRecord) {
		issuesController?.scrollToShowRecord(record)

		reportIssueCard.nextState = .hidden

		reportIssueCard.changeToNextState { _ in
			self.goBackCard.currentState = .hidden
			self.goBackCard.resetToCurrentState()
			self.goBackCard.nextState = .minimized
			self.goBackCard.changeToNextState(nil, delay: 0.3)
		}

		issuesCard.enabledButton(false)
	}

	@objc func goBackAction(_ sender: AnyObject?) {
		issuesController?.scrollToShowList()

		goBackCard.nextState = .hidden
		reportIssueCard.nextState = .minimized

		goBackCard.changeToNextState()
		reportIssueCard.changeToNextState()

		issuesCard.enabledButton(true)
	}

}
