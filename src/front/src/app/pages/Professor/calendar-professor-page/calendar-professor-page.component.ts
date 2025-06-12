import { Component, inject, ViewChild } from "@angular/core";
import {
	CalendarioItemComponent,
	ItemDeCalendario,
} from "../../../components/calendario-item/calendario-item.component";
import { UsuarioTipos } from "../../../models/usuario.model";
import { TipoAluno } from "../../../models/aluno.model";
import { BotaoComponent } from "../../../components/botao/botao.component";
import {
	AdminService,
	AlunoResponse,
	ProfessorResponse,
} from "../../../services/admin.service";
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
	templateUrl: "./calendar-professor-page.component.html",
	styleUrl: "./calendar-professor-page.component.css",
})
export class CalendarProfessorPageComponent {
	currPag: number = 0;

	adminService = inject(AdminService);
	aulaService = inject(AulaService);
	userService = inject(UsuarioService);
	modalidadesService = inject(ModalidadesService);
	alertService = inject(AlertService);

	calendarItems: ItemDeCalendario[] = [];
	showCalItems: Array<ItemDeCalendario[]> = [];
	selectedDay: Date = new Date();
	dadosSobreMesSelecionado!: { firstDay: Date; lastDay: Date };
	paginas = Math.ceil(this.calendarItems.length / 5);

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
		this.isLoading = true;
		let itensDeCalendario: ItemDeCalendario[] = [];
		this.adminService
			.fetchAulasOcorrentes({
				professores: [
					(this.userService.usuario() as ProfessorResponse)!.id,
				],
				dataInicio: this.dadosSobreMesSelecionado.firstDay,
				dataFim: this.dadosSobreMesSelecionado.lastDay,
			} as AulaOcorrenciaFilter)
			.pipe(
				switchMap((aulasOcorrentes: any) => {
					let arr = [...aulasOcorrentes?.conteudo];
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
							subtitle: `Sala: ${aula.idAula.idSala.nome}`,
							dataHorario: dataIni,
							tipoUsuario: (
								this.userService.usuario() as ProfessorResponse
							).idUsuario.tipo,
							button:
								dataIni.getTime() < new Date().getTime()
									? {
											label: "Lista de Presença",
											cor: "dark",
											click: () => {
												// TODO: COLOCAR LÓGICA DA CHAMADA
												console.log(
													"Fazendo chamada!!",
												);
											},
										}
									: undefined,
						} as ItemDeCalendario;
					});
					itensDeCalendario = [...itensDeCalendario, ...arr];
					return this.aulaService.filterAulaExtra({
						idProfessor: this.userService.usuario()!.id,
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
						const salaTexto = aula.idAula?.idSala
							? aula.idAula.idSala.nome
							: "Indefinida";
						return {
							title: "Aula Extra ",
							subtitle: `Sala: ${salaTexto} | Status: ${statusAula[aula.situacao - 1]}`,
							dataHorario: new Date(aula.horarioInicio),
							tipoUsuario: (
								this.userService.usuario() as ProfessorResponse
							).idUsuario.tipo,
						} as ItemDeCalendario;
					});
					itensDeCalendario = [...itensDeCalendario, ...arr];
					return this.adminService.filterEnsaio({
						idProfessor: this.userService.usuario()!.id,
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
								subtitle: `Evento: ${ensaio.idApresentacaoEvento.eventoNome}`,
								dataHorario: new Date(ensaio.dataHoraInicio),
								tipoUsuario: (
									this.userService.usuario() as ProfessorResponse
								).idUsuario.tipo,
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

	mudarMes(dadosSobreMes: { firstDay: Date; lastDay: Date }) {
		this.dadosSobreMesSelecionado = dadosSobreMes;
		this.carregarItens();
	}

	get dateArrayFromItems() {
		return this.calendarItems.map((item) => {
			return item.dataHorario;
		});
	}
}
