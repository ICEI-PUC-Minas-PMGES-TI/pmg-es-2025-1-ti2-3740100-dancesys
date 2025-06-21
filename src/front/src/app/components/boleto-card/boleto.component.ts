import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule, CurrencyPipe, DatePipe } from '@angular/common';
import { BotaoComponent } from '../botao/botao.component';
import { Dividendo } from '../../models/Dividendo.model';

@Component({
  selector: 'app-boleto-card',
  standalone: true,
  imports: [CommonModule, BotaoComponent, CurrencyPipe, DatePipe],
  templateUrl: './boleto.component.html',
  styleUrls: ['./boleto.component.css']
})
export class BoletoCardComponent {
  @Input() boleto!: Dividendo;

  @Output() payEvent = new EventEmitter<number>();

  getCorFundo(): string {
    switch (this.boleto.status) {
      case 1: // Pendente
        return '#d9cfc4';
      case 2: // Pago
        return '#c9d8c4';
      case 3: // Atrasado
        return '#e8b5b5';
      default:
        return '#d9cfc4';
    }
  }

  getCorBotao(): string {
    switch (this.boleto.status) {
      case 2: // Pago
        return 'success';
      case 3: // Atrasado
        return 'danger';
      default: // Pendente
        return 'medium';
    }
  }

  getTextoBotao(): string {
    switch (this.boleto.status) {
      case 2: 
        return 'Pago!';
      default:
        return 'Gerar Boleto';
    }
  }

  pagarBoleto() {
    this.payEvent.emit(this.boleto.id)
  }

    getTipo(tipo: number){
    switch(tipo){
      case 1 :
        return 'Matricula'
      case 2 : 
        return 'Mensalidade'
      case 3 : 
        return 'Aula extra'
      case 4 :
        return 'Ingresso de evento'
      case 5 :
        return 'Figurino'
      default : 
        return ''
    }
  }
}