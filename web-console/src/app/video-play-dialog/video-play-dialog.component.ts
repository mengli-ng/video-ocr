import {Component, OnInit, ElementRef, OnDestroy, ViewEncapsulation, AfterViewInit} from "@angular/core";

let videojs = require('video.js');

@Component({
  selector: 'app-video-play-dialog',
  templateUrl: './video-play-dialog.component.html',
  styleUrls: ['./video-play-dialog.component.css'],
  encapsulation: ViewEncapsulation.None,
})
export class VideoPlayDialogComponent implements OnInit, AfterViewInit, OnDestroy {

  player: any;
  url: any;

  constructor(private elementRef: ElementRef) {
  }

  ngOnInit() {
    this.player = videojs(this.elementRef.nativeElement.querySelector('video'));
  }

  ngAfterViewInit() {
    this.player.src({type: "video/mp4", src: this.url});
  }

  ngOnDestroy() {
    this.player.dispose();
  }

  public play(url) {
    this.url = url;
  }
}
