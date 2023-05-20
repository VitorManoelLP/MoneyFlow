import { Component, Input, OnInit, Output } from '@angular/core';
import { Outlay } from './models/outlay';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MesUtil } from 'src/app/core/util/mes';
import { TipoRendimentoUtil } from 'src/app/base/tipo-rendimento';
import { RendimentosService } from 'src/app/services/rendimentos.service';

@Component({
  selector: 'contas-form',
  templateUrl: './contas-form.component.html'
})
export class ContasFormComponent implements OnInit {

  @Input() outlay: Outlay[];
  @Input() searchFields: string[];
  @Input() placeholder: string = '';
  @Input() fieldsWithHandler: Map<any, Function>;

  formRendimentos: FormGroup;
  form: FormGroup;

  constructor(private formBuilder: FormBuilder,
    private usuarioRendimentoService: RendimentosService) {}

  ngOnInit(): void {
    this.form = this.createForm();
    this.formRendimentos = this.createFormRendimentos();
  }

  public createForm(): FormGroup {

    const form = this.formBuilder.group({
      'competencia': ['', Validators.required],
      'descricao': ['', Validators.required],
      'tipoRendimento': ['', Validators.required],
      'id': ['', Validators.required],
      'rendimentos': this.formBuilder.array([], Validators.required)
    });

    return form;
  }

  public addInsideGroup(): void {
    const rendimentos = this.form.get('rendimentos') as FormArray
    const formRendimentoClone = this.formBuilder.group(this.formRendimentos.value);
    rendimentos.push(formRendimentoClone);
    this.formRendimentos.reset();
  }

  public addGroup(grupo: any): void {
    this.form.patchValue({
      'competencia': grupo.mes,
      'descricao': grupo.nome,
      'tipoRendimento': grupo.tipoRendimento,
      'id': grupo.idUsuarioRendimento
    });
  }

  public enableEditMode(item: any) {
    item.editMode = true;
    item.backup = Object.assign({}, item);
    console.log(item);
  }

  public cancelEdit(item: any, index: number) {
    this.disableEditMode(item);
    this.changeItemValueByIndex(index, item?.backup);
  }

  public editItem(item: any, index: number) {
    this.changeItemValueByIndex(index, item);
    this.disableEditMode(item);
  }

  public removeItem(index: number): void {
    const rendimentos = this.form.get('rendimentos') as FormArray
    rendimentos.removeAt(index);
  }

  public getFieldTipoRendimento(): string {
    return TipoRendimentoUtil.getFieldByTipoRendimento(this.form.get('tipoRendimento')?.value);
  }

  public getMesField(): string {
    return MesUtil.getMesField(this.form.get('competencia')?.value);
  }

  public salvar() {
    this.usuarioRendimentoService.salvar(this.form.value).subscribe((res) => {
      document?.getElementById('closeModal')?.click();
    });
  }

  private createFormRendimentos() {
    return this.formBuilder.group({
      'descricao': ['', Validators.required],
      'valor': ['', Validators.required],
      'data': ['', Validators.required]
    });
  }

  private changeItemValueByIndex(index: number, item: any) {
    const rendimentos = this.form.get('rendimentos') as FormArray;
    rendimentos.at(index).patchValue(item);
  }

  private disableEditMode(item: any) {
    item.editMode = false;
  }
}
