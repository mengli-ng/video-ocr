import { Component, OnInit } from '@angular/core';
import {MdDialogRef} from "@angular/material";

@Component({
  selector: 'app-add-result-dialog',
  templateUrl: './add-result-dialog.component.html',
  styleUrls: ['./add-result-dialog.component.css']
})
export class AddResultDialogComponent implements OnInit {

  position = "00:00:00";
  text = "";

  constructor(private dialogRef: MdDialogRef<AddResultDialogComponent>) {
  }

  ngOnInit() {
  }

  ok() {
    this.dialogRef.close({
      position: this.position,
      text: this.text
    });
  }
}
