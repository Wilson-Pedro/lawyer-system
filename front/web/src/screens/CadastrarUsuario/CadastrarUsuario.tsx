import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate, Navigate } from "react-router-dom";
import { Toast, ToastContainer } from "react-bootstrap";
import styles from "./CadastrarUsuario.module.css";

import Input from "../../components/Input/Input";

const API_URL = process.env.REACT_APP_API;

export default function CadastrarUsuario() {
  const navigate = useNavigate();

  const [nome, setNome] = useState("");
  const [email, setEmail] = useState("");
  const [tipoAtor, setTipoAtor] = useState("");
  const [tiposDeAtores, setTiposDeAtores] = useState<string[]>([]);
  const [senha, setSenha] = useState("");

  const [mostrarToast, setMostrarToast] = useState(false);
  const [mensagemToast, setMensagemToast] = useState("");
  const [varianteToast, setVarianteToast] = useState<"success" | "danger">("success");

  useEffect(() => {

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
    buscarTipoAtores();
  }, []);

  const cadastrarUsuario = async () => {
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
      <button className={styles.backButton} onClick={() => navigate("/cadastrar")}>
        ← Voltar
      </button>

      <h1 className={styles.title}>Cadastrar Usuário</h1>

      <form className={styles.form} onSubmit={(e) => { e.preventDefault(); cadastrarUsuario(); }}>

        <Input
          value={nome}
          title="Nome Completo"
          setValue={setNome}
          required={true}
        />

        <Input
          value={email}
          title="E-mail"
          type="email"
          setValue={setEmail}
          required={true}
        />

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

        <Input
          value={senha}
          title="Senha"
          type="password"
          setValue={setSenha}
          required={true}
        />

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
