import {Pipe, PipeTransform} from "@angular/core";

@Pipe({
  name: 'taskStatusText'
})
export class TaskStatusTextPipe implements PipeTransform {

  transform(value: any, args?: any): any {
    return value == 'NEW' ? '待执行'
      : value == 'EXECUTING' ? '正在执行'
      : value == 'FINISHED' ? '执行完成'
      : value == 'FAILED' ? '执行失败'
      : '';
  }
}
