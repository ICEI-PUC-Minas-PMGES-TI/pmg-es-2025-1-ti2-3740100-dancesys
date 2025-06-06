import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { environment } from "../../environment/environment";
import { AulaExtraFilter } from "../models/AulaExtra.model";
import { Mensagem } from "../models/Mensagem.model";

@Injectable({
	providedIn: "root",
})
export class AulaService {
	http = inject(HttpClient);

    url: string = `${environment.API_URL}aula/`

	public filterAulaExtra(filtro: AulaExtraFilter){
        return this.http.post(`${this.url}extra/buscar`, {
            ...filtro,
        });
    }

    public acitarAulaExtra(idAula: number, idSala: number){
        return this.http.get(`${this.url}extra/aceitar/${idAula}/${idSala}`)
    }

    public indeferirAulaExtra(idAula: number, msg: Mensagem){
        return this.http.post(`${this.url}extra/indeferir/${idAula}`, {
            ...msg
        })
    }
}