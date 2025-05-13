import { Component, inject } from "@angular/core";
import { ModalComponent } from "../../../../components/modal/modal.component";
import { BotaoComponent } from "../../../../components/botao/botao.component";
import { SimpleTableComponent } from "../../../../components/simple-table/simple-table.component";
import { MultiSelectInputComponent } from "../../../../components/multi-select-input/multi-select-input.component";
import { CommonModule } from "@angular/common";
import {
	Form,
	FormBuilder,
	FormGroup,
	ReactiveFormsModule,
} from "@angular/forms";
import { AdminService } from "../../../../services/admin.service";
import { SearchBoxMultiComponent } from "../../../../components/search-box-multi/search-box-multi.component";
import { UsuarioFiltro } from "../../../../models/usuario.model";
import { AlertService } from "../../../../services/Alert.service";

@Component({
	selector: "app-fincanceiro-admin-page",
	standalone: true,
	imports: [
		CommonModule,
		ReactiveFormsModule,
		BotaoComponent,
		ModalComponent,
		SimpleTableComponent,
		MultiSelectInputComponent,
		SearchBoxMultiComponent,
	],
	templateUrl: "./fincanceiro-admin-page.component.html",
	styleUrl: "./fincanceiro-admin-page.component.css",
})
export class FincanceiroAdminPageComponent {
	filterForm: FormGroup;
	adminService = inject(AdminService);
	alertService = inject(AlertService);

	paginaAtual: number = 0;
	itensPage: number = 10;
	dividendos: any = [];

	constructor(private fb: FormBuilder) {
		this.filterForm = this.fb.group({
			criadoEm: [""],
			pagoEm: [""],
			status: [[]],
			tipos: [[]],
			alunos: [[]],
			tamanho: [this.itensPage],
			pagina: [this.paginaAtual],
		});
	}
	statusMap: Record<number, string> = {
		1: "Pendente",
		2: "Pago",
		3: "Atrasado",
	};

	tipoMap: Record<number, string> = {
		1: "Matricula",
		2: "Mensalidade",
		3: "Aula",
		4: "Evento",
		5: "Figurino",
	};

	colunas = [
		{ chave: "idAluno.idUsuario.nome", titulo: "Aluno" },
		{
			chave: "tipo",
			titulo: "Tipo",
			formatar: (valor: number) => this.tipoMap[valor] ?? String(valor),
		},
		{
			chave: "criadoEm",
			titulo: "Criado em",
			formatar: (valor: Date) =>
				valor != null ? this.formartarData(valor) : "",
		},
		{
			chave: "pagoEm",
			titulo: "Pago em",
			formatar: (valor: Date) =>
				valor != null ? this.formartarData(valor) : "",
		},
		{
			chave: "mesesAtrasos",
			titulo: "Meses Atrasados",
			width: "12%",
			formatar: (valor: number) =>
				valor != null ? this.formatarAtraso(valor) : "",
		},
		{
			chave: "valor",
			titulo: "Valor",
			width: "12%",
			formatar: (valor: number) =>
				valor != null ? this.formatarValor(valor) : "",
		},
		{
			chave: "status",
			titulo: "Status",
			formatar: (valor: number) => this.statusMap[valor] ?? String(valor),
		},
	];

	alunosFilterLs: any[] = [];
	alunosFilter: any[] = [];
	tiposFilter: any[] = [];
	statusFilter: any[] = [];

	tipos: any[] = [
		{ value: 1, text: "Matricula" },
		{ value: 2, text: "Mensalidade" },
		{ value: 3, text: "Aula" },
		{ value: 4, text: "Evento" },
		{ value: 5, text: "Figurino" },
	];

	status: any[] = [
		{ value: 1, text: "Pendente" },
		{ value: 2, text: "Pago" },
		{ value: 3, text: "Atrasado" },
	];

	datesfilter: { value: string; text: string }[] = [];

	ngOnInit(): void {
		this.datesfilter = this.gerarMesesAteAtual("04/2025");
		this.buscar();
	}

	getFilter() {
		this.filterForm = this.fb.group({
			criadoEm: [this.filterForm.get("criadoEm")?.value],
			pagoEm: [this.filterForm.get("pagoEm")?.value],
			status: [this.statusFilter],
			tipos: [this.tiposFilter],
			alunos: [this.alunosFilter],
		});

		return this.filterForm.value;
	}
	buscar() {
		this.adminService.filterDividendos(this.getFilter()).subscribe({
			next: (response: any) => {
				if(response.total == 0){
					this.alertService.info("Nenhum registro encontrado!")
				}else{
					this.dividendos = response;
				}
			},
			error: (err: any) => {},
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

	formartarData(valor: Date) {
		const str = valor.toString();
		const strarr = str.split("-");
		return `${strarr[2]}/${strarr[1]}/${strarr[0]}`;
	}

	formatarValor(valor: number) {
		return `R$${valor}`;
	}

	formatarAtraso(valor: number) {
		if (valor == 1) return `${valor} mês`;
		return `${valor} meses`;
	}

	gerarMesesAteAtual(inicio: string): { value: string; text: string }[] {
		const resultado: { value: string; text: string }[] = [];

		const nomesMeses = [
			"jan",
			"fev",
			"mar",
			"abr",
			"mai",
			"jun",
			"jul",
			"ago",
			"set",
			"out",
			"nov",
			"dez",
		];

		const [mesInicio, anoInicio] = inicio.split("/").map(Number);
		const dataAtual = new Date();
		const mesAtual = dataAtual.getMonth() + 1;
		const anoAtual = dataAtual.getFullYear();

		let ano = anoInicio;
		let mes = mesInicio;

		while (ano < anoAtual || (ano === anoAtual && mes <= mesAtual)) {
			const mesStr = mes.toString().padStart(2, "0");
			resultado.push({
				value: `${mesStr}/${ano}`,
				text: `${nomesMeses[mes - 1]}/${ano}`,
			});

			mes++;
			if (mes > 12) {
				mes = 1;
				ano++;
			}
		}

		return resultado;
	}

	onAlunoChange(alunos: any[]) {
		this.alunosFilter = alunos;
	}

	onTipoChange(tipos: any[]) {
		this.tiposFilter = tipos;
	}

	onStatusChange(status: any[]) {
		this.statusFilter = status;
	}

	onPaginacaoChange(event: { paginaSelecionada: number; itensPage: number }) {
		this.paginaAtual = --event.paginaSelecionada;
		this.itensPage = event.itensPage;
		this.buscar();
	}
}
