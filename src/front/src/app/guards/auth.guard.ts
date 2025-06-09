import { inject } from "@angular/core";
import { CanMatchFn, RedirectCommand, Router } from "@angular/router";
import { UsuarioService } from "../services/usuario.service";
import { Usuario, UsuarioTipos } from "../models/usuario.model";
import { AlunoResponse, ProfessorResponse } from "../services/admin.service";

export const AlunoCanMatchFn: CanMatchFn = () => {
	const router = inject(Router);
	const usuarioService = inject(UsuarioService);
	if (usuarioService.getLoggedInUserType() === UsuarioTipos.ALUNO) {
		return true;
	}
	return new RedirectCommand(router.parseUrl("/login"));
};

export const AdminCanMatchFn: CanMatchFn = () => {
	const router = inject(Router);
	const usuarioService = inject(UsuarioService);
	if (usuarioService.getLoggedInUserType() === UsuarioTipos.ADMIN) {
		return true;
	}
	// return true; // DEBUG: NAO PRECISA LOGAR
	return new RedirectCommand(router.parseUrl("/login"));
};

export const ProfessorCanMatchFn: CanMatchFn = () => {
	const router = inject(Router);
	const usuarioService = inject(UsuarioService);
	if (usuarioService.getLoggedInUserType() === UsuarioTipos.FUNCIONARIO) {
		return true;
	}
	return new RedirectCommand(router.parseUrl("/login"));
};
