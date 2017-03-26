import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'videoStatusText'
})
export class VideoStatusTextPipe implements PipeTransform {

  transform(value: any, args?: any): any {
    return value == 'NEW' ? '待转码'
      : value == 'TRANSCODING' ? '转码中'
      : value == 'TRANSCODED' ? '转码完成'
      : value == 'TRANSCODE_FAILED' ? '转码失败'
      : value == 'READY' ? '已就绪'
      : '';
  }
}
