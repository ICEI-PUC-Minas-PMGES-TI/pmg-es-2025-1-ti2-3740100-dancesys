import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-financeiro-aluno-page',
  imports: [],
  templateUrl: './financeiro-aluno-page.component.html',
  styleUrl: './financeiro-aluno-page.component.css'
})
export class FinanceiroAlunoPageComponent implements OnInit {

boletos = [
    { mesAno: '03/2025', valor: 199.99, status: 'pendente' },
    { mesAno: '02/2025', valor: 214.99, status: 'atrasado' },
    { mesAno: '01/2025', valor: 199.99, status: 'pago' }
  ];

  constructor() {}

  ngOnInit(){}
 
}
