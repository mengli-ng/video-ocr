import { TaskStatusTextPipe } from './task-status-text.pipe';

describe('TaskStatusTextPipe', () => {
  it('create an instance', () => {
    const pipe = new TaskStatusTextPipe();
    expect(pipe).toBeTruthy();
  });
});
