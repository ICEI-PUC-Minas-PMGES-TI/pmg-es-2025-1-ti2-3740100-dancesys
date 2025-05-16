import { Component, NgModule, inject } from "@angular/core";
import { BotaoComponent } from "../../../../components/botao/botao.component";
import { SimpleTableComponent } from "../../../../components/simple-table/simple-table.component"
import { MultiSelectInputComponent } from "../../../../components/multi-select-input/multi-select-input.component"
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms'; 
import { AdminService } from "../../../../services/admin.service";
import { HorarioProfessor } from "../../../../models/horarioProfessor.model";
import { ModalComponent } from "../../../../components/modal/modal.component";
import { AlertService } from "../../../../services/Alert.service";

enum ToggleModal {
	NEW = "Criar Horario",
	EDIT = "Editar Horario",
}

@Component({
	selector: "app-horarios-admin-page",
	standalone: true,
	imports: [
		BotaoComponent, 
		SimpleTableComponent, 
		MultiSelectInputComponent,
		CommonModule,
		ReactiveFormsModule,
		ModalComponent
	],
	templateUrl: "./horarios-admin-page.component.html",
	styleUrl: "./horarios-admin-page.component.css",
})

export class HorariosAdminPageComponent {
	adminService = inject(AdminService);
	alertService = inject(AlertService);
	
	filterForm: FormGroup;
	horarioForm: FormGroup;
	ToggleModal = ToggleModal;
	professoresFilter!: number[];
	diasSemanaFilter!: number[];
	professoresObj: any = [];
	isModalOpen: boolean = false;
	isModalConfirm: boolean = false;
	idDelete!: number
	isEdit = false;
	paginaAtual: number = 0;
	itensPage: number = 10;
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
		  diasSemana: [[]],
		  pagina: [this.paginaAtual],
		  tamanho: [this.itensPage]
		});

		this.horarioForm = this.fb.group({
			id: [],
			diaSemana: [],
			horarioEntrada: [],
			horarioSaida: [],
			idProfessor: []
		})
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
		  cor: 'dark',
		  callback: (item: any) => this.editar(item)
		},
		{
		  icon: 'delete',
		  title: 'Excluir',
		  cor: 'dark',
		  callback: (item: any) => this.excluir(item)
		}
	  ];

	ngOnInit(): void{
		this.carregarProfessores();
		this.buscar();
	}

	resetHorarioForm(){
		this.horarioForm = this.fb.group({
			id: [],
			diaSemana: [],
			horarioEntrada: [],
			horarioSaida: [],
			idProfessor: []
		})
	}

	preencherHorarioForm(item: HorarioProfessor){
		this.horarioForm = this.fb.group({
			id: [item.id],
			diaSemana: [item.diaSemana],
			horarioEntrada: [item.horarioEntrada],
			horarioSaida: [item.horarioSaida],
			idProfessor: [item.idProfessor.id]
		})
	}

	getHorarioFormvalue(){
		const item = this.horarioForm.value;
		const horarioItem: HorarioProfessor = item;
		return horarioItem;
	}

	isFormValido(): boolean {
		return this.horarioForm.valid;
	}

	carregarProfessores(){
		this.adminService.fetchProfessores().subscribe({
			next: (response) => {
				this.professoresObj = response;
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
			diasSemana: [this.diasSemanaFilter],
			pagina: [this.paginaAtual],
		  	tamanho: [this.itensPage]
		  });
		return this.filterForm.value;
	}

	buscar(){
		this.adminService.fetchHoraioProfessor(this.filterGet()).subscribe({
			next: (response: any) => {
				if(response.total == 0){
					this.alertService.info("Nenhum registro encontrado!")
				}else{
					this.professores = response
				}
			},
			error: (err) => {
				console.log(err, { color: "red" });
			},
		});
	}

	closeModal(){
		this.isModalOpen = false;	
	}

	openAddModal(){
		this.isModalOpen = true;
		this.isEdit = false;
		this.resetHorarioForm();
	}

	salvar(){
		this.adminService.salvarHorarioProfessor(this.getHorarioFormvalue()).subscribe({
			next: (response) =>{
				this.closeModal();
				this.buscar();
				this.alertService.sucesso("Registro salvo!")
			},
			error: (err) => {
				console.log(err, { color: "red" });
			},
		});
	}

	editar(item: any) {
		this.isModalOpen = true;
		this.isEdit = true;
		this.preencherHorarioForm(item);
	}

	onConfirmDelete(choice: boolean | void){
		if(choice){
			this.adminService.excluirHorarioProfessor(this.idDelete).subscribe({
				next: () =>{
					this.buscar();
					this.alertService.exclusao("Horario excluid com sucesso!")
				},
				error: (err) => {
					console.log(err, { color: "red" });
				},
			})
		}
		this.idDelete = 0
		this.isModalConfirm = false;
	}
	  
	excluir(item: any) {
		this.idDelete = item.id;
		this.isModalConfirm = true;
	}

	onPaginacaoChange(event: { paginaSelecionada: number; itensPage: number }){
		this.paginaAtual = --event.paginaSelecionada;
		this.itensPage = event.itensPage;
		this.buscar();
	}
}