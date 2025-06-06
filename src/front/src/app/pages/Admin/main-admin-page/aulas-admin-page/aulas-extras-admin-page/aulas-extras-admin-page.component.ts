import { Component, inject, ViewChild } from "@angular/core";
import { ModalComponent } from "../../../../../components/modal/modal.component";
import { SimpleTableComponent } from "../../../../../components/simple-table/simple-table.component";
import { MultiSelectInputComponent } from "../../../../../components/multi-select-input/multi-select-input.component";
import { FormBuilder, FormGroup, ReactiveFormsModule } from "@angular/forms";
import { BotaoComponent } from "../../../../../components/botao/botao.component";
import { CommonModule } from "@angular/common";
import { SearchBoxSingleComponent } from "../../../../../components/search-box-single/search-box-single.component";
import { AdminService } from "../../../../../services/admin.service";
import { ProfessorFiltro } from "../../../../../models/professor.model";
import { UsuarioFiltro } from "../../../../../models/usuario.model";
import { AulaExtraFilter } from "../../../../../models/AulaExtra.model";
import { AulaService } from "../../../../../services/aula.service";
import { AlertService } from "../../../../../services/Alert.service";

@Component({
	selector: "app-aulas-extras-admin-page",
	imports: [
		ModalComponent,
		SimpleTableComponent,
		MultiSelectInputComponent,
		SearchBoxSingleComponent,
		ReactiveFormsModule,
		BotaoComponent,
		CommonModule,
	],
	templateUrl: "./aulas-extras-admin-page.component.html",
	styleUrl: "./aulas-extras-admin-page.component.css",
	standalone: true,
})
export class AulasExtrasAdminPageComponent {
  	@ViewChild(SimpleTableComponent) tabela!: SimpleTableComponent


	adminService = inject(AdminService);
	aulaService = inject(AulaService)
	alertServie = inject(AlertService)

	filterForm: FormGroup;

	professoresFilterLs: any[] = [];
	alunosFilterLs: any[] = [];
	aulaExtraObj: any = []

	paginaAtual: number = 0;
	itensPage: number = 10;
	orderByValue!: string;
	orderValue!: string

	statusMap: Record<number, string> = {
		1: "Pendente",
		2: "Aceita",
		3: "Indeferida",
		4: "cancelada"
	};

	colunas = [
		{ chave: "idAluno.idUsuario.nome", titulo: "Aluno" },
		{ chave: "idProfessor.idUsuario.nome", titulo: "Professor"},
		{ chave: "horarioInicio", titulo: "Inicio", formatar: (valor: Date) => valor != null ? this.formartarData(valor) : "" },
		{ chave: "horarioFim", titulo: "Fim", formatar: (valor: Date) => valor != null ? this.formartarData(valor) : "" },
		{ chave: "motivo", titulo: "Motivo", width: "20%"},
    	{ chave: "situacao", titulo: "Situacao", formatar: (valor: number) => this.statusMap[valor] ?? String(valor),}
	];

  acoes = [
		{
			icon: "check",
			title: "Acitar",
			cor: "green",
			callback: (item: any) => this.aceitar(item),
		},
		{
			icon: "delete",
			title: "Indeferir",
			cor: "red",
			callback: (item: any) => this.indeferir(item.id),
		},
    	{
			icon: "warning",
			title: "Cancelar",
			cor: "yellow",
			callback: (item: any) => this.cancelar(item.id),
		},
	];

	statusObj: { value: number; name: string }[] = [
		{ value: 1, name: "Pendente" },
		{ value: 2, name: "Indeferido" },
		{ value: 3, name: "Aceito" },
		{ value: 4, name: "Cancelado" },
	];

	constructor(private fb: FormBuilder) {
		this.filterForm = this.fb.group({
			idAluno: [],
			idProfessor: [],
			status: [[]],
			pagina: [this.paginaAtual],
			tamanho: [this.itensPage],
			orderBy: [this.orderByValue],
			order: [this.orderValue]
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
		const aulaFilter: AulaExtraFilter = item;

		return aulaFilter;
	}

	buscarProfessor(termo: any) {
		const filtro: ProfessorFiltro = {
			nome: termo,
			email: "",
			cpf: "",
			status: 1,
		};
		this.adminService.filterProfessores(filtro).subscribe({
			next: (response) => {
				this.professoresFilterLs = response;
			},
		});
	}

	buscarAluno(termo: any) {
		const filtro: UsuarioFiltro = {
			nome: termo,
			email: "",
			cpf: "",
			tipo: 2,
			status: 1,
		};
		this.adminService.filterUsuarios(filtro).subscribe({
			next: (response) => {
				this.alunosFilterLs = response;
			},
		});
	}

	aceitar(item: any){

	}

	indeferir(item: any){

	}

	cancelar(item: any){

	}

	onFilter() {
		this.tabela.isLoad(true)
		this.paginaAtual = 0;
		this.tabela.resetPage()
		this.buscar()
	}

	limparFiltros(){
		this.filterForm = this.fb.group({
			idAluno: [],
			idProfessor: [],
			status: [[]],
			pagina: [this.paginaAtual],
			tamanho: [this.itensPage],
			orderBy: [this.orderByValue],
			order: [this.orderValue]
		});
	}

	buscar(){
		this.aulaService.filterAulaExtra(this.getFilter()).subscribe({
			next: (response: any) =>{
				if(response.total == 0){
					this.alertServie.info("Nenhum registro encontrado")
				}else{
					this.aulaExtraObj = response
				}
    		this.tabela.isLoad(false)
			}
		})
	}

	onPaginacaoChange(event: { paginaSelecionada: number; itensPage: number }) {
		this.tabela.isLoad(true)
		this.paginaAtual = --event.paginaSelecionada;
		this.itensPage = event.itensPage;
		this.buscar();
  	}	

	orderBy(event: { chave: string, direcao: 'asc' | 'desc' }){
		this.tabela.isLoad(true)
		this.orderByValue = event.chave;
		this.orderValue = event.direcao;
		this.buscar()
	}

	formartarData(valor: Date) {
		const str = valor.toLocaleString();
		const strarr = str.split("T");
		const strD = strarr[0].split("-");
		return `${strD[2]}/${strD[1]}/${strD[0]} - ${strarr[1]}`;
	}
}
