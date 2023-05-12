import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { ContasComponent } from './contas.component';
import { RendimentosService } from 'src/app/services/rendimentos.service';
import { AutoCompleteModule } from 'src/app/core/components/autocomplete/autocomplete.module';
import { ContasFormComponent } from './contas-form.component';
import { CurrencyMaskModule } from 'ng2-currency-mask';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

const routes: Routes = [
  {
    path: '',
    component: ContasComponent
  }
]

@NgModule({
  declarations: [ContasComponent, ContasFormComponent],
  imports: [
    RouterModule.forChild(routes),
    CommonModule,
    AutoCompleteModule,
    CurrencyMaskModule,
    ReactiveFormsModule,
    FormsModule
  ],
  providers: [RendimentosService]
})
export class ContasModule { }
