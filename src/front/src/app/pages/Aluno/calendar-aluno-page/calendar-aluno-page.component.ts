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
import { MiniCalendarComponent } from "../../../components/mini-calendar/mini-calendar.component";
import { DatePipe } from "@angular/common";

@Component({
	selector: "app-calendar-aluno-page",
	imports: [
		CalendarioItemComponent,
		MiniCalendarComponent,
		BotaoComponent,
		DatePipe,
	],
	templateUrl: "./calendar-aluno-page.component.html",
	styleUrl: "./calendar-aluno-page.component.css",
})
export class CalendarAlunoPageComponent {
	currPag: number = 0;

	adminService = inject(AdminService);
	userService = inject(UsuarioService);

	calendarItems: ItemDeCalendario[] = [];
	showCalItems: Array<ItemDeCalendario[]> = [];
	selectedDay: Date = new Date();
	dadosSobreMesSelecionado!: { firstDay: Date; lastDay: Date };
	paginas = Math.ceil(this.calendarItems.length / 5);

	vendoMeuCalendario: boolean = true;

	ngOnInit(): void {
		const currentDate = new Date();
		const firstDayOfMonth = new Date(
			currentDate.getFullYear(),
			currentDate.getMonth(),
			1,
		);
		const lastDayOfMonth = new Date(
			currentDate.getFullYear(),
			currentDate.getMonth() + 1,
			0,
		);
		this.dadosSobreMesSelecionado = {
			firstDay: firstDayOfMonth,
			lastDay: lastDayOfMonth,
		};
		this.carregarItens();
	}

	private paginarItens() {
		let oldArr = [
			...this.calendarItems.filter((item) => {
				return (
					item.dataHorario.getDate() === this.selectedDay.getDate() &&
					item.dataHorario.getMonth() ===
						this.selectedDay.getMonth() &&
					item.dataHorario.getFullYear() ===
						this.selectedDay.getFullYear()
				);
			}),
		];
		this.paginas = Math.ceil(oldArr.length / 5);
		const arr = [];
		for (let i = 0; i < this.paginas; i++) {
			arr.push([...oldArr.splice(0, 5)]);
		}
		this.showCalItems = [...arr];
	}

	private carregarItens() {
		this.calendarItems = [];
		this.userService
			.getAlunoIdByUserId(this.userService.usuario()!.id)
			.subscribe({
				next: (alunoId) => {
					this.adminService
						.fetchAulasOcorrentes({
							alunos: this.vendoMeuCalendario ? [alunoId] : null,
							dataInicio: this.dadosSobreMesSelecionado.firstDay,
							dataFim: this.dadosSobreMesSelecionado.lastDay,
							alunoNotIm: !this.vendoMeuCalendario
								? alunoId
								: null,
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
												this.userService.usuario()!
													.tipo,
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
									dataInicio:
										this.dadosSobreMesSelecionado.firstDay,
									dataFim:
										this.dadosSobreMesSelecionado.lastDay,
								} as EnsaioFilter);
							}),
						)
						.subscribe({
							next: (ensaios: any) => {
								const arr = ensaios?.conteudo.map(
									(ensaio: any) => {
										return {
											title: `Ensaio de ${ensaio.idApresentacaoEvento.nome}`,
											subtitle: `Professor: ${ensaio.idProfessor.idUsuario.nome}`,
											dataHorario: new Date(
												ensaio.dataHoraInicio,
											),
											tipoUsuario:
												this.userService.usuario()!
													.tipo,
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

	selecionarDia(dia: Date) {
		this.selectedDay = dia;
		this.paginarItens();
	}

	mudarMes(dadosSobreMes: { firstDay: Date; lastDay: Date }) {
		this.dadosSobreMesSelecionado = dadosSobreMes;
		this.carregarItens();
	}

	changeToMyCalendar(yes: boolean) {
		const changed = this.vendoMeuCalendario !== yes;
		this.vendoMeuCalendario = yes;
		if (changed) {
			this.carregarItens();
		}
	}

	get dateArrayFromItems() {
		return this.calendarItems.map((item) => {
			return item.dataHorario;
		});
	}
}
