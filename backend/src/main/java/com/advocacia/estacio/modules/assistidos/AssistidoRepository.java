package com.advocacia.estacio.modules.assistidos;

import org.springframework.data.jpa.repository.JpaRepository;

interface AssistidoRepository extends JpaRepository<Assistido, Long> {
	
//	Page<Assistido> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

//	@Query("""
//			SELECT new com.advocacia.estacio.domain.dto.ResponseMinDto(
//				ass.id,
//				ass.nome,
//				ass.email,
//				ass.registro
//			)
//			FROM Assistido ass
//			""")
//	Page<Assistido> buscarTodos(Pageable pageable);

}
