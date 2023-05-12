import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { AutocompleteComponent } from '../autocomplete.component';
import { NgbModule, NgbTypeaheadModule } from '@ng-bootstrap/ng-bootstrap';

fdescribe('AutocompleteComponent', () => {
  let component: AutocompleteComponent;
  let fixture: ComponentFixture<AutocompleteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AutocompleteComponent],
      imports: [
        NgbModule,
        NgbTypeaheadModule
      ]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AutocompleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should filter values based on search input', (done) => {
    component.values = [
      { id: 1, name: 'John', age: 30 },
      { id: 2, name: 'Mary', age: 25 },
      { id: 3, name: 'Tom', age: 28 },
    ];
    component.searchFields = ['id', 'name', 'age'];

    const searchResults = component.search(of('John'));

    searchResults.subscribe((results: any) => {
      expect(results).toEqual(['1 - John - 30']);
      done();
    });
  });

  it('should apply custom handlers to specific fields', (done) => {
    component.values = [
      { id: 1, name: 'John', age: 30 },
      { id: 2, name: 'Mary', age: 25 },
      { id: 3, name: 'Tom', age: 28 },
    ];
    component.searchFields = ['id', 'name', 'age'];
    component.fieldsWithHandler = new Map();
    component.fieldsWithHandler.set('age', (age: number) => age + 5);

    const searchResults = component.search(of('35'));

    searchResults.subscribe((results: any) => {
      expect(results).toEqual(['1 - John - 35']);
      done();
    });
  });

  it('should not find results for non-existing fields', (done) => {
    component.values = [
      { id: 1, name: 'John', age: 30 },
      { id: 2, name: 'Mary', age: 25 },
      { id: 3, name: 'Tom', age: 28 },
    ];
    component.searchFields = ['non_existing_field'];

    const searchResults = component.search(of('John'));

    searchResults.subscribe((results: any) => {
      expect(results).toEqual([]);
      done();
    });
  });

});
