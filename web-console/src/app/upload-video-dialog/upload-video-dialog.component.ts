import {Component, OnInit, ElementRef} from "@angular/core";
import {MdDialogRef} from "@angular/material";
import {environment} from "../../environments/environment";
import {Http, URLSearchParams} from "@angular/http";
import {DialogUtilsService} from "../services/dialog-utils.service";

let Resumable = require("resumablejs");

const CHECK_VIDEO_EXISTS_URL = environment.SERVER_BASE_PATH + 'videos/exists';
const UPLOAD_URL = environment.SERVER_BASE_PATH + 'videos/upload';

@Component({
  selector: 'app-upload-video-dialog',
  templateUrl: './upload-video-dialog.component.html',
  styleUrls: ['upload-video-dialog.component.scss']
})
export class UploadVideoDialogComponent implements OnInit {

  resumable: any;
  uploading = false;
  uploaded = false;

  constructor(private dialogRef: MdDialogRef<UploadVideoDialogComponent>,
              private http: Http,
              private dialogUtils: DialogUtilsService,
              private elementRef: ElementRef) {
  }

  ngOnInit() {

    this.resumable = new Resumable({
      target: UPLOAD_URL,
      testChunks: false
    });

    this.resumable.assignBrowse(this.elementRef.nativeElement.querySelectorAll('.btn-browse'));
    Array.from((<HTMLElement>this.elementRef.nativeElement).querySelectorAll('input[type=file]')).forEach(file => {
      file.setAttribute('accept', '.avi,.divx,.3gp,.mov,.mpeg,.mpg,.xvid,.flv,.asf,.rm,.dat,.mp4,.vob,.mkv,.wmv,.rmvb,.ts,video/*');
    });

    this.resumable.on('fileAdded', file => {

      let params = new URLSearchParams();
      params.set('name', file.fileName);

      this.http.get(CHECK_VIDEO_EXISTS_URL, {search: params})
        .map(response => response.json())
        .subscribe(result => {
          if (result) {
            this.dialogUtils.confirm(file.fileName + " 已存在，是否覆盖已有文件？")
              .subscribe(result => {
                if (!result) {
                  this.deleteFile(file);
                }
              });
          }
        });
    });

    this.resumable.on("fileProgress", file => {
      file.progressPercent = file.progress(false) * 100;
    });

    this.resumable.on("fileSuccess", file => {
      file.succeed = true;
    });

    this.resumable.on("fileError", file => {
      file.error = true;
    });

    this.resumable.on("complete", () => {
      this.uploaded = true;
      this.uploading = false;
  });
  }

  get files(): any[] {
    return this.resumable.files ? this.resumable.files : [];
  }

  deleteFile(file) {
    this.resumable.removeFile(file);
  }

  upload() {

    if (this.files.length == 0) {
      this.dialogUtils.alert("请添加一个或多个视频。");
      return;
    }

    this.uploading = true;
    this.resumable.upload();
  }

  ok() {

    let files = this.files.filter(file => file.succeed).map(file => {
      return {
        fileName: file.fileName,
        fileIdentifier: file.uniqueIdentifier
      }
    });

    if (files.length == 0) {
      this.dialogUtils.alert("请上传一个或多个视频。");
      return;
    }

    this.dialogRef.close(files);
  }
}
