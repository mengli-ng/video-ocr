import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {Routes, RouterModule} from "@angular/router";
import {AppComponent} from "./app.component";
import {UploadVideoComponent} from "./upload-video/upload-video.component";
import {MaterialModule} from "@angular/material";
import {UploadVideoDialogComponent} from "./upload-video-dialog/upload-video-dialog.component";
import {FlexLayoutModule} from "@angular/flex-layout";
import {VideoStatusTextPipe} from "./pipes/video-status-text.pipe";
import {SettingsComponent} from "./settings/settings.component";
import {VideoPlayDialogComponent} from "./video-play-dialog/video-play-dialog.component";
import {TaskListComponent} from "./task-list/task-list.component";
import {CreateTaskDialogComponent} from "./create-task-dialog/create-task-dialog.component";
import {SelectDetectRegionDialogComponent} from "./select-detect-range-dialog/select-detect-range-dialog.component";
import {TaskStatusTextPipe} from "./pipes/task-status-text.pipe";
import {HomeComponent} from "./home/home.component";
import {AlertDialogComponent} from "./shared/alert-dialog/alert-dialog.component";
import {ConfirmDialogComponent} from "./shared/confirm-dialog/confirm-dialog.component";
import {DialogUtilsService} from "./services/dialog-utils.service";
import {MdDataTable} from "./shared/data-table/data_table";
import {MdDataTableHeaderSelectableRow, MdDataTableSelectableRow} from "./shared/data-table/data_table_selectable_tr";
import {
  MdPaginationItemsPerPage, MdPaginationControls, MdPagination,
  MdPaginationRange
} from "./shared/pagination/pagination";
import {PaginationService} from "./shared/pagination/pagination_service";
import { TaskResultComponent } from './task-result/task-result.component';
import { PositionPipe } from './pipes/position.pipe';
import { AddResultDialogComponent } from './add-result-dialog/add-result-dialog.component';

const ROUTES: Routes = [
  {path: '', component: HomeComponent},
  {path: 'upload-video', component: UploadVideoComponent},
  {path: 'task-list', component: TaskListComponent},
  {path: 'task-result', component: TaskResultComponent},
  {path: 'settings', component: SettingsComponent},
];

@NgModule({
  declarations: [
    AppComponent,
    UploadVideoComponent,
    UploadVideoDialogComponent,
    VideoStatusTextPipe,
    SettingsComponent,
    VideoPlayDialogComponent,
    TaskListComponent,
    CreateTaskDialogComponent,
    SelectDetectRegionDialogComponent,
    TaskStatusTextPipe,
    HomeComponent,
    AlertDialogComponent,
    ConfirmDialogComponent,
    MdDataTable,
    MdDataTableHeaderSelectableRow,
    MdDataTableSelectableRow,
    MdPagination,
    MdPaginationControls,
    MdPaginationItemsPerPage,
    MdPaginationRange,
    TaskResultComponent,
    PositionPipe,
    AddResultDialogComponent
  ],
  imports: [
    RouterModule.forRoot(ROUTES, { useHash: true }),
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    MaterialModule,
    FlexLayoutModule
  ],
  providers: [
    DialogUtilsService,
    PaginationService
  ],
  bootstrap: [
    AppComponent
  ],
  entryComponents: [
    AlertDialogComponent,
    ConfirmDialogComponent,
    UploadVideoDialogComponent,
    VideoPlayDialogComponent,
    CreateTaskDialogComponent,
    SelectDetectRegionDialogComponent,
    AddResultDialogComponent
  ]
})
export class AppModule {
}
