import { Routes } from '@angular/router';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { DashboardAlunoPageComponent } from './pages/Aluno/dashboard-aluno-page/dashboard-aluno-page.component';
import { DashboardAdminPageComponent } from './pages/Admin/dashboard-admin-page/dashboard-admin-page.component';
import { AdminCanMatchFn, AlunoCanMatchFn } from './guards/auth.guard';
import { MainAdminPageComponent } from './pages/Admin/main-admin-page/main-admin-page.component';
import { CalendarAdminPageComponent } from './pages/Admin/calendar-admin-page/calendar-admin-page.component';

export const routes: Routes = [
	{ path: "", redirectTo: "login", pathMatch: "full" },
	{ path: "login", component: LoginPageComponent },
	{
		path: "aluno", canMatch: [AlunoCanMatchFn], children: [
			{ path: "", redirectTo: "dashboard", pathMatch: "full" },
			{ path: "dashboard", component: DashboardAlunoPageComponent }
		]
	},
	{
		path: "admin", canMatch: [AdminCanMatchFn], children: [
			{ path: "", redirectTo: "dashboard/main", pathMatch: "full" },
			{
				path: "dashboard", component: DashboardAdminPageComponent, children: [
					{ path: "main", component: MainAdminPageComponent },
					{ path: "calendar", component: CalendarAdminPageComponent },
				]
			}
		]
	},
	{
		path: "professor", canMatch: [AdminCanMatchFn], children: [
			{ path: "", redirectTo: "dashboard", pathMatch: "full" },
			{ path: "dashboard", component: DashboardAdminPageComponent }
		]
	}
];
