import {Component, OnInit, ViewChild} from '@angular/core';
import {ReceiverService} from "../service/receiver.service";
import {Sample} from "../model/sample.model";

@Component({
  selector: 'app-sample-viewer',
  templateUrl: './sample-viewer.component.html',
  styleUrls: ['./sample-viewer.component.scss']
})
export class SampleViewerComponent implements OnInit {
  @ViewChild('myChartCanvas') chartCanvas: any;

  chartData: any[] = [];
  chartLabels: any[] = [];
  chartOptions: any = {
    responsive: true
  };

  constructor(private receiverService: ReceiverService) {
  }

  ngOnInit(): void {
    this.receiverService.getAllSamples().then(samples => {
      this.addData(samples);
    });
  }

  addData(samples: Sample[]): void {
    let dataset1 = samples.filter(value => value.name == "sensor1");
    let dataset1Mapped = dataset1.map(sample => {
      return {x: sample.time, y: sample.value};
    });

    let dataset2 = samples.filter(value => value.name == "sensor2");
    let dataset2Mapped = dataset2.map(sample => {
      return {x: sample.time, y: sample.value};
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

    this.chartCanvas.chart.update();
  }
}
