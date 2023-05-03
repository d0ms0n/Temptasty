import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {SampleViewerComponent} from "./sample-viewer/sample-viewer.component";

const routes: Routes = [
  {
    path: 'sample-viewer',
    component: SampleViewerComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
