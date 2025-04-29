import { Component, NgModule, inject } from "@angular/core";
import { BotaoComponent } from "../../../../components/botao/botao.component";
import { SimpleTableComponent } from "../../../../components/simple-table/simple-table.component"
import { MultiSelectInputComponent } from "../../../../components/multi-select-input/multi-select-input.component"
import { CommonModule } from '@angular/common';
import { FormsModule, FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms'; 
import { AdminService, ProfessorResponse } from "../../../../services/admin.service";

@Component({
	selector: "app-horarios-admin-page",
	standalone: true,
	imports: [
		BotaoComponent, 
		SimpleTableComponent, 
		MultiSelectInputComponent,
		CommonModule,
		FormsModule,
		ReactiveFormsModule
	],
	templateUrl: "./horarios-admin-page.component.html",
	styleUrl: "./horarios-admin-page.component.css",
})
export class HorariosAdminPageComponent {
	adminService = inject(AdminService);

	filterForm: FormGroup;
	professoresFilter!: number[];
	diasSemanaFilter!: number[];
	professoresObj: any = [];
	diasObj = [
		{dia: 1, nome: "Segunda"},
		{dia: 2, nome: "Terça"},
		{dia: 3, nome: "Quarta"},
		{dia: 4, nome: "Quinta"},
		{dia: 5, nome: "Sexta"},
		{dia: 6, nome: "Sabado"},
		{dia: 7, nome: "Domingo"},
	] 

	constructor(private fb: FormBuilder) {
		this.filterForm = this.fb.group({
		  professores: [[]],
		  diasSemana: [[]]
		});
	  }

	  diaSemanaMap: Record<number, string> = {
		1: 'Segunda',
		2: 'Terça',
		3: 'Quarta',
		4: 'Quinta',
		5: 'Sexta',
		6: 'Sábado',
		7: 'Domingo'
	  };
	  

	colunas = [
		{ chave: 'idProfessor.idUsuario.nome', titulo: 'Professor' },
		{ chave: 'horarioEntrada', titulo: 'Entrada' },
		{ chave: 'horarioSaida', titulo: 'Saida' },
		{ chave: 'diaSemana', titulo: 'Dia', width: '10%', formatar: (valor: number) => this.diaSemanaMap[valor] ?? String(valor) }
	  ];
	  
	  professores: any = [];
	  
	  acoes = [
		{
		  icon: 'edit',
		  title: 'Editar',
		  cor: 'blue',
		  callback: (item: any) => this.editar(item)
		},
		{
		  icon: 'delete',
		  title: 'Excluir',
		  cor: 'danger',
		  callback: (item: any) => this.excluir(item)
		}
	  ];

	ngOnInit(): void{
		this.carregarProfessores();
		this.buscar();
	}

	carregarProfessores(){
		this.adminService.fetchProfessores().subscribe({
			next: (response) => {
				this.professoresObj = response;
				console.log(this.professoresObj);
			},
			error: (err) => {
				console.log(err, { color: "red" });
			},
		});
	}

	onDiaChange(selected: number[]) {
		this.diasSemanaFilter = selected;
	}

	onProfessorChange(selected: number[]){
		this.professoresFilter = selected;
	}

	filterGet(){
		this.filterForm = this.fb.group({
			professores: [this.professoresFilter],
			diasSemana: [this.diasSemanaFilter]
		  });
		return this.filterForm.value;
	}

	buscar(){
		this.adminService.fetchHoraioProfessor(this.filterGet()).subscribe({
			next: (response) => {
				this.professores = response
			},
			error: (err) => {
				console.log(err, { color: "red" });
			},
		});
	}

	editar(item: any) {
		console.log('Editando:', item);
	}
	  
	excluir(item: any) {
		console.log('Excluindo:', item);
	}
}
