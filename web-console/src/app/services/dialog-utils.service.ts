import {Injectable} from '@angular/core';
import {MdDialogRef, MdDialog} from "@angular/material";
import {AlertDialogComponent} from "../shared/alert-dialog/alert-dialog.component";
import {ConfirmDialogComponent} from "../shared/confirm-dialog/confirm-dialog.component";
import {Observable} from "rxjs";

@Injectable()
export class DialogUtilsService {

  constructor(private dialog: MdDialog) {
  }

  public alert(message): Observable<any> {
    let dialogRef = this.dialog.open(AlertDialogComponent, {
      disableClose: true
    });
    dialogRef.componentInstance.message = message;
    return dialogRef.afterClosed();
  }

  public confirm(message): Observable<any> {
    let dialogRef = this.dialog.open(ConfirmDialogComponent, {
      disableClose: true
    });
    dialogRef.componentInstance.message = message;
    return dialogRef.afterClosed();
  }
}
