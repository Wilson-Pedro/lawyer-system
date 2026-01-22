import React, { useState } from "react";
import { useNavigate, Navigate } from "react-router-dom";
import axios from "axios";
import { Toast, ToastContainer } from "react-bootstrap";
import styles from "./CadastrarAdvogado.module.css";
import Input from "../../components/Input/Input";

const API_URL = process.env.REACT_APP_API;

export default function CadastrarAdvogado() {

  const [nome, setNome] = useState("");
  const [email, setEmail] = useState("");
  //const [cpf, setCpf] = useState("23423423234");
  const [telefone, setTelefone] = useState("");
  const [dataDeNascimento, setDataDeNascimento] = useState("");
  const [cidade, setCidade] = useState("");
  const [bairro, setBairro] = useState("");
  const [rua, setRua] = useState("");
  const [numeroDaCasa, setNumeroDaCasa] = useState("");
  const [cep, setCep] = useState("");
  const [senha, setSenha] = useState("");

  const [mostrarToast, setMostrarToast] = useState(false);
  const [mensagemToast, setMensagemToast] = useState("");
  const [varianteToast, setVarianteToast] = useState<"success" | "danger">("success");

  const navigate = useNavigate();

  const token = localStorage.getItem('token');
  if(!token) return <Navigate to="/login" />

  const cadastrarAdvogado = async (e:any) => {
    e.preventDefault();

    try {
      await axios.post(`${API_URL}/advogados/`, {
        nome,
        email,
        telefone,
        dataDeNascimento,
        cidade,
        bairro,
        rua,
        numeroDaCasa: parseInt(numeroDaCasa),
        cep,
        senha,
      }, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });

      setMostrarToast(true);
      setMensagemToast("Advogado cadastrado com sucesso.");
      setVarianteToast("success");

      limparCampos();
    } catch (error) {
      console.error(error);

      setMostrarToast(true);
      setMensagemToast("Falha ao Cadastrar advogado");
      setVarianteToast("danger");
    }
  };

  const formatarData = (dataValue: string) => {
    let numeros = dataValue.replace(/\D/g, "");

    if (numeros.length > 8) {
      numeros = numeros.substring(0, 8);
    }

    let formatado = numeros;

    if (numeros.length > 2) {
      formatado = numeros.substring(0, 2) + "/" + numeros.substring(2);
    }

    if (numeros.length > 4) {
      formatado = numeros.substring(0, 2) + "/" + numeros.substring(2, 4) + "/" + numeros.substring(4);
    }

    setDataDeNascimento(formatado);
  }

  const limparCampos = () => {
    setNome("");
    setEmail("");
    setTelefone("");
    setDataDeNascimento("");
    setCidade("");
    setBairro("");
    setRua("");
    setNumeroDaCasa("");
    setCep("");
  };

  return (

    <form className={styles.container} onSubmit={cadastrarAdvogado}>
      <button className={styles.backButton} onClick={() => navigate(-1)}>
        ← Voltar
      </button>
      <h1 className={styles.title}>Cadastrar Advogado</h1>

      <Input
        value={nome}
        title="Nome Completo"
        setValue={setNome}
        required={true}
      />

      <Input
        value={email}
        title="E-mail"
        setValue={setEmail}
        type="email"
        required={true}
      />

      <Input
        value={telefone}
        title="Telefone"
        setValue={setTelefone}
        type={"tel"}
        required={true}
      />

      <Input
        title="Data de Nascimento"
        value={dataDeNascimento}
        placeholder={"DD/MM/AAAA"}
        setValue={formatarData}
        required={true}
      />

      <Input
        title="Cidade"
        value={cidade}
        setValue={setCidade}
        required={true}
      />

      <Input
        title="Bairro"
        value={bairro}
        setValue={setBairro}
        required={true}
      />


      <div className={styles.inputGroup}>
        <Input
          title="Rua"
          value={rua}
          setValue={setRua}
          required={true}
        />
      </div>

      <div className={styles.inputGroup}>
        <Input
          title="Número da Casa"
          value={numeroDaCasa}
          setValue={setNumeroDaCasa}
          type="number"
          required={true}
        />
      </div>

      <div className={styles.inputGroup}>
        <Input
          title="Senha"
          value={senha}
          setValue={setSenha}
          required={true}
        />
      </div>

      <button type="submit" className={styles.button}>
        Cadastrar
      </button>
      <ToastContainer position="top-end" className="p-3" style={{ zIndex: 9999 }}>
        <Toast
          onClose={() => setMostrarToast(false)}
          show={mostrarToast}
          bg={varianteToast}
          delay={3000}
          autohide
        >
          <Toast.Body className="text-white fw-semibold">
            {mensagemToast}
          </Toast.Body>
        </Toast>
      </ToastContainer>
    </form>

  );
}
