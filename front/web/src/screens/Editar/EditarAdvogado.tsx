import React, { useEffect, useState } from "react";
import { useNavigate, Navigate, useParams } from "react-router-dom";
import axios from "axios";
import { Toast, ToastContainer } from "react-bootstrap";
import styles from "./EditarAdvogado.module.css";
import { scrollToTop } from './../../utils/Utils';

const API_URL = process.env.REACT_APP_API;

export default function EditarAdvogado() {

  const [nome, setNome] = useState("");
  const [email, setEmail] = useState("");
  const [telefone, setTelefone] = useState("");
  const [dataDeNascimento, setDataDeNascimento] = useState("");
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
  const advogadoId = params.advogadoId || '';

  useEffect(() => {

    const token = localStorage.getItem('token');

    const fecthAdvogado = async () => {
      try {
          const response = await axios.get(`${API_URL}/advogados/${advogadoId}`, {
            headers: {
              Authorization: `Bearer ${token}`
            }
          });

          const dados = response.data;
          setNome(dados.nome);
          setEmail(dados.email);
          setTelefone(dados.telefone);
          setDataDeNascimento(dados.dataDeNascimento);
          setCidade(dados.cidade);
          setBairro(dados.bairro);
          setRua(dados.rua);
          setNumeroDaCasa(dados.numeroDaCasa);
          setCep(dados.cep);
      } catch(error) {
        console.log(error)
      }
    }

    fecthAdvogado();
  }, []);

  const atualizarAdvogado = async (e:any) => {
    e.preventDefault();

    try {
      await axios.put(`${API_URL}/advogados/${advogadoId}`, {
        nome,
        email,
        telefone,
        dataDeNascimento,
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

      scrollToTop();

      setMostrarToast(true);
      setMensagemToast("Advogado atualizado com sucesso.");
      setVarianteToast("success");
    } catch (error) {
      console.error(error);

      setMostrarToast(true);
      setMensagemToast("Falha ao atualizar advogado");
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

  const token = localStorage.getItem('token');
  if(!token) return <Navigate to="/login" />

  return (

    <form className={styles.container} onSubmit={atualizarAdvogado}>
      <button className={styles.backButton} onClick={() => navigate('/usuarios')}>
        ← Voltar
      </button>
      <h1 className={styles.title}>Atualizar Advogado</h1>

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
        Atualizar
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
