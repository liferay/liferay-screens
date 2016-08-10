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
import AVFoundation


public class AudioDisplayView_default: BaseScreenletView, FileDisplayViewModel {

	public var volume: Float = 0.5
	public var numberOfLoops = -1

	@IBOutlet weak var view: UIView?

	@IBOutlet weak var playButton: UIButton?
	@IBOutlet weak var pauseButton: UIButton?

	@IBOutlet weak var rewindButton: UIButton?
	@IBOutlet weak var forwardButton: UIButton?

	@IBOutlet weak var sliderDuration: UISlider? {
		didSet {
			sliderDuration?.setThumbImage(
				NSBundle.imageInBundles(
					name: "default-point",
					currentClass: self.dynamicType),
				forState: UIControlState.Normal)
		}
	}
	
	@IBOutlet weak var sliderVolume: UISlider? {
		didSet {
			sliderVolume?.setThumbImage(
				NSBundle.imageInBundles(
					name: "default-point",
					currentClass: self.dynamicType),
				forState: UIControlState.Normal)
		}
	}

	@IBOutlet weak var audioProgressLabel: UILabel?
	@IBOutlet weak var audioDurationLabel: UILabel?

	@IBOutlet weak var titleLabel: UILabel?

	public var url: NSURL? {
		didSet {
			if let url = url {
				do {
					self.audio = try AVAudioPlayer(contentsOfURL: url)
					if let audio = audio {
						audio.volume = self.volume
						audio.numberOfLoops = self.numberOfLoops
						playAction()

						self.sliderDuration?.maximumValue = Float(audio.duration)
						self.audioDurationLabel?.text =
							updateAudioDurationLabel(Float(audio.duration))
					}
				}
				catch let error as NSError {
					disableComponents()
					print("AVAudioPlayer error: \(error.debugDescription)")
				}
			}
		}
	}

	public var title: String? {
		didSet {
			self.titleLabel?.text = title
		}
	}

	public var audio: AVAudioPlayer?

	public var timer: NSTimer?
	public var duration: NSTimer?

	override public func onHide() {
		audio?.stop()
	}

	@IBAction func pauseAction() {
		audio?.pause()
		timer?.invalidate()
		duration?.invalidate()
		self.updateView(false)
	}

	@IBAction func playAction() {
		audio?.play()

		timer = NSTimer.scheduledTimerWithTimeInterval(0.01,
				target: self,
				selector: #selector(AudioDisplayView_default.updateProgress),
				userInfo: nil,
				repeats: true)

		duration = NSTimer.scheduledTimerWithTimeInterval(0.01,
				target: self,
				selector: #selector(AudioDisplayView_default.updateDurationLabel),
				userInfo: nil,
				repeats: true)

		self.updateView(true)
	}

	@IBAction func sliderDurationChanged(sender: UISlider) {
		changeCurrentTime(sender.value, slider: true)
	}

	@IBAction func sliderVolumeChanged(sender: UISlider) {
		let selectedValue = sender.value
		audio?.volume = selectedValue
	}

	@IBAction func moveRewindAction() {
		changeCurrentTime(-5, slider: false)
	}
	
	@IBAction func moveForwardAction() {
		changeCurrentTime(5, slider: false)
	}

	private func updateView(play: Bool) {
		self.pauseButton?.hidden = !play
		self.playButton?.hidden = play
	}

	private func updateAudioDurationLabel(interval: Float) -> String {
		let interval = Int(interval)
		let seconds = interval % 60
		let minutes = (interval / 60) % 60
		return String(format: "%02d:%02d", minutes, seconds)
	}

	private func changeCurrentTime(time: Float, slider: Bool) {
		if slider {
			audio?.currentTime = NSTimeInterval(time)
		}
		else {
			audio?.currentTime += NSTimeInterval(time)
		}
		updateProgress()
		updateDurationLabel()
	}

	private func disableComponents() {
		playButton?.enabled = false
		rewindButton?.enabled = false
		forwardButton?.enabled = false
		sliderDuration?.enabled = false
		sliderVolume?.enabled = false
		pauseButton?.hidden = true
	}

	func updateProgress() {
		self.sliderDuration?.setValue(Float(audio!.currentTime), animated: true)
	}

	func updateDurationLabel() {
		let currentTime = audio!.currentTime
		if audio!.playing && currentTime == audio!.duration {
			self.updateView(false)
			self.audioProgressLabel?.text = updateAudioDurationLabel(0)
		}
		else {
			self.audioProgressLabel?.text = updateAudioDurationLabel(Float(currentTime))
		}
	}
}
