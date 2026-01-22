import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate, Navigate } from "react-router-dom";
import { Toast, ToastContainer } from "react-bootstrap";
import styles from "./CadastrarEstagiario.module.css";
import Input from "../../components/Input/Input";

const API_URL = process.env.REACT_APP_API;

export default function CadastrarEstagiario() {
  const navigate = useNavigate();

  const [nome, setNome] = useState("");
  const [email, setEmail] = useState("");
  const [telefone, setTelefone] = useState("");
  const [matricula, setMatricula] = useState("");
  const [periodos, setPeriodos] = useState<string[]>([]);
  const [periodo, setPeriodo] = useState("");
  const [senha, setSenha] = useState("");

  const [mostrarToast, setMostrarToast] = useState(false);
  const [mensagemToast, setMensagemToast] = useState("");
  const [varianteToast, setVarianteToast] = useState<"success" | "danger">("success");

  useEffect(() => {

    const buscarPeriodos = async () => {

      try {

        const response = await axios.get(`${API_URL}/estagiarios/periodos`, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        setPeriodos(response.data);

      } catch(error) {
        console.log(error);
      }

    }

    buscarPeriodos();
  }, []);

  const token = localStorage.getItem('token');
  if(!token) return <Navigate to="/login" />

  const cadastrarEstagiario = async () => {
    try {
      await axios.post(`${API_URL}/estagiarios/`, {
        nome,
        email,
        telefone,
        matricula,
        periodo,
        senha,
      }, {
        headers: {
          Authorization: `Bearer ${token}`
        }
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
      <button className={styles.backButton} onClick={() => navigate(-1)}>
        ← Voltar
      </button>

      <h1 className={styles.title}>Cadastrar Estagiário</h1>

      <form className={styles.form} onSubmit={(e) => { e.preventDefault(); cadastrarEstagiario(); }}>

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
        
        <Input
          value={telefone}
          title="Telefone"
          setValue={setTelefone}
          required={true}
        />

        <Input
          value={matricula}
          title="Matrícula"
          setValue={setMatricula}
          required={true}
        />

        <div className={styles.inputGroup}>
          <label className={styles.label}>Período</label>
          <select
            className={styles.input}
            value={periodo}
            onChange={selecionarPeriodo}
          >
            <option value="" disabled selected></option>
            {periodos.map((option, key) => (
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
