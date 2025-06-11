import { Component, OnInit } from "@angular/core";
import { CommonModule } from '@angular/common';
import { AdminService } from "../../../services/admin.service";
import { EventoResponse } from "../../../models/evento.model";
import { AlertService } from "../../../services/Alert.service";

@Component({
	selector: "app-eventos-aluno-page",
	templateUrl: "./eventos-aluno-page.component.html",
	styleUrls: ["./eventos-aluno-page.component.css"],
  imports: [CommonModule]
})
export class EventosAlunoPageComponent implements OnInit {
	eventos: EventoResponse | undefined;

	constructor(
		private adminService: AdminService,
		private alertService: AlertService
	) {}

	ngOnInit(): void {
		this.onFiltrar();
	}

	onFiltrar(): void {
		this.adminService
			.fetchEventos({
				nome: "",
				local: "",
				data: null,
				alunos: null,
				pagina: 0,
				tamanho: 10,
			})
			.subscribe({
				next: (res: EventoResponse) => {
					this.eventos = res;
				},
				error: (err) => {
					this.alertService.erro("Erro ao carregar eventos.");
				},
			});
	}
}
