import { Component, inject } from "@angular/core";
import {
	CalendarioItemComponent,
	ItemDeCalendario,
} from "../../../components/calendario-item/calendario-item.component";
import { UsuarioTipos } from "../../../models/usuario.model";
import { TipoAluno } from "../../../models/aluno.model";
import { BotaoComponent } from "../../../components/botao/botao.component";
import { AdminService } from "../../../services/admin.service";
import { UsuarioService } from "../../../services/usuario.service";
import { AulaOcorrenciaFilter } from "../../../models/AulaOcorrencia.model";
import { switchMap } from "rxjs";
import { EnsaioFilter } from "../../../models/Ensaio.model";

@Component({
	selector: "app-calendar-aluno-page",
	imports: [CalendarioItemComponent, BotaoComponent],
	templateUrl: "./calendar-aluno-page.component.html",
	styleUrl: "./calendar-aluno-page.component.css",
})
export class CalendarAlunoPageComponent {
	currPag: number = 0;

	adminService = inject(AdminService);
	userService = inject(UsuarioService);

	calendarItems: ItemDeCalendario[] = [];
	showCalItems: Array<ItemDeCalendario[]> = [];
	paginas = Math.ceil(this.calendarItems.length / 5);

	ngOnInit(): void {
		this.carregarItens();
	}

	private paginarItens() {
		this.paginas = Math.ceil(this.calendarItems.length / 5);
		let oldArr = [...this.calendarItems];
		const arr = [];
		for (let i = 0; i < this.paginas; i++) {
			arr.push([...oldArr.splice(0, 5)]);
		}
		this.showCalItems = [...arr];
	}

	private carregarItens() {
		this.userService
			.getAlunoIdByUserId(this.userService.usuario().id)
			.subscribe({
				next: (alunoId) => {
					this.adminService
						.fetchAulasOcorrentes({
							alunos: [alunoId],
						} as AulaOcorrenciaFilter)
						.pipe(
							switchMap((aulasOcorrentes: any) => {
								const arr = aulasOcorrentes?.conteudo.map(
									(aula: any) => {
										return {
											title: `Aula de ${aula.idAula.idModalidade.nome}`,
											subtitle: `Professor: ${aula.idAula.idProfessor.idUsuario.nome}`,
											dataHorario: new Date(
												new Date(
													aula.data,
												).getFullYear(),
												new Date(aula.data).getMonth(),
												new Date(aula.data).getDate(),
												aula.idAula.horarioInicio.split(
													":",
												)[0],
												aula.idAula.horarioInicio.split(
													":",
												)[1],
											),
											tipoUsuario:
												this.userService.usuario().tipo,
											TipoAluno: TipoAluno.FLEXIVEL,
										} as ItemDeCalendario;
									},
								);
								this.calendarItems = [
									...this.calendarItems,
									...arr,
								];
								return this.adminService.filterEnsaio({
									alunos: [alunoId],
								} as EnsaioFilter);
							}),
						)
						.subscribe({
							next: (ensaios: any) => {
								const arr = ensaios?.conteudo.map(
									(ensaio: any) => {
										console.log(ensaio);
										return {
											title: `Ensaio de ${ensaio.idApresentacaoEvento.nome}`,
											subtitle: `Professor: ${ensaio.idProfessor.idUsuario.nome}`,
											dataHorario: new Date(
												ensaio.dataHoraInicio,
											),
											tipoUsuario:
												this.userService.usuario().tipo,
											TipoAluno: TipoAluno.FLEXIVEL,
										} as ItemDeCalendario;
									},
								);
								this.calendarItems = [
									...this.calendarItems,
									...arr,
								];
								this.calendarItems.sort((a, b) =>
									a.dataHorario.getTime() <
									b.dataHorario.getTime()
										? 1
										: -1,
								);
								this.paginarItens();
								console.log(this.showCalItems);
							},
						});
				},
				error: (erro) => {
					console.log(erro);
				},
			});
	}

	nextPage() {
		if (this.currPag == this.paginas - 1) {
			this.currPag = 0;
			return;
		}
		this.currPag++;
	}

	previousPage() {
		if (this.currPag == 0) {
			this.currPag = this.paginas - 1;
			return;
		}
		this.currPag--;
	}
}
