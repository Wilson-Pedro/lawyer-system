import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate, Navigate, useParams } from "react-router-dom";
import { Toast, ToastContainer } from "react-bootstrap";
import styles from "./EditarUsuario.module.css";
import { scrollToTop } from './../../utils/Utils';

const API_URL = process.env.REACT_APP_API;

export default function EditarUsuario() {
  const navigate = useNavigate();

  const [nome, setNome] = useState("");
  const [email, setEmail] = useState("");
  const [tipoAtor, setTipoAtor] = useState("");
  const [tiposDeAtores, setTiposDeAtores] = useState<string[]>([]);
  const [usuarioStatus, setUsuarioStatus] = useState("");
  const [statusDoUsuario, setStatusDoUsuario] = useState<string[]>([]);
  const [senha, setSenha] = useState("");

  const [mostrarToast, setMostrarToast] = useState(false);
  const [mensagemToast, setMensagemToast] = useState("");
  const [varianteToast, setVarianteToast] = useState<"success" | "danger">("success");

  const params = useParams();
  const usuarioId = params.usuarioId || '';

  useEffect(() => {

    const token = localStorage.getItem('token');

    const buscarTipoAtores = async () => {

      try {

        const response = await axios.get(`${API_URL}/atores/tipoAtores`, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        setTiposDeAtores(response.data);

      } catch(error) {
        console.log(error);
      }

    }

    const buscarUsuarioStatus = async () => {

      try {

        const response = await axios.get(`${API_URL}/auth/usuarioStatus`, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        setStatusDoUsuario(response.data);

      } catch(error) {
        console.log(error);
      }

    }

    const fetchUsuario = async () => {
      try {
        const response = await axios.get(`${API_URL}/atores/${usuarioId}`, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        const dados = response.data;
        setNome(dados.nome);
        setEmail(dados.email);
        setTipoAtor(dados.tipoAtor);
        setSenha("");
        setUsuarioStatus(dados.usuarioStatus);
      } catch(error) {

      }
    }

    fetchUsuario();
    buscarTipoAtores();
    buscarUsuarioStatus();
  }, []);

  const atualizarUsuario = async () => {
    try {
      await axios.put(`${API_URL}/atores/${usuarioId}`, {
        nome,
        email,
        tipoAtor,
        usuarioStatus,
        senha,
      }, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      
      scrollToTop();

      setMensagemToast(`${tipoAtor} atualizado com sucesso!`);
      setVarianteToast("success");
      setMostrarToast(true);

    } catch (error) {
      console.error(error);

      setMensagemToast(`${tipoAtor} ao atualizar usuário.`);
      setVarianteToast("danger");
      setMostrarToast(true);
    }
  };

  const selecionarTipoAtor = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setTipoAtor(e.target.value);
  };

  const selecionarUsuarioStatus = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setUsuarioStatus(e.target.value);
  };
  

  const token = localStorage.getItem('token');
  if(!token) return <Navigate to="/login" />

  return (
    <div className={styles.container}>
      {/* Botão de Voltar */}
      <button className={styles.backButton} onClick={() => navigate("/usuarios")}>
        ← Voltar
      </button>

      <h1 className={styles.title}>Atualizar Usuário</h1>

      <form className={styles.form} onSubmit={(e) => { e.preventDefault(); atualizarUsuario(); }}>
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
          <label className={styles.label}>Função</label>
          <select
            className={styles.input}
            value={tipoAtor}
            onChange={selecionarTipoAtor}
          >
            <option value="" disabled selected></option>
            {tiposDeAtores.map((option, key) => (
              <option key={key} value={option}>{option}</option>
            ))}
          </select>
        </div>

        <div className={styles.inputGroup}>
          <label className={styles.label}>Status</label>
          <select
            className={styles.input}
            value={usuarioStatus}
            onChange={selecionarUsuarioStatus}
          >
            <option value="" disabled selected></option>
            {statusDoUsuario.map((option, key) => (
              <option key={key} value={option}>{option}</option>
            ))}
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
