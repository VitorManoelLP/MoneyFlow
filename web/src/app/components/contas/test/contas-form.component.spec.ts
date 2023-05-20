import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder } from '@angular/forms';
import { ContasFormComponent } from '../contas-form.component';

describe('ContasFormComponent', () => {
  let component: ContasFormComponent;
  let fixture: ComponentFixture<ContasFormComponent>;
  let formBuilder: FormBuilder;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ContasFormComponent],
      providers: [FormBuilder]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ContasFormComponent);
    component = fixture.componentInstance;
    formBuilder = TestBed.inject(FormBuilder);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('form initially invalid', () => {
    expect(component.formRendimentos.valid).toBeFalsy();
  });

  it('form should be valid', () => {
    component.formRendimentos.controls['descricao'].setValue('Test Description');
    component.formRendimentos.controls['valor'].setValue('100');
    component.formRendimentos.controls['data'].setValue('2023-01-01');
    expect(component.formRendimentos.valid).toBeTruthy();
  });

  it('should add a item inside group', () => {
    let initialValue = component.form.get('rendimentos')?.value.length || 0;
    component.formRendimentos.controls['descricao'].setValue('Test Description');
    component.formRendimentos.controls['valor'].setValue('100');
    component.formRendimentos.controls['data'].setValue('2023-01-01');
    component.addInsideGroup();
    let finalValue = component.form.get('rendimentos')?.value.length || 0;
    expect(finalValue).toBeGreaterThan(initialValue);
  });

  it('should remove a item from group', () => {
    component.formRendimentos.controls['descricao'].setValue('Test Description');
    component.formRendimentos.controls['valor'].setValue('100');
    component.formRendimentos.controls['data'].setValue('2023-01-01');
    component.addInsideGroup();
    let initialValue = component.form.get('rendimentos')?.value.length || 0;
    component.removeItem(0);
    let finalValue = component.form.get('rendimentos')?.value.length || 0;
    expect(finalValue).toBeLessThan(initialValue);
  });

  it('should return correct field name month by number', () => {
    component.form.get('grupo')?.patchValue({ mes: 1 });
    let field = component.getMesField();
    expect(field).toBe('Janeiro');
  });

  it('should return a capitalize type rendimentos', () => {
    component.formRendimentos.controls['tipoRendimento'].setValue('despesa');
    let capitalized = component.getFieldTipoRendimento();
    expect(capitalized).toBe('Despesa');
  });

  it('should set backup object when enable edit mode is called', () => {
    let obj: any = { descricao: 'Teste', valor: 100, data: '2023-01-01' };
    component.enableEditMode(obj);
    expect(obj?.editMode).toBeTrue();
    expect(obj?.backup).toEqual(obj);
  });

});
