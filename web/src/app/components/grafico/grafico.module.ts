import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GraficoComponent } from "./grafico.component";
import { NgChartsConfiguration, NgChartsModule } from "ng2-charts";

@NgModule({
  declarations: [GraficoComponent],
  imports: [
    CommonModule,
    NgChartsModule
  ],
  exports: [GraficoComponent],
  providers: [{ provide: NgChartsConfiguration, useValue: { generateColors: false } }]
})
export class GraficoModule {
}
