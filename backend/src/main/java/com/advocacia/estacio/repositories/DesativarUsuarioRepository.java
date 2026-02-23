package com.advocacia.estacio.repositories;

import com.advocacia.estacio.domain.entities.DesativarUsuario;
import com.advocacia.estacio.domain.entities.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DesativarUsuarioRepository extends JpaRepository<DesativarUsuario, Long> {

}
