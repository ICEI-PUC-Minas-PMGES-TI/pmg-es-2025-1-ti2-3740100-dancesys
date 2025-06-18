import { Component, inject, ViewChild } from '@angular/core';
import { ModalComponent } from '../../../../../components/modal/modal.component';
import { SimpleTableComponent } from '../../../../../components/simple-table/simple-table.component';
import { SearchBoxSingleComponent } from '../../../../../components/search-box-single/search-box-single.component';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { BotaoComponent } from '../../../../../components/botao/botao.component';
import { CommonModule } from '@angular/common';
import { FigurinoFilter } from '../../../../../models/Figurino.model';
import { EventoService } from '../../../../../services/evento.service';
import { AlertService } from '../../../../../services/Alert.service';

@Component({
  selector: 'app-figurinos-admin-page',
  imports: [
    ModalComponent,
		SimpleTableComponent,
		SearchBoxSingleComponent,
		ReactiveFormsModule,
		BotaoComponent,
		CommonModule,
  ],
  templateUrl: './figurinos-admin-page.component.html',
  styleUrl: './figurinos-admin-page.component.css'
})
export class FigurinosAdminPageComponent {
	@ViewChild(SimpleTableComponent) tabela!: SimpleTableComponent;

  eventoService = inject(EventoService);
  alertService = inject(AlertService);

	filterForm: FormGroup;

  paginaAtual: number = 0;
	itensPage: number = 10;
	orderByValue!: string;
	orderValue!: string;

  figurinoObj: any = []

  tipoMap: Record<number, string> = {
		1: "Aluguel",
		2: "Compra",
	};

  colunas = [
		{ chave: "nome", titulo: "Nome" },
		{ chave: "valor", titulo: "Valor", formatar: (valor: Number) => valor != null? this.valorFormater(valor) : '' },
		{ chave: "tipo", titulo: "tipo", formatar: (valor: number) => this.tipoMap[valor] ?? String(valor), },
	];


  tipos: {value: number; name: string}[] = [
    {value: 1, name: "Aluguel"},
    {value: 2, name: "Compra"}
  ]

  constructor(private fb: FormBuilder) {
		this.filterForm = this.fb.group({
			nome: [],
			tipo: [],
			valor: [],
			pagina: [this.paginaAtual],
			tamanho: [this.itensPage],
			orderBy: [this.orderByValue],
			order: [this.orderValue],
		});
	}

  ngOnInit(){
    this.buscar()
  }

  buscar(){
    this.eventoService.filterFigurino(this.getFilter()).subscribe({
      next: (reponse: any) =>{
        if(reponse.total <= 0){
          this.alertService.info("Nenhum registro encontrado")
        }else{
          this.figurinoObj = reponse
          console.log(reponse)
        }
				this.tabela.isLoad(false);
      }
    })
  }

  getFilter() {
      this.filterForm.get("tamanho")?.setValue(this.itensPage);
      this.filterForm.get("pagina")?.setValue(this.paginaAtual);
      this.filterForm.get("orderBy")?.setValue(this.orderByValue);
      this.filterForm.get("order")?.setValue(this.orderValue);
  
      const item = this.filterForm.value;
      const FigurinoFilter: FigurinoFilter = item;
  
      return FigurinoFilter;
    }

  onFilter(){
    this.tabela.isLoad(true);
		this.paginaAtual = 0;
		this.tabela.resetPage();
		this.buscar();
  }

  novo(){

  }

  limparFiltros(){
    this.filterForm = this.fb.group({
			nome: [],
			tipo: [],
			valor: [],
			pagina: [this.paginaAtual],
			tamanho: [this.itensPage],
			orderBy: [this.orderByValue],
			order: [this.orderValue],
		});
  }

  valorFormater(valor: any){
    return `R$${valor}`
  }

  onPaginacaoChange(event: { paginaSelecionada: number; itensPage: number }) {
		this.tabela.isLoad(true);
		this.paginaAtual = --event.paginaSelecionada;
		this.itensPage = event.itensPage;
		this.buscar();
	}

	orderBy(event: { chave: string; direcao: "asc" | "desc" }) {
		this.tabela.isLoad(true);
		this.orderByValue = event.chave;
		this.orderValue = event.direcao;
		this.buscar();
	}
}
