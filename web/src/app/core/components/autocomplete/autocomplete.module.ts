import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AutocompleteComponent } from './autocomplete.component';
import { NgbModule, NgbTypeaheadModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [
    AutocompleteComponent
  ],
  exports: [AutocompleteComponent],
  imports: [
    CommonModule,
    NgbModule,
    NgbTypeaheadModule
  ]
})
export class AutoCompleteModule { }
