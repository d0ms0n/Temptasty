import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SampleViewerComponent } from './sample-viewer.component';

describe('SampleViewerComponent', () => {
  let component: SampleViewerComponent;
  let fixture: ComponentFixture<SampleViewerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SampleViewerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SampleViewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
