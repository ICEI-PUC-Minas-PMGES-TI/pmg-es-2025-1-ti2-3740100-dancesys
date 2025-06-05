import { Component, Input } from '@angular/core';

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

}