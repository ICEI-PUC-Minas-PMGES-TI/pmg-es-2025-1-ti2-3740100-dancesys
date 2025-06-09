import { Component, inject, ViewChild } from '@angular/core';
import { ModalComponent } from '../../../../../components/modal/modal.component';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SimpleTableComponent } from '../../../../../components/simple-table/simple-table.component';
import { MultiSelectInputComponent } from '../../../../../components/multi-select-input/multi-select-input.component';
import { SearchBoxSingleComponent } from '../../../../../components/search-box-single/search-box-single.component';
import { BotaoComponent } from '../../../../../components/botao/botao.component';
import { CommonModule } from '@angular/common';
import { NgxMaskDirective, NgxMaskPipe, provideNgxMask } from 'ngx-mask';
import { AdminService } from '../../../../../services/admin.service';
import { ProfessorFiltro } from '../../../../../models/professor.model';
import { AulaExperimental, AulaExperimentalFilter } from '../../../../../models/AulaExperimental.model';
import { AulaService } from '../../../../../services/aula.service';
import { AlertService } from '../../../../../services/Alert.service';

enum ToggleModal {
	NEW = "Criar Aula experimental",
	EDIT = "Editar Aula experimental",
}

@Component({
  selector: 'app-aulas-experimentais-admin-page',
  standalone: true,
  imports: [
    ModalComponent,
		FormsModule,
		SimpleTableComponent,
		MultiSelectInputComponent,
		SearchBoxSingleComponent,
		ReactiveFormsModule,
		BotaoComponent,
		CommonModule,
    NgxMaskDirective,
    NgxMaskPipe
  ],
  providers: [
    NgxMaskPipe,
    provideNgxMask()
  ],
  templateUrl: './aulas-experimentais-admin-page.component.html',
  styleUrl: './aulas-experimentais-admin-page.component.css'
})

export class AulasExperimentaisAdminPageComponent {
	@ViewChild(SimpleTableComponent) tabela!: SimpleTableComponent;

  adminService = inject(AdminService);
  aulaService = inject(AulaService);
  alertService = inject(AlertService);
  maskPipe = inject(NgxMaskPipe)

	filterForm: FormGroup;
	aulaForm: FormGroup;

  ToggleModal = ToggleModal

  isModalOpen: boolean = false
  isEdit: boolean = false

  professoresLs: any = []

  situacao: {value: number, name: string}[] = [
    {value: 1, name: 'Pendente'},
    {value: 2, name: 'Convertido'},
    {value: 3, name: 'Recusado'}
  ]

  motivos: {value: number, name: string}[] = [
    {value: 1, name: 'Interesse'},
    {value: 2, name: 'Financeiro'},
    {value: 3, name: 'Outro'}
  ]

  situacaoMap: Record<number, string> = {
		1: "Pendente",
		2: "Convertido",
		3: "Recusado",
	};

  motivoMap: Record<number, string> = {
		1: "Pendente",
		2: "Convertido",
		3: "Recusado",
	};

  colunas = [
		{ chave: "idProfessor.idUsuario.nome", titulo: "Professor" },
		{ chave: "nome", titulo: "Nome" },
		{ chave: "numero", titulo: "Numero", formatar: (valor: string) => this.maskPipe.transform(valor, '(00) 00000-0000') ?? '' },
		{ chave: "dataHorarioInicio", titulo: "Inicio", formatar: (valor: Date) => valor != null ? this.formartarData(valor) : "" },
		{ chave: "dataHorarioFim", titulo: "Fim", formatar: (valor: Date) => valor != null ? this.formartarData(valor) : "" },
		{ chave: "situcao", titulo: "Situcao", formatar: (valor: number) => valor != null? this.situacaoMap[valor] ?? String(valor) : ""},
		{ chave: "motivo", titulo: "Motivo", formatar: (valor: number) => valor != null? this.motivoMap[valor] ?? String(valor) : "" },
  ]

  acoes = [
		{
			icon: "edit",
			title: "Editar",
			cor: "dark",
			callback: (item: any) => this.editar(item),
		},
	];

  paginaAtual: number = 0;
	itensPage: number = 10;
	orderByValue!: string;
	orderValue!: string;

  aulasObj: any = []

  constructor(private fb: FormBuilder) {
		this.filterForm = this.fb.group({
			dataInicial: [],
      dataFinal: [],
      cpf: [],
      idProfessor: [],
      situacao: [[]],
      motivos: [[]],
			pagina: [this.paginaAtual],
			tamanho: [this.itensPage],
			orderBy: [this.orderByValue],
			order: [this.orderValue],
		});

    this.aulaForm = this.fb.group({
        data: [],
        horarioInicio: [],
        horarioFim: [],
        cpf: [],
        numero: [],
        nome: [],
        idProfessor: []
    });
	}

  ngOnInit(){
    this.buscar()
  }

  getFilter() {
    this.filterForm.get("tamanho")?.setValue(this.itensPage);
    this.filterForm.get("pagina")?.setValue(this.paginaAtual);
    this.filterForm.get("orderBy")?.setValue(this.orderByValue);
    this.filterForm.get("order")?.setValue(this.orderValue);
  
    const item = this.filterForm.value;
    const aulaFilter: AulaExperimentalFilter  = item;
  
    return aulaFilter;
  }
  
  onFilter() {
    this.tabela.isLoad(true);
    this.paginaAtual = 0;
    this.tabela.resetPage();
    this.buscar();
  }

  buscar(){
    this.aulaService.filterAulaExperimental(this.getFilter()).subscribe({
      next: (response: any) =>{
        if(response.total <= 0){
          this.alertService.info("Nenhum registro encontrado!")
        }else{
            this.aulasObj = response
        }
        this.tabela.isLoad(false)
      }
    })
  }
  isFormValido(){
    return this.aulaForm.valid;
  }

  salvar(){
    this.aulaService.salvarAulaExperimental(this.getAulaForm()).subscribe({
      next: (response) =>{
        this.closeModal()
        this.alertService.sucesso(this.isEdit? 'Aula experimental alterado com sucesso' : 'Aula experimental criada com sucesso')
        this.buscar()
      }
    })
  }

  editar(item: any){
    
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

  getAulaForm(){
    const item = this.aulaForm.value;
    const aulaItem: AulaExperimental = item;
    
    return aulaItem;
  }

  limparAulaForm(){
    this.aulaForm = this.fb.group({
        data: [],
        horarioInicio: [],
        horarioFim: [],
        cpf: [],
        numero: [],
        nome: [],
        idProfessor: []
    });
  }

  openModal(){
    this.isModalOpen = true
    this.professoresLs = []
  }

  closeModal(){
    this.isModalOpen = false
    this.professoresLs = []
    this.limparAulaForm()
  }

  limparFiltros(){
    this.filterForm = this.fb.group({
			dataInicial: [],
      dataFinal: [],
      cpf: [],
      idProfessor: [],
      situacao: [[]],
      motivos: [[]],
			pagina: [this.paginaAtual],
			tamanho: [this.itensPage],
			orderBy: [this.orderByValue],
			order: [this.orderValue],
		});
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

  formartarData(valor: Date) {
		const str = valor.toLocaleString();
		const strarr = str.split("T");
		const strD = strarr[0].split("-");
		return `${strD[2]}/${strD[1]}/${strD[0]} - ${strarr[1]}`;
	}
}
