import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectDetectRegionDialogComponent } from './select-detect-range-dialog.component';

describe('SelectDetectRegionDialogComponent', () => {
  let component: SelectDetectRegionDialogComponent;
  let fixture: ComponentFixture<SelectDetectRegionDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SelectDetectRegionDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SelectDetectRegionDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
