import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Aulas, AulasModalidade, Conversao, Financeiro } from "../models/Indicadores.model";
import { environment } from "../../environment/environment";

@Injectable({
	providedIn: "root",
})

export class IndicadoresService{
	http = inject(HttpClient);

    url = `${environment.API_URL}indicador/`

    public getRelatorioFinanceiro(ano: number): Observable<Financeiro[]>{
        return this.http.get(`${this.url}financeiro/${ano}`) as Observable<Financeiro[]>
    }

    public getRelatorioConversao(ano: number): Observable<Conversao[]>{
        return this.http.get(`${this.url}conversao/${ano}`) as Observable<Conversao[]>
    }

    public getRelatorioAulas(idProfesseor: number, ano: number): Observable<Aulas[]>{
        return this.http.get(`${this.url}aulas/${ano}/${idProfesseor}`) as Observable<Aulas[]>
    }

    public getRelatorioAulasModalidade(ano: number): Observable<AulasModalidade[]>{
        return this.http.get(`${this.url}aulas/modalidade/${ano}`) as Observable<AulasModalidade[]>
    }
}