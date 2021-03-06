package br.com.zapelini.lanzendorf.facialrecognitionapi.repository.coordenador;

import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Coordenador;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

@Repository
public class CoordenadorRepositoryQueryImpl implements CoordenadorRepositoryQuery{

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Coordenador> filter(Pageable pageable, String nome, String email) {
        String sql = "SELECT c FROM Coordenador c " +
                "JOIN c.usuario u " +
                "WHERE c.idCoordenador != NULL ";
        sql = filterSql(sql, nome, email);
        Query query = entityManager.createQuery(sql, Coordenador.class);
        adicionarPaginacao(query, pageable);
        addParamFilter(query, nome, email);
        return query.getResultList();
    }

    @Override
    public Integer filterCount(String nome, String email) {
        String sql = "SELECT COUNT(c.*) FROM coordenador c " +
                "JOIN usuario u ON c.id_usuario = u.id_usuario " +
                "WHERE true ";

        sql = filterSql(sql, nome, email);
        Query query = entityManager.createNativeQuery(sql);
        addParamFilter(query, nome, email);
        return ((BigInteger) query.getSingleResult()).intValue();
    }

    private String filterSql(String sql, String nome, String email) {
        if (!StringUtils.isEmpty(nome)) {
            sql += "AND LOWER(u.nome) LIKE LOWER(:nome) ";
        }
        if (!StringUtils.isEmpty(email)) {
            sql += "AND u.email = :email ";
        }
        return sql;
    }

    private void addParamFilter(Query query, String nome, String email) {
        if(!StringUtils.isEmpty(nome)) {
            query.setParameter("nome", "%" + nome + "%");
        }
        if(!StringUtils.isEmpty(email)) {
            query.setParameter("email", email);
        }
    }

    private void adicionarPaginacao(Query query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int registrosPorPagina = pageable.getPageSize();
        int primeiroRegistro = paginaAtual * registrosPorPagina;

        query.setFirstResult(primeiroRegistro);
        query.setMaxResults(registrosPorPagina);
    }

}
