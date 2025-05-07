import { Component, inject } from '@angular/core';
import { Aula, AulaFilter } from '../../../../../models/Aula.model';
import { ModalComponent } from '../../../../../components/modal/modal.component';
import { SimpleTableComponent } from '../../../../../components/simple-table/simple-table.component';
import { SearchBoxMultiComponent } from '../../../../../components/search-box-multi/search-box-multi.component';
import { MultiSelectInputComponent } from '../../../../../components/multi-select-input/multi-select-input.component';
import { Form, FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms'; 
import { AdminService } from '../../../../../services/admin.service';


@Component({
  selector: 'app-aulas-fixas-admin-page',
  standalone: true,
  imports: [
    ModalComponent, 
    SimpleTableComponent, 
    SearchBoxMultiComponent, 
    MultiSelectInputComponent,
    ReactiveFormsModule
  ],
  templateUrl: './aulas-fixas-admin-page.component.html',
  styleUrl: './aulas-fixas-admin-page.component.css'
})
export class AulasFixasAdminPageComponent {
  filterForm: FormGroup
	adminService = inject(AdminService);

  paginaAtual: number = 0;
	itensPage: number = 10;

  constructor(private fb: FormBuilder) {
		this.filterForm = this.fb.group({
		  dias: [''],
      professores: [''],
      modalidades: [[]],
      tamanho: [this.itensPage],
      pagina: [this.paginaAtual]
		});
	}

  diasObj = [
		{dia: 1, nome: "Segunda"},
		{dia: 2, nome: "Terça"},
		{dia: 3, nome: "Quarta"},
		{dia: 4, nome: "Quinta"},
		{dia: 5, nome: "Sexta"},
		{dia: 6, nome: "Sabado"},
		{dia: 7, nome: "Domingo"},
	] 

  diasFilter: number[] = []

  buscar(){

  }

  onDiasChange(selected: number[]){
    this.diasFilter = selected;
    console.log(this.diasFilter)
  }


}
