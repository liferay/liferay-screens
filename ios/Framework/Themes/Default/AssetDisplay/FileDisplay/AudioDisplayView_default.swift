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


public class AudioDisplayView_default: BaseScreenletView, AudioDisplayViewModel {
	
	@IBOutlet weak var view: UIView!

	@IBOutlet weak var playButton: UIButton!
	@IBOutlet weak var pauseButton: UIButton!

	@IBOutlet weak var sliderDuration: UISlider! {
		didSet {
			sliderDuration.setThumbImage(NSBundle.imageInBundles(name: "default-point", currentClass: self.dynamicType), forState: UIControlState.Normal)
		}
	}
	
	@IBOutlet weak var sliderVolume: UISlider! {
		didSet {
			sliderVolume.setThumbImage(NSBundle.imageInBundles(name: "default-point", currentClass: self.dynamicType), forState: UIControlState.Normal)
		}
	}


	@IBOutlet weak var audioProgressLabel: UILabel!
	@IBOutlet weak var audioDurationLabel: UILabel!

	@IBOutlet weak var titleLabel: UILabel!

	public var fileEntry: FileEntry? {
		didSet {
			if let fileEntry = fileEntry {
				self.pauseButton.hidden = true
				let path = LiferayServerContext.server + fileEntry.url
				let url = NSURL(string: path)
				let audioData = NSData(contentsOfURL: url!)
				do {
					self.audio = try AVAudioPlayer(data: audioData!)
					if let audio = audio {
						audio.volume = 0.5
						audio.numberOfLoops = -1
						playAction()

						self.sliderDuration.maximumValue = Float(audio.duration)
						self.audioDurationLabel.text = updateAudioDurationLabel(Float(audio.duration))
					}
				} catch let error as NSError {
					print("AVAudioPlayer error: \(error.debugDescription)")
				}
			}
		}
	}

	public var audioTitle: String? {
		didSet {
			self.titleLabel.text = audioTitle
		}
	}

	public var audio: AVAudioPlayer?

	public var timer: NSTimer?
	public var duration: NSTimer?

	public override func didMoveToWindow() {
		if window == nil {
			audio?.stop()
		}
	}

	@IBAction func pauseAction() {
		audio?.pause()
		timer?.invalidate()
		duration?.invalidate()
		self.updateView(false)
	}

	@IBAction func playAction() {
		audio?.play()
		timer = NSTimer.scheduledTimerWithTimeInterval(0.01, target: self, selector: #selector(AudioDisplayView_default.updateProgress), userInfo: nil, repeats: true)
		duration = NSTimer.scheduledTimerWithTimeInterval(0.01, target: self, selector: #selector(AudioDisplayView_default.updateDurationLabel), userInfo: nil, repeats: true)
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
		self.pauseButton.hidden = !play
		self.playButton.hidden = play
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

	func updateProgress() {
		self.sliderDuration.setValue(Float(audio!.currentTime), animated: true)
	}

	func updateDurationLabel() {
		let currentTime = audio!.currentTime
		if audio!.playing && currentTime == audio!.duration {
			self.updateView(false)
			self.audioProgressLabel.text = updateAudioDurationLabel(0)
		}
		else {
			self.audioProgressLabel.text = updateAudioDurationLabel(Float(currentTime))
		}
	}
}
