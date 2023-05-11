import {Component, OnInit, ViewChild} from '@angular/core';
import {ReceiverService} from "../service/receiver.service";
import {Measurement} from "../model/measurement.model";

@Component({
  selector: 'app-measurement-viewer',
  templateUrl: './measurement-viewer.component.html',
  styleUrls: ['./measurement-viewer.component.scss']
})
export class MeasurementViewerComponent implements OnInit {
  @ViewChild('myChartCanvas') chartCanvas: any;

  chartData: any[] = [];
  chartLabels: any[] = [];
  chartOptions: any = {
    responsive: true
  };

  constructor(private receiverService: ReceiverService) {
  }

  ngOnInit(): void {
  }

  ngAfterViewInit() {
    this.receiverService.getAllMeasurements().then(measurements => {
      this.addData(measurements);
    });
  }

  addData(measurements: Measurement[]): void {
    let dataset1 = measurements.filter(value => value.name == "sensor1");
    let dataset1Mapped = dataset1.map(measurement => {
      return {x: measurement.time, y: measurement.value};
    });

    let dataset2 = measurements.filter(value => value.name == "sensor2");
    let dataset2Mapped = dataset2.map(measurement => {
      return {x: measurement.time, y: measurement.value};
    });

    this.chartData = [
      {
        data: dataset1Mapped,
        label: 'Sensor 1',
      },
      {
        data: dataset2Mapped,
        label: 'Sensor 2',
      },
    ];
  }
}
