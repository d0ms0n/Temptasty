import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MeasurementViewerComponent } from './measurement-viewer.component';

describe('MeasurementViewerComponent', () => {
  let component: MeasurementViewerComponent;
  let fixture: ComponentFixture<MeasurementViewerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MeasurementViewerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MeasurementViewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
