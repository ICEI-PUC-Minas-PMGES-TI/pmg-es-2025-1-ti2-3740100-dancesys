import { Component } from '@angular/core';
import { BotaoComponent } from "../../../components/botao/botao.component";
import { OtherCardComponent } from "../../../components/other-card/other-card.components";
@Component({
  selector: 'app-calendar-aluno-page',
  imports: [OtherCardComponent,BotaoComponent],
  templateUrl: './calendar-aluno-page.component.html',
  styleUrl: './calendar-aluno-page.component.css'
})

export class CalendarAlunoPageComponent {
  meses: string[] = [
    'Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho',
    'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'
  ];

  mesAtual: number = new Date().getMonth();

  get nomeMesAtual(): string {
    return this.meses[this.mesAtual];
  }

  proximoMes(): void {
    this.mesAtual = (this.mesAtual + 1) % 12;
  }

  mesAnterior(): void {
    this.mesAtual = (this.mesAtual - 1 + 12) % 12;
  }

  

}

