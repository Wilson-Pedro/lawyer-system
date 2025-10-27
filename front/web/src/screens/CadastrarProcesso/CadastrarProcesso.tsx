import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import styles from "./CadastrarProcesso.module.css";
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

export default function CadastrarProcesso() {
  const navigate = useNavigate();

  const [numeroDoProcessoPje, setNumeroDoProcessoPje] = useState("");
  const [assunto, setAssunto] = useState("");
  const [vara, setVara] = useState("");
  const [responsavel, setResponsavel] = useState("");
  const [areaDoDireito, setAreaDeDireito] = useState("");
  const [tribunal, setTribunal] = useState("");
  const [prazo, setPrazo] = useState("");

  const [messageDataError, setMessageDataError] = useState("");

  const [nomeAssistido, setNomeAssistido] = useState("");
  const [nomeAssistidoSearch, setNomeAssistidoSearch] = useState("");
  const [assistidoId, setAssistidoId] = useState<number>(0);
  const [assistidos, setAssistidos] = useState<Assistido[]>([]);

  const [nomeAdvogado, setNomeAdvogado] = useState("");
  const [nomeAdvogadoSearch, setNomeAdvogadoSearch] = useState("");
  const [advogadoId, setAdvogadoId] = useState<number>(0);
  const [advogados, setAdvogados] = useState<Advogado[]>([]);

  const [nomeEstagiario, setNomeEstagiario] = useState("");
  const [nomeEstagiarioSearch, setNomeEstagiarioSearch] = useState("");
  const [estagiarioId, setEstagiarioId] = useState<number>(0);
  const [estagiarios, setEstagiarios] = useState<Estagiario[]>([]);

  const [mostrarToast, setMostrarToast] = useState(false);
  const [mensagemToast, setMensagemToast] = useState("");
  const [varianteToast, setVarianteToast] = useState<"success" | "danger">("success");
  
  const page = 0;
  const size = 20;


  useEffect(() => {
    const buscarAssistido = async () => {
      if (nomeAssistidoSearch.length < 2) {
        setAssistidos([]);
        return;
      }
      try {
        const response = await axios.get(`${API_URL}/assistidos/buscar/${nomeAssistidoSearch}?page=${page}&size=${size}`);
        const pageData: Page<Assistido> = response.data;
        setAssistidos(pageData.content);

        limparCampos();
      } catch (error) {
        setMensagemToast("Error ao buscar assistidos");
      }

    };

    const delay = setTimeout(buscarAssistido, 100);

    return () => clearTimeout(delay);
  }, [nomeAssistidoSearch]);

  useEffect(() => {
    const buscarAdvogado = async () => {
      if (nomeAdvogadoSearch.length < 2) {
        setAdvogados([]);
        return;
      }
      try {
        const response = await axios.get(`${API_URL}/advogados/buscar/${nomeAdvogadoSearch}?page=${page}&size=${size}`);
        const pageData: Page<Advogado> = response.data;
        setAdvogados(pageData.content);
      } catch (error) {
        setMensagemToast("Error ao tentar buscar advogados");
      }

    };

    const delay = setTimeout(buscarAdvogado, 100);

    return () => clearTimeout(delay);
  }, [nomeAdvogadoSearch]);

  useEffect(() => {
    const buscarEstagiario = async () => {
      if(nomeEstagiarioSearch.length < 2) {
        setEstagiarios([]);
        return;
      }
      try {
        const response = await axios.get(`${API_URL}/estagiarios/buscar/${nomeEstagiarioSearch}?page=${page}&size=${size}`);
        const pageData: Page<Estagiario> = response.data;
        setEstagiarios(pageData.content);

      } catch(error) {
        console.log("Error ao tentar buscar estagiarios ", error)
      }
    };

    const delay = setTimeout(buscarEstagiario, 100);

    return () => clearTimeout(delay);
  }, [nomeEstagiarioSearch]);


  const cadastrarProcesso = async () => {

    try {
      await axios.post(`${API_URL}/processos/`, {
        assistidoId,
        numeroDoProcessoPje,
        assunto,
        vara,
        responsavel,
        advogadoId,
        estagiarioId,
        areaDoDireito,
        tribunal,
        prazo,
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

    if (numeros.length === 8) {
      const dia = parseInt(numeros.substring(0, 2));
      const mes = parseInt(numeros.substring(2, 4));
      const ano = parseInt(numeros.substring(4, 8));

      const dataDigitada = new Date(ano, mes - 1, dia);
      const hoje = new Date();

      if (dataDigitada.getTime() < hoje.getTime()) {
        setMessageDataError("*Data inválida");
      } else {
        setMessageDataError("");
      }
    }

    setPrazo(formatado);
  }

  const setAssistido = (assistido: Assistido) => {
    setNomeAssistido(assistido.nome);
    setAssistidoId(assistido.id);
    setNomeAssistidoSearch("");
  }

  const setAdvogado = (advogado: Advogado) => {
    setNomeAdvogado(advogado.nome);
    setAdvogadoId(advogado.id);
    setNomeAdvogadoSearch("");
  }

  const setEstagiario = (estagiario: Estagiario) => {
    setNomeEstagiario(estagiario.nome);
    setEstagiarioId(estagiario.id);
    setNomeEstagiarioSearch("");
  }

  const selecionarAreaDoDireito = async (e:any) => {
    setAreaDeDireito(e.target.value);
  }

  const selecionarTribunal = async (e:any) => {
    setTribunal(e.target.value);
  }

  const limparCampos = () => {
    setAssunto("");
    setVara("");
    setResponsavel("");
    setPrazo("");
    setAreaDeDireito("");
    setTribunal("");
    setNumeroDoProcessoPje("");
  };

  return (

    <div className={styles.container}>
      <button className={styles.backButton} onClick={() => navigate("/admin")}>
        ← Voltar
      </button>

      <h1 className={styles.title}>Cadastro de Processo</h1>
      <form className={styles.form} onSubmit={cadastrarProcesso}>
        <div className={styles.inputGroup}>
          <label className={styles.label}>Assistido</label>
          <input
            className={styles.input}
            placeholder="Digite o nome do assistido"
            value={nomeAssistidoSearch || nomeAssistido}
            onChange={(e) => setNomeAssistidoSearch(e.target.value)}
            required
          />
          {assistidos.length > 0 && (
            <ul className={styles.ul}>
              {assistidos.map((data) => (
                <li
                  className={styles.li}
                  key={data.id}
                  onClick={() => setAssistido(data)}
                >{data.nome}</li>
              ))}
            </ul>
          )}
        </div>

        <div className={styles.inputGroup}>
          <label className={styles.label}>Assunto</label>
          <input
            className={styles.input}
            placeholder="Assunto"
            value={assunto}
            onChange={(e) => setAssunto(e.target.value)}
            required
          />
        </div>

        <div className={styles.inputGroup}>
          <label className={styles.label}>Vara</label>
          <input
            className={styles.input}
            placeholder="Vara"
            value={vara}
            onChange={(e) => setVara(e.target.value)}
            required
          />
        </div>

        <div className={styles.inputGroup}>
          <label className={styles.label}>Nº do processo do PJE</label>
          <input
            className={styles.input}
            placeholder="Nº do processo do PJE"
            value={numeroDoProcessoPje}
            onChange={(e) => setNumeroDoProcessoPje(e.target.value)}
          />
        </div>

        <div className={styles.inputGroup}>
          <label className={styles.label}>Responsável</label>
          <input
            className={styles.input}
            placeholder="Responsável"
            value={responsavel}
            onChange={(e) => setResponsavel(e.target.value)}
            required
          />
        </div>

        <div className={styles.inputGroup}>
          <label className={styles.label}>Advogado</label>
          <input
            className={styles.input}
            placeholder="Digite o nome do advogado"
            value={nomeAdvogadoSearch || nomeAdvogado}
            onChange={(e) => setNomeAdvogadoSearch(e.target.value)}
            required
          />
          {advogados.length > 0 && (
            <ul className={styles.ul}>
              {advogados.map((data) => (
                <li
                  className={styles.li}
                  key={data.id}
                  onClick={() => setAdvogado(data)}
                >{data.nome}</li>
              ))}
            </ul>
          )}
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
          <label className={styles.label}>Área de Direito</label>
          <select className={styles.input} onChange={selecionarAreaDoDireito} required>
            <option value="" disabled selected></option>
            <option value="Civil">Civil</option>
            <option value="Trabalho">Trabalho</option>
            <option value="Previdenciário">Previdenciário</option>
          </select>
        </div>

        <div >
          <label className={styles.label}>Tribunal</label>
          <select className={styles.input} onChange={selecionarTribunal} required>
            <option value="" disabled selected></option>
            <option value="Estadual">Estadual</option>
            <option value="Federal">Federal</option>
            <option value="Trabalho">Trabalho</option>
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
          Cadastrar Processo
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
