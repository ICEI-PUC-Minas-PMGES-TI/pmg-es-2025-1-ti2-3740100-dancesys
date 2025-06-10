import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { environment } from "../../environment/environment";
import { AulaExtraFilter } from "../models/AulaExtra.model";
import { Mensagem } from "../models/Mensagem.model";
import { AulaExtraDTO } from "../models/Aula.model";
import { AulaExperimental, AulaExperimentalFilter } from "../models/AulaExperimental.model";

@Injectable({
	providedIn: "root",
})
export class AulaService {
	http = inject(HttpClient);

	url: string = `${environment.API_URL}aula/`;

	public filterAulaExtra(filtro: AulaExtraFilter) {
		return this.http.post(`${this.url}extra/buscar`, {
			...filtro,
		});
	}

	public solicitarAulaExtra(aula: AulaExtraDTO) {
		return this.http.post(`${this.url}extra`, { ...aula });
	}

    public aceitarAulaExtra(idAula: number, idSala: number){
        return this.http.get(`${this.url}extra/aceitar/${idAula}/${idSala}`)
    }

    public indeferirAulaExtra(idAula: number, msg: Mensagem){
        return this.http.post(`${this.url}extra/indeferir/${idAula}`, {
            ...msg
        })
    }

    public cancelarAulaextra(idAula: number, msg: Mensagem){
        return this.http.post(`${this.url}extra/cancelar/${idAula}`, {
            ...msg
        })
    }

    public salvarAulaExperimental(item: AulaExperimental){
        return this.http.post(`${this.url}experimental` , { ...item })
    }

    public filterAulaExperimental(filtro: AulaExperimentalFilter){
        return this.http.post(`${this.url}experimental/buscar`, { ...filtro })
    }

    public converterAulaExperimental(id: number){
        return this.http.get(`${this.url}experimental/converter/${id}`)
    }

    public rejeitarAulaExperimental(motivo: number, id: number, msg: Mensagem){
        return this.http.post(`${this.url}experimental/rejeitar/${motivo}/${id}`, { ...msg })
    }
}
