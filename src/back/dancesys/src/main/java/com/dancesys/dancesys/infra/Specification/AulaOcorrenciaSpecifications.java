package com.dancesys.dancesys.infra.Specification;

import com.dancesys.dancesys.entity.AulaOcorrencia;
import com.dancesys.dancesys.entity.Chamada;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class AulaOcorrenciaSpecifications {
    public static Specification<AulaOcorrencia> entreDatas(LocalDate dataInicio, LocalDate dataFim) {
        return (root, query, cb) -> cb.between(root.get("data"), dataInicio, dataFim);
    }

    public static Specification<AulaOcorrencia> naoPossuiChamadaParaAluno(Long alunoId) {
        return (root, query, cb) -> {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Chamada> chamadaRoot = subquery.from(Chamada.class);
            subquery.select(chamadaRoot.get("idAulaOcorrencia").get("id"))
                    .where(cb.equal(chamadaRoot.get("idAluno").get("id"), alunoId));

            return cb.not(root.get("id").in(subquery));
        };
    }
}
