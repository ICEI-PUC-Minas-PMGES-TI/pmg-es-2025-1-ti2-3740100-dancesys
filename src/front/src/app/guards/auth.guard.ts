import { inject } from "@angular/core";
import { CanMatchFn, RedirectCommand, Router } from "@angular/router";
import { UsuarioService } from "../services/usuario.service";
import { UsuarioTipos } from "../models/usuario.model";

export const AlunoCanMatchFn: CanMatchFn = () => {
	const router = inject(Router);
	const usuarioService = inject(UsuarioService);
	const usuario = usuarioService.usuario;
	if (usuario().tipo == UsuarioTipos.ALUNO) {
		return true;
	}
	return new RedirectCommand(router.parseUrl("/login"));
};

export const AdminCanMatchFn: CanMatchFn = () => {
	const router = inject(Router);
	const usuarioService = inject(UsuarioService);
	const usuario = usuarioService.usuario;
	if (usuario().tipo == UsuarioTipos.ADMIN) {
		return true;
	}
	// return true; // DEBUG: NAO PRECISA LOGAR
	return new RedirectCommand(router.parseUrl("/login"));
};

export const ProfessorCanMatchFn: CanMatchFn = () => {
	const router = inject(Router);
	const usuarioService = inject(UsuarioService);
	const usuario = usuarioService.usuario;
	if (usuario().tipo == UsuarioTipos.FUNCIONARIO) {
		return true;
	}
	return new RedirectCommand(router.parseUrl("/login"));
};
