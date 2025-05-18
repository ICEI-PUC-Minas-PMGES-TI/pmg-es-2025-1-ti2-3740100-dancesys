package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.dto.DividendoDTO;
import com.dancesys.dancesys.dto.DividendoFilter;
import com.dancesys.dancesys.entity.Dividendo;
import com.dancesys.dancesys.entity.HorarioProfessor;
import com.dancesys.dancesys.infra.CriterialUtils;
import com.dancesys.dancesys.infra.PaginatedResponse;
import com.dancesys.dancesys.mapper.DividendoMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
public class DividendoRepositoryCustom {
    private EntityManager em;

    public DividendoRepositoryCustom(EntityManager em) { this.em = em; }

    public PaginatedResponse<Dividendo> buscar(DividendoFilter filtro) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Dividendo> query = cb.createQuery(Dividendo.class);
        Root<Dividendo> root = query.from(Dividendo.class);

        List<Predicate> predicates = new ArrayList<>();

        if(filtro.getCriadoEm() != null && !filtro.getCriadoEm().equals("")){
            String mesAnoStr = filtro.getCriadoEm();
            int cmes = Integer.parseInt(mesAnoStr.substring(0, 2));
            int cano = Integer.parseInt(mesAnoStr.substring(3));

            Expression<Integer> mesExpr = cb.function("month", Integer.class, root.get("criadoEm"));
            Expression<Integer> anoExpr = cb.function("year", Integer.class, root.get("criadoEm"));

            predicates.add(cb.equal(mesExpr, cmes));
            predicates.add(cb.equal(anoExpr, cano));
        }

        if(filtro.getPagoEm() != null && !filtro.getPagoEm().equals("")){
            String mesAnoStr = filtro.getPagoEm();
            int pmes = Integer.parseInt(mesAnoStr.substring(0, 2));
            int pano = Integer.parseInt(mesAnoStr.substring(3));

            Expression<Integer> mesExpr = cb.function("month", Integer.class, root.get("pagoEm"));
            Expression<Integer> anoExpr = cb.function("year", Integer.class, root.get("pagoEm"));

            predicates.add(cb.equal(mesExpr, pmes));
            predicates.add(cb.equal(anoExpr, pano));
        }

        if(filtro.getAlunos() != null && !filtro.getAlunos().isEmpty()){
            predicates.add(root.get("idAluno").get("id").in(filtro.getAlunos()));
        }

        if(filtro.getStatus() != null && !filtro.getStatus().isEmpty()){
            predicates.add(root.get("status").in(filtro.getStatus()));
        }

        if(filtro.getTipos() != null && !filtro.getTipos().isEmpty()){
            predicates.add(root.get("tipo").in(filtro.getTipos()));
        }

        if (filtro.getOrderBy() != null && !filtro.getOrderBy().isEmpty() && !filtro.getOrderBy().equals("mesesAtraso")) {
            Path<?> campoOrdenacao = CriterialUtils.getPath(root, filtro.getOrderBy());

            if (filtro.getOrder().equalsIgnoreCase("asc")) {
                query.orderBy(cb.asc(campoOrdenacao));
            } else {
                query.orderBy(cb.desc(campoOrdenacao));
            }
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));
        TypedQuery<Dividendo> typedQuery = em.createQuery(query);

        if (filtro.getTamanho() != null && filtro.getTamanho() > 0) {
            int pagina = filtro.getPagina() != null ? filtro.getPagina() : 0;
            typedQuery.setFirstResult(pagina * filtro.getTamanho());
            typedQuery.setMaxResults(filtro.getTamanho());
        }

        List<Dividendo> resultado = typedQuery.getResultList();

        if ("mesesAtraso".equals(filtro.getOrderBy())) {
            Comparator<Dividendo> comparator = Comparator.comparing(Dividendo::getMesesAtraso);
            if (filtro.getOrder().equalsIgnoreCase("desc")) {
                comparator = comparator.reversed();
            }
            resultado.sort(comparator);
        }

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Dividendo> countRoot = countQuery.from(Dividendo.class);
        countQuery.select(cb.count(countRoot));

        List<Predicate> countPredicates = new ArrayList<>();

        if(filtro.getCriadoEm() != null && !filtro.getCriadoEm().equals("")){
            String mesAnoStr = filtro.getCriadoEm();
            int cmes = Integer.parseInt(mesAnoStr.substring(0, 2));
            int cano = Integer.parseInt(mesAnoStr.substring(3));

            Expression<Integer> mesExpr = cb.function("month", Integer.class, countRoot.get("criadoEm"));
            Expression<Integer> anoExpr = cb.function("year", Integer.class, countRoot.get("criadoEm"));

            countPredicates.add(cb.equal(mesExpr, cmes));
            countPredicates.add(cb.equal(anoExpr, cano));
        }

        if(filtro.getPagoEm() != null && !filtro.getPagoEm().equals("")){
            String mesAnoStr = filtro.getPagoEm();
            int pmes = Integer.parseInt(mesAnoStr.substring(0, 2));
            int pano = Integer.parseInt(mesAnoStr.substring(3));

            Expression<Integer> mesExpr = cb.function("month", Integer.class, countRoot.get("pagoEm"));
            Expression<Integer> anoExpr = cb.function("year", Integer.class, countRoot.get("pagoEm"));

            countPredicates.add(cb.equal(mesExpr, pmes));
            countPredicates.add(cb.equal(anoExpr, pano));
        }

        if(filtro.getAlunos() != null && !filtro.getAlunos().isEmpty()){
            countPredicates.add(countRoot.get("idAluno").get("id").in(filtro.getAlunos()));
        }

        if(filtro.getStatus() != null && !filtro.getStatus().isEmpty()){
            countPredicates.add(countRoot.get("status").in(filtro.getStatus()));
        }

        if(filtro.getTipos() != null && !filtro.getTipos().isEmpty()){
            countPredicates.add(countRoot.get("tipo").in(filtro.getTipos()));
        }

        countQuery.where(cb.and(countPredicates.toArray(new Predicate[0])));
        Long total = em.createQuery(countQuery).getSingleResult();

        return new PaginatedResponse<>(resultado, total);
    }
}
