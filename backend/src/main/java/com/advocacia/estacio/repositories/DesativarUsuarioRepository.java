package com.advocacia.estacio.repositories;

import com.advocacia.estacio.domain.entities.DataParaDesativarUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DesativarUsuarioRepository extends JpaRepository<DataParaDesativarUsuario, Long> {

}
