import { Component, ViewChild } from "@angular/core";
import { FormsModule, NgForm } from "@angular/forms";
import { ModalComponent } from "../../../../../components/modal/modal.component";
import { BotaoComponent } from "../../../../../components/botao/botao.component";
import { CommonModule } from "@angular/common";
import { SimpleTableComponent } from "../../../../../components/simple-table/simple-table.component";

@Component({
	selector: "app-aulas-recorrentes-admin-page",
	imports: [
		ModalComponent,
		BotaoComponent,
		CommonModule,
		FormsModule,
		SimpleTableComponent,
	],
	templateUrl: "./aulas-recorrentes-admin-page.component.html",
	styleUrl: "./aulas-recorrentes-admin-page.component.css",
})
export class AulasRecorrentesAdminPageComponent {
	@ViewChild("filterForm") filterForm!: NgForm;
	paginaAtual: number = 0;
	itensPage: number = 10;
	colunas = [
		{ chave: "idProfessor.idUsuario.nome", titulo: "Professor" },
		{ chave: "data", titulo: "Data" },
		{ chave: "horario", titulo: "Horário" },
		{ chave: "status", titulo: "Status", width: "10%" },
		{ chave: "motivo", titulo: "Motivo" },
	];
	acoes = [
		{
			icon: "warning",
			title: "Cancelar",
			cor: "dark",
			callback: (item: any) => console.log(item),
		},
	];
	onPaginacaoChange(event: { paginaSelecionada: number; itensPage: number }) {
		this.paginaAtual = --event.paginaSelecionada;
		this.itensPage = event.itensPage;
		this.onFiltrar();
	}
	onFiltrar() {}
}
