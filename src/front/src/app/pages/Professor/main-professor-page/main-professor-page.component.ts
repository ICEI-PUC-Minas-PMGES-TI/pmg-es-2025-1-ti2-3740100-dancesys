import { Component, inject } from "@angular/core";
import { BotaoComponent } from "../../../components/botao/botao.component";
import { SimpleCardComponent } from "../../../components/simple-card/simple-card.component";
import { UsuarioService } from "../../../services/usuario.service";
import { IconComponent } from "../../../components/icon/icon.component";
import { AulaService } from "../../../services/aula.service";
import {
	AdminService,
	AlunoResponse,
	ProfessorResponse,
} from "../../../services/admin.service";
import {
	CalendarioItemComponent,
	ItemDeCalendario,
} from "../../../components/calendario-item/calendario-item.component";
import { Evento } from "../../../models/evento.model";
import { AulaOcorrenciaFilter } from "../../../models/AulaOcorrencia.model";
import { UsuarioTipos } from "../../../models/usuario.model";
import { switchMap } from "rxjs";
import { AulaExtraFilter } from "../../../models/AulaExtra.model";
import { AulaExperimentalFilter } from "../../../models/AulaExperimental.model";

@Component({
	selector: "app-main-professor-page",
	standalone: true,
	imports: [
		BotaoComponent,
		CalendarioItemComponent,
		SimpleCardComponent,
		IconComponent,
	],
	templateUrl: "./main-professor-page.component.html",
	styleUrl: "./main-professor-page.component.css",
})
export class MainProfessorPageComponent {
	aulaService = inject(AulaService);
	adminService = inject(AdminService);
	usuarioService = inject(UsuarioService);

	aulasDoDia: ItemDeCalendario[] = [];
	proximosEventos: Evento[] = [];

	aulaNaTelaIndex: number = 0;

	ngOnInit(): void {
		let aulaArr: any[] = [];
		this.adminService
			.fetchAulasOcorrentes({
				professores: [
					(this.usuarioService.usuario() as ProfessorResponse).id,
				],
				dataInicio: new Date(),
				dataFim: new Date(),
				orderBy: "idAula.horarioInicio",
				order: "desc",
			} as AulaOcorrenciaFilter)
			.pipe(
				switchMap((val: any) => {
					aulaArr = val.conteudo.map((val: any) => {
						const dataIni = new Date(
							new Date(val.data).getFullYear(),
							new Date(val.data).getMonth(),
							new Date(val.data).getDate() + 1,
							val.idAula.horarioInicio.split(":")[0],
							val.idAula.horarioInicio.split(":")[1],
						);
						const dataFim = new Date(
							new Date(val.data).getFullYear(),
							new Date(val.data).getMonth(),
							new Date(val.data).getDate() + 1,
							val.idAula.horarioFim.split(":")[0],
							val.idAula.horarioFim.split(":")[1],
						);
						return {
							title: `Aula de ${val.idAula.idModalidade.nome}`,
							subtitle: `Professor: ${val.idAula.idProfessor.idUsuario.nome}`,
							tipoUsuario: UsuarioTipos.FUNCIONARIO,
							dataHorario: dataIni,
							dataHorarioFim: dataFim,
							isHomeProp: true,
						};
					});
					return this.aulaService.filterAulaExperimental({
						idProfessor: (
							this.usuarioService.usuario() as ProfessorResponse
						).id,
						dataInicial: new Date(),
						dataFinal: new Date(),
					} as AulaExperimentalFilter);
				}),
				switchMap((val: any) => {
					aulaArr = [
						...val.conteudo.map((aula: any) => {
							const dataIni = new Date(aula.dataHorarioInicio);
							return {
								title: `Aula Experimental`,
								subtitle: `Aluno: ${aula.nome}`,
								dataHorario: dataIni,
								tipoUsuario: (
									this.usuarioService.usuario() as ProfessorResponse
								).idUsuario.tipo,
							} as ItemDeCalendario;
						}),
					];
					return this.aulaService.filterAulaExtra({
						idProfessor: this.usuarioService.usuario()!.id,
						dataInicio: new Date(),
						dataFim: new Date(),
						status: [2],
					} as AulaExtraFilter);
				}),
			)
			.subscribe({
				next: (val: any) => {
					aulaArr = [
						...val.conteudo.map((value: any) => {
							return {
								title: "Aula Extra ",
								subtitle: `Professor: ${value.idProfessor.idUsuario.nome}`,
								dataHorario: new Date(value.horarioInicio),
								dataHorarioFim: new Date(value.horarioFim),
								tipoUsuario: UsuarioTipos.ALUNO,
								isHomeProp: true,
							} as ItemDeCalendario;
						}),
					];
					this.aulasDoDia = [...aulaArr];
				},
				error: (err) => {
				},
			});

		this.adminService
			.fetchEventos({
				nome: "",
				local: "",
				data: null,
				dataInicio: new Date(),
				alunos: null,
				pagina: 0,
				tamanho: 4,
				orderBy: "dataHoraInicio",
				order: "asc",
			})
			.subscribe({
				next: (val) => {
					this.proximosEventos = [...val.conteudo];
				},
				error: (err) => {
				},
			});
	}

	proximaAula() {
		if (this.aulaNaTelaIndex === this.aulasDoDia.length - 1) {
			this.aulaNaTelaIndex = 0;
			return;
		}
		this.aulaNaTelaIndex++;
	}

	aulaAnterior() {
		if (this.aulaNaTelaIndex === 0) {
			this.aulaNaTelaIndex = this.aulasDoDia.length - 1;
			return;
		}
		this.aulaNaTelaIndex--;
	}

	getDataTexto(evento: Evento): string {
		const data = new Date(evento.dataHoraInicio);
		const dia = data.getDate();
		const mes = data.getMonth();
		return `${dia.toString().padStart(2, "0")}/${mes.toString().padStart(2, "0")}`;
	}

	getHorarioTexto(evento: Evento): string {
		const dataIni = new Date(evento.dataHoraInicio);
		const dataFim = new Date(evento.dataHoraFim);
		const horarioInicio = `${dataIni.getHours().toString().padStart(2, "0")}:${dataIni.getMinutes().toString().padStart(2, "0")}`;
		const horarioFinal = `${dataFim.getHours().toString().padStart(2, "0")}:${dataFim.getMinutes().toString().padStart(2, "0")}`;
		return `${horarioInicio} às ${horarioFinal}`;
	}
}
