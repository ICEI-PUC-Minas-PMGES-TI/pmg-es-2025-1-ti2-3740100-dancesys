import { Component, inject, ViewChild } from '@angular/core';
import { ChartDataset } from 'chart.js';
import { CommonModule } from '@angular/common';
import { GraphicLineComponent } from '../../../../components/graphic-line/graphic-line.component';
import { IndicadoresService } from '../../../../services/indicadores.service';
import { Financeiro } from '../../../../models/Indicadores.model';
import { GraphicPizzaComponent } from '../../../../components/graphic-pizza/graphic-pizza.component';
import { Form, FormBuilder, FormGroup, ReactiveFormsModule } from "@angular/forms";


@Component({
  standalone: true,
  selector: 'app-indicador-financeiro-admin-page',
  imports: [
    CommonModule,
    GraphicLineComponent,
    GraphicPizzaComponent,
    ReactiveFormsModule
  ],
  templateUrl: './indicador-financeiro-admin-page.component.html',
  styleUrl: './indicador-financeiro-admin-page.component.css'
})
export class IndicadorFinanceiroAdminPageComponent {
  labelsGL: string[] = ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro']
  labelsGP: string[] = ['Matricula', 'Mensalidade', 'Aula', 'Evento', 'Figurino']
  colorsGP: string[] = ['rgb(34, 94, 169)', 'rgb(79, 183, 31)', 'rgb(178, 43, 43)', 'rgb(95, 26, 141)', 'rgb(221, 158, 41)']

  dataGL: ChartDataset[] = []
  dataGP1: ChartDataset[] = []
  dataGP2: ChartDataset[] = []
  dataGP3: ChartDataset[] = []
  dataGP4: ChartDataset[] = []

  service = inject(IndicadoresService);

  relatorioresponse: Financeiro[] = []
  hasMes: boolean = true
	anoForm: FormGroup;
	mesForm: FormGroup;

  anoInicial: number = 2025
  anoSelecionado: number = 2025

  meses: {value: number; name: string}[] = [
    {value: 1, name: 'Janeiro'},
    {value: 2, name: 'Fevereiro'},
    {value: 3, name: 'Março'},
    {value: 4, name: 'Abril'},
    {value: 5, name: 'Maio'},
    {value: 6, name: 'Junho'},
    {value: 7, name: 'Julho'},
    {value: 8, name: 'Agosto'},
    {value: 9, name: 'Setembro'},
    {value: 10, name: 'Outubro'},
    {value: 11, name: 'Novembro'},
    {value: 12, name: 'Dezembro'},

  ]

  anos: number[] = []

  constructor(private fb: FormBuilder) {
		this.anoForm = this.fb.group({
			ano: [this.anoInicial]
		});

		this.mesForm = this.fb.group({
			mes: [1]
		});
	}

  ngOnInit(){
    this.buscar()
    this.getAnos()

    this.anoForm.get('ano')?.valueChanges.subscribe((novoValor) => {
      this.buscar();
      this.anoSelecionado = this.anoForm.get('ano')?.value;
    });

    this.mesForm.get('mes')?.valueChanges.subscribe((novoValor) => {
      this.filtroPorMes();
    });
  }

  getAnos(){
    let ano = this.anoInicial
    const anoAtual = new Date().getFullYear();
    let i = 0;
    while(ano <= anoAtual){
      this.anos[i] = ano;
      i++
      ano ++
    }
  }

  buscar(){
    const ano = this.anoForm.get('ano')?.value;
    this.service.getRelatorioFinanceiro(ano).subscribe({
			next: (response: Financeiro[]) => {
				this.relatorioresponse = response;
        this.gerarGraficoLinha();
        this.gerarCardsAnual();
        this.filtroPorMes();
			},
		});
  }

  @ViewChild('graficoLinha') graficoLinha!: GraphicLineComponent
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

  filtroPorMes(){
    const mes = this.mesForm.get('mes')?.value;
    const resultado: Financeiro[] = this.relatorioresponse.filter(el => el.mes == mes)
    if(resultado.length > 0){
      this.hasMes = true
      this.gerarGraficoPizza1(resultado)
      this.gerarGraficoPizza2(resultado)
      this.gerarGraficoPizza3(resultado)
      this.gerarGraficoPizza4(resultado)
      this.getBoletosMesPagosNoPrazo(resultado)
      this.getBoletosMesValorNoPrazo(resultado)
      this.getBoletosMesPagosAtrasado(resultado)
      this.getBoletosMesValorAtrasado(resultado)
      this.getBoletosMesMediaDiasAtraso(resultado)
      this.getBoletosMesNaoPagos(resultado)

    }else{
      this.hasMes = false
    }
  }

  @ViewChild('graficoPizza1') graficoPizza1!: GraphicPizzaComponent
  gerarGraficoPizza1(response: Financeiro[]){
    this.dataGP1 = []
    this.dataGP1.push({
      label: 'Total',
      data: this.getTotalAtrasoPorTipo(response),
      backgroundColor: this.colorsGP,
      hoverOffset: 10
    })

    setTimeout(() => {
      this.graficoPizza1.gerarGraficoPizza();
    });
  }

  @ViewChild('graficoPizza2') graficoPizza2!: GraphicPizzaComponent
  gerarGraficoPizza2(response: Financeiro[]){
    this.dataGP2 = []
    this.dataGP2.push({
      label: 'Total(R$)',
      data: this.getValorAtrasoPorTipo(response),
      backgroundColor: this.colorsGP,
      hoverOffset: 10
    })

    setTimeout(() => {
      this.graficoPizza2.gerarGraficoPizza()  
    })
  }

  @ViewChild('graficoPizza3') graficoPizza3!: GraphicPizzaComponent
  gerarGraficoPizza3(response: Financeiro[]){
    this.dataGP3 = []
    this.dataGP3.push({
      label: 'Total',
      data: this.getTotalNoPrazoPorTipo(response),
      backgroundColor: this.colorsGP,
      hoverOffset: 10
    })

    setTimeout(() => {
      this.graficoPizza3.gerarGraficoPizza()
    })
  }

  @ViewChild('graficoPizza4') graficoPizza4!: GraphicPizzaComponent
  gerarGraficoPizza4(response: Financeiro[]){
    this.dataGP4 = []
    this.dataGP4.push({
      label: 'Total(R$)',
      data: this.getValorNoPrazoPorTipo(response),
      backgroundColor: this.colorsGP,
      hoverOffset: 10
    })

    setTimeout(() => {
      this.graficoPizza4.gerarGraficoPizza()
    })
  }

  getTotalAtrasoPorTipo(response: Financeiro[]){
    const array: number[] = [0,0,0,0,0]

    response.forEach((el) =>{
      array[el.tipo - 1] += el.boletosPagosComAtraso;
    })

    return array;
  }

  getValorAtrasoPorTipo(response: Financeiro[]){
    const array: number[] = [0,0,0,0,0]

    response.forEach((el) =>{
      array[el.tipo - 1] += el.somaValoresComAtraso;
    })

    return array;
  }

  getTotalNoPrazoPorTipo(response: Financeiro[]){
    const array: number[] = [0,0,0,0,0]

    response.forEach((el) =>{
      array[el.tipo - 1] += el.boletosPagosSemAtraso;
    })

    return array;
  }

  getValorNoPrazoPorTipo(response: Financeiro[]){
    const array: number[] = [0,0,0,0,0]

    response.forEach((el) =>{
      array[el.tipo - 1] += el.somaValoresSemAtraso;
    })

    return array;
  }

  boletosMesPagosNoPrazo: number = 0;
  boletosMesValorNoPrazo: number = 0;
  boletosMesPagosAtrasado: number = 0;
  boletosMesValorAtrasado: number = 0;
  boletosMesMediaDiasAtraso: number = 0;
  boletosMesNaoPagos: number = 0;

  getBoletosMesPagosNoPrazo(response: Financeiro[]){
    let n = 0;
    response.forEach((el) =>{
      n += el.boletosPagosSemAtraso;
    })

    this.boletosMesPagosNoPrazo = n
  }

  getBoletosMesValorNoPrazo(response: Financeiro[]){
    let n = 0;
    response.forEach((el) =>{
      n += el.boletosPagosSemAtraso
    })

    this.boletosMesValorNoPrazo = n
  }

  getBoletosMesPagosAtrasado(response: Financeiro[]){
    let n = 0;
    response.forEach((el) =>{
      n += el.boletosPagosComAtraso
    })

    this.boletosMesPagosAtrasado = n
  }

  getBoletosMesValorAtrasado(response: Financeiro[]){
    let n = 0;
    response.forEach((el) =>{
      n += el.somaValoresComAtraso
    })

    this.boletosMesValorAtrasado = n
  }

  getBoletosMesMediaDiasAtraso(response: Financeiro[]){
    let n = 0;
    response.forEach((el) =>{
      n += el.mediaDiasAtraso
    })

    this.boletosMesMediaDiasAtraso = n/5
  }

  getBoletosMesNaoPagos(response: Financeiro[]){
    let n = 0;
    response.forEach((el) =>{
      n += el.boletosNaoPagos
    })

    this.boletosMesNaoPagos = n
  }
}