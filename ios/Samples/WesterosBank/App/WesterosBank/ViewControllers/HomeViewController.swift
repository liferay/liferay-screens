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


class HomeViewController: UIViewController, AudienceTargetingDisplayScreenletDelegate {

	@IBOutlet weak var issuesDeck: CardDeckView!
	@IBOutlet weak var issuesCard: CardView!
	@IBOutlet weak var reportIssueCard: CardView!
	@IBOutlet weak var goBackCard: CardView!
	@IBOutlet weak var settingsView: UIView!

	@IBOutlet weak var accountButton: UIButton!
	@IBOutlet weak var restButtons: UIView!

	@IBOutlet weak var bannerScreenlet: AudienceTargetingDisplayScreenlet!


	var goToAccountSegueName: String = "view-account"
	var surveyResponse: String? {
		get {
			return NSUserDefaults.standardUserDefaults().stringForKey("survey")
		}
		set {
			NSUserDefaults.standardUserDefaults().setObject(newValue, forKey: "survey")
		}
	}
	var surveyId: Int64?
	var personaName: String?

	let marketerSegment = Int64(20806)
	let businessMenSegment = Int64(20810)
	let businessWomenSegment = Int64(21180)

	var issuesController: IssuesViewController?
	var reportIssueController: ReportIssueViewController?

	var canReportIssue: Bool? {
		didSet {
			if reportIssueCard.hidden && (canReportIssue ?? false) {
				dispatch_main {
					if self.issuesCardAnimationCompleted {
						self.animateReportButton(delay: 0.5)
					}
				}
			}
		}
	}
	var issuesCardAnimationCompleted: Bool = false {
		didSet {
			if reportIssueCard.hidden {
				dispatch_main {
					if let canReport = self.canReportIssue where canReport {
						self.animateReportButton(delay: 0.5)
					}
				}
			}
		}
	}

    override func viewDidLoad() {
        super.viewDidLoad()

		bannerScreenlet.delegate = self

		issuesController = IssuesViewController(card: issuesCard)

		issuesController?.onEditIssue = onEditIssue
		issuesController?.onViewIssue = onViewIssue

		issuesDeck.topCard = issuesCard

		reportIssueCard.normalHeight = issuesDeck.frame.size.height - 50

		reportIssueCard.frame.size.height = reportIssueCard.normalHeight - reportIssueCard.minimizedHeight

		reportIssueController = ReportIssueViewController(card: reportIssueCard)
		reportIssueController!.onDone = {
			self.issuesDeck.bottomCardTouchUpInside(self.issuesDeck.bottomCard!.button!)
			self.issuesController?.listScreenlet.loadList()
		}

		issuesDeck.bottomCard = reportIssueCard

		self.settingsView.layer.zPosition = -1000
		self.issuesDeck.layer.zPosition = 0
		self.reportIssueCard.layer.zPosition = 1000

		issuesCard.currentState = .Hidden
		reportIssueCard.currentState = .Hidden

		issuesCard.resetToCurrentState()
		reportIssueCard.resetToCurrentState()

		issuesCard.hidden = true
		reportIssueCard.hidden = true

		goBackCard.createButton(UIColor.whiteColor())
				.addTarget(self,
					action: Selector("goBackAction:"),
					forControlEvents: UIControlEvents.TouchUpInside)

		issuesDeck.onButtonTouched = { card in
			if card === self.reportIssueCard && !card.currentState.isVisible {
				self.reportIssueController?.issueRecord = nil
				self.reportIssueController?.editable = true
			}
		}
    }

	override func viewWillAppear(animated: Bool) {
		if SessionContext.hasSession {
			//
			// audience targeting
			//
			bannerScreenlet.loadContent(
					context: ["survey":surveyResponse ?? "empty"])

			audienceTargeting().content(
				placeholderId: "go-to-account",
				result: { (segueName, error) -> Void in
					if let segueName = segueName {
						self.goToAccountSegueName = segueName
					}
					else {
						// can't reach AT: use default value?
					}
				})

			audienceTargeting().content(
				placeholderId: "persona",
				context: ["survey":surveyResponse ?? "empty"],
				result: { (personaName, error) -> Void in
					if error != nil || personaName == nil {
						return
					}

					let isTargetedSegment =
						audienceTargeting().belongsToSegment(self.marketerSegment)
							|| audienceTargeting().belongsToSegment(self.businessMenSegment)
							|| audienceTargeting().belongsToSegment(self.businessWomenSegment)

					if isTargetedSegment && self.surveyResponse == nil {
						self.personaName = personaName!
						delayed(1) {
							self.confirmSurvey();
						}
					}
				})

			audienceTargeting().loadContent(
				placeholderId: "portrait-theme",
				result: { (value, error) -> Void in
					// don't do anything, just for caching
				})

			issuesCard.hidden = false
			issuesCard.nextState = .Maximized

			reportIssueCard.nextState = .Minimized
			reportIssueCard.currentState = .Hidden
			reportIssueCard.resetToCurrentState()

			issuesCard.changeToNextState() { Bool -> Void in
				self.issuesCardAnimationCompleted = true
			}

			requestForCanReport()

			UIView.animateWithDuration(1.5) {
				self.settingsView.alpha = 1.0
			}
		}
	}

	func requestForCanReport() {
		audienceTargeting().content(
			placeholderId: "can-report",
			context: ["survey":surveyResponse ?? "empty"],
			result: { (canReportValue, error) -> Void in
				if let canReportValue = canReportValue {
					if self.surveyResponse != nil {
						self.canReportIssue = Bool.from(string: canReportValue)
					}
				}
				else {
					// error
					self.canReportIssue = true
				}
			})
	}

	func confirmSurvey() {
		audienceTargeting().content(
			placeholderId: "survey-id",
			context: ["survey":surveyResponse ?? "empty"],
			result: { (surveyId, error) -> Void in
				if let surveyId = surveyId {
					self.surveyId = surveyId.toInt().map { Int64($0) }

					dispatch_main {
						let alert = UIAlertController(
								title: "Quick survey",
								message: "It seems you're a \(self.personaName!), so we need you to answer a short survey to know more about you.\n\nDo you want to do it right now?",
								preferredStyle: .Alert)

						let actionYes = UIAlertAction(
								title: "Sure thing",
								style: .Default) { action in
									self.performSegueWithIdentifier("survey", sender: nil)
								}

						let actionNo = UIAlertAction(
								title: "Later",
								style: .Cancel) { action in
								}

						alert.addAction(actionYes)
						alert.addAction(actionNo)

						self.presentViewController(alert, animated: true, completion: nil)
					}
				}
			})
	}

	func animateReportButton(#delay: Double) {
		reportIssueCard.hidden = false
		reportIssueCard.changeToNextState(time: nil, delay: delay)
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

	override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
		if segue.identifier == "survey" {
			let survey = segue.destinationViewController as! SurveyViewController
			survey.onFinish = onSurveyCompleted
			survey.recordSetId = surveyId!
			survey.structureId = surveyId! - 2 // portal hack!
		}
	}

	func onSurveyCompleted(responses: String) {
		self.surveyResponse = responses
		audienceTargeting().clearCache(key: "can-report")
		requestForCanReport()
	}

	@IBAction func accountSettingsAction(sender: AnyObject) {
		self.performSegueWithIdentifier(goToAccountSegueName, sender: nil)
	}

	@IBAction func callAction(sender: AnyObject) {
	}

	@IBAction func sendMessageAction(sender: AnyObject) {
	}

	@IBAction func signOutAction(sender: AnyObject) {
		SessionContext.clearSession()
		self.performSegueWithIdentifier("onboarding", sender: nil)
	}

	private func onEditIssue(record: DDLRecord) {
		issuesCard.nextState = .Background
		reportIssueCard.nextState = .Normal // .Maximized

		reportIssueController?.issueRecord = record
		reportIssueController?.editable = true

		issuesCard.changeToNextState()
		reportIssueCard.changeToNextState()
	}

	private func onViewIssue(record: DDLRecord) {
		issuesController?.scrollToShowRecord(record)

		reportIssueCard.nextState = .Hidden

		reportIssueCard.changeToNextState() { Bool -> Void in
			self.goBackCard.currentState = .Hidden
			self.goBackCard.resetToCurrentState()
			self.goBackCard.nextState = .Minimized
			self.goBackCard.changeToNextState(time: nil, delay: 0.3)
		}

		issuesCard.enabledButton(false)
	}

	func goBackAction(sender: AnyObject?) {
		issuesController?.scrollToShowList()

		goBackCard.nextState = .Hidden
		reportIssueCard.nextState = .Minimized

		goBackCard.changeToNextState()
		reportIssueCard.changeToNextState()

		issuesCard.enabledButton(true)
	}

	func screenlet(screenlet: AudienceTargetingDisplayScreenlet,
			onAudienceTargetingResponse value: AnyObject,
			mimeType: String?) {
		screenlet.hidden = false
	}

	func screenletOnAudienceTargetingEmptyResponse(
			screenlet: AudienceTargetingDisplayScreenlet) {
		screenlet.hidden = true
	}

	func screenlet(screenlet: AudienceTargetingDisplayScreenlet,
			onAudienceTargetingError error: NSError) {
		screenlet.hidden = true
	}


}
