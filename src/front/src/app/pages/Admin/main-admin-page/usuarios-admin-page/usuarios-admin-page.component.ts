import { Component, inject, OnInit, ViewChild } from "@angular/core";
import { FormsModule, NgForm } from "@angular/forms";
import { BotaoComponent } from "../../../../components/botao/botao.component";
import { ModalComponent } from "../../../../components/modal/modal.component";
import { Aluno } from "../../../../models/aluno.model";
import {
	Modalidade,
	ModalidadeAlunoNivel,
} from "../../../../models/modalidade.model";
import { Professor, ProfessorFiltro } from "../../../../models/professor.model";
import { UsuarioFiltro, UsuarioTipos } from "../../../../models/usuario.model";
import { StatusUsuarioPipe } from "../../../../pipes/status-usuario.pipe";
import { TipoAlunoPipe } from "../../../../pipes/tipo-aluno.pipe";
import {
	AdminService,
	AlunoResponse,
	ProfessorResponse,
} from "../../../../services/admin.service";
import { ModalidadesService } from "../../../../services/modalidades.service";
import { NgxMaskPipe, NgxMaskDirective } from "ngx-mask";
import { RouterOutlet } from "@angular/router";
import {
	InternalSidebarComponent,
	InternalSidebarRoute,
} from "../../../../components/internal-sidebar/internal-sidebar.component";

enum ToggleUserStatusMessages {
	ACTIVATE = "Tem certeza que deseja habilitar a conta?",
	DEACTIVATE = "Tem certeza que deseja desabilitar a conta?",
}

@Component({
	selector: "app-usuarios-admin-page",
	standalone: true,
	imports: [
		BotaoComponent,
		ModalComponent,
		RouterOutlet,
		FormsModule,
		InternalSidebarComponent,
		TipoAlunoPipe,
		NgxMaskDirective,
		NgxMaskPipe,
		StatusUsuarioPipe,
	],
	templateUrl: "./usuarios-admin-page.component.html",
	styleUrl: "./usuarios-admin-page.component.css",
})
export class UsuariosAdminPageComponent {
	sidebarRoutes: InternalSidebarRoute[] = [
		{
			label: "Alunos",
			route: "alunos",
		},
		{
			label: "Professores",
			route: "professores",
		},
	];
}
