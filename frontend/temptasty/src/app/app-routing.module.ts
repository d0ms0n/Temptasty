import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MeasurementViewerComponent} from "./measurement-viewer/measurement-viewer.component";

const routes: Routes = [
  {
    path: 'measurement-viewer',
    component: MeasurementViewerComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
