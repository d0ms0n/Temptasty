import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {firstValueFrom, map} from "rxjs";
import {Sample} from "../model/sample.model";
import {environment} from "src/environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ReceiverService {

  constructor(private httpClient: HttpClient) {
  }

  getAllSamples(): Promise<Sample[]> {
    let sampleUrl = environment.apiURL + "/samples";
    return firstValueFrom(
      this.httpClient.get<Sample[]>(sampleUrl).pipe(
        map(sample => {
          return sample;
        })
      )
    );
  }
}
