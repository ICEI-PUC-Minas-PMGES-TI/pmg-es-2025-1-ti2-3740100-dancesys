import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { GraphicLineComponent } from '../../../../components/graphic-line/graphic-line.component';
import { GraphicPizzaComponent } from '../../../../components/graphic-pizza/graphic-pizza.component';
import { ReactiveFormsModule } from '@angular/forms';
import { IndicadoresService } from '../../../../services/indicadores.service';
import { Aulas } from '../../../../models/Indicadores.model';

@Component({
  selector: 'app-indicador-aulas-admin-page',
  imports: [
    CommonModule,
    GraphicLineComponent,
    GraphicPizzaComponent,
    ReactiveFormsModule
  ],
  templateUrl: './indicador-aulas-admin-page.component.html',
  styleUrl: './indicador-aulas-admin-page.component.css'
})
export class IndicadorAulasAdminPageComponent {
  indicadoresService = inject(IndicadoresService)
  hasMes: boolean = false

  relatorioResponse: Aulas[] = []

  ngOnInit(){
    this.buscar()
  }

  buscar(){
    this.indicadoresService.getRelatorioAulas(15,2025).subscribe({
      next: (response) =>{
        this.relatorioResponse = response
        console.log(this.relatorioResponse)
      }
    })
  }

  horasAulasOcorrentes: string = ''
  filtroPorMes(){
      const mes = 6//this.mesForm.get('mes')?.value;
      const resultado: Aulas[] = this.relatorioResponse.filter(el => el.mes == mes)
      console.log(resultado)
  
      if(resultado.length > 0){
        this.hasMes = true
        this.horasAulasOcorrentes = this.getHorasAulaRecorrente(resultado)
      }else{
        this.hasMes = false
      }
    }

    getHorasAulaRecorrente(result: Aulas[]){
      const totalMin: number = result[0].minutosAulasOcorrentes

      let hr = totalMin/60
      let min = totalMin%60

      return `${hr}h${min}`
    }
}
