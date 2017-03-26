import {Component, OnInit, AfterViewInit, OnDestroy, ElementRef, ViewChild} from "@angular/core";
import {FormControl} from "@angular/forms";
import {Http, URLSearchParams} from "@angular/http";
import {environment} from "../../environments/environment";
import {MdDialog, MdDialogRef, MdSelect} from "@angular/material";
import {Observable} from "rxjs";
import {DialogUtilsService} from "../services/dialog-utils.service";
import {SelectDetectRegionDialogComponent} from "../select-detect-range-dialog/select-detect-range-dialog.component";

let videojs = require('video.js');
const QUERY_READY_VIDEOS_URL = environment.SERVER_BASE_PATH + "videos/ready";
const GET_LANGUAGE_URL = environment.SERVER_BASE_PATH + "languages";

@Component({
  selector: 'app-create-task-dialog',
  templateUrl: './create-task-dialog.component.html',
  styleUrls: ['./create-task-dialog.component.css']
})
export class CreateTaskDialogComponent implements OnInit, AfterViewInit, OnDestroy {

  selectVideoCtrl: FormControl;
  selectedVideos: any;

  player: any;
  detectRange = null;
  languages = [];
  selectedLanguage;

  @ViewChild("languageSelect")
  languageSelect: MdSelect;

  constructor(private http: Http,
              private dialog: MdDialog,
              private dialogRef: MdDialogRef<CreateTaskDialogComponent>,
              private dialogUtils: DialogUtilsService,
              private elementRef: ElementRef) {

    this.selectVideoCtrl = new FormControl();
    this.selectedVideos = this.selectVideoCtrl.valueChanges
      .startWith(null)
      .switchMap(value => this.filterSelectedVideos(value));
  }

  get detectRangeText() {
    return `left=${this.detectRange.left}, 
            top=${this.detectRange.top}, 
            width=${this.detectRange.width}, 
            height=${this.detectRange.height}`;
  }

  ngOnInit() {
    this.http.get(GET_LANGUAGE_URL)
      .map(response => response.json())
      .subscribe(languages => {
        this.languages = languages;
        if (this.languages.length) {
          this.selectedLanguage = this.languages[0].code;
        }
      });
  }

  ngAfterViewInit() {
    this.player = videojs(this.elementRef.nativeElement.querySelector('video'));
  }

  ngOnDestroy() {
    this.player.dispose();
  }

  filterSelectedVideos(value) {

    if (value && value.browseFileUrl) {
      this.player.src({type: "video/mp4", src: this.selectVideoCtrl.value.browseFileUrl});
      return Observable.empty();
    }

    let params = new URLSearchParams();

    if (value) {
      params.set("name", name);
    }

    return this.http.get(QUERY_READY_VIDEOS_URL, {search: params})
      .map(response => response.json());
  }

  displayVideoName(video: any) {
    return video ? video.name : null;
  }

  detect() {
    if (!this.selectVideoCtrl.value) {
      this.dialogUtils.alert("请先选择视频");
      return;
    }
    this.player.pause();

    let dialogRef = this.dialog.open(SelectDetectRegionDialogComponent, {
      disableClose: true
    });
    dialogRef.componentInstance.drawVideo(this.elementRef.nativeElement.querySelector('video'));
    dialogRef.afterClosed().subscribe(detectRange => {
      this.detectRange = detectRange;
    });
  }

  ok() {
    if (!this.selectVideoCtrl.value) {
      this.dialogUtils.alert('请先选择视频');
      return;
    }
    if (!this.detectRange) {
      this.dialogUtils.alert('请先选择字幕区域');
      return;
    }
    this.dialogRef.close({
      videoId: this.selectVideoCtrl.value.id,
      detectRange: this.detectRange,
      language: this.selectedLanguage
    });
  }
}
