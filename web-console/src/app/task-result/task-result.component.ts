import {Component, OnInit, ElementRef} from "@angular/core";
import {Http, Headers, URLSearchParams} from "@angular/http";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {environment} from "../../environments/environment";
import {DialogUtilsService} from "../services/dialog-utils.service";
import {MdDialog} from "@angular/material";
import {AddResultDialogComponent} from "../add-result-dialog/add-result-dialog.component";
import {PositionPipe} from "../pipes/position.pipe";

const GET_TASK_URL = environment.SERVER_BASE_PATH + "tasks";
const GET_VIDEO_URL = environment.SERVER_BASE_PATH + "videos";
const SAVE_RESULT_URL = environment.SERVER_BASE_PATH + "tasks/saveResult";
const DELETE_RESULT_URL = environment.SERVER_BASE_PATH + "tasks/deleteResult";
const ADD_RESULT_URL = environment.SERVER_BASE_PATH + "tasks";

let videojs = require('video.js');

@Component({
  selector: 'app-task-result',
  templateUrl: './task-result.component.html',
  styleUrls: ['./task-result.component.css']
})
export class TaskResultComponent implements OnInit {

  task: any = { results: []};
  video: any;
  player: any;

  constructor(private http: Http,
              private route: ActivatedRoute,
              private router: Router,
              private elementRef: ElementRef,
              private dialogUtils: DialogUtilsService,
              private dialog: MdDialog) {
  }

  ngOnInit() {

    this.route.params
      .switchMap((params: Params) => {
        return this.http.get(GET_TASK_URL + "/" + params['taskId'])
      })
      .map(response => response.json())
      .subscribe(task => {
        this.task = task;
        this.http.get(GET_VIDEO_URL + "/" + this.task.videoId)
          .map(response => response.json())
          .subscribe(video => {
            this.initPlayer(video);
          });
      });

    let video = this.elementRef.nativeElement.querySelector('video');
    this.player = videojs(video);
  }

  clickResult(result) {
    this.player.pause();
    setTimeout(() => {
      this.player.currentTime(result.position / 1000);
    });
    this.selectResult(result);
  }

  selectResult(result) {
    this.task.results.forEach(result => result.selected = false);
    result.selected = true;

    //scroll to result
    setTimeout(() => {
      let selectedRow = this.elementRef.nativeElement.querySelector('tr.selected');
      if (selectedRow != null) {
        this.scrollIntoViewIfNeeded(selectedRow);
      }
    });
  }

  saveText(event, result) {

    let button = <HTMLElement>event.currentTarget;
    let text = button.closest('tr').querySelector('textarea').value;
    //let text = button.closest('tr').querySelector('input').value;

    let headers = new Headers();
    headers.append("Content-Type", "application/x-www-form-urlencoded");

    let formData = new URLSearchParams();
    formData.append("text", text);

    this.http.post(SAVE_RESULT_URL + "/" + result.id, formData.toString(), {headers})
      .subscribe(() => {
        result.changed = false;
      });
  }

  getExportHref() {
    return this.task.id ? environment.SERVER_BASE_PATH + "tasks/" + this.task.id + "/export" : "#";
  }

  back() {
    this.router.navigate(['/task-list']);
  }

  addResult() {
    this.player.pause();
    let dialogRef = this.dialog.open(AddResultDialogComponent, {
      width: '600px',
      position: {
        right: '40px'
      }
    });
    dialogRef.componentInstance.position = new PositionPipe().transform(this.player.currentTime().toFixed() * 1000);
    dialogRef.afterClosed().subscribe(result => {
      if (result) {

        let headers = new Headers();
        headers.append("Content-Type", "application/x-www-form-urlencoded");

        let formData = new URLSearchParams();
        formData.append("text", result.text);
        formData.append("position", String(new PositionPipe().transformFrom(result.position)));

        this.http.post(ADD_RESULT_URL + "/" + this.task.id + "/addResult", formData.toString(), {headers})
          .subscribe(() => {
            this.refreshTask();
          });
      }
    });
  }

  deleteResult(result) {
    this.dialogUtils.confirm("是否删除此字幕？").subscribe(ok => {
      if (ok) {
        this.http.delete(DELETE_RESULT_URL + "/" + result.id)
          .subscribe(() => {
            this.refreshTask();
          });
      }
    });
  }

  private initPlayer(video) {
    this.player.src(video.browseFileUrl, 'video/mp4');
    this.player.pause();
    this.player.on('timeupdate', () => {
      let currentTime = this.player.currentTime();
      // scroll to current time
      this.task.results.forEach(result => {
        if (result.position / 1000 == currentTime.toFixed()) {
          this.selectResult(result);
        }
      });
    });
  }

  private refreshTask() {
    this.http.get(GET_TASK_URL + "/" + this.task.id)
      .map(response => response.json())
      .subscribe(task => this.task = task);
  }

  private scrollIntoViewIfNeeded(target) {
    var rect = target.getBoundingClientRect();
    if (rect.bottom > window.innerHeight) {
      target.scrollIntoView(false);
    }
    if (rect.top < 0) {
      target.scrollIntoView();
    }
  }
}
