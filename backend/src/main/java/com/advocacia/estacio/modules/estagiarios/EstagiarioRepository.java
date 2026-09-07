package com.advocacia.estacio.modules.estagiarios;

import org.springframework.data.jpa.repository.JpaRepository;

interface EstagiarioRepository extends JpaRepository<Estagiario, Long> {
	
//	Page<Estagiario> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

//	@Query("""
//			SELECT new com.advocacia.estacio.domain.records.EntidadeMinDto(
//					e.id,
//					e.nome
//			)
//			FROM Estagiario e WHERE e.email = :email
//			""")
//	Optional<EntidadeMinDto> buscarEstagiarioMinPorEmail(@Param("email") String email);
//
//	@Query("""
//			SELECT new com.advocacia.estacio.modules.estagiarios.Estagiario(
//				est.id,
//				est.nome,
//				est.email,
//				est.telefone,
//				est.matricula,
//				est.periodo,
//				est.usuario
//			)
//			FROM Estagiario est
//			""")
//	Page<Estagiario> buscarTodos(Pageable pageable);
}
