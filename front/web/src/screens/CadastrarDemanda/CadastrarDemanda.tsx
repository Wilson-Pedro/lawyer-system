import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate, Navigate } from "react-router-dom";
import styles from "./CadastrarDemanda.module.css";
import { Toast, ToastContainer } from "react-bootstrap";

const API_URL = process.env.REACT_APP_API;

export interface Page<T> {
  content: T[];
  totalPages: number;
  totalElements: number;
  size: number;
  number: number;
}

export interface Entity {
  id: number;
  nome: string;
}

export interface Assistido extends Entity {
}

export interface Advogado extends Entity {
}

export interface Estagiario extends Entity {
}

export default function CadastrarDemanda() {
  const navigate = useNavigate();

  const [demanda, setDemanda] = useState<string>("");
  const [demandaStatus, setDemandaStatus] = useState<string>("");
  const [prazo, setPrazo] = useState<string>("");

  const [messageDataError, setMessageDataError] = useState<string>("");

  const [nomeEstagiario, setNomeEstagiario] = useState<string>("");
  const [nomeEstagiarioSearch, setNomeEstagiarioSearch] = useState<string>("");
  const [estagiarioId, setEstagiarioId] = useState<number>(0);
  const [estagiarios, setEstagiarios] = useState<Estagiario[]>([]);

  const [mostrarToast, setMostrarToast] = useState(false);
  const [mensagemToast, setMensagemToast] = useState("");
  const [varianteToast, setVarianteToast] = useState<"success" | "danger">("success");
  
  const page = 0;
  const size = 20;

  useEffect(() => {
    const buscarEstagiario = async () => {
      if(nomeEstagiarioSearch.length < 2) {
        setEstagiarios([]);
        return;
      }
      try {
        const response = await axios.get(`${API_URL}/estagiarios/buscar/${nomeEstagiarioSearch}?page=${page}&size=${size}`, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
        const pageData: Page<Estagiario> = response.data;
        setEstagiarios(pageData.content);

      } catch(error) {
        console.log("Error ao tentar buscar estagiarios ", error)
      }
    };

    const delay = setTimeout(buscarEstagiario, 100);

    return () => clearTimeout(delay);
  }, [nomeEstagiarioSearch]);


  const cadastrarDemanda = async (e:any) => {
    e.preventDefault();

    try {
      await axios.post(`${API_URL}/demandas/`, {
            demanda,
            estagiarioId,
            demandaStatus,
            prazo
      }, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      
      setMostrarToast(true);
      setMensagemToast("Demanda cadastrada com sucesso.");
      setVarianteToast("success");

      limparCampos();
    } catch (error) {
      console.error(error);

      setMostrarToast(true);
      setMensagemToast("Falha ao Cadastrar Demanda");
      setVarianteToast("danger");
    }
  };

  const formatarData = (dataValue: string) => {
    let numeros = dataValue.replace(/\D/g, "");

    if (numeros.length === 0) {
      setMessageDataError("");
    }

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

    if (numeros.length === 8) {
      const dia = parseInt(numeros.substring(0, 2));
      const mes = parseInt(numeros.substring(2, 4));
      const ano = parseInt(numeros.substring(4, 8));

      const dataDigitada = new Date(ano, mes - 1, dia);
      const hoje = new Date();

      if (dataDigitada.getTime() < hoje.getTime()) {
        setMessageDataError("*Prazo inválido");
      } else {
        setMessageDataError("");
      }
    }

    setPrazo(formatado);
  }

  const setEstagiario = (estagiario: Estagiario) => {
    setNomeEstagiario(estagiario.nome);
    setEstagiarioId(estagiario.id);
    setNomeEstagiarioSearch("");
  }

  const selecionarDemandaStatus = async (e:any) => {
    setDemandaStatus(e.target.value);
  }

  const limparCampos = () => {
    setPrazo("");
    setNomeEstagiarioSearch("")
    setDemandaStatus("");
  };

  const token = localStorage.getItem('token');
  if(!token) return <Navigate to="/login" />

  return (

    <div className={styles.container}>
      <button className={styles.backButton} onClick={() => navigate("/cadastrar")}>
        ← Voltar
      </button>

      <h1 className={styles.title}>Cadastro de Demanda</h1>
      <form className={styles.form} onSubmit={cadastrarDemanda}>

        <div className={styles.inputGroup}>
            <label className={styles.label}>Demanda</label>
            <input
            className={styles.input}
            placeholder="Demanda"
            value={demanda}
            onChange={(e) => setDemanda(e.target.value)}
            required
            />
        </div>

        <div className={styles.inputGroup}>
          <label className={styles.label}>Estagiário</label>
          <input
            className={styles.input}
            placeholder="Digite o nome do estagiário"
            value={nomeEstagiarioSearch || nomeEstagiario}
            onChange={(e) => setNomeEstagiarioSearch(e.target.value)}
            required
          />
          {estagiarios.length > 0 && (
            <ul className={styles.ul}>
              {estagiarios.map((data) => (
                <li
                  className={styles.li}
                  key={data.id}
                  onClick={() => setEstagiario(data)}
                >{data.nome}</li>
              ))}
            </ul>
          )}
        </div>

        <div className={styles.inputGroup}>
          <label className={styles.label}>Status da demanda</label>
          <select className={styles.input} onChange={selecionarDemandaStatus} required>
            <option value="" disabled selected></option>
            <option value="Atendido">Atendido</option>
            <option value="Não Atendido">Não Atendido</option>
            <option value="Prorrogada">Prorrogada</option>
          </select>
        </div>

        <div className={styles.inputGroup}>
          <label className={styles.label}>Prazo <span className={styles.messageError}>{messageDataError}</span></label>
          <input
            className={styles.input}
            placeholder="Prazo (DD/MM/AAAA)"
            value={prazo}
            onChange={(e) => formatarData(e.target.value)}
            required
          />
        </div>

        <button type="submit" className={styles.button}>
          Cadastrar Demanda
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
      
    </div>
  );
}
