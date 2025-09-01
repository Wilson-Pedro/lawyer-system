import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import styles from "./CadastrarProcesso.module.css";

const API_URL = process.env.REACT_APP_API;

export default function CadastrarProcesso() {
  const navigate = useNavigate();

  const [assunto, setAssunto] = useState("");
  const [vara, setVara] = useState("");
  const [responsavel, setResponsavel] = useState("");
  const [prazo, setPrazo] = useState("11/11/2026");

  const cadastrarProcesso = async () => {
    try {
      await axios.post(`${API_URL}/processos/`, {
        assunto,
        vara,
        responsavel,
        prazo,
      });
      alert("Processo cadastrado com sucesso!");
      limparCampos();
    } catch (error) {
      console.error(error);
      alert("Erro ao cadastrar processo.");
    }
  };

  const limparCampos = () => {
    setAssunto("");
    setVara("");
    setResponsavel("");
    setPrazo("");
  };

  return (
    <form className={styles.container} onSubmit={cadastrarProcesso}>

      {/* Botão de Voltar */}
      <button className={styles.backButton} onClick={() => navigate(-1)}>
        ← Voltar
      </button>

      <h1 className={styles.title}>Cadastro de Processo {API_URL}</h1>

      <div className={styles.inputGroup}>
        <label className={styles.label}>Assunto</label>
        <input
          className={styles.input}
          placeholder="Assunto"
          value={assunto}
          onChange={(e) => setAssunto(e.target.value)}
          required
        />
      </div>

      <div className={styles.inputGroup}>
        <label className={styles.label}>Vara</label>
        <input
          className={styles.input}
          placeholder="Vara"
          value={vara}
          onChange={(e) => setVara(e.target.value)}
          required
        />
      </div>

      <div className={styles.inputGroup}>
        <label className={styles.label}>Responsável</label>
        <input
          className={styles.input}
          placeholder="Responsável"
          value={responsavel}
          onChange={(e) => setResponsavel(e.target.value)}
          required
        />
      </div>

      <div className={styles.inputGroup}>
        <label className={styles.label}>Prazo</label>
        <input
          className={styles.input}
          placeholder="Prazo (DD/MM/AAAA)"
          value={prazo}
          onChange={(e) => setPrazo(e.target.value)}
          required
        />
      </div>

      <button type="submit" className={styles.button}>
        Cadastrar Processo
      </button>
    </form>
  );
}
