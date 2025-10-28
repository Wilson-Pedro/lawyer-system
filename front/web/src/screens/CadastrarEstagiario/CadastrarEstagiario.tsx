import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { Toast, ToastContainer } from "react-bootstrap";
import styles from "./CadastrarEstagiario.module.css";

const API_URL = process.env.REACT_APP_API;

export default function CadastrarEstagiario() {
  const navigate = useNavigate();

  const [nome, setNome] = useState("");
  const [email, setEmail] = useState("");
  const [matricula, setMatricula] = useState("");
  const [periodo, setPeriodo] = useState("");
  const [senha, setSenha] = useState("");

  const [mostrarToast, setMostrarToast] = useState(false);
  const [mensagemToast, setMensagemToast] = useState("");
  const [varianteToast, setVarianteToast] = useState<"success" | "danger">("success");

  const cadastrarEstagiario = async () => {
    try {
      await axios.post(`${API_URL}/estagiarios/`, {
        nome,
        email,
        matricula,
        periodo,
        senha,
      });

      setMensagemToast("Estagiário cadastrado com sucesso!");
      setVarianteToast("success");
      setMostrarToast(true);

      limparCampos();
    } catch (error) {
      console.error(error);

      setMensagemToast("Erro ao cadastrar estagiário.");
      setVarianteToast("danger");
      setMostrarToast(true);
    }
  };

  const selecionarPeriodo = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setPeriodo(e.target.value);
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
      <button className={styles.backButton} onClick={() => navigate("/admin")}>
        ← Voltar
      </button>

      <h1 className={styles.title}>Cadastrar Estagiário</h1>

      <form className={styles.form} onSubmit={(e) => { e.preventDefault(); cadastrarEstagiario(); }}>
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
          <select
            className={styles.input}
            value={periodo}
            onChange={selecionarPeriodo}
          >
            <option value="" disabled selected></option>
            <option value="Estágio I">Estágio I</option>
            <option value="Estágio II">Estágio II</option>
            <option value="Estágio III">Estágio III</option>
            <option value="Estágio IV">Estágio IV</option>
          </select>
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

        <button type="submit" className={styles.button}>
          Enviar Cadastro
        </button>
      </form>

      {/* Toast visual */}
      <ToastContainer position="top-end" className="p-3" style={{ zIndex: 9999 }}>
        <Toast
          onClose={() => setMostrarToast(false)}
          show={mostrarToast}
          bg={varianteToast}
          delay={3000}
          autohide
        >
          <Toast.Body className={`${styles.toastMessage} text-white`}>
            {mensagemToast}
          </Toast.Body>
        </Toast>
      </ToastContainer>
    </div>
  );
}
