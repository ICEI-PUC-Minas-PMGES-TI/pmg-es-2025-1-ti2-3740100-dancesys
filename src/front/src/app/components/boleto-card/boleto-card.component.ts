import { Component, Input, numberAttribute } from '@angular/core';

@Component({
  selector: 'app-boleto-card',
  templateUrl: './boleto-card.component.html',
  styleUrls: ['./boleto-card.component.css']
})
export class BoletoCardComponent {
  @Input() boleto!: { mesAno: string; valor: number; status: string; };

  getCorBotao(status: string): string {
    switch (status) {
      case 'pago': return 'green';
      case 'atrasado': return 'red';
      default: return 'brown';
    }
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
        return 'Ingresso de vento'
      case 5 :
        return 'Figurino'
      default : 
        return ''
    }
  }
}
