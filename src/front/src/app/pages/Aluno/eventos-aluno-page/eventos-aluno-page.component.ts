import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { CommonModule } from "@angular/common";
import { ReactiveFormsModule } from "@angular/forms";
import { UsuarioService } from "../../../services/usuario.service";
import { AdminService } from "../../../services/admin.service";
import { EventoResponse } from "../../../models/evento.model";
import { AlertService } from "../../../services/Alert.service";
import { ModalComponent } from "../../../components/modal/modal.component";
import { EventoService } from "../../../services/evento.service";
import { Ingresso } from "../../../models/Ingresso.model";

@Component({
	selector: "app-eventos-aluno-page",
	templateUrl: "./eventos-aluno-page.component.html",
	styleUrls: ["./eventos-aluno-page.component.css"],
	imports: [CommonModule, ReactiveFormsModule, ModalComponent],
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
		private usuarioService: UsuarioService,
		private eventoService: EventoService
	) {
		this.ingressoForm = this.fb.group({
			qtdIngressos: [
				1,
				[Validators.required, Validators.min(1), Validators.max(10)],
			],
			tipoIngresso: [1, Validators.required],
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

			const ingresso : Ingresso = {
				id: null,
				tipo: tipoIngresso,
				codigo: null,
				quantidade: qtdIngressos,
				idAluno: this.alunoId,
				idEvento: this.eventoId
			}
			
			this.eventoService.gerarIngresso(ingresso).subscribe({
				next: () =>{
					this.alertService.sucesso("Compra realizada com sucesso!");
				}
			})
			this.fecharModal();
		} else {
			this.alertService.erro("Formulario invalido");
		}
	}
}
