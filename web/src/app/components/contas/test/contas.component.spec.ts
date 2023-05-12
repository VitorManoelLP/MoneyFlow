import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContasComponent } from '../contas.component';
import { UsuarioService } from 'src/app/services/usuario.service';
import { inject } from '@angular/core';
import { of } from 'rxjs';
import { TipoRendimento } from 'src/app/base/tipo-rendimento';
import { RendimentosService } from 'src/app/services/rendimentos.service';
import { Outlay } from '../models/outlay';
import { DetailOutlay } from '../models/detail-outlay';

describe('ContasComponent', () => {

  let component: ContasComponent;
  let fixture: ComponentFixture<ContasComponent>;
  let usuarioService: jasmine.SpyObj<UsuarioService>;
  let rendimentoService: jasmine.SpyObj<RendimentosService>;

  beforeEach(async () => {

    const usuarioSpy = jasmine.createSpyObj('UsuarioService', ['findInitialInformations']);
    const rendimentoSpy = jasmine.createSpyObj('RendimentosService', ['getRendimentosByIdUsuarioRendimento']);

    await TestBed.configureTestingModule({
      declarations: [ ContasComponent ],
      providers: [
        { provide: UsuarioService, useValue: usuarioSpy },
        { provide: RendimentosService, useValue: rendimentoSpy }
      ]
    })
    .compileComponents();

    usuarioService = TestBed.inject(UsuarioService) as jasmine.SpyObj<UsuarioService>;
    usuarioService.findInitialInformations.and.callFake(() => of({
      rendimentosTotais: [
        { id: 1 }
      ],
      valorTotalDespesas: 100,
      valorTotalReceitas: 200
    }));

    rendimentoService = TestBed.inject(RendimentosService) as jasmine.SpyObj<RendimentosService>;
    rendimentoService.getRendimentosByIdUsuarioRendimento.and.callFake(() => of([createDetailOutlayMock(1, 100)]));

  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ContasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should set outlay object with rendimentosTotais of findInitialInformations return and totals when ngOnInit is called', () => {

    component.ngOnInit();

    let outlay: Outlay = {
      idUsuarioRendimento: 1,
      mes: 12,
      nome: 'Nome',
      valorTotal: 100,
      tipoRendimento: TipoRendimento.DESPESA
    };

    expect(usuarioService.findInitialInformations).toHaveBeenCalled();
    expect(component.outlay).toEqual([outlay]);
    expect(component.totalizadores).toEqual({
      totalDespesas: 100,
      totalReceitas: 200
    });

  });

  it('should capitalize field by enum TipoRendimento', () => {

    let fieldReceita = component.getFieldTipoRendimento(TipoRendimento.RECEITA);
    expect(fieldReceita).toEqual('Receita');

    let fieldDespesa = component.getFieldTipoRendimento(TipoRendimento.DESPESA);
    expect(fieldDespesa).toEqual('Despesa');

  });

  it('should find month name by number', () => {

    let dezembro = component.getMesField(12);
    expect(dezembro).toEqual('Dezembro');

    let janeiro = component.getMesField(1);
    expect(janeiro).toEqual('Janeiro');

    let junho = component.getMesField(6);
    expect(junho).toEqual('Junho');

  });


  it('should get detailOutlay by idUsuarioRendimento field', () => {

    const outlay1 = createDetailOutlayMock(1, 200);
    const outlay2 = createDetailOutlayMock(2, 100);

    component.detailOutlay = [
      outlay2,
      outlay1
    ];

    let secondDetail = component.getSpecificOutlay(2);
    expect(secondDetail).toEqual([
      outlay2
    ]);

    let firstDetail = component.getSpecificOutlay(1);
    expect(firstDetail).toEqual([
      outlay1
    ]);

    let zero = component.getSpecificOutlay(10);
    expect(zero).toEqual([]);
  });

  it('should set field expand to true when openDetail is called', () => {

    let entity: Outlay = {
      idUsuarioRendimento: 1,
      mes: 1,
      nome: 'Nome',
      valorTotal: 100,
      tipoRendimento: TipoRendimento.DESPESA,
      expand: false
    };

    component.openDetail(entity);

    expect(entity.expand).toBeTrue();

  });

  it('should call getRendimentosByIdUsuarioRendimento when openDetail is called with none same idUsuarioRendimento inside detailOutlay', () => {

    const detailOutlay = createDetailOutlayMock(2, 100);

    component.detailOutlay.push(detailOutlay);

    let outlay: Outlay = {
      idUsuarioRendimento: 2,
      mes: 1,
      tipoRendimento: TipoRendimento.DESPESA,
      nome: 'Nome',
      valorTotal: 10,
      expand: false
    };

    component.openDetail(outlay);

    fixture.detectChanges();

    expect(rendimentoService.getRendimentosByIdUsuarioRendimento).toHaveBeenCalled();
    expect(component.detailOutlay).toEqual([
      detailOutlay,
      createDetailOutlayMock(1, 100)
    ]);

  });

});

function createDetailOutlayMock(idUsuarioRendimento: number, valor: number): DetailOutlay {
  return {
    descricao: 'Descricao',
    idUsuarioRendimento: idUsuarioRendimento,
    valor: valor,
    data: new Date()
  };
}

