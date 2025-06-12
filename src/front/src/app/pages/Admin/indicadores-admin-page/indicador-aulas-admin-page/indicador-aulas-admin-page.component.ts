import { CommonModule } from '@angular/common';
import { Component, inject, ViewChild } from '@angular/core';
import { GraphicLineComponent } from '../../../../components/graphic-line/graphic-line.component';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { IndicadoresService } from '../../../../services/indicadores.service';
import { Aulas } from '../../../../models/Indicadores.model';
import { SearchBoxSingleComponent } from '../../../../components/search-box-single/search-box-single.component';
import { ProfessorFiltro } from '../../../../models/professor.model';
import { AdminService } from '../../../../services/admin.service';
import { ChartDataset } from 'chart.js';

@Component({
  selector: 'app-indicador-aulas-admin-page',
  imports: [
    CommonModule,
    GraphicLineComponent,
    ReactiveFormsModule,
    SearchBoxSingleComponent
  ],
  templateUrl: './indicador-aulas-admin-page.component.html',
  styleUrl: './indicador-aulas-admin-page.component.css'
})
export class IndicadorAulasAdminPageComponent {
  indicadoresService = inject(IndicadoresService)
  adminService = inject(AdminService)
  hasMes: boolean = false
  hasAno: boolean = false

  labelsGL: string[] = ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro']
  dataGL: ChartDataset[] = []


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

	mesForm: FormGroup;
  anoForm: FormGroup;

  anoInicial: number = 2025
  anoSelecionado: number = 2025

  professoresLs: any = []
  relatorioResponse: Aulas[] = []

  constructor(private fb: FormBuilder) {
    this.anoForm = this.fb.group({
			ano: [this.anoInicial],
      idProfessor: []
		});

		this.mesForm = this.fb.group({
			mes: [1]
		});
	}

  ngOnInit(){
    this.getAnos()
    this.gerarGraficoLinha()

    this.mesForm.get('mes')?.valueChanges.subscribe((novoValor) => {
      this.filtroPorMes();
    });

    this.anoForm.get('ano')?.valueChanges.subscribe((novoValor) => {
      const idProfessor = this.anoForm.get('idProfessor')?.value;
      if(idProfessor !=null){
        this.dataGL = []
        this.buscar()
      }
    });

    this.anoForm.get('idProfessor')?.valueChanges.subscribe((novoValor) => {
      const idProfessor = this.anoForm.get('idProfessor')?.value;
      if(idProfessor !=null){
        this.dataGL = []
        this.buscar()
      }
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

  @ViewChild('graficoLinha') graficoLinha!: GraphicLineComponent
  gerarGraficoLinha(){    
    this.dataGL.push({
      label: 'Total aulas ocorrentes',
      data: this.getTotalAulasOcorrentes(),
      borderColor: 'rgb(34, 94, 169)',
      backgroundColor: 'rgb(34, 94, 169)',
      pointBackgroundColor: 'rgb(34, 94, 169)',
      fill: false
    });

    this.dataGL.push({
      label: 'Total aulas ocorrentes canceladas',
      data: this.getTotalAulasOcorrentesCanceladas(),
      borderColor: 'rgb(95, 26, 141)',
      backgroundColor: 'rgb(95, 26, 141)',
      pointBackgroundColor: 'rgb(95, 26, 141)',
      fill: false
    });

    this.dataGL.push({
      label: 'Total aulas extras',
      data: this.getTotalAulasExtras(),
      borderColor: 'rgb(79, 183, 31)',
      backgroundColor: 'rgb(79, 183, 31)',
      pointBackgroundColor: 'rgb(79, 183, 31)',
      fill: false
    });

    this.dataGL.push({
      label: 'Total aulas extras canceladas',
      data: this.getTotalAulasExtrasCanceladas(),
      borderColor: 'rgb(178, 43, 43)',
      backgroundColor: 'rgb(178, 43, 43)',
      pointBackgroundColor: 'rgb(178, 43, 43)',
      fill: false
    });

    this.dataGL.push({
      label: 'Total aulas experimentais',
      data: this.getTotalAulasExperimentais(),
      borderColor: 'rgb(221, 158, 41)',
      backgroundColor: 'rgb(221, 158, 41)',
      pointBackgroundColor: 'rgb(221, 158, 41)',
      fill: false
    });

    setTimeout(() => {
      this.graficoLinha.gerarGraficoLinha()
    });
  }

  getTotalAulasOcorrentes(){
    const array: number[] = [0,0,0,0,0,0,0,0,0,0,0,0]

    this.relatorioResponse.forEach((el) =>{
      array[el.mes - 1] = el.totalAulasOcorrentesRealizadas
    })

    return array;
  }

  getTotalAulasOcorrentesCanceladas(){
    const array: number[] = [0,0,0,0,0,0,0,0,0,0,0,0]

    this.relatorioResponse.forEach((el) =>{
      array[el.mes - 1] = el.totalAulasOcorrentesCanceladas
    })

    return array;
  }

  getTotalAulasExtras(){
    const array: number[] = [0,0,0,0,0,0,0,0,0,0,0,0]

    this.relatorioResponse.forEach((el) =>{
      array[el.mes - 1] = el.totalAulasExtrasRealizadas
    })

    return array;
  }

  getTotalAulasExtrasCanceladas(){
    const array: number[] = [0,0,0,0,0,0,0,0,0,0,0,0]

    this.relatorioResponse.forEach((el) =>{
      array[el.mes - 1] = el.totalAulasExtrasCanceladas
    })

    return array;
  }

  getTotalAulasExperimentais(){
    const array: number[] = [0,0,0,0,0,0,0,0,0,0,0,0]

    this.relatorioResponse.forEach((el) =>{
      array[el.mes - 1] = el.totalAulasExperimentais
    })

    return array;
  }

  buscarProfessor(termo: any){
      const filtro: ProfessorFiltro = {
        nome: termo,
        status: 1
      };
      
      this.adminService.filterProfessores(filtro).subscribe({
        next: (response: any) => {
          this.professoresLs = response.conteudo;
        },
      });
    }

  buscar(){
    const idProfessor = this.anoForm.get('idProfessor')?.value;
    const ano = this.anoForm.get('ano')?.value;

    this.indicadoresService.getRelatorioAulas(idProfessor,ano).subscribe({
      next: (response) =>{
        if(response.length <= 0){
          this.hasAno = false
        }else{
          this.hasAno = true
        }
        this.relatorioResponse = response
        this.gerarGraficoLinha()
        this.filtroPorMes()
      }
    })
  }

  horasAulasOcorrentes: string = ''
  horasAulasExtras: string = ''
  horasAulasExperimentais: string = ''

  filtroPorMes(){
      const mes = this.mesForm.get('mes')?.value;
      const resultado: Aulas[] = this.relatorioResponse.filter(el => el.mes == mes)
  
      if(resultado.length > 0){
        this.hasMes = true
        this.horasAulasOcorrentes = this.getHorasAulaRecorrente(resultado)
        this.horasAulasExtras = this.getHorasAulaExtra(resultado)
        this.horasAulasExperimentais = this.getHorasAulaExperimental(resultado)
      }else{
        this.hasMes = false
      }
    }

    getHorasAulaRecorrente(result: Aulas[]){
      const totalMin: number = result[0].minutosAulasOcorrentes

      let hr = Math.floor(totalMin/60)
      let min = totalMin%60

      return `${hr}h${min}`
    }

    getHorasAulaExtra(result: Aulas[]){
      const totalMin: number = result[0].minutosAulasExtras

      let hr = Math.floor(totalMin/60)
      let min = totalMin%60

      return `${hr}h${min}`
    }

    getHorasAulaExperimental(result: Aulas[]){
      const totalMin: number = result[0].minutosAulasExperimentais

      let hr = Math.floor(totalMin/60)
      let min = totalMin%60

      return `${hr}h${min}`
    }
}
