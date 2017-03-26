import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'position'
})
export class PositionPipe implements PipeTransform {

  transform(value: any, args?: any): any {

    let seconds = value / 1000;
    let second = seconds % 60;
    let minute = (seconds - second) / 60 % 60;
    let hour = (seconds - minute * 60 - second) / 3600 % 24;

    return (hour < 10 ? "0" + hour : hour.toString()) + ":"
      + (minute < 10 ? "0" + minute : minute.toString()) + ":"
      + (second < 10 ? "0" + second : second.toString())
  }

  transformFrom(text) {
    let array = text.split(':');
    return (Number(array[2]) + Number(array[1]) * 60 + Number(array[0]) * 3600) * 1000;
  }
}
