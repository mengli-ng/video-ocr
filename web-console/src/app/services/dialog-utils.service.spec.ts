import { TestBed, inject } from '@angular/core/testing';
import { DialogUtilsService } from './dialog-utils.service';

describe('DialogUtilsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DialogUtilsService]
    });
  });

  it('should ...', inject([DialogUtilsService], (service: DialogUtilsService) => {
    expect(service).toBeTruthy();
  }));
});
