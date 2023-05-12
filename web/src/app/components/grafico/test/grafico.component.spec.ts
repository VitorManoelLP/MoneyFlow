import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GraficoComponent } from '../grafico.component';
import { of } from 'rxjs';
import { NgChartsModule } from 'ng2-charts';

describe('GraficoComponent', () => {
  let component: GraficoComponent;
  let fixture: ComponentFixture<GraficoComponent>;

  beforeEach(async () => {

    await TestBed.configureTestingModule({
      imports: [NgChartsModule],
      declarations: [ GraficoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GraficoComponent);
    component = fixture.componentInstance;
    component.usuarioInfo = of({ rendimentosTotais: [] });
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should update chart values when updateValues is called and usuarioInfo is updated', () => {

    component.usuarioInfo = of({ rendimentosTotais: [
      {
        mes: 1,
        tipoRendimento: "RECEITA",
        valorTotal: 100
      },
      {
        mes: 2,
        tipoRendimento: "DESPESA",
        valorTotal: 100
      },
      {
        mes: 2,
        tipoRendimento: "DESPESA",
        valorTotal: 0
      },
      {
        mes: 2,
        tipoRendimento: "RECEITA",
        valorTotal: 100
      }
    ]});

    component.ngOnInit();

    expect(component.barChartData.datasets[0].data).toEqual([
      100, 100, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
    ]);

    expect(component.barChartData.datasets[1].data).toEqual([
      0, 100, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
    ]);

  });

});
