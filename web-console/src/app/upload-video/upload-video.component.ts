import {Component, OnInit} from "@angular/core";
import {MdDialog} from "@angular/material";
import {UploadVideoDialogComponent} from "../upload-video-dialog/upload-video-dialog.component";
import {Http, URLSearchParams, Headers} from "@angular/http";
import {environment} from "../../environments/environment";
import {VideoPlayDialogComponent} from "../video-play-dialog/video-play-dialog.component";
import {DialogUtilsService} from "../services/dialog-utils.service";
import {IPaginationModel} from "../shared/pagination/pagination";

const CREATE_VIDEO_URL = environment.SERVER_BASE_PATH + "videos";
const QUERY_VIDEOS_URL = environment.SERVER_BASE_PATH + "videos";
const DELETE_VIDEO_URL = environment.SERVER_BASE_PATH + "videos";

@Component({
  selector: 'app-upload-video',
  templateUrl: './upload-video.component.html',
  styleUrls: ['./upload-video.component.css']
})
export class UploadVideoComponent implements OnInit {

  videos = [];
  pageNumber = 0;
  pageSize = 10;
  totalElements = 0;
  name = "";

  constructor(private dialog: MdDialog,
              private http: Http,
              private dialogUtils: DialogUtilsService) {
  }

  ngOnInit() {
  }

  get pagination(): IPaginationModel {
    return {
      currentPage: this.pageNumber + 1,
      itemsPerPage: this.pageSize,
      totalItems: this.totalElements
    };
  }

  upload() {
    let dialogRef = this.dialog.open(UploadVideoDialogComponent, {
      width: '600px',
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {

        let headers = new Headers();
        headers.append("Content-Type", "application/json");

        this.http.post(CREATE_VIDEO_URL, JSON.stringify(result), {headers})
          .subscribe(() => {
            this.pageNumber = 0;
            this.name = "";
            this.query();
          });
      }
    });
  }

  query() {
    let params = new URLSearchParams();
    params.set('page', this.pageNumber.toString());
    params.set('size', this.pageSize.toString());

    if (this.name) {
      params.set("name", this.name);
    }

    this.http.get(QUERY_VIDEOS_URL, {search: params})
      .map(response => response.json())
      .subscribe(page => {
        this.videos = page.content;
        this.pageNumber = page.number;
        this.pageSize = page.size;
        this.totalElements = page.totalElements;
      });
  }

  queryByName(name) {
    this.name = name;
    this.query();
  }

  onPageChanged(event) {
    this.pageNumber= event.pagination.currentPage - 1;
    this.pageSize= event.pagination.itemsPerPage;
    this.query();
  }

  play(video) {
    if (video.status != 'READY') {
      return;
    }
    let dialogRef = this.dialog.open(VideoPlayDialogComponent);
    dialogRef.componentInstance.play(video.browseFileUrl);
  }

  delete(video) {
    this.dialogUtils.confirm("是否删除此视频？").subscribe(result => {
      if (result) {
        this.http.delete(DELETE_VIDEO_URL + "/" + video.id)
          .subscribe(() => {
            this.query();
          });
      }
    });
  }
}
