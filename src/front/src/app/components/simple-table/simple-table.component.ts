import { Component, EventEmitter, Input, Output, SimpleChanges, OnChanges } from '@angular/core';
import { BotaoComponent } from "../botao/botao.component"
import { IconComponent } from "../icon/icon.component";
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms'; 


@Component({
  selector: 'app-simple-table',
  imports: [BotaoComponent,IconComponent, CommonModule,ReactiveFormsModule],
  templateUrl: './simple-table.component.html',
  styleUrl: './simple-table.component.css'
})
export class SimpleTableComponent implements OnChanges {
  @Input() colunas: { chave: string; titulo: string; order?: boolean; width?: string, formatar?: (valor: any, item?: any) => string;}[] = [];
  @Input() dados: any[] = [];
  @Input() botoesAcoes: { icon: string; title: string; cor: string; callback: (item: any) => void }[] = [];
  @Input() totalItens: number = 0;
  @Input() paged: boolean = true;
  @Input() actionsWidth: String = '20%'

  @Output() paginacaoChange = new EventEmitter<{ paginaSelecionada: number; itensPage: number }>();
  @Output() orderChange = new EventEmitter<{ chave: string, direcao: 'asc' | 'desc' }>();

  itensForm: FormGroup;
  pgsLs: number[] = [];
  paginaSelecionada: number = 1;
  paginaIn: number = 1;
  totalPaginas!: number;
  itensPage: number = 10;

  ordenacao: { chave: string; direcao: 'asc' | 'desc' } | null = null;


  constructor(private fb: FormBuilder) {
		this.itensForm = this.fb.group({
		  itensPage: [this.itensPage],
		});
	}

  ngOnChanges(changes: SimpleChanges) {
    if (changes['totalItens'] && changes['totalItens'].currentValue !== undefined) {
      this.calcTotalPaginas();
      this.gerarPgsLs();
    }
  }

  ngOnInit(): void{
    this.itensForm.get('itensPage')?.valueChanges.subscribe((valor) => {
      this.onItensPorPaginaChange(valor);
    });
	}

  onItensPorPaginaChange(valor: number): void {
    this.itensPage = valor;
    this.paginaSelecionada = 1;
    if(valor == 0){
      this.totalPaginas = 1;
    }else{
      this.calcTotalPaginas();
    }
    this.gerarPgsLs();
    this.emitirPaginacao();
  }
  

  selecionarPagina(pagina: number): void {
    this.paginaSelecionada = pagina;
    this.emitirPaginacao();
  }

  primeiraPagina(){
    this.paginaSelecionada = this.paginaIn;
    this.gerarPgsLs();
    this.emitirPaginacao();
  }

  ultimaPagina(){
    this.paginaSelecionada = this.totalPaginas;
    this.gerarPgsLsUltimo();
    this.emitirPaginacao();
  }

  proximaPagina(){
    if(this.paginaSelecionada !== this.totalPaginas){
      this.paginaSelecionada++
      this.emitirPaginacao();
    }

    if(this.paginaSelecionada > this.pgsLs[4]){
      this.gerarPgsLsProximo();
    }
  }

  anteriorPagina(){
    if(this.paginaSelecionada !== this.paginaIn){
      this.paginaSelecionada--
      this.emitirPaginacao();
    }

    if(this.paginaSelecionada < this.pgsLs[0]){
      this.gerarPgsLsAnterios()
    }
  }

  calcTotalPaginas(){
    this.totalPaginas = Math.ceil(this.totalItens / this.itensPage);
  }

  gerarPgsLs(){
    this.pgsLs = [];
    for(let i = 0; i < 5; i++){
      if(this.paginaSelecionada + i <= this.totalPaginas){
        this.pgsLs.push(this.paginaSelecionada + i);
      }
    }
  }

  gerarPgsLsUltimo(){
    this.pgsLs = [];
    let count = this.totalPaginas % 5;
    if(count == 0){
      for(let i = 4; i >= 0; i--){
        this.pgsLs.push(this.paginaSelecionada - i);
      }
    }else{
      for(let i = this.totalPaginas - count; i < this.totalPaginas; i++){
        this.pgsLs.push(i + 1);
      }
    }
  }

  gerarPgsLsProximo(){
    this.pgsLs = [];
    for(let i = 0; i < 5; i++){
      if(this.paginaSelecionada + i <= this.totalPaginas){
        this.pgsLs.push(this.paginaSelecionada + i);
      }
    }
  }

  gerarPgsLsAnterios(){
    this.pgsLs = [];
    for(let i = 4; i >= 0; i--){
      if(this.paginaSelecionada - i >= this.paginaIn){
        this.pgsLs.push(this.paginaSelecionada - i);
      }
    }
  }

  emitirPaginacao() {
    this.paginacaoChange.emit({
      paginaSelecionada: this.paginaSelecionada,
      itensPage: this.itensPage
    });
  }
  
  getValor(item: any, chave: string): any {
    const valor = chave.split('.').reduce((obj, key) => {
      return obj?.[key];
    }, item);

    return valor;
  }

  ordenarPor(chave: string) {
  const coluna = this.colunas.find(c => c.chave === chave);
  if (!coluna || coluna.order === false) return;

  if (this.ordenacao?.chave === chave) {
    this.ordenacao.direcao = this.ordenacao.direcao === 'asc' ? 'desc' : 'asc';
  } else {
    this.ordenacao = { chave, direcao: 'asc' };
  }

  if (!this.paged) {
    this.ordenarDados();
  } else {
    this.orderChange.emit(this.ordenacao);
  }
}


ordenarDados() {
  const { chave, direcao } = this.ordenacao!;
  const direcaoMultiplicador = direcao === 'asc' ? 1 : -1;

  this.dados.sort((a, b) => {
    const valorA = this.getValor(a, chave);
    const valorB = this.getValor(b, chave);

    if (valorA == null) return -1 * direcaoMultiplicador;
    if (valorB == null) return 1 * direcaoMultiplicador;

    if (typeof valorA === 'string') {
      return valorA.localeCompare(valorB) * direcaoMultiplicador;
    }

    if (typeof valorA === 'number' || valorA instanceof Date) {
      return (valorA > valorB ? 1 : valorA < valorB ? -1 : 0) * direcaoMultiplicador;
    }

    return 0;
  });
}

resetPage(){
  this.paginaSelecionada = 1;
  this.paginaIn = 1;
}


}
