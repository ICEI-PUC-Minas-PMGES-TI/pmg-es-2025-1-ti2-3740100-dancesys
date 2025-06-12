import { CommonModule } from '@angular/common';
import { Component, inject, ViewChild } from '@angular/core';
import { GraphicLineComponent } from '../../../../components/graphic-line/graphic-line.component';
import { GraphicPizzaComponent } from '../../../../components/graphic-pizza/graphic-pizza.component';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Conversao } from '../../../../models/Indicadores.model';
import { IndicadoresService } from '../../../../services/indicadores.service';
import { ChartDataset } from 'chart.js';

@Component({
  standalone: true,
  selector: 'app-indicador-conversao-admin-page',
  imports: [
    CommonModule,
    GraphicLineComponent,
    GraphicPizzaComponent,
    ReactiveFormsModule
  ],
  templateUrl: './indicador-conversao-admin-page.component.html',
  styleUrl: './indicador-conversao-admin-page.component.css'
})
export class IndicadorConversaoAdminPageComponent {
  service = inject(IndicadoresService);

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

  relatorioResponse: Conversao[] = []

  labelsGL: string[] = ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro']
  labelsGP: string[] = ['Interesse', 'Financeiro', 'Outro']
  colorsGP: string[] = ['rgb(34, 94, 169)', 'rgb(79, 183, 31)', 'rgb(178, 43, 43)']

  dataGL: ChartDataset[] = []
  dataGP: ChartDataset[] = []

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
    this.service.getRelatorioConversao(ano).subscribe({
      next: (response) =>{
        this.relatorioResponse = response
        this.gerarGraficoLinha()
        this.filtroPorMes()
      }
    })
  }

  @ViewChild('graficoLinha') graficoLinha!: GraphicLineComponent
  gerarGraficoLinha(){
    this.dataGL.push({
      label: 'Convertido',
      data: this.getConvertido(),
      borderColor: 'rgb(79, 183, 31)',
      backgroundColor: 'rgb(79, 183, 31)',
      pointBackgroundColor: 'rgb(79, 183, 31)',
      fill: false
    });

    this.dataGL.push({
      label: 'Recusado',
      data: this.getRecusado(),
      borderColor: 'rgb(178, 43, 43)',
      backgroundColor: 'rgb(178, 43, 43)',
      pointBackgroundColor: 'rgb(178, 43, 43)',
      fill: false
    });

    this.graficoLinha.gerarGraficoLinha()
  }

  getConvertido(){
    const array: number[] = [0,0,0,0,0,0,0,0,0,0,0,0]
    this.relatorioResponse.forEach((el) =>{
      array[el.mes - 1] = el.totalConvertido
    })

    return array;
  }

  getRecusado(){
    const array: number[] = [0,0,0,0,0,0,0,0,0,0,0,0]
    this.relatorioResponse.forEach((el) =>{
      array[el.mes - 1] = el.totalRecusado
    })

    return array;
  }

  marcadasEm: number = 0;
  finalizadaEm: number = 0;
  porcentagemInteresse: any;
  porcentagemFinanceiro: any;
  porcentagemOutro: any;


  filtroPorMes(){
    const mes = this.mesForm.get('mes')?.value;
    const resultado: Conversao[] = this.relatorioResponse.filter(el => el.mes == mes)

    if(resultado.length > 0){
      this.hasMes = true
      this.gerarGraficoPizza(resultado)
      this.marcadasEm = this.getMarcadaEm(resultado)
      this.finalizadaEm = this.getFinalziadaEm(resultado)
      const t: number = resultado[0].totalCriadas + resultado[0].totalFinalizadas
      this.porcentagemInteresse = this.getPorcentagem(resultado[0].totalInteresse, t)
      this.porcentagemFinanceiro = this.getPorcentagem(resultado[0].totalFinanceiro, t)
      this.porcentagemOutro = this.getPorcentagem(resultado[0].totalOutro, t)

    }else{
      this.hasMes = false
    }
  }

  @ViewChild('graficoPizza') graficoPizza1!: GraphicPizzaComponent
    gerarGraficoPizza(response: Conversao[]){
      this.dataGP = []
      this.dataGP.push({
        label: 'Total',
        data: this.getMotivos(response),
        backgroundColor: this.colorsGP,
        hoverOffset: 10
      })
  
      setTimeout(() => {
        this.graficoPizza1.gerarGraficoPizza();
      });
    }

    getMotivos(response: Conversao[]){
      return [response[0].totalInteresse,response[0].totalFinanceiro,response[0].totalOutro];
    }
    
    getMarcadaEm(item: Conversao[]){
      return item[0].totalCriadas
    }

    getFinalziadaEm(item: Conversao[]){
      return item[0].totalFinalizadas
    }

    getPorcentagem(n: number, t: number){
      if(n <= 0 || t <= 0) return 0;

      const porcentagem = (n/t) * 100

      return porcentagem.toFixed(2)
    }
}
