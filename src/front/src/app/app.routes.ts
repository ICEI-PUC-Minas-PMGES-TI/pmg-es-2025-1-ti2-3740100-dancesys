import { Routes } from "@angular/router";
import { LoginPageComponent } from "./pages/login-page/login-page.component";
import { DashboardAlunoPageComponent } from "./pages/Aluno/dashboard-aluno-page/dashboard-aluno-page.component";
import { DashboardAdminPageComponent } from "./pages/Admin/dashboard-admin-page/dashboard-admin-page.component";
import {
	AdminCanMatchFn,
	AlunoCanMatchFn,
	ProfessorCanMatchFn,
} from "./guards/auth.guard";
import { MainAdminPageComponent } from "./pages/Admin/main-admin-page/main-admin-page.component";
import { CalendarAdminPageComponent } from "./pages/Admin/calendar-admin-page/calendar-admin-page.component";
import { UsuariosAdminPageComponent } from "./pages/Admin/main-admin-page/usuarios-admin-page/usuarios-admin-page.component";
import { HorariosAdminPageComponent } from "./pages/Admin/main-admin-page/horarios-admin-page/horarios-admin-page.component";
import { MainAlunoPageComponent } from "./pages/Aluno/main-aluno-page/main-aluno-page.component";
import { CalendarAlunoPageComponent } from "./pages/Aluno/calendar-aluno-page/calendar-aluno-page.component";
import { FinanceiroAlunoPageComponent } from "./pages/Aluno/financeiro-aluno-page/financeiro-aluno-page.component";
import { EventosAlunoPageComponent } from "./pages/Aluno/eventos-aluno-page/eventos-aluno-page.component";
import { FincanceiroAdminPageComponent } from "./pages/Admin/main-admin-page/fincanceiro-admin-page/fincanceiro-admin-page.component";
import { AulasAdminPageComponent } from "./pages/Admin/main-admin-page/aulas-admin-page/aulas-admin-page.component";
import { AulasExtrasAdminPageComponent } from "./pages/Admin/main-admin-page/aulas-admin-page/aulas-extras-admin-page/aulas-extras-admin-page.component";
import { AulasFixasAdminPageComponent } from "./pages/Admin/main-admin-page/aulas-admin-page/aulas-fixas-admin-page/aulas-fixas-admin-page.component";
import { AulasRecorrentesAdminPageComponent } from "./pages/Admin/main-admin-page/aulas-admin-page/aulas-recorrentes-admin-page/aulas-recorrentes-admin-page.component";
import { EventosMainAdminPageComponent } from "./pages/Admin/main-admin-page/eventos-main-admin-page/eventos-main-admin-page.component";
import { EventosAdminPageComponent } from "./pages/Admin/main-admin-page/eventos-main-admin-page/eventos-admin-page/eventos-admin-page.component";
import { FigurinosAdminPageComponent } from "./pages/Admin/main-admin-page/eventos-main-admin-page/figurinos-admin-page/figurinos-admin-page.component";
import { FigurinosPorAlunoAdminPageComponent } from "./pages/Admin/main-admin-page/eventos-main-admin-page/figurinos-por-aluno-admin-page/figurinos-por-aluno-admin-page.component";
import { ApresentacoesAdminPageComponent } from "./pages/Admin/main-admin-page/eventos-main-admin-page/apresentacoes-admin-page/apresentacoes-admin-page.component";
import { EnsaiosAdminPageComponent } from "./pages/Admin/main-admin-page/eventos-main-admin-page/ensaios-admin-page/ensaios-admin-page.component";
import { OutrosAdminPageComponent } from "./pages/Admin/main-admin-page/outros-admin-page/outros-admin-page.component";
import { ModalidadeAdminPageComponent } from "./pages/Admin/main-admin-page/outros-admin-page/modalidade-admin-page/modalidade-admin-page.component";
import { SalaAdminPageComponent } from "./pages/Admin/main-admin-page/outros-admin-page/sala-admin-page/sala-admin-page.component";
import { IndicadoresAdminPageComponent } from "./pages/Admin/indicadores-admin-page/indicadores-admin-page.component";
import { IndicadorFinanceiroAdminPageComponent } from "./pages/Admin/indicadores-admin-page/indicador-financeiro-admin-page/indicador-financeiro-admin-page.component";
import { UsuarioPageComponent } from "./pages/usuario-page/usuario-page.component";
import { AlunoTabelaAdminPageComponent } from "./pages/Admin/main-admin-page/usuarios-admin-page/aluno-tabela-admin-page/aluno-tabela-admin-page.component";
import { ProfessorTabelaAdminPageComponent } from "./pages/Admin/main-admin-page/usuarios-admin-page/professor-tabela-admin-page/professor-tabela-admin-page.component";
import { AulasExperimentaisAdminPageComponent } from "./pages/Admin/main-admin-page/aulas-admin-page/aulas-experimentais-admin-page/aulas-experimentais-admin-page.component";
import { IndicadorConversaoAdminPageComponent } from "./pages/Admin/indicadores-admin-page/indicador-conversao-admin-page/indicador-conversao-admin-page.component";
import { IndicadorAulasAdminPageComponent } from "./pages/Admin/indicadores-admin-page/indicador-aulas-admin-page/indicador-aulas-admin-page.component";
import { CalendarProfessorPageComponent } from "./pages/Professor/calendar-professor-page/calendar-professor-page.component";
import { IndicardorModalidadeAdminPageComponent } from "./pages/Admin/indicadores-admin-page/indicardor-modalidade-admin-page/indicardor-modalidade-admin-page.component";

export const routes: Routes = [
	{ path: "", redirectTo: "login", pathMatch: "full" },
	{ path: "login", component: LoginPageComponent },
	{
		path: "aluno",
		canMatch: [AlunoCanMatchFn],
		children: [
			{ path: "", redirectTo: "dashboard/main", pathMatch: "full" },
			{
				path: "dashboard",
				component: DashboardAlunoPageComponent,
				children: [
					{
						path: "main",
						component: MainAlunoPageComponent,
					},
					{
						path: "calendar",
						component: CalendarAlunoPageComponent,
					},
					{
						path: "financeiro",
						component: FinanceiroAlunoPageComponent,
					},
					{
						path: "eventos",
						component: EventosAlunoPageComponent,
					},
					{
						path: "profile",
						component: UsuarioPageComponent,
					},
				],
			},
		],
	},
	{
		path: "admin",
		canMatch: [AdminCanMatchFn],
		children: [
			{
				path: "",
				redirectTo: "dashboard/main/usuarios",
				pathMatch: "full",
			},
			{
				path: "dashboard",
				component: DashboardAdminPageComponent,
				children: [
					{
						path: "main",
						component: MainAdminPageComponent,
						children: [
							{
								path: "",
								redirectTo: "usuarios",
								pathMatch: "full",
							},
							{
								path: "usuarios",
								component: UsuariosAdminPageComponent,
								children: [
									{
										path: "",
										redirectTo: "alunos",
										pathMatch: "full",
									},
									{
										path: "alunos",
										component:
											AlunoTabelaAdminPageComponent,
									},
									{
										path: "professores",
										component:
											ProfessorTabelaAdminPageComponent,
									},
								],
							},
							{
								path: "aulas",
								component: AulasAdminPageComponent,
								children: [
									{
										path: "",
										redirectTo: "fixas",
										pathMatch: "full",
									},
									{
										path: "fixas",
										component: AulasFixasAdminPageComponent,
									},
									{
										path: "recorrentes",
										component:
											AulasRecorrentesAdminPageComponent,
									},
									{
										path: "extras",
										component:
											AulasExtrasAdminPageComponent,
									},
									{
										path: "experimentais",
										component:
											AulasExperimentaisAdminPageComponent,
									},
								],
							},
							{
								path: "horarios",
								component: HorariosAdminPageComponent,
							},
							{
								path: "financeiro",
								component: FincanceiroAdminPageComponent,
							},
							{
								path: "eventos",
								component: EventosMainAdminPageComponent,
								children: [
									{
										path: "",
										redirectTo: "eventos",
										pathMatch: "full",
									},
									{
										path: "eventos",
										component: EventosAdminPageComponent,
									},
									{
										path: "figurinos",
										component: FigurinosAdminPageComponent,
									},
									{
										path: "figurinosPorAluno",
										component:
											FigurinosPorAlunoAdminPageComponent,
									},
									{
										path: "apresentacoes",
										component:
											ApresentacoesAdminPageComponent,
									},
									{
										path: "ensaios",
										component: EnsaiosAdminPageComponent,
									},
								],
							},
							{
								path: "outros",
								component: OutrosAdminPageComponent,
								children: [
									{
										path: "",
										redirectTo: "modalidade",
										pathMatch: "full",
									},
									{
										path: "modalidade",
										component: ModalidadeAdminPageComponent,
									},
									{
										path: "sala",
										component: SalaAdminPageComponent,
									},
								],
							},
						],
					},
					{ path: "calendar", component: CalendarAdminPageComponent },
					{
						path: "indicadores",
						component: IndicadoresAdminPageComponent,
						children: [
							{
								path: "",
								redirectTo: "financeiro",
								pathMatch: "full",
							},
							{
								path: "financeiro",
								component:
									IndicadorFinanceiroAdminPageComponent,
							},
							{
								path: "conversao",
								component: IndicadorConversaoAdminPageComponent,
							},
							{
								path: "aulas",
								component: IndicadorAulasAdminPageComponent,
							},
							{
								path: "modalidade",
								component: IndicardorModalidadeAdminPageComponent,
							},
						],
					},
					{
						path: "profile",
						component: UsuarioPageComponent,
					},
				],
			},
		],
	},
	{
		path: "professor",
		canMatch: [ProfessorCanMatchFn],
		children: [
			{ path: "", redirectTo: "dashboard", pathMatch: "full" },
			{
				path: "dashboard",
				component: DashboardAdminPageComponent,
				children: [
					{
						path: "profile",
						component: UsuarioPageComponent,
					},
					{
						path: "calendar",
						component: CalendarProfessorPageComponent,
					},
				],
			},
		],
	},
];
