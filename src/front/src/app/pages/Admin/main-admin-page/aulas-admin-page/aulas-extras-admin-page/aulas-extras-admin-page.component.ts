import { Component, inject } from "@angular/core";
import { ModalComponent } from "../../../../../components/modal/modal.component";
import { SimpleTableComponent } from "../../../../../components/simple-table/simple-table.component";
import { MultiSelectInputComponent } from "../../../../../components/multi-select-input/multi-select-input.component";
import { FormBuilder, FormGroup, ReactiveFormsModule } from "@angular/forms";
import { BotaoComponent } from "../../../../../components/botao/botao.component";
import { CommonModule } from "@angular/common";
import { SearchBoxSingleComponent } from "../../../../../components/search-box-single/search-box-single.component";
import { AdminService } from "../../../../../services/admin.service";
import { ProfessorFiltro } from "../../../../../models/professor.model";
import { UsuarioFiltro } from "../../../../../models/usuario.model";

@Component({
	selector: "app-aulas-extras-admin-page",
	imports: [
		ModalComponent,
		SimpleTableComponent,
		MultiSelectInputComponent,
		SearchBoxSingleComponent,
		ReactiveFormsModule,
		BotaoComponent,
		CommonModule,
	],
	templateUrl: "./aulas-extras-admin-page.component.html",
	styleUrl: "./aulas-extras-admin-page.component.css",
	standalone: true,
})
export class AulasExtrasAdminPageComponent {
	adminService = inject(AdminService);

	filterForm: FormGroup;

	professoresFilterLs: any[] = [];
	alunosFilterLs: any[] = [];

	statusObj: { value: number; name: string }[] = [
		{ value: 1, name: "Pendente" },
		{ value: 2, name: "Indeferido" },
		{ value: 3, name: "Aceito" },
		{ value: 4, name: "Cancelado" },
	];

	constructor(private fb: FormBuilder) {
		this.filterForm = this.fb.group({
			aluno: [],
			professor: [],
			status: [[]],
		});
	}

	getFilter() {
		const item = this.filterForm.value;
		//const aulaItem: Aula = item;

		return item;
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
				this.professoresFilterLs = response;
			},
		});
	}

	buscarAluno(termo: any) {
		const filtro: UsuarioFiltro = {
			nome: termo,
			email: "",
			cpf: "",
			tipo: 2,
			status: 1,
		};
		this.adminService.filterUsuarios(filtro).subscribe({
			next: (response) => {
				this.alunosFilterLs = response;
			},
		});
	}

	onFilter() {}

	onStatusChange(selected: number[]) {}
}
