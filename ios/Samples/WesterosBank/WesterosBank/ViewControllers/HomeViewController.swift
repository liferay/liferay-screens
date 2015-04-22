//
//  SettingsViewController.swift
//  WesterosBank
//
//  Created by jmWork on 21/04/15.
//  Copyright (c) 2015 Liferay. All rights reserved.
//

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

			UIView.animateWithDuration(0.7) {
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

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
