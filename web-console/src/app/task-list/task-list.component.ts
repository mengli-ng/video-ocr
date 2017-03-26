import {Component, OnInit} from '@angular/core';
import {environment} from "../../environments/environment";
import {URLSearchParams, Http, Headers} from "@angular/http";
import {MdDialogRef, MdDialog} from "@angular/material";
import {CreateTaskDialogComponent} from "../create-task-dialog/create-task-dialog.component";
import {Router} from "@angular/router";
import {DialogUtilsService} from "../services/dialog-utils.service";
import {IPaginationModel} from "../shared/pagination/pagination";

const CREATE_TASK_URL = environment.SERVER_BASE_PATH + "tasks";
const QUERY_TASKS_URL = environment.SERVER_BASE_PATH + "tasks";
const DELETE_TASKS_URL = environment.SERVER_BASE_PATH + "tasks";

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})
export class TaskListComponent implements OnInit {

  tasks = [];
  pageNumber = 0;
  pageSize = 10;
  totalElements = 0;
  videoName = "";

  constructor(private http: Http,
              private dialog: MdDialog,
              private router: Router,
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

  query() {
    let params = new URLSearchParams();
    params.set('page', this.pageNumber.toString());
    params.set('size', this.pageSize.toString());

    if (this.videoName) {
      params.set("videoName", this.videoName);
    }

    this.http.get(QUERY_TASKS_URL, {search: params})
      .map(response => response.json())
      .subscribe(page => {
        this.tasks = page.content;
        this.pageNumber = page.number;
        this.pageSize = page.size;
        this.totalElements = page.totalElements;
      });
  }

  queryByVideoName(videoName) {
    this.videoName = videoName;
    this.query();
  }

  onPageChanged(event) {
    this.pageNumber = event.pagination.currentPage - 1;
    this.pageSize = event.pagination.itemsPerPage;
    this.query();
  }

  create() {
    let dialogRef = this.dialog.open(CreateTaskDialogComponent, {
      width: '600px',
      disableClose: true
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {

        let task = {
          videoId: result.videoId,
          detectLeft: result.detectRange.left,
          detectTop: result.detectRange.top,
          detectWidth: result.detectRange.width,
          detectHeight: result.detectRange.height,
          language: result.language
        };

        let headers = new Headers();
        headers.append("Content-Type", "application/json");

        this.http.post(CREATE_TASK_URL, JSON.stringify(task), {headers})
          .subscribe(() => {
            this.pageNumber = 0;
            this.videoName = "";
            this.query();
          });
      }
    });
  }

  delete(task) {
    this.dialogUtils.confirm("是否删除此任务？").subscribe(result => {
      if (result) {
        this.http.delete(DELETE_TASKS_URL + "/" + task.id)
          .subscribe(() => {
            this.query();
          });
      }
    });
  }

  showResult(task) {
    this.router.navigate(['/task-result', {taskId: task.id}]);
  }
}
