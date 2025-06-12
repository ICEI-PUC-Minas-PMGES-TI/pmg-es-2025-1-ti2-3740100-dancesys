import { CommonModule } from '@angular/common';
import { Component, inject, ViewChild } from '@angular/core';
import { GraphicLineComponent } from '../../../../components/graphic-line/graphic-line.component';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { IndicadoresService } from '../../../../services/indicadores.service';
import { AulasModalidade } from '../../../../models/Indicadores.model';
import { ChartDataset } from 'chart.js';
import { GraphicBarYComponent } from '../../../../components/graphic-bar-y/graphic-bar-y.component';

@Component({
  selector: 'app-indicador-modalidade-admin-page',
  imports: [
    CommonModule,
    GraphicLineComponent,
    GraphicBarYComponent,
    ReactiveFormsModule
  ],
  templateUrl: './indicador-modalidade-admin-page.component.html',
  styleUrl: './indicador-modalidade-admin-page.component.css'
})
export class IndicardorModalidadeAdminPageComponent {
  indicaodresService = inject(IndicadoresService)

  dataGL: ChartDataset[] = []
  labelsGL: string[] = ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro']
  
  relatorioResponse1: AulasModalidade[] = []
	anoForm: FormGroup;

  anoInicial: number = 2025
  anos: number[] = []
  
  constructor(private fb: FormBuilder) {
		this.anoForm = this.fb.group({
			ano: [this.anoInicial]
		});
	}

  ngOnInit(){
    this.buscar()
    this.getAnos()

    this.anoForm.get('ano')?.valueChanges.subscribe((novoValor) => {
      this.buscar();
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
    this.indicaodresService.getRelatorioAulasModalidade(ano).subscribe({
      next: (response) =>{
        this.relatorioResponse1 = response
        this.gerarGraficoLinha(this.relatorioResponse1)
      }
    })
  }

  @ViewChild('graficoLinha') graficoLinha!: GraphicLineComponent
  gerarGraficoLinha(aulas: AulasModalidade[]){    
    this.dataGL = []
    const dados: ChartDataset[] = this.gerarDadosGrafico(aulas)
    this.dataGL = dados

    setTimeout(() => {
      this.graficoLinha.gerarGraficoLinha()
    }, 100);
  }

  gerarDadosGrafico(aulas: AulasModalidade[]) {
    const modalidadesMap = new Map<string, number[]>();

    for (const aula of aulas) {
      const { modalidade, mes, totalAulas } = aula;

      if (!modalidadesMap.has(modalidade)) {
        modalidadesMap.set(modalidade, new Array(12).fill(0));
      }

      const valores = modalidadesMap.get(modalidade)!;
      valores[mes - 1] = totalAulas;
    }

    const datasets: ChartDataset[] = Array.from(modalidadesMap.entries()).map(([modalidade, valores]) => {
      const color = this.getRandomColor()
      return {
        label: modalidade,
        data: valores,
        fill: false,
        borderColor: color,
        backgroundColor: color,
        pointBackgroundColor: color,
      };
    });

    return datasets;
  }

  getRandomColor() {
    const letters = '0123456789ABCDEF';
    let color = '#';
    for (let i = 0; i < 6; i++) {
      color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
  }

  //@ViewChild('graficoBar') graficoBar!: GraphicBarYComponent
  // gerarGraficoBar(){    
  //   setTimeout(() => {
  //     this.graficoBar.gerarGraficoBar()
  //   }, 1000);
  // }
}
