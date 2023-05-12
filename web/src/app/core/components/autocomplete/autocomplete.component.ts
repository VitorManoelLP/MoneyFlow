import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Observable, debounceTime, distinctUntilChanged, filter, map } from 'rxjs';

@Component({
  selector: 'autocomplete',
  templateUrl: './autocomplete.component.html',
  styleUrls: ['./autocomplete.component.css']
})
export class AutocompleteComponent {

  @Input() values: any[];
  @Input() searchFields: string[];
  @Input() placeholder: string = '';
  @Input() fieldsWithHandler: Map<any, Function>;
  @Input() title: string = '';
  @Output() selected: EventEmitter<any> = new EventEmitter<any>();

  objByField = new Map<string, any>();

  search = (text: Observable<string>) =>
    text.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      map(term => this.values.filter(v => this.buildSearch(v, term)).map(obj => this.showOptions(obj)))
    );

  emitSelectedItem(event: any) {
    this.selected.emit(this.objByField.get(event.item));
  }

  private showOptions(obj: any): string {

    let fields = this.searchFields.map(field => {

      if(this.fieldsWithHandler?.has(field)) {
        return this.fieldsWithHandler?.get(field)?.call(this, obj[field]);
      }

      return obj[field];

    }).join(' - ');

    this.setObjToSearch(fields, obj);

    return fields;
  }

  private buildSearch(value: any, term: string): boolean {

    return this.searchFields.map(field => {

      if (!field || !value.hasOwnProperty(field)) {
        return '';
      }

      if (this.fieldsWithHandler?.has(field)) {
        return this.fieldsWithHandler?.get(field)?.call(this, value[field]).toString();
      }

      const fieldValue = value[field];

      if (typeof fieldValue === 'number') {
        return fieldValue;
      }

      if (typeof fieldValue === 'string') {
        return fieldValue.toLowerCase();
      }

      return '';
    }).join(' - ').toLowerCase().includes(term.toLowerCase());
  }

  private setObjToSearch(field: string, obj: any) {
    this.objByField.set(field, obj);
  }

}

