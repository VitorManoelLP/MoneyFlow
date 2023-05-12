import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomePageComponent } from './home-page.component';
import { RouterModule, Routes } from '@angular/router';
import { GraficoModule } from "../grafico/grafico.module";

const routes: Routes = [
  { path: '', component: HomePageComponent }
]

@NgModule({
  declarations: [HomePageComponent],
  imports: [
    RouterModule.forChild(routes),
    CommonModule,
    GraficoModule
  ]
})
export class HomePageModule {
}
