import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { Toast, ToastContainer } from "react-bootstrap";
import styles from "./CadastrarAdvogado.module.css";

const API_URL = process.env.REACT_APP_API;

export default function CadastrarAdvogado() {

  const [nome, setNome] = useState("Júlio Silva");
  const [email, setEmail] = useState("julio@gmail.com");
  const [cpf, setCpf] = useState("23423423234");
  const [telefone, setTelefone] = useState("8893545325");
  const [dataDeNascimento, setDataDeNascimento] = useState("");
  const [cidade, setCidade] = useState("Belo Horizonte");
  const [bairro, setBairro] = useState("Monte Castelo");
  const [rua, setRua] = useState("Rua das Goiabadas");
  const [numeroDaCasa, setNumeroDaCasa] = useState("12");
  const [cep, setCep] = useState("120454122");

  const [mostrarToast, setMostrarToast] = useState(false);
  const [mensagemToast, setMensagemToast] = useState("");
  const [varianteToast, setVarianteToast] = useState<"success" | "danger">("success");

  const navigate = useNavigate();

  const cadastrarAssistido = async (e:any) => {
    e.preventDefault();

    try {
      await axios.post(`${API_URL}/advogados/`, {
        nome,
        email,
        cpf,
        telefone,
        dataDeNascimento,
        cidade,
        bairro,
        rua,
        numeroDaCasa: parseInt(numeroDaCasa),
        cep,
      });

      setMostrarToast(true);
      setMensagemToast("Aassistido cadastrado com sucesso.");
      setVarianteToast("success");

      limparCampos();
    } catch (error) {
      console.error(error);

      setMostrarToast(true);
      setMensagemToast("Falha ao Cadastrar Assistido");
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
    setCpf("");
    setTelefone("");
    setDataDeNascimento("");
    setCidade("");
    setBairro("");
    setRua("");
    setNumeroDaCasa("");
    setCep("");
  };

  return (

    <form className={styles.container} onSubmit={cadastrarAssistido}>
      <button className={styles.backButton} onClick={() => navigate("/cadastrar")}>
        ← Voltar
      </button>
      <h1 className={styles.title}>Cadastrar Advogado</h1>

      <div className={styles.inputGroup}>
        <label className={styles.label}>Nome Completo</label>
        <input
          className={styles.input}
          placeholder="Nome Completo"
          value={nome}
          onChange={(e) => setNome(e.target.value)}
          required
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
          required
        />
      </div>

      <div className={styles.inputGroup}>
        <label className={styles.label}>CPF</label>
        <input
          className={styles.input}
          placeholder="Cpf"
          value={cpf}
          onChange={(e) => setCpf(e.target.value)}
          required
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
          required
        />
      </div>

      <div className={styles.inputGroup}>
        <label className={styles.label}>Data de Nascimento</label>
        <input
          className={styles.input}
          placeholder="DD/MM/AAAA"
          value={dataDeNascimento}
          onChange={(e) => formatarData(e.target.value)}
          required
        />
      </div>

      <div className={styles.inputRow}>
        <div className={styles.inputGroup}>
          <label className={styles.label}>Cidade</label>
          <input
            className={styles.input}
            placeholder="Cidade"
            value={cidade}
            onChange={(e) => setCidade(e.target.value)}
            required
          />
        </div>

        <div className={styles.inputGroup}>
          <label className={styles.label}>Bairro</label>
          <input
            className={styles.input}
            placeholder="Bairro"
            value={bairro}
            onChange={(e) => setBairro(e.target.value)}
            required
          />
        </div>
      </div>


      <div className={styles.inputGroup}>
        <label className={styles.label}>Rua</label>
        <input
          className={styles.input}
          placeholder="Rua"
          value={rua}
          onChange={(e) => setRua(e.target.value)}
          required
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
          required
        />
      </div>

      <div className={styles.inputGroup}>
        <label className={styles.label}>CEP</label>
        <input
          className={styles.input}
          placeholder="CEP"
          value={cep}
          onChange={(e) => setCep(e.target.value)}
          required
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
