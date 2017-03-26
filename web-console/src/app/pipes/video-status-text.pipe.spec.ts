import { VideoStatusTextPipe } from './video-status-text.pipe';

describe('VideoStatusTextPipe', () => {
  it('create an instance', () => {
    const pipe = new VideoStatusTextPipe();
    expect(pipe).toBeTruthy();
  });
});
