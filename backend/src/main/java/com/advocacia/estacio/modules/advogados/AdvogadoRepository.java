package com.advocacia.estacio.modules.advogados;

import com.advocacia.estacio.modules.usuarios.enums.UsuarioStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.Optional;

interface AdvogadoRepository extends JpaRepository<Advogado, Long> {

    @EntityGraph(attributePaths = {"pessoa", "pessoa.endereco", "pessoa.usuario"})
    @Query("SELECT a FROM Advogado a WHERE a.id = :id")
    Optional<Advogado> buscarDetalhesPorId(@Param("id") Long id);

    @EntityGraph(attributePaths = {"pessoa", "pessoa.usuario"})
    @Query("""
        SELECT a FROM Advogado a
        JOIN a.pessoa p
        LEFT JOIN p.usuario u
        WHERE (:nome IS NULL OR LOWER(a.pessoa.nome) LIKE LOWER(CONCAT('%', :nome, '%')))
        AND (:status IS NULL OR a.pessoa.usuario.status = :status)
    """)
    Page<Advogado> pesquisarComFiltros(
            @Param("nome") String nome,
            @Param("status") UsuarioStatus status,
            Pageable pageable
    );

    @EntityGraph(attributePaths = {"pessoa"})
    @Query("""
        SELECT a FROM Advogado a
        WHERE LOWER(a.pessoa.nome) LIKE LOWER(CONCAT('%', :nome, '%'))
        AND a.pessoa.usuario.status = :status
    """)
    Page<Advogado> buscarAtivosPorNome(
            @Param("nome") String nome,
            @Param("status") UsuarioStatus status,
            Pageable pageable
    );


//	@Query("""
//			SELECT new com.advocacia.estacio.modules.advogados.Advogado(
//				adv.id,
//				adv.nome
//			)
//			FROM Advogado adv WHERE adv.email = :email
//			""")
//	Optional<Advogado> buscarIdPorEmail(@Param("email") String email);
}
