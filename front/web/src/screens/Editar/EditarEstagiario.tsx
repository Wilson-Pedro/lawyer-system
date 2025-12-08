import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate, Navigate, useParams } from "react-router-dom";
import { Toast, ToastContainer } from "react-bootstrap";
import styles from "./EditarEstagiario.module.css";
import { scrollToTop } from './../../utils/Utils';

const API_URL = process.env.REACT_APP_API;

export default function EditarEstagiario() {
  const navigate = useNavigate();

  const [nome, setNome] = useState("");
  const [email, setEmail] = useState("");
  const [matricula, setMatricula] = useState("");
  const [periodo, setPeriodo] = useState("");
  const [senha, setSenha] = useState("");

  const [mostrarToast, setMostrarToast] = useState(false);
  const [mensagemToast, setMensagemToast] = useState("");
  const [varianteToast, setVarianteToast] = useState<"success" | "danger">("success");

  const params = useParams();
  const estagiarioId = params.estagiarioId || '';

  useEffect(() => {

    const token = localStorage.getItem('token');

    const fetchEstagiario = async () => {
      try {
        const response = await axios.get(`${API_URL}/estagiarios/${estagiarioId}`, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        const dados = response.data;
        setNome(dados.nome);
        setEmail(dados.email);
        setMatricula(dados.matricula);
        setPeriodo(dados.periodo);
      } catch(error) {

      }
    }

    fetchEstagiario();
  }, []);

  const atualizarEstagiario = async () => {
    try {
      await axios.put(`${API_URL}/estagiarios/${estagiarioId}`, {
        nome,
        email,
        matricula,
        periodo,
        senha,
      }, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      
      scrollToTop();

      setMensagemToast("Estagiário atualizado com sucesso!");
      setVarianteToast("success");
      setMostrarToast(true);

    } catch (error) {
      console.error(error);

      setMensagemToast("Erro ao atualizar estagiário.");
      setVarianteToast("danger");
      setMostrarToast(true);
    }
  };

  const selecionarPeriodo = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setPeriodo(e.target.value);
  };

  const token = localStorage.getItem('token');
  if(!token) return <Navigate to="/login" />

  return (
    <div className={styles.container}>
      {/* Botão de Voltar */}
      <button className={styles.backButton} onClick={() => navigate('/usuarios')}>
        ← Voltar
      </button>

      <h1 className={styles.title}>Atualizar Estagiário</h1>

      <form className={styles.form} onSubmit={(e) => { e.preventDefault(); atualizarEstagiario(); }}>
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
          Atualizar
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
