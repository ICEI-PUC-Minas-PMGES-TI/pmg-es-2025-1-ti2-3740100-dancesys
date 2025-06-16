import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { CommonModule } from "@angular/common";
import { ReactiveFormsModule } from "@angular/forms";
import { UsuarioService } from "../../../services/usuario.service";
import { AdminService } from "../../../services/admin.service";
import { EventoResponse } from "../../../models/evento.model";
import { AlertService } from "../../../services/Alert.service";

@Component({
	selector: "app-eventos-aluno-page",
	templateUrl: "./eventos-aluno-page.component.html",
	styleUrls: ["./eventos-aluno-page.component.css"],
	imports: [CommonModule, ReactiveFormsModule],
})
export class EventosAlunoPageComponent implements OnInit {
	eventos: EventoResponse | undefined;
	isModalOpen = false;
	eventoId: number | undefined;
	alunoId: number | undefined;
	ingressoForm: FormGroup;

	constructor(
		private adminService: AdminService,
		private alertService: AlertService,
		private fb: FormBuilder,
		private usuarioService: UsuarioService
	) {
		this.ingressoForm = this.fb.group({
			qtdIngressos: [
				1,
				[Validators.required, Validators.min(1), Validators.max(10)],
			],
			tipoIngresso: ["normal", Validators.required],
		});
	}

	ngOnInit(): void {
		this.alunoId = this.usuarioService.usuario()?.id;
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
				tamanho: 0,
				orderBy: '',
				order: ''
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

	comprarIngresso(eventoId: number): void {
		this.eventoId = eventoId;
		this.isModalOpen = true;
	}

	fecharModal(): void {
		this.isModalOpen = false;
	}

	processarCompra(): void {
		if (this.ingressoForm.valid && this.alunoId !== undefined) {
			const qtdIngressos = this.ingressoForm.value.qtdIngressos;
			const tipoIngresso = this.ingressoForm.value.tipoIngresso;
			console.log(
				`Evento ID: ${this.eventoId}, Aluno ID: ${this.alunoId}, Quantidade: ${qtdIngressos}, Tipo: ${tipoIngresso}`
			);
			this.alertService.sucesso("Compra realizada com sucesso!");
			this.fecharModal();
		} else {
			this.alertService.erro("ID do aluno não encontrado.");
		}
	}
}
