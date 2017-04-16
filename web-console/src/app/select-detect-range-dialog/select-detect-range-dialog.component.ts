import {Component, OnInit, ElementRef, AfterViewInit} from "@angular/core";
import {MdDialogRef} from "@angular/material";

@Component({
  selector: 'app-select-detect-region-dialog',
  templateUrl: 'select-detect-range-dialog.component.html',
  styleUrls: ['select-detect-range-dialog.component.css']
})
export class SelectDetectRegionDialogComponent implements OnInit, AfterViewInit {

  private videoWidth: number;
  private videoHeight: number;
  private drawContext: any;
  private bgContext: any;
  private drawRect = {
    startX: 0,
    startY: 0,
    width: 0,
    height: 0
  };
  private painting = false;

  constructor(private elementRef: ElementRef,
              private dialogRef: MdDialogRef<SelectDetectRegionDialogComponent>) {
  }

  ngOnInit() {
  }

  public drawVideo(video: HTMLVideoElement) {

    let canvasContainer = this.elementRef.nativeElement.querySelector("#canvas-container");
    let drawCanvas = this.elementRef.nativeElement.querySelector("#draw-canvas");
    let bgCanvas = this.elementRef.nativeElement.querySelector("#bg-canvas");

    let scale = 1;
    this.videoWidth = video.videoWidth;
    this.videoHeight = video.videoHeight;

    if (this.videoWidth > 600) {
      this.videoWidth = 600;

      scale = this.videoWidth / video.videoWidth;
      this.videoHeight = video.videoHeight * scale;
    }

    canvasContainer.style.width = this.videoWidth + "px";
    canvasContainer.style.height = this.videoHeight + "px";
    drawCanvas.width = bgCanvas.width = this.videoWidth;
    drawCanvas.height = bgCanvas.height = this.videoHeight;

    this.drawContext = drawCanvas.getContext('2d');
    this.drawContext.strokeStyle = "red";
    this.drawContext.lineWidth = 1;

    this.bgContext = bgCanvas.getContext('2d');
    this.bgContext.scale(scale, scale);
    this.bgContext.drawImage(video, 0, 0);

    // draw events
    drawCanvas.addEventListener('mousedown', (e) => {
      let canvasRect = drawCanvas.getBoundingClientRect();
      this.drawRect.startX = e.clientX - canvasRect.left;
      this.drawRect.startY = e.clientY - canvasRect.top;
      this.painting = true;
    });

    drawCanvas.addEventListener('mousemove', (e) => {
      if (this.painting) {
        let canvasRect = drawCanvas.getBoundingClientRect();
        this.drawRect.width = (e.clientX - canvasRect.left) - this.drawRect.startX;
        this.drawRect.height = (e.clientY - canvasRect.top) - this.drawRect.startY;
        this.drawContext.clearRect(0, 0, drawCanvas.width, drawCanvas.height);
        this.drawContext.strokeRect(this.drawRect.startX, this.drawRect.startY, this.drawRect.width, this.drawRect.height);
      }
    });

    drawCanvas.addEventListener('mouseup', () => this.painting = false);
    drawCanvas.addEventListener('mouseleave', () => this.painting = false);
  }

  ngAfterViewInit() {
  }

  ok() {
    this.dialogRef.close({
      left: (this.drawRect.startX / this.videoWidth).toFixed(2),
      top: (this.drawRect.startY / this.videoHeight).toFixed(2),
      width: (this.drawRect.width / this.videoWidth).toFixed(2),
      height: (this.drawRect.height / this.videoHeight).toFixed(2),
    });
  }
}
