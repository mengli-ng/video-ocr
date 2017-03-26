import { Component, OnInit } from '@angular/core';
import {MdDialogRef} from "@angular/material";

@Component({
  selector: 'app-confirm-dialog',
  templateUrl: './confirm-dialog.component.html',
  styleUrls: ['./confirm-dialog.component.css']
})
export class ConfirmDialogComponent implements OnInit {

  message = "";

  constructor(private dialogRef: MdDialogRef<ConfirmDialogComponent>) { }

  ngOnInit() {
  }

  ok() {
    this.dialogRef.close(true);
  }
}
