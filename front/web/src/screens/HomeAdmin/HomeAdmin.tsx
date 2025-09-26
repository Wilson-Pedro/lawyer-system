import React from "react";
import { useNavigate } from "react-router-dom";
import MenuButton from "../../components/MenuButton/MenuButton";
import styles from "./HomeAdmin.module.css";

export default function HomeAdmin() {
  const navigate = useNavigate();

  return (
    <div className={styles.container}>
      <h1 className={styles.title}>Painel Administrativo</h1>

      <div className={styles.menu}>
        <MenuButton label="Ver Processos" onClick={() => navigate("/processos")} />
        <MenuButton label="Cadastrar Assistido" onClick={() => navigate("/CadastrarAssistido")} />
        <MenuButton label="Cadastrar Estagiário" onClick={() => navigate("/CadastrarEstagiario")} />
        <MenuButton label="Cadastrar Advogado" onClick={() => navigate("/CadastrarAdvogado")}/>
        <MenuButton label="Cadastrar Processo" onClick={() => navigate("/CadastrarProcesso")} />
        <MenuButton label="Sair" onClick={() => navigate("/")} color="#e74c3c" />
      </div>
    </div>
  );
}
