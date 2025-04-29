import { Component, Input } from '@angular/core';
import { BotaoComponent } from "../botao/botao.component"
import { IconComponent } from "../icon/icon.component";
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-simple-table',
  imports: [BotaoComponent,IconComponent, CommonModule],
  templateUrl: './simple-table.component.html',
  styleUrl: './simple-table.component.css'
})
export class SimpleTableComponent {
  @Input() colunas: { chave: string; titulo: string; width?: string, formatar?: (valor: any, item?: any) => string;}[] = [];
  @Input() dados: any[] = [];
  @Input() botoesAcoes: { icon: string; title: string; cor: string; callback: (item: any) => void }[] = [];

  getValor(item: any, chave: string): any {
    const valor = chave.split('.').reduce((obj, key) => {
      return obj?.[key];
    }, item);

    return valor;
  }
  
   
}
