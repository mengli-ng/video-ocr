import {Component, ViewEncapsulation, AfterViewInit} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['app.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class AppComponent implements AfterViewInit {

  navItems = [
    {name: '视频管理', icon: 'video_library', route: 'upload-video'},
    {name: '任务管理', icon: 'assignment', route: 'task-list'},
  ];

  ngAfterViewInit(): void {
    document.body.classList.add("loaded");
  }
}
