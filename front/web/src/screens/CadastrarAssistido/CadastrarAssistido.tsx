import React, { useEffect, useState } from "react";
import { useNavigate, Navigate } from "react-router-dom";
import axios from "axios";
import { Toast, ToastContainer } from "react-bootstrap"
import styles from "./CadastrarAssistido.module.css";
import Input from "../../components/Input/Input";

const API_URL = process.env.REACT_APP_API;

export default function CadastrarAssistido() {

  const [nome, setNome] = useState("");
  const [matricula, setMatricula] = useState("");
  const [telefone, setTelefone] = useState("");
  const [email, setEmail] = useState("");
  const [profissao, setProfissao] = useState("");
  const [nacionalidade, setNacionalidade] = useState("brasileiro");
  const [naturalidade, setNaturalidade] = useState("");
  const [estadosCivis, setEstadosCivis] = useState<string[]>([]);
  const [estadoCivil, setEstadoCivil] = useState("");
  const [cidade, setCidade] = useState("");
  const [bairro, setBairro] = useState("");
  const [rua, setRua] = useState("");
  const [numeroDaCasa, setNumeroDaCasa] = useState("");
  const [cep, setCep] = useState("");

  const [mostrarToast, setMostrarToast] = useState(false);
  const [mensagemToast, setMensagemToast] = useState("");
  const [varianteToast, setVarianteToast] = useState<"success" | "danger">("success");

  const navigate = useNavigate();

  useEffect(() => {

    const buscarEstadosCivis = async () => {

      try {

        const response = await axios.get(`${API_URL}/assistidos/estadosCivis`, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        setEstadosCivis(response.data);

      } catch(error) {
        console.log(error);
      }

    }

    buscarEstadosCivis();
  }, []);

  const token = localStorage.getItem('token');
  if(!token) return <Navigate to="/login" />


  const cadastrarAssistido = async (e:any) => {
    e.preventDefault();
    
    try {
      await axios.post(`${API_URL}/assistidos/`, {
        nome,
        matricula,
        telefone,
        email,
        profissao,
        nacionalidade,
        naturalidade,
        estadoCivil,
        cidade,
        bairro,
        rua,
        numeroDaCasa: parseInt(numeroDaCasa),
        cep,
      }, {
        headers: {
          Authorization: `Bearer ${token}`
        }
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

  const selecionarEstadoCivil = async (e:any) => {
    setEstadoCivil(e.target.value)
  }

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
    setProfissao("");
    setNacionalidade("");
    setNaturalidade("");
    setEstadoCivil("");
  };

  return (
    <form className={styles.container} onSubmit={cadastrarAssistido}>
      <button className={styles.backButton} onClick={() => navigate(-1)}>
        ← Voltar
      </button>
      <h1 className={styles.title}>Cadastrar Assistido</h1>

        <Input
          value={nome}
          title="Nome Completo"
          setValue={setNome}
          required={true}
        />

        <Input
          value={matricula}
          title="Matrícula"
          setValue={setMatricula}
          required={false}
        />

        <Input
          value={telefone}
          title="Telefone"
          setValue={setTelefone}
          required={false}
        />

        <Input
          value={email}
          title="E-mail"
          setValue={setEmail}
          type="email"
          required={false}
        />
        
        <Input
          value={profissao}
          title="Profissão"
          setValue={setProfissao}
          required={false}
        />

        <Input
          value={nacionalidade}
          title="Nacionalidade"
          setValue={setNacionalidade}
          required={false}
        />

        <Input
          value={naturalidade}
          title="Naturalidade"
          setValue={setNaturalidade}
          required={false}
        />

        <div className={styles.inputGroup}>
          <label className={styles.label}>Estado Civil</label>
          <select className={styles.input} onChange={selecionarEstadoCivil}>
            <option value="" disabled selected></option>
            {estadosCivis.map((option, key) => (
              <option key={key} value={option}>{option}</option>
            ))}
          </select>
        </div>

        <Input
          value={cidade}
          title="Cidade"
          setValue={setCidade}
          required={false}
        />
        
        <Input
          value={bairro}
          title="Bairro"
          setValue={setBairro}
          required={false}
        />

        <Input
          value={rua}
          title="Rua"
          setValue={setRua}
          required={false}
        />

        <Input
          value={numeroDaCasa}
          title="Número da Casa"
          setValue={setNumeroDaCasa}
          type="number"
          required={false}
        />

        <Input
          value={cep}
          title="CEP"
          setValue={setCep}
          required={false}
        />

        <button type="submit" className={styles.button}>
          Cadastrar
        </button>
        <ToastContainer position="top-end" className="p-3" style={{ zIndex: 9999 }}>
          <Toast
            show={mostrarToast}
            onClose={() => setMostrarToast(false)}
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
