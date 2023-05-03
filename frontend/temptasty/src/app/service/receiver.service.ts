import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import { firstValueFrom, map } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ReceiverService {

  constructor(private httpClient: HttpClient) {
  }

  getAllSamples(): Promise<string> {
    return firstValueFrom(
      this.httpClient.get<{ _id: string }>("/samples/").pipe(
        map(value => {
          const folderId = value._id;
          if (folderId) {
            return folderId;
          } else {
            throw {message: "could not get rootFolderId"};
          }
        })
      )
    );
  }
}
