import { Routes } from '@angular/router';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { DashboardAlunoPageComponent } from './pages/Aluno/dashboard-aluno-page/dashboard-aluno-page.component';
import { DashboardAdminPageComponent } from './pages/Admin/dashboard-admin-page/dashboard-admin-page.component';
import { AdminCanMatchFn, AlunoCanMatchFn } from './guards/auth.guard';

export const routes: Routes = [
	{ path: "", redirectTo: "login", pathMatch: "full" },
	{ path: "login", component: LoginPageComponent },
	{
		path: "aluno", canMatch: [AlunoCanMatchFn], children: [
			{ path: "", redirectTo: "home", pathMatch: "full" },
			{ path: "home", component: DashboardAlunoPageComponent }
		]
	},
	{
		path: "admin", canMatch: [AdminCanMatchFn], children: [
			{ path: "", redirectTo: "home", pathMatch: "full" },
			{ path: "home", component: DashboardAdminPageComponent }
		]
	},
	{
		path: "professor", canMatch: [AdminCanMatchFn], children: [
			{ path: "", redirectTo: "home", pathMatch: "full" },
			{ path: "home", component: DashboardAdminPageComponent }
		]
	}
];
