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
  const [prazo, setPrazo] = useState("");
  const [messageDataError, setMessageDataError] = useState("");
  const [postValid, setPostValid] = useState(false);

  const cadastrarProcesso = async () => {
    if(postValid) {
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
    }
  };

  const formatarData = (dataValue: string) => {
    let numeros = dataValue.replace(/\D/g, "");

    if(numeros.length > 8) {
      numeros = numeros.substring(0, 8);
    }

    let formatado = numeros;

    if(numeros.length > 2) {
      formatado = numeros.substring(0, 2) + "/" + numeros.substring(2);
    }

    if(numeros.length > 4) {
      formatado = numeros.substring(0, 2) + "/" + numeros.substring(2, 4) + "/" + numeros.substring(4);
    }

    if(numeros.length === 8) {
      const dia = parseInt(numeros.substring(0, 2));
      const mes = parseInt(numeros.substring(2, 4));
      const ano = parseInt(numeros.substring(4, 8));

      const dataDigitada = new Date(ano, mes - 1, dia);
      const hoje = new Date();

      if(dataDigitada.getTime() < hoje.getTime()) {
        setMessageDataError("*Data inválida");
      }
    } else {
        setPostValid(true);
    }

    setPrazo(formatado);
  }

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

      <h1 className={styles.title}>Cadastro de Processo</h1>

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
        <label className={styles.label}>Prazo <span className={styles.messageError}>{messageDataError}</span></label>
        <input
          className={styles.input}
          placeholder="Prazo (DD/MM/AAAA)"
          value={prazo}
          onChange={(e) => formatarData(e.target.value)}
          required
        />
      </div>

      <button type="submit" className={styles.button}>
        Cadastrar Processo
      </button>
    </form>
  );
}
