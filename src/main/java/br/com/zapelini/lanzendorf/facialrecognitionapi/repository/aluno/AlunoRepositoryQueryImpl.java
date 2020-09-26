package br.com.zapelini.lanzendorf.facialrecognitionapi.repository.aluno;

import br.com.zapelini.lanzendorf.facialrecognitionapi.model.Aluno;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class AlunoRepositoryQueryImpl implements AlunoRepositoryQuery{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Aluno> filter(String nome, String email, String matricula) {
        String sql = "SELECT a FROM Aluno a " +
                "JOIN a.usuario u " +
                "WHERE a.idAluno != NULL ";

        if(!StringUtils.isEmpty(nome)) {
            sql += "AND LOWER(u.nome) LIKE LOWER(:nome) ";
        }
        if(!StringUtils.isEmpty(email)) {
            sql += "AND u.email = :email ";
        }
        if(!StringUtils.isEmpty(matricula)) {
            sql += "AND a.matricula = :matricula ";
        }

        Query query = entityManager.createQuery(sql, Aluno.class);

        if(!StringUtils.isEmpty(nome)) {
            query.setParameter("nome", "%" + nome + "%");
        }
        if(!StringUtils.isEmpty(email)) {
            query.setParameter("email", email);
        }
        if(!StringUtils.isEmpty(matricula)) {
            query.setParameter("matricula", matricula);
        }

        return query.getResultList();
    }
}