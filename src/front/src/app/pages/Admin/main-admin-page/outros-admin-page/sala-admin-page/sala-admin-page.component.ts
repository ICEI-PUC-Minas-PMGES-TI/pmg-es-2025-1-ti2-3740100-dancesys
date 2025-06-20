import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { BotaoComponent } from '../../../../../components/botao/botao.component';
import { SimpleTableComponent } from '../../../../../components/simple-table/simple-table.component';
import { ModalComponent } from '../../../../../components/modal/modal.component';
import { SalaService } from '../../../../../services/sala.service';
import { AlertService } from '../../../../../services/Alert.service';
import { Sala } from '../../../../../models/Sala.model';

enum ToggleModal {
	NEW = "Criar sala",
	EDIT = "Editar sala",
}

@Component({
  selector: 'app-sala-admin-page',
  imports: [
    ModalComponent,
    SimpleTableComponent,
    ReactiveFormsModule,
    BotaoComponent,
    CommonModule
  ],
  templateUrl: './sala-admin-page.component.html',
  styleUrl: './sala-admin-page.component.css'
})
export class SalaAdminPageComponent {
  salaService = inject(SalaService)
  alertService = inject(AlertService)

  salaForm: FormGroup;
  isModalOpen: boolean = false
  isEdit: boolean = false
  isModalConfirm: boolean = false
  selectId: number = 0
  ToggleModal = ToggleModal
  salaObj: Sala[] = []

  colunas = [
		{ chave: 'nome', titulo: 'Nome' },
	];
	  	  
	acoes = [
		{
		  icon: 'edit',
		  title: 'Editar',
		  cor: 'black_blue',
		  callback: (item: any) => this.editar(item)
		},
		{
		  icon: 'trash',
		  title: 'Excluir',
		  cor: 'dark',
		  callback: (item: any) => this.excluir(item.id)
		}
	];

  constructor(private fb: FormBuilder) {
    this.salaForm = this.fb.group({
      id: [],
      nome: []
    });
	}

  ngOnInit(){
    this.buscar()
  }

  buscar(){
    this.salaService.fetchSalas().subscribe({
      next: (response) =>{
        this.salaObj = response
      }
    })
  }

  novo(){
    this.isModalOpen = true
    this.isEdit = false
  }

  getSalaForm(){
    const item = this.salaForm.value;
    const salaItem: Sala = item;
    
    return salaItem;
  }

  salvar(){
    this.salaService.salvarSala(this.getSalaForm()).subscribe({
      next: (response) =>{
        this.alertService.sucesso("Sala salva com sucesso")
        this.closeModal()
        this.buscar()
      }
    })
  }

  excluir(id: number){
    this.isModalConfirm = true
    this.selectId = id
  }

  onConfirmDelete(choice: boolean | void){
    if(choice){
			this.salaService.excluir(this.selectId).subscribe({
				next: () =>{
					this.buscar();
					this.alertService.exclusao("Horario excluido com sucesso!")
				},
				error: (err) => {
          this.alertService.erro("Sala em uso")
				},
			})
		}
		this.selectId = 0
		this.isModalConfirm = false;
  }

  preencherSalaForm(item: Sala){
    this.salaForm = this.fb.group({
      id: [item.id],
      nome: [item.nome]
    });
  }

  resetSalaForm(){
    this.salaForm = this.fb.group({
      id: [],
      nome: []
    });
  }

  editar(item: Sala){
    this.isEdit = true
    this.isModalOpen = true
    this.preencherSalaForm(item)
  }

  isFormValido(){
    return this.salaForm.valid
  }

  closeModal(){
    this.isModalOpen = false
    this.isEdit = false
    this.resetSalaForm()
  }
}
