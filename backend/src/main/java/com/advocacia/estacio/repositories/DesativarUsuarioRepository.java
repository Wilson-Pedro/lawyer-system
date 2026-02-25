package com.advocacia.estacio.repositories;

import com.advocacia.estacio.domain.entities.DataParaDesativarUsuarios;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DesativarUsuarioRepository extends JpaRepository<DataParaDesativarUsuarios, Long> {

}
