import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Financeiro } from "../models/Indicadores.model";
import { environment } from "../../environment/environment";

@Injectable({
	providedIn: "root",
})

export class IndicadoresService{
	http = inject(HttpClient);

    public getRelatorioFinanceiro(ano: number): Observable<Financeiro[]>{
        return this.http.get(
            `${environment.API_URL}indicador/financeiro/${ano}`,
        ) as Observable<Financeiro[]>
    }
}