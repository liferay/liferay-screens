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
import Hokusai

@objcMembers
class MenuViewController: UIViewController, WebScreenletDelegate, CallMeBackDelegate {

    @IBOutlet weak var webScreenlet: WebScreenlet!
    @IBOutlet weak var heightCallMeBack: NSLayoutConstraint!
    @IBOutlet weak var headerCallmeBack: UIView!
    @IBOutlet weak var viewCallmeBack: UIView!
    @IBOutlet weak var labelCallMeBack: UILabel!
    @IBOutlet weak var callMeBack: CallMeBackView!
    @IBOutlet weak var coverForClick: UIVisualEffectView!

    var coverNavegationBar: UIView?

    override func viewDidLoad() {
        super.viewDidLoad()

        addLogoToNavigationBar()
        modifyHeightCallMeBack(height: 0)
        callMeBack.delegate = self

        buttonChangeLanguage(language: LanguageHelper.shared().threeLettersFormatted)
        textButtonBack()
        loadWebScreenlet()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }

    func modifyHeightCallMeBack(height: CGFloat) {
        heightCallMeBack.constant = height
        self.viewCallmeBack.layoutIfNeeded()
    }

    func addLogoToNavigationBar() {
        let logo = UIImage(named: "Logo") as UIImage?
        let imageView = UIImageView(image:logo)
        imageView.frame.size.width = 100
        imageView.frame.size.height = 32
        imageView.contentMode = UIViewContentMode.scaleAspectFill

        self.navigationItem.titleView = imageView
    }

    func buttonChangeLanguage(language: String) {
        navigationItem.rightBarButtonItem = UIBarButtonItem(
            title: language,
            style: .plain,
            target: self,
            action: Colorful.flag ? #selector(menuChangeLanguageColorful) : #selector(menuChangeLanguage))
    }

    func menuChangeLanguage() {
        let actionSheetController: UIAlertController = UIAlertController(title: "language".localized(), message: "choose".localized(), preferredStyle: .actionSheet)

        for value in LanguageHelper.shared().listLanguages {
            let itemAction: UIAlertAction = UIAlertAction(title: value, style: .default) { _ -> Void in
                self.actionChangeLanguage(value: value)
            }
            actionSheetController.addAction(itemAction)
        }

        let cancelAction: UIAlertAction = UIAlertAction(title: "cancel".localized(), style: .cancel) { _ -> Void in
            actionSheetController.dismiss(animated: true, completion: nil)
        }

        actionSheetController.addAction(cancelAction)
        self.present(actionSheetController, animated: true, completion: nil)
    }

    func menuChangeLanguageColorful() {
        let hokusai = Hokusai()

        hokusai.colors = HOKColors(
            backGroundColor: UIColor.lightPurple,
            buttonColor: UIColor.darkPurple,
            cancelButtonColor: UIColor.darkPurple,
            fontColor: UIColor.white
        )

        for value in LanguageHelper.shared().listLanguages {
            hokusai.addButton(value) {
                self.actionChangeLanguage(value: value)
            }
        }
        hokusai.show()
    }

    func actionChangeLanguage(value: String) {
        LanguageHelper.shared().change(language: value)
        self.buttonChangeLanguage(language: LanguageHelper.shared().threeLettersFormatted)
        self.loadWebScreenlet()
        self.textButtonBack()
        self.callMeBack.setTextOutlets()
    }

    func textButtonBack() {
        let backItem = UIBarButtonItem(title: "back".localized(), style: .plain, target: nil, action: nil)
        navigationItem.backBarButtonItem = backItem
    }

    func attachClickHeaderCallBack() {
        let headerGesture = UITapGestureRecognizer(target: self, action:  #selector (MenuViewController.callBackActions(sender:)))

        let touchUpViewGesture = UITapGestureRecognizer(target: self, action:  #selector (MenuViewController.callBackActions(sender:)))

        let navBarViewGesture = UITapGestureRecognizer(target: self, action:  #selector (MenuViewController.callBackActions(sender:)))

        self.headerCallmeBack.addGestureRecognizer(headerGesture)
        self.coverForClick.addGestureRecognizer(touchUpViewGesture)
        self.coverNavegationBar!.addGestureRecognizer(navBarViewGesture)

    }

    func callBackActions(sender: UITapGestureRecognizer) {
        UIView.animate(withDuration: 0.5, animations: {
            let bigSize: CGFloat = 400.0
            let smallSize: CGFloat = 50.0

            if self.heightCallMeBack.constant.isEqual(to: bigSize) {
                self.heightCallMeBack.constant = smallSize
                self.viewCallmeBack.superview?.layoutIfNeeded()

                self.navigationController?.navigationBar.dismissChangeBackgroundColor(view: self.coverNavegationBar!, isUserInteraccionEnable: false)
                self.coverForClick.dismissChangeBackgroundColor(isHidden: true)
                self.coverNavegationBar?.isUserInteractionEnabled = false
            }
            else {
                self.heightCallMeBack.constant = bigSize
                self.viewCallmeBack.superview?.layoutIfNeeded()

                self.navigationController?.navigationBar.animatedChangeBackgroundColor(view: self.coverNavegationBar!, isUserInteraccionEnable: true)
                self.coverForClick.animatedChangeBackgroundColor(isHidden: false)
                self.coverNavegationBar?.isUserInteractionEnabled = true
            }

        })
    }

    func prepareCoverNavegationBar() {
        let xposition = self.navigationController?.navigationBar.frame.origin.x
        let yposition = (self.navigationController?.navigationBar.frame.origin.y)! - 60
        let height = (self.navigationController?.navigationBar.frame.size.height)! + 40
        let width = self.navigationController?.navigationBar.frame.size.width

        let frame = CGRect(x: xposition!, y: yposition, width: width!, height: height)

        self.coverNavegationBar = UIView(frame: frame)
        self.navigationController?.navigationBar.addSubview(self.coverNavegationBar!)
        self.coverNavegationBar?.isUserInteractionEnabled = false
    }

    func loadWebScreenlet() {
        let webScreenletConfiguration = WebScreenletConfigurationBuilder(url: LanguageHelper.shared().url(page: .index))
            .set(webType: .other)
            .addCss(localFile: "menu")
            .addJs(localFile: "menu")
            .load()
        webScreenlet.configuration = webScreenletConfiguration
        webScreenlet.isScrollEnabled = false
        webScreenlet.backgroundColor = UIColor.lightPurple

        webScreenlet.load()
        webScreenlet.delegate = self
    }

    func onWebLoad(_ screenlet: WebScreenlet, url: String) {
        prepareCoverNavegationBar()
        attachClickHeaderCallBack()
    }

    func screenlet(_ screenlet: WebScreenlet, onScriptMessageNamespace namespace: String, onScriptMessage message: String) {
        switch namespace {
            case "call-me-back":
                createPopOverCallMeBack(message: message)
            case "click-button":
                goNextForfet(position: message)
            case "map":
                goToMap()
            default:
                print("Any event")
        }

    }

    func createPopOverCallMeBack(message: String) {
        labelCallMeBack.text = message
        UIView.animate(withDuration: 1, animations: {
            self.heightCallMeBack.constant = 50
            self.viewCallmeBack.superview?.layoutIfNeeded()
        })
    }

    func goNextForfet(position: String) {
        performSegue(withIdentifier: "forfet", sender: position)
    }

    func goToMap() {
        performSegue(withIdentifier: "map", sender: nil)
    }

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "forfet" ,
            let vc = segue.destination as? ForfetViewController {
            switch sender as! String {
            case "0":
                vc.url = LanguageHelper.shared().url(page: .mobile)
            case "1":
                vc.url = LanguageHelper.shared().url(page: .roaming)
            case "2":
                vc.url = LanguageHelper.shared().url(page: .paquete69)
            case "3":
                vc.url = LanguageHelper.shared().url(page: .optima)
            default:
                print("Sorry, we don't have more cases")
            }

        }
    }

    func showAlertLegalNotAccepted(callMeBackView: CallMeBackView, title: String, message: String) {
        let alert = UIAlertController(title: title, message: message, preferredStyle: UIAlertControllerStyle.alert)
        alert.addAction(UIAlertAction(title: "Ok", style: UIAlertActionStyle.default, handler: nil))
        alert.colorfull()
        self.present(alert, animated: true, completion: nil)
    }

    func showLegalCoditions(callMeBackView: CallMeBackView) {
        let alertController = UIAlertController(title: "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n", message: nil, preferredStyle: UIAlertControllerStyle.actionSheet)

        let margin: CGFloat = 10.0
        let rect = CGRect(x: margin, y: margin, width: alertController.view.bounds.size.width - margin * 6.0, height: 340)

        let legalConditionView = LegalConditionsView(frame: rect)

        alertController.view.addSubview(legalConditionView)

        let acceptAction = UIAlertAction(title: "accept".localized(), style: .default, handler: { (_: UIAlertAction!) in self.callMeBack.legalConditionsChange(isAccepted: true) })

        let cancelAction = UIAlertAction(title: "cancel".localized(), style: .cancel, handler: { (_: UIAlertAction!) in self.callMeBack.legalConditionsChange(isAccepted: false) })

        alertController.addAction(acceptAction)
        alertController.addAction(cancelAction)

        alertController.colorfull()

        DispatchQueue.main.async {
            self.present(alertController, animated: true, completion: {})
        }

    }
}
