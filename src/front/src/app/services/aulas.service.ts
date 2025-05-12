import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'; // Importando o HttpClient
import { Observable } from 'rxjs';

export interface Aula {
  id: number;
  dataHora: string; // data e hora da aula
  diaSemana: string; // dia da semana (ex: Segunda-feira)
  modalidade: string;
  professor: string;
  local: string;
}

@Injectable({
  providedIn: 'root'
})
export class AulasService {
  private apiUrl = 'http://localhost:3000/api/aulas'; 

  constructor(private http: HttpClient) {}

  
  getAulas(): Observable<Aula[]> {
    return this.http.get<Aula[]>(this.apiUrl); 
  }


  cancelarAula(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`); 
}
}
