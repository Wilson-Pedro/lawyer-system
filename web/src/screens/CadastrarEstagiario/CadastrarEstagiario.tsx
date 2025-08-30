import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import styles from "./CadastrarEstagiario.module.css";

const API_URL = process.env.REACT_APP_API_URL;

export default function CadastrarEstagiario() {
  const navigate = useNavigate();

  const [nome, setNome] = useState("");
  const [email, setEmail] = useState("");
  const [matricula, setMatricula] = useState("");
  const [periodo, setPeriodo] = useState("Estágio I");
  const [senha, setSenha] = useState("");

  const cadastrarEstagiario = async () => {
    try {
      await axios.post(`${API_URL}/estagiarios/`, {
        nome,
        email,
        matricula,
        periodo,
        senha,
      });
      alert("Estagiário cadastrado com sucesso!");
      limparCampos();
    } catch (error) {
      console.error(error);
      alert("Erro ao cadastrar estagiário.");
    }
  };

  const limparCampos = () => {
    setNome("");
    setEmail("");
    setMatricula("");
    setPeriodo("Estágio I");
    setSenha("");
  };

  return (
    <div className={styles.container}>

      {/* Botão de Voltar */}
      <button className={styles.backButton} onClick={() => navigate(-1)}>
        ← Voltar
      </button>

      <h1 className={styles.title}>Cadastro - Núcleo de Práticas Jurídicas</h1>

      <div className={styles.inputGroup}>
        <label className={styles.label}>Nome Completo</label>
        <input
          className={styles.input}
          placeholder="Nome Completo"
          value={nome}
          onChange={(e) => setNome(e.target.value)}
        />
      </div>

      <div className={styles.inputGroup}>
        <label className={styles.label}>E-mail</label>
        <input
          className={styles.input}
          placeholder="E-mail"
          type="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
      </div>

      <div className={styles.inputGroup}>
        <label className={styles.label}>Matrícula</label>
        <input
          className={styles.input}
          placeholder="Matrícula"
          value={matricula}
          onChange={(e) => setMatricula(e.target.value)}
        />
      </div>

      <div className={styles.inputGroup}>
        <label className={styles.label}>Período</label>
        <input
          className={styles.input}
          placeholder="Período"
          value={periodo}
          onChange={(e) => setPeriodo(e.target.value)}
        />
      </div>

      <div className={styles.inputGroup}>
        <label className={styles.label}>Senha</label>
        <input
          className={styles.input}
          placeholder="Senha"
          type="password"
          value={senha}
          onChange={(e) => setSenha(e.target.value)}
        />
      </div>

      <button className={styles.button} onClick={cadastrarEstagiario}>
        Enviar Cadastro
      </button>
    </div>
  );
}
