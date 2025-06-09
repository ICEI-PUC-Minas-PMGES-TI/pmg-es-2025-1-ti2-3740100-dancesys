import { Time } from "@angular/common";

export class AulaExperimental {
    id!: number;
    data!: Date;
    horarioInicio!: Time;
    horarioFim!: Time;
    nome!: String;
    cpf!: String;
    numero!: String;
    situacao!: number;
    motivo!: number;
    motivoOutro!: String;
    idProfessor!: number;
}

export class AulaExperimentalFilter {
    dataInicial!: Date;
    dataFinal!: Date;
    cpf!: string;
    idProfessor!: number;
    situacao!: number[];
    motivos!: number[];
    pagina!: number;
    tamanho!: number;
    orderBy!: string;
    order!: string;
}