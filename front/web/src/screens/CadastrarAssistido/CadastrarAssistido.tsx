import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import styles from "./CadastrarAssistido.module.css";

const API_URL = process.env.REACT_APP_API;

export default function CadastrarAssistido() {

  const [nome, setNome] = useState("lucas");
  const [matricula, setMatricula] = useState("23423423");
  const [telefone, setTelefone] = useState("88776643466");
  const [email, setEmail] = useState("lucas@gmail.com");
  const [cidade, setCidade] = useState("São Luís");
  const [bairro, setBairro] = useState("Alemanha");
  const [rua, setRua] = useState("Rua das Flores");
  const [numeroDaCasa, setNumeroDaCasa] = useState("12");
  const [cep, setCep] = useState("84023-242");

  const navigate = useNavigate();

  const cadastrarAssistido = async () => {
    
    try {
      await axios.post(`${API_URL}/assistidos/`, {
        nome,
        matricula,
        telefone,
        email,
        cidade,
        bairro,
        rua,
        numeroDaCasa: parseInt(numeroDaCasa),
        cep,
      });
      alert("Cadastrado com Sucesso! O assistido foi cadastrado.");
      limparCampos();
    } catch (error) {
      console.error(error);
    }
  };

  const limparCampos = () => {
    setNome("");
    setMatricula("");
    setTelefone("");
    setEmail("");
    setCidade("");
    setBairro("");
    setRua("");
    setNumeroDaCasa("");
    setCep("");
  };

  return (
    <form className={styles.container} onSubmit={cadastrarAssistido}>
      <button className={styles.backButton} onClick={() => navigate("/admin")}>
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
          <label className={styles.label}>Matrícula</label>
          <input
            className={styles.input}
            placeholder="Matrícula"
            value={matricula}
            onChange={(e) => setMatricula(e.target.value)}
          />
        </div>
        <div className={styles.inputGroup}>
          <label className={styles.label}>Telefone</label>
          <input
            className={styles.input}
            placeholder="Telefone"
            type="tel"
            value={telefone}
            onChange={(e) => setTelefone(e.target.value)}
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
          <label className={styles.label}>Cidade</label>
          <input
            className={styles.input}
            placeholder="Cidade"
            value={cidade}
            onChange={(e) => setCidade(e.target.value)}
          />
        </div>
        <div className={styles.inputGroup}>
          <label className={styles.label}>Bairro</label>
          <input
            className={styles.input}
            placeholder="Bairro"
            value={bairro}
            onChange={(e) => setBairro(e.target.value)}
          />
        </div>
        <div className={styles.inputGroup}>
          <label className={styles.label}>Rua</label>
          <input
            className={styles.input}
            placeholder="Rua"
            value={rua}
            onChange={(e) => setRua(e.target.value)}
          />
        </div>
        <div className={styles.inputGroup}>
          <label className={styles.label}>Número da Casa</label>
          <input
            className={styles.input}
            placeholder="Número da Casa"
            type="number"
            value={numeroDaCasa}
            onChange={(e) => setNumeroDaCasa(e.target.value)}
          />
        </div>
        <div className={styles.inputGroup}>
          <label className={styles.label}>CEP</label>
          <input
            className={styles.input}
            placeholder="CEP"
            value={cep}
            onChange={(e) => setCep(e.target.value)}
          />
        </div>
        <button type="submit" className={styles.button}>
          Cadastrar
        </button>
    </form>
  );
}
