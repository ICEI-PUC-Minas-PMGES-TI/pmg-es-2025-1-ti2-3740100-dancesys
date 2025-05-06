import { Routes } from "@angular/router";
import { LoginPageComponent } from "./pages/login-page/login-page.component";
import { DashboardAlunoPageComponent } from "./pages/Aluno/dashboard-aluno-page/dashboard-aluno-page.component";
import { DashboardAdminPageComponent } from "./pages/Admin/dashboard-admin-page/dashboard-admin-page.component";
import { AdminCanMatchFn, AlunoCanMatchFn } from "./guards/auth.guard";
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
				redirectTo: "dashboard/main/aulas",
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
								path: "usuarios",
								component: UsuariosAdminPageComponent,
							},
							{
								path: "aulas",
								component: AulasAdminPageComponent,
								children: [
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
						],
					},
					{ path: "calendar", component: CalendarAdminPageComponent },
				],
			},
		],
	},
	{
		path: "professor",
		canMatch: [AdminCanMatchFn],
		children: [
			{ path: "", redirectTo: "dashboard", pathMatch: "full" },
			{ path: "dashboard", component: DashboardAdminPageComponent },
		],
	},
];
