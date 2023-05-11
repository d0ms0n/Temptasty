import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {firstValueFrom, map} from "rxjs";
import {Measurement} from "../model/measurement.model";
import {environment} from "src/environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ReceiverService {

  constructor(private httpClient: HttpClient) {
  }

  getAllMeasurements(): Promise<Measurement[]> {
    const measurementUrl = environment.apiURL + "/measurements";
    return firstValueFrom(
      this.httpClient.get<Measurement[]>(measurementUrl).pipe(
        map(measurement => {
          return measurement;
        })
      )
    );
  }
}
