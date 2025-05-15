import { Component, inject, OnDestroy, OnInit, ViewChild } from "@angular/core";
import { SimpleTableComponent } from "../../../../../components/simple-table/simple-table.component";
import { BotaoComponent } from "../../../../../components/botao/botao.component";
import { CommonModule, DatePipe, Time } from "@angular/common";
import { FormsModule, NgForm } from "@angular/forms";
import { ModalComponent } from "../../../../../components/modal/modal.component";
import { AdminService } from "../../../../../services/admin.service";
import { Subscription } from "rxjs";
import { ApresentacaoEvento } from "../../../../../models/apresentacao_evento.model";

@Component({
	selector: "app-eventos-admin-page",
	imports: [
		SimpleTableComponent,
		BotaoComponent,
		CommonModule,
		DatePipe,
		FormsModule,
		ModalComponent,
	],
	templateUrl: "./apresentacoes-admin-page.component.html",
	styleUrl: "./apresentacoes-admin-page.component.css",
})
export class ApresentacoesAdminPageComponent implements OnInit, OnDestroy {
	@ViewChild("filterForm") filterForm!: NgForm;
	private adminService = inject(AdminService);

	paginaAtual: number = 0;
	itensPage: number = 10;

	currentApresentacaoEditar: ApresentacaoEvento | undefined = undefined;

	excluirApresentacaoId: number | undefined = undefined;

	isModalCriarOpen: boolean = false;
	isModalEditarOpen: boolean = false;
	isModalExcluirOpen: boolean = false;

	colunas = [
		{ chave: "nome", titulo: "Apresentação" },
		{
			chave: "horaInicio",
			titulo: "Horário de Início",
			formatar: (valor: Time) =>
				valor != null ? `${valor.hours}:${valor.minutes}` : '',
		},
		{
			chave: "horaFim",
			titulo: "Horário Final",
			formatar: (valor: Time) =>
				valor != null ? `${valor.hours}:${valor.minutes}` : '',
		},
	];
	acoes = [
		{
			icon: "edit",
			title: "Editar",
			cor: "dark",
			callback: (item: ApresentacaoEvento) => this.onToggleEditarModal(item),
		},
		{
			icon: "delete",
			title: "Excluir",
			cor: "dark",
			callback: (item: ApresentacaoEvento) => this.onOpenExcluirModal(item.id),
		},
	];

	apresentacoes: ApresentacaoEvento[] = [];
	apresentacoesSub?: Subscription;

	ngOnInit(): void {
		// codigo da subscription de apresentacao aqui
	}

	ngOnDestroy(): void {
		this.apresentacoesSub?.unsubscribe();
	}

	onPaginacaoChange(event: { paginaSelecionada: number; itensPage: number }) {
		this.paginaAtual = --event.paginaSelecionada;
		this.itensPage = event.itensPage;
		this.onFiltrar();
	}

	onToggleCriarModal() {
		this.isModalCriarOpen = !this.isModalCriarOpen;
	}

	onToggleEditarModal(item?: ApresentacaoEvento) {
		this.isModalEditarOpen = !this.isModalEditarOpen;
		if (this.isModalEditarOpen) {
			this.currentApresentacaoEditar = item;
			return;
		}
	}
	onToggleExcluirModal(confirmed?: boolean | void) {
		this.isModalExcluirOpen = !this.isModalExcluirOpen;
		if (confirmed) {
			// codigo de deletar uma apresentacao pelo adminService
		}
		if (!this.isModalEditarOpen) {
			this.excluirApresentacaoId = undefined;
		}
	}

	onOpenExcluirModal(id: number) {
		this.onToggleExcluirModal();
		this.excluirApresentacaoId = id;
	}

	submitCriarApresentacaoForm(form: NgForm) {
		if (form.valid) {
			// codigo para criar a apresentacao pelo adminService
		}
	}

	onFiltrar() { }
}
