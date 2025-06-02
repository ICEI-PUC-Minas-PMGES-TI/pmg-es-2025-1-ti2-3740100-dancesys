import { Component, inject, ViewChild } from '@angular/core';
import { ChartDataset } from 'chart.js';
import { CommonModule } from '@angular/common';
import { GraphicLineComponent } from '../../../../components/graphic-line/graphic-line.component';
import { IndicadoresService } from '../../../../services/indicadores.service';
import { Financeiro } from '../../../../models/Indicadores.model';


@Component({
  standalone: true,
  selector: 'app-indicador-financeiro-admin-page',
  imports: [
    CommonModule,
    GraphicLineComponent
  ],
  templateUrl: './indicador-financeiro-admin-page.component.html',
  styleUrl: './indicador-financeiro-admin-page.component.css'
})
export class IndicadorFinanceiroAdminPageComponent {
  labelsGL: string[] = ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro']
  dataGL: ChartDataset[] = []

  service = inject(IndicadoresService);

  relatorioresponse: Financeiro[] = []

  ngOnInit(){
    this.buscar()
  }

  @ViewChild('graficoLinha') graficoLinha!: GraphicLineComponent
  buscar(){
    this.service.getRelatorioFinanceiro(2025).subscribe({
			next: (response: Financeiro[]) => {
				this.relatorioresponse = response;
        console.log(response)
        this.gerarGraficoLinha();
        this.gerarCardsAnual();
			},
		});
  }

  gerarGraficoLinha(){
    this.dataGL.push({
      label: 'No prazo',
      data: this.getTotalPagosNoPrazo(),
      borderColor: 'green',
      backgroundColor: 'green',
      pointBackgroundColor: 'green',
      fill: false
    });

    this.dataGL.push({
      label: 'Com atraso',
      data: this.getTotalPagosAtrasado(),
      borderColor: 'red',
      backgroundColor: 'red',
      pointBackgroundColor: 'red',
      fill: false
    });

    this.graficoLinha.gerarGraficoLinha()
  }

  getTotalPagosNoPrazo(){
    const array: number[] = [0,0,0,0,0,0,0,0,0,0,0,0]
    this.relatorioresponse.forEach((el) =>{
      array[el.mes - 1] += el.boletosPagosSemAtraso;
    })

    return array;
  }

  getTotalPagosAtrasado(){
    const array: number[] = [0,0,0,0,0,0,0,0,0,0,0,0]
    this.relatorioresponse.forEach((el) =>{
      array[el.mes - 1] += el.boletosPagosComAtraso;
    })

    return array;
  }

  gerarCardsAnual(){
    this.getTotalBoletosPagosNoPrazo();
    this.getTotalBoletosPagosAtrasado();
    this.getTotalValorPagoNoPrazo();
    this.getTotalValorPagoAtrasado();
  }

  totalBoletosPagosNoPrazo: number = 0
  totalBoletosPagosAtrasado: number = 0
  totalValorPagoNoPrazo: number = 0
  totalValorPagoAtrasado: number = 0

  getTotalBoletosPagosNoPrazo(){
    this.relatorioresponse.forEach((el) =>{
      this.totalBoletosPagosNoPrazo += el.boletosPagosSemAtraso
    })
  }

  getTotalBoletosPagosAtrasado(){
    this.relatorioresponse.forEach((el) =>{
      this.totalBoletosPagosAtrasado += el.boletosPagosComAtraso
    })
  }

  getTotalValorPagoNoPrazo(){
    this.relatorioresponse.forEach((el) =>{
      this.totalValorPagoNoPrazo += el.somaValoresSemAtraso
    })
  }

  getTotalValorPagoAtrasado(){
    this.relatorioresponse.forEach((el) =>{
      this.totalValorPagoAtrasado += el.somaValoresComAtraso
    })
  }
}
