import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate, Navigate } from "react-router-dom";
import styles from "./CadastrarProcesso.module.css";
import { Toast, ToastContainer } from "react-bootstrap";

import Input from "../../components/Input/Input";

import { scrollToTop } from "./../../utils/Utils";

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
  const [areasDoDireito, setAreasDeDireito] = useState<string[]>([]);

  const [tribunal, setTribunal] = useState("");
  const [tribunais, setTribunais] = useState<string[]>([]);
  
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

  const [selected, setSelected] = useState<boolean>(true);
  
  const page = 0;
  const size = 20;

  useEffect(() => {

    const buscarAreasDoDireito = async () => {

      try {

        const response = await axios.get(`${API_URL}/processos/areasDoDireito`, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        setAreasDeDireito(response.data);

      } catch(error) {
        console.log(error);
      }

    }

    const buscarTribunais = async () => {

      try {

        const response = await axios.get(`${API_URL}/processos/tribunais`, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        setTribunais(response.data);

      } catch(error) {
        console.log(error);
      }

    }

    buscarAreasDoDireito();
    buscarTribunais();
  }, []);


  useEffect(() => {
    const buscarAssistido = async () => {
      if (nomeAssistidoSearch.length < 2) {
        setAssistidos([]);
        return;
      }
      try {
        const response = await axios.get(`${API_URL}/assistidos/buscar/${nomeAssistidoSearch}?page=${page}&size=${size}`, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
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
        // if(nomeAdvogadoSearch.length <= 1 && advogados.length === 0) {
        //   setNomeAdvogadoSearch("");
        //   setNomeAdvogado("");
        // }
        return;
      }
      try {
        const response = await axios.get(`${API_URL}/advogados/buscar/${nomeAdvogadoSearch}?page=${page}&size=${size}`, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
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


  const cadastrarProcesso = async (e:any) => {
    e.preventDefault();

    try {
      await axios.post(`${API_URL}/processos/`, {
        assistidoId,
        numeroDoProcessoPje: numeroDoProcessoPje || "",
        assunto,
        vara,
        responsavel,
        advogadoId,
        estagiarioId,
        areaDoDireito,
        tribunal,
        prazo,
      }, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      
      setMostrarToast(true);
      setMensagemToast("Processo cadastrado com sucesso.");
      setVarianteToast("success");

      limparCampos();

      scrollToTop();
    } catch (error) {
      console.error(error);

      setMostrarToast(true);
      setMensagemToast("Falha ao Cadastrar Processo");
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

  const setAdvogadoNome = (nome: string) =>  {
    setNomeAdvogado(nome);
    setNomeAdvogadoSearch(nome);
    if(nome.length === 0) {
      setAdvogados([]);
    }
  }

  const setEstagiarioNome = (nome: string) =>  {
    setNomeEstagiario(nome);
    setNomeEstagiarioSearch(nome);
    if(nome.length === 0) {
      setEstagiarios([]);
    }
  }

  const setAssistido = (assistido: Assistido) => {
    setNomeAssistido(assistido.nome);
    setAssistidoId(assistido.id);
    setNomeAssistidoSearch("");
  }

  const setAdvogado = (advogado: Advogado) => {
    setNomeAdvogado(advogado.nome);
    setAdvogadoId(advogado.id);
    setResponsavel(advogado.nome);
    setNomeAdvogadoSearch("");
  }

  const setEstagiario = (estagiario: Estagiario) => {
    setNomeEstagiario(estagiario.nome);
    setEstagiarioId(estagiario.id);
    setNomeEstagiarioSearch("");
  }

  const selecionarAreaDoDireito = async (e:any) => {
    setSelected(false);
    setAreaDeDireito(e.target.value);
  }

  const selecionarTribunal = async (e:any) => {
    setSelected(false);
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
    setNomeAssistido("");
    setNomeAdvogado("");
    setNomeEstagiario("")
    setSelected(true)
  };

  const token = localStorage.getItem('token');
  if(!token) return <Navigate to="/login" />

  return (

    <div className={styles.container}>
      <button className={styles.backButton} onClick={() => navigate("/cadastrar")}>
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

        <Input
          value={assunto}
          title="Assunto"
          setValue={setAssunto}
          required={true}
        />

        <Input
          value={vara}
          title="Vara"
          setValue={setVara}
          required={true}
        />


        <Input
          value={numeroDoProcessoPje}
          title="Nº do processo do PJE"
          setValue={setNumeroDoProcessoPje}
        />

        <div className={styles.inputGroup}>
          <label className={styles.label}>Advogado</label>
          <input
            className={styles.input}
            placeholder="Digite o nome do advogado"
            value={nomeAdvogadoSearch || nomeAdvogado}
            onChange={(e) => setAdvogadoNome(e.target.value)}
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
            onChange={(e) => setEstagiarioNome(e.target.value)}
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

        <Input
          value={responsavel}
          title="Responsável"
          setValue={setResponsavel}
          required={true}
        />

        <div className={styles.inputGroup}>
          <label className={styles.label}>Área de Direito</label>
          <select className={styles.input} onChange={selecionarAreaDoDireito} required>
            <option value="" disabled selected={selected}></option>
            {areasDoDireito.map((option, key) => (
              <option key={key} value={option}>{option}</option>
            ))}
          </select>
        </div>

        <div className={styles.inputGroup}>
          <label className={styles.label}>Tribunal</label>
          <select className={styles.input} onChange={selecionarTribunal} required>
            <option value="" disabled selected={selected}></option>
            {tribunais.map((option, key) => (
              <option key={key} value={option}>{option}</option>
            ))}
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


