import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { ChartConfiguration, ChartData, ChartType } from "chart.js";
import { BaseChartDirective } from 'ng2-charts';
import { Observable } from 'rxjs';
import { TipoRendimento } from 'src/app/base/tipo-rendimento';

@Component({
  selector: 'money-flow-grafico',
  templateUrl: './grafico.component.html'
})
export class GraficoComponent implements OnInit {

  @ViewChild(BaseChartDirective) chart: BaseChartDirective | undefined;

  @Input() usuarioInfo: Observable<any>;

  ngOnInit(): void {
    this.updateValues();
  }

  public barChartOptions: ChartConfiguration['options'] = {
    responsive: true,
    scales: {
      x: {},
      y: {
        ticks: {
          callback: (value, index, values) => {
            return value.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
          }
        }
      }
    },
    plugins: {
      legend: {
        display: true,
        position: 'bottom'
      },
      title: {
        display: true,
        text: 'Receitas e Despesas 2023',
        font: {
          size: 20
        }
      },
      tooltip: {
        callbacks: {
          label: (context) => {
            return context.parsed.y.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
          }
        }
      }
    }
  };

  public barChartType: ChartType = 'bar';
  public barChartPlugins = [];

  public barChartData: ChartData<'bar'> = {
    labels: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
    datasets: [
      {
        data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        label: 'Receitas',
        backgroundColor: '#85bb65'
      },
      {
        data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
        label: 'Despesas',
        backgroundColor: '#dc3545'
      }
    ]
  };

  private updateValues() {

    this.usuarioInfo.subscribe(usuarioInfo => {

      let rendimentos = usuarioInfo?.rendimentosTotais as any[];

      let valoresReceitas = [];
      let valoresDespesas = [];

      for(let mes = 1; mes <= 12; mes++) {

        valoresReceitas.push();

        let receitasMes = rendimentos?.filter(rendimento => rendimento.mes === mes && rendimento.tipoRendimento === TipoRendimento.RECEITA);

        receitasMes.length ? valoresReceitas.push(receitasMes.map(rendimento => rendimento.valorTotal).reduce((a,b) => a + b)) : valoresReceitas.push(0);

        let despesasMes = rendimentos?.filter(rendimento => rendimento.mes === mes && rendimento.tipoRendimento === TipoRendimento.DESPESA);

        despesasMes.length ? valoresDespesas.push(despesasMes.map(rendimento => rendimento.valorTotal).reduce((a,b) => a + b)) : valoresDespesas.push(0);

      }

      this.barChartData.datasets[0].data = [...valoresReceitas];
      this.barChartData.datasets[1].data = [...valoresDespesas];

      this.chart?.update();

    });
  }

}
