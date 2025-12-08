import React, { useState, useEffect } from "react";
import { useNavigate, Navigate, useParams } from "react-router-dom";
import axios from "axios";
import { Toast, ToastContainer } from "react-bootstrap"
import styles from "./EditarAssistido.module.css";

const API_URL = process.env.REACT_APP_API;

export default function EditarAssistido() {

  const [nome, setNome] = useState("");
  const [matricula, setMatricula] = useState("");
  const [telefone, setTelefone] = useState("");
  const [email, setEmail] = useState("");
  const [profissao, setProfissao] = useState("");
  const [nacionalidade, setNacionalidade] = useState("brasileiro");
  const [naturalidade, setnaturalidade] = useState("");
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
  const params = useParams();
  const assistidoId = params.assistidoId || '';

  useEffect(() => {

    const token = localStorage.getItem('token');

    const fetchAssitido = async () => {
      try {
          const response = await axios.get(`${API_URL}/assistidos/${assistidoId}`, {
            headers: {
              Authorization: `Bearer ${token}`
            }
          });

          const dados = response.data;
          setNome(dados.nome);
          setEmail(dados.email);
          setTelefone(dados.telefone);
          setCidade(dados.cidade);
          setBairro(dados.bairro);
          setRua(dados.rua);
          setNumeroDaCasa(dados.numeroDaCasa);
          setCep(dados.cep);
          setProfissao(dados.profissao);
          setNacionalidade(dados.nacionalidade);
          setnaturalidade(dados.naturalidade);
          setEstadoCivil(dados.setEstadoCivil);
      } catch(error) {
        console.log(error)
      }
    }

    fetchAssitido();
  }, []);

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
    setnaturalidade("");
    setEstadoCivil("");
  };

    const token = localStorage.getItem('token');
  if(!token) return <Navigate to="/login" />

  return (
    <form className={styles.container} onSubmit={cadastrarAssistido}>
      <button className={styles.backButton} onClick={() => navigate('/usuarios')}>
        ← Voltar
      </button>
      <h1 className={styles.title}>Atualizar Assistido</h1>

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
          <label className={styles.label}>Profissão</label>
          <input
            className={styles.input}
            placeholder="Profissão"
            value={profissao}
            onChange={(e) => setProfissao(e.target.value)}
          />
        </div>

        <div className={styles.inputGroup}>
          <label className={styles.label}>Nacionalidade</label>
          <input
            className={styles.input}
            placeholder="Nacionalidade"
            value={nacionalidade}
            onChange={(e) => setNacionalidade(e.target.value)}
          />
        </div>

        <div className={styles.inputGroup}>
          <label className={styles.label}>Naturalidade</label>
          <input
            className={styles.input}
            placeholder="Naturalidade"
            value={naturalidade}
            onChange={(e) => setnaturalidade(e.target.value)}
          />
        </div>

        <div className={styles.inputGroup}>
          <label className={styles.label}>Estado Civil</label>
          <select className={styles.input} onChange={selecionarEstadoCivil}>
            <option value="" disabled selected></option>
            <option value="Solteiro(a)">Solteiro(a)</option>
            <option value="Casado(a)">Casado(a)</option>
            <option value="Divorciado(a)">Divorciado(a)</option>
            <option value="Viuvo(a)">Viuvo(a)</option>
            <option value="Separado Judicialmente">Separado Judicialmente</option>
          </select>
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
          Atualizar
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
