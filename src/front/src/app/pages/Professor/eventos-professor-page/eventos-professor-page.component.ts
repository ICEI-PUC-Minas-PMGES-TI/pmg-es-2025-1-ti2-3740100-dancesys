import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { EventoResponse } from '../../../models/evento.model';
import { AlertService } from '../../../services/Alert.service';
import { AdminService } from '../../../services/admin.service';

@Component({
  selector: 'app-eventos-professor-page',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './eventos-professor-page.component.html',
  styleUrl: './eventos-professor-page.component.css'
})
export class EventosProfessorPageComponent {
  eventos: EventoResponse | undefined;

	constructor(
		private adminService: AdminService,
		private alertService: AlertService,
	) {
	
	}

	ngOnInit(): void {
		this.onFiltrar();
	}

	onFiltrar(): void {
		this.adminService
			.fetchEventos({
				nome: "",
				local: "",
				data: null,
				dataInicio: new Date(),
				alunos: null,
				pagina: 0,
				tamanho: 0,
				orderBy: '',
				order: ''
			})
			.subscribe({
				next: (res: EventoResponse) => {
					this.eventos = res;
				},
				error: (err: any) => {
					this.alertService.erro("Erro ao carregar eventos.");
				},
			});
	}
}
