import { Component, inject, ViewChild } from "@angular/core";
import {
	CalendarioItemComponent,
	ItemDeCalendario,
} from "../../../components/calendario-item/calendario-item.component";
import { UsuarioTipos } from "../../../models/usuario.model";
import { TipoAluno } from "../../../models/aluno.model";
import { BotaoComponent } from "../../../components/botao/botao.component";
import { AdminService, AlunoResponse } from "../../../services/admin.service";
import { UsuarioService } from "../../../services/usuario.service";
import { AulaOcorrenciaFilter } from "../../../models/AulaOcorrencia.model";
import { of, switchMap } from "rxjs";
import { EnsaioFilter } from "../../../models/Ensaio.model";
import { MiniCalendarComponent } from "../../../components/mini-calendar/mini-calendar.component";
import { CommonModule, DatePipe } from "@angular/common";
import { ModalComponent } from "../../../components/modal/modal.component";
import { FormsModule, NgForm } from "@angular/forms";
import { Modalidade } from "../../../models/modalidade.model";
import { ModalidadesService } from "../../../services/modalidades.service";
import { ProfessorFiltro } from "../../../models/professor.model";
import { SearchBoxSingleComponent } from "../../../components/search-box-single/search-box-single.component";
import { AulaExtraDTO } from "../../../models/Aula.model";
import { AulaService } from "../../../services/aula.service";
import { AulaExtraFilter } from "../../../models/AulaExtra.model";
import { AlertService } from "../../../services/Alert.service";

@Component({
	selector: "app-calendar-aluno-page",
	imports: [
		ModalComponent,
		FormsModule,
		CalendarioItemComponent,
		SearchBoxSingleComponent,
		MiniCalendarComponent,
		BotaoComponent,
		DatePipe,
		CommonModule,
	],
	templateUrl: "./calendar-aluno-page.component.html",
	styleUrl: "./calendar-aluno-page.component.css",
})

export class CalendarAlunoPageComponent {
	currPag: number = 0;

	adminService = inject(AdminService);
	aulaService = inject(AulaService);
	userService = inject(UsuarioService);
	modalidadesService = inject(ModalidadesService);
	alertService = inject(AlertService);

	modalidades: Modalidade[] = [];

	calendarItems: ItemDeCalendario[] = [];
	showCalItems: Array<ItemDeCalendario[]> = [];
	selectedDay: Date = new Date();
	dadosSobreMesSelecionado!: { firstDay: Date; lastDay: Date };
	paginas = Math.ceil(this.calendarItems.length / 5);
	professoresFilterLs: any[] = [];

	vendoMeuCalendario: boolean = true;

	openModal: "solicitarAulaExtra" | null = null;

	isLoading: boolean = true;

	@ViewChild(MiniCalendarComponent) minicalendar!: MiniCalendarComponent;

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
		this.reloadModalidades();
		this.carregarItens();
	}

	private reloadModalidades() {
		this.modalidadesService.fetchModalidades().subscribe({
			next: (response) => {
				this.modalidades = response;
			},
			error: (err: any) => {},
		});
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
				this.professoresFilterLs = response.conteudo;
			},
		});
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

	private seInscreverEmAula(aula: any) {
		this.aulaService
			.seInscreverAula(aula.id, this.userService.usuario()!.id)
			.subscribe({
				next: (value: any) => {
					this.alertService.sucesso(
						"Inscrição realizada com sucesso! 😎",
					);
					this.carregarItens();
				},
				error: (err: any) => {
					this.alertService.erro(err.error.mensagem);
				},
			});
	}

	private carregarItens() {
		this.isLoading = true;
		let itensDeCalendario: ItemDeCalendario[] = [];
		this.adminService
			.fetchAulasOcorrentes(
				{
					alunos: this.vendoMeuCalendario
						? [this.userService.usuario()!.id]
						: null,
					dataInicio: this.dadosSobreMesSelecionado.firstDay,
					dataFim: this.dadosSobreMesSelecionado.lastDay,
					alunoNotIn: !this.vendoMeuCalendario
						? this.userService.usuario()!.id
						: null,
				} as AulaOcorrenciaFilter,
				!this.vendoMeuCalendario,
			)
			.pipe(
				switchMap((aulasOcorrentes: any) => {
					let arr = this.vendoMeuCalendario
						? [...aulasOcorrentes?.conteudo]
						: [...aulasOcorrentes];
					arr = arr.map((aula: any) => {
						const dataIni = new Date(
							new Date(aula.data).getFullYear(),
							new Date(aula.data).getMonth(),
							new Date(aula.data).getDate() + 1,
							aula.idAula.horarioInicio.split(":")[0],
							aula.idAula.horarioInicio.split(":")[1],
						);
						return {
							title: `Aula de ${aula.idAula.idModalidade.nome}`,
							subtitle: `Professor: ${aula.idAula.idProfessor.idUsuario.nome}`,
							dataHorario: dataIni,
							tipoUsuario: (
								this.userService.usuario() as AlunoResponse
							).tipo,
							TipoAluno: TipoAluno.FLEXIVEL,
							button:
								!this.vendoMeuCalendario &&
								(this.userService.usuario() as AlunoResponse)
									.tipo == TipoAluno.FLEXIVEL
									? {
											label: "Se Inscrever",
											cor: "green",
											click: () => {
												this.seInscreverEmAula(aula);
											},
										}
									: this.vendoMeuCalendario &&
										  (
												this.userService.usuario() as AlunoResponse
										  ).tipo == TipoAluno.FLEXIVEL &&
										  dataIni.getTime() >
												new Date().getTime()
										? {
												label: "Cancelar",
												cor: "danger",
												click: () => {
													this.aulaService
														.seDesinscreverAula(
															aula.id,
															(
																this.userService.usuario() as AlunoResponse
															).id,
														)
														.subscribe({
															next: () => {
																this.alertService.sucesso(
																	"Saiu da aula com sucesso!",
																);
																this.carregarItens();
															},
															error: (
																err: any,
															) => {
																this.alertService.erro(
																	err.error
																		.mensagem,
																);
															},
														});
												},
											}
										: undefined,
						} as ItemDeCalendario;
					});
					itensDeCalendario = [...itensDeCalendario, ...arr];
					if (!this.vendoMeuCalendario) {
						return this.adminService.filterEnsaio({
							alunos: [this.userService.usuario()!.id],
							dataInicio: this.dadosSobreMesSelecionado.firstDay,
							dataFim: this.dadosSobreMesSelecionado.lastDay,
						} as EnsaioFilter);
					}
					return this.aulaService.filterAulaExtra({
						idAluno: this.userService.usuario()!.id,
						dataInicio: this.dadosSobreMesSelecionado.firstDay
							.toISOString()
							.substring(0, 10),
						dataFim: this.dadosSobreMesSelecionado.lastDay
							.toISOString()
							.substring(0, 10),
					} as AulaExtraFilter);
				}),
				switchMap((aulasExtras: any) => {
					const statusAula = [
						"Pendente ⏲️",
						"Aceita 😼",
						"Indeferida ❌",
						"Cancelada ⛔",
					];
					const arr = aulasExtras?.conteudo.map((aula: any) => {
						return {
							title: "Aula Extra ",
							subtitle: `Professor: ${aula.idProfessor.idUsuario.nome} | Status: ${statusAula[aula.situacao - 1]}`,
							dataHorario: new Date(aula.horarioInicio),
							tipoUsuario: (
								this.userService.usuario() as AlunoResponse
							).tipo,
							TipoAluno: TipoAluno.FLEXIVEL,
						} as ItemDeCalendario;
					});
					itensDeCalendario = [...itensDeCalendario, ...arr];
					if (!this.vendoMeuCalendario) {
						return of(null);
					}
					return this.adminService.filterEnsaio({
						alunos: [this.userService.usuario()!.id],
						dataInicio: this.dadosSobreMesSelecionado.firstDay,
						dataFim: this.dadosSobreMesSelecionado.lastDay,
					} as EnsaioFilter);
				}),
			)
			.subscribe({
				next: (ensaios: any) => {
					if (ensaios) {
						const arr = ensaios?.conteudo.map((ensaio: any) => {
							return {
								title: `Ensaio de ${ensaio.idApresentacaoEvento.nome}`,
								subtitle: `Professor: ${ensaio.idProfessor.idUsuario.nome}`,
								dataHorario: new Date(ensaio.dataHoraInicio),
								tipoUsuario: (
									this.userService.usuario() as AlunoResponse
								).tipo,
								TipoAluno: TipoAluno.FLEXIVEL,
							} as ItemDeCalendario;
						});
						itensDeCalendario = [...itensDeCalendario, ...arr];
						itensDeCalendario.sort((a, b) =>
							a.dataHorario.getTime() < b.dataHorario.getTime()
								? 1
								: -1,
						);
					}
					this.calendarItems = [...itensDeCalendario];
					this.isLoading = false;
					this.paginarItens();
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

	abrirModalSolicitarAulaExtra() {
		this.openModal = "solicitarAulaExtra";
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

	solicitarAulaExtra(form: NgForm | false) {
		if (form == false) {
			this.openModal = null;
			return;
		}
		if (form.invalid) {
			return;
		}
		const horIni: Date = new Date(form.value.dataAE);
		const horFim: Date = new Date(form.value.dataAE);
		horIni.setUTCHours(
			form.value.horarioInicioAE.split(":")[0] as number,
			form.value.horarioInicioAE.split(":")[1] as number,
		);
		horFim.setUTCHours(
			form.value.horarioFimAE.split(":")[0] as number,
			form.value.horarioFimAE.split(":")[1] as number,
		);

		const valor = {
			horarioInicio: horIni,
			horarioFim: horFim,
			idProfessor: form.value.professorAE,
			idAluno: (this.userService.usuario() as AlunoResponse).id,
		} as AulaExtraDTO;
		this.aulaService.solicitarAulaExtra(valor).subscribe({
			next: () => {
				this.alertService.sucesso(
					"Solicitação realizada com sucesso! 😎",
				);
				this.carregarItens();
			},
			error: (error: any) => {
				this.alertService.erro(error.error.mensagem);
			},
		});
		this.openModal = null;
	}

	get dateArrayFromItems() {
		return this.calendarItems.map((item) => {
			return item.dataHorario;
		});
	}
}

