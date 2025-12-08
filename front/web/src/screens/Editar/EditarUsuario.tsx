import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate, Navigate, useParams } from "react-router-dom";
import { Toast, ToastContainer } from "react-bootstrap";
import styles from "./EditarUsuario.module.css";

const API_URL = process.env.REACT_APP_API;

export default function EditarUsuario() {
  const navigate = useNavigate();

  const [nome, setNome] = useState("");
  const [email, setEmail] = useState("");
  const [tipoAtor, setTipoAtor] = useState("");
  const [senha, setSenha] = useState("");

  const [mostrarToast, setMostrarToast] = useState(false);
  const [mensagemToast, setMensagemToast] = useState("");
  const [varianteToast, setVarianteToast] = useState<"success" | "danger">("success");

  const params = useParams();
  const usuarioId = params.usuarioId || '';

  useEffect(() => {

    const token = localStorage.getItem('token');

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
      } catch(error) {

      }
    }

    fetchUsuario();
  }, []);

  const cadastrarEstagiario = async () => {
    try {
      await axios.post(`${API_URL}/atores/`, {
        nome,
        email,
        tipoAtor,
        senha,
      }, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });

      setMensagemToast("Usuário cadastrado com sucesso!");
      setVarianteToast("success");
      setMostrarToast(true);

      limparCampos();
    } catch (error) {
      console.error(error);

      setMensagemToast("Erro ao cadastrar usuário.");
      setVarianteToast("danger");
      setMostrarToast(true);
    }
  };

  const selecionarTipoAtor = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setTipoAtor(e.target.value);
  };

  const limparCampos = () => {
    setNome("");
    setEmail("");
    setTipoAtor("");
    setSenha("");
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
          <label className={styles.label}>Função</label>
          <select
            className={styles.input}
            value={tipoAtor}
            onChange={selecionarTipoAtor}
          >
            <option value="" disabled selected></option>
            <option value="Coordenador do curso">Coordenador(a) do curso</option>
            <option value="Secretário">Secretário(a)</option>
            <option value="Professor">Professor(a)</option>
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
